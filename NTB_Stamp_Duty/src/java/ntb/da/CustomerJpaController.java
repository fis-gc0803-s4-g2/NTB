/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.da;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ntb.entity.Sale;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import ntb.da.exceptions.NonexistentEntityException;
import ntb.da.exceptions.PreexistingEntityException;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Customer;

/**
 *
 * @author TUNG
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (customer.getSaleList() == null) {
            customer.setSaleList(new ArrayList<Sale>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Sale> attachedSaleList = new ArrayList<Sale>();
            for (Sale saleListSaleToAttach : customer.getSaleList()) {
                saleListSaleToAttach = em.getReference(saleListSaleToAttach.getClass(), saleListSaleToAttach.getSAId());
                attachedSaleList.add(saleListSaleToAttach);
            }
            customer.setSaleList(attachedSaleList);
            em.persist(customer);
            for (Sale saleListSale : customer.getSaleList()) {
                Customer oldCIdOfSaleListSale = saleListSale.getCId();
                saleListSale.setCId(customer);
                saleListSale = em.merge(saleListSale);
                if (oldCIdOfSaleListSale != null) {
                    oldCIdOfSaleListSale.getSaleList().remove(saleListSale);
                    oldCIdOfSaleListSale = em.merge(oldCIdOfSaleListSale);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCustomer(customer.getCId()) != null) {
                throw new PreexistingEntityException("Customer " + customer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer persistentCustomer = em.find(Customer.class, customer.getCId());
            List<Sale> saleListOld = persistentCustomer.getSaleList();
            List<Sale> saleListNew = customer.getSaleList();
            List<Sale> attachedSaleListNew = new ArrayList<Sale>();
            for (Sale saleListNewSaleToAttach : saleListNew) {
                saleListNewSaleToAttach = em.getReference(saleListNewSaleToAttach.getClass(), saleListNewSaleToAttach.getSAId());
                attachedSaleListNew.add(saleListNewSaleToAttach);
            }
            saleListNew = attachedSaleListNew;
            customer.setSaleList(saleListNew);
            customer = em.merge(customer);
            for (Sale saleListOldSale : saleListOld) {
                if (!saleListNew.contains(saleListOldSale)) {
                    saleListOldSale.setCId(null);
                    saleListOldSale = em.merge(saleListOldSale);
                }
            }
            for (Sale saleListNewSale : saleListNew) {
                if (!saleListOld.contains(saleListNewSale)) {
                    Customer oldCIdOfSaleListNewSale = saleListNewSale.getCId();
                    saleListNewSale.setCId(customer);
                    saleListNewSale = em.merge(saleListNewSale);
                    if (oldCIdOfSaleListNewSale != null && !oldCIdOfSaleListNewSale.equals(customer)) {
                        oldCIdOfSaleListNewSale.getSaleList().remove(saleListNewSale);
                        oldCIdOfSaleListNewSale = em.merge(oldCIdOfSaleListNewSale);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customer.getCId();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getCId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<Sale> saleList = customer.getSaleList();
            for (Sale saleListSale : saleList) {
                saleListSale.setCId(null);
                saleListSale = em.merge(saleListSale);
            }
            em.remove(customer);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer findCustomer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
