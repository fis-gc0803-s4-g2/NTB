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
import ntb.entity.Building;
import ntb.entity.Sale;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import ntb.da.exceptions.NonexistentEntityException;
import ntb.da.exceptions.PreexistingEntityException;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Apartment;

/**
 *
 * @author TUNG
 */
public class ApartmentJpaController implements Serializable {

    public ApartmentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Apartment apartment) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (apartment.getSaleList() == null) {
            apartment.setSaleList(new ArrayList<Sale>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Building BId = apartment.getBId();
            if (BId != null) {
                BId = em.getReference(BId.getClass(), BId.getBId());
                apartment.setBId(BId);
            }
            List<Sale> attachedSaleList = new ArrayList<Sale>();
            for (Sale saleListSaleToAttach : apartment.getSaleList()) {
                saleListSaleToAttach = em.getReference(saleListSaleToAttach.getClass(), saleListSaleToAttach.getSAId());
                attachedSaleList.add(saleListSaleToAttach);
            }
            apartment.setSaleList(attachedSaleList);
            em.persist(apartment);
            if (BId != null) {
                BId.getApartmentList().add(apartment);
                BId = em.merge(BId);
            }
            for (Sale saleListSale : apartment.getSaleList()) {
                Apartment oldAPIdOfSaleListSale = saleListSale.getAPId();
                saleListSale.setAPId(apartment);
                saleListSale = em.merge(saleListSale);
                if (oldAPIdOfSaleListSale != null) {
                    oldAPIdOfSaleListSale.getSaleList().remove(saleListSale);
                    oldAPIdOfSaleListSale = em.merge(oldAPIdOfSaleListSale);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findApartment(apartment.getAPId()) != null) {
                throw new PreexistingEntityException("Apartment " + apartment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Apartment apartment) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Apartment persistentApartment = em.find(Apartment.class, apartment.getAPId());
            Building BIdOld = persistentApartment.getBId();
            Building BIdNew = apartment.getBId();
            List<Sale> saleListOld = persistentApartment.getSaleList();
            List<Sale> saleListNew = apartment.getSaleList();
            if (BIdNew != null) {
                BIdNew = em.getReference(BIdNew.getClass(), BIdNew.getBId());
                apartment.setBId(BIdNew);
            }
            List<Sale> attachedSaleListNew = new ArrayList<Sale>();
            for (Sale saleListNewSaleToAttach : saleListNew) {
                saleListNewSaleToAttach = em.getReference(saleListNewSaleToAttach.getClass(), saleListNewSaleToAttach.getSAId());
                attachedSaleListNew.add(saleListNewSaleToAttach);
            }
            saleListNew = attachedSaleListNew;
            apartment.setSaleList(saleListNew);
            apartment = em.merge(apartment);
            if (BIdOld != null && !BIdOld.equals(BIdNew)) {
                BIdOld.getApartmentList().remove(apartment);
                BIdOld = em.merge(BIdOld);
            }
            if (BIdNew != null && !BIdNew.equals(BIdOld)) {
                BIdNew.getApartmentList().add(apartment);
                BIdNew = em.merge(BIdNew);
            }
            for (Sale saleListOldSale : saleListOld) {
                if (!saleListNew.contains(saleListOldSale)) {
                    saleListOldSale.setAPId(null);
                    saleListOldSale = em.merge(saleListOldSale);
                }
            }
            for (Sale saleListNewSale : saleListNew) {
                if (!saleListOld.contains(saleListNewSale)) {
                    Apartment oldAPIdOfSaleListNewSale = saleListNewSale.getAPId();
                    saleListNewSale.setAPId(apartment);
                    saleListNewSale = em.merge(saleListNewSale);
                    if (oldAPIdOfSaleListNewSale != null && !oldAPIdOfSaleListNewSale.equals(apartment)) {
                        oldAPIdOfSaleListNewSale.getSaleList().remove(saleListNewSale);
                        oldAPIdOfSaleListNewSale = em.merge(oldAPIdOfSaleListNewSale);
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
                Integer id = apartment.getAPId();
                if (findApartment(id) == null) {
                    throw new NonexistentEntityException("The apartment with id " + id + " no longer exists.");
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
            Apartment apartment;
            try {
                apartment = em.getReference(Apartment.class, id);
                apartment.getAPId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apartment with id " + id + " no longer exists.", enfe);
            }
            Building BId = apartment.getBId();
            if (BId != null) {
                BId.getApartmentList().remove(apartment);
                BId = em.merge(BId);
            }
            List<Sale> saleList = apartment.getSaleList();
            for (Sale saleListSale : saleList) {
                saleListSale.setAPId(null);
                saleListSale = em.merge(saleListSale);
            }
            em.remove(apartment);
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

    public List<Apartment> findApartmentEntities() {
        return findApartmentEntities(true, -1, -1);
    }

    public List<Apartment> findApartmentEntities(int maxResults, int firstResult) {
        return findApartmentEntities(false, maxResults, firstResult);
    }

    private List<Apartment> findApartmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Apartment.class));
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

    public Apartment findApartment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apartment.class, id);
        } finally {
            em.close();
        }
    }

    public int getApartmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Apartment> rt = cq.from(Apartment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
