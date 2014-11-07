/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.da;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import ntb.da.exceptions.NonexistentEntityException;
import ntb.da.exceptions.PreexistingEntityException;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Apartment;
import ntb.entity.Building;
import ntb.entity.Contract;

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
    
    public List<Apartment> getApartmentById(int id){
          TypedQuery<Apartment> query = getEntityManager().createQuery("SELECT a FROM Apartment a where a.bId.bId =:id", Apartment.class);
          query.setParameter("id", id);
        return query.getResultList();
    }


    public void create(Apartment apartment) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (apartment.getContractList() == null) {
            apartment.setContractList(new ArrayList<Contract>());
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
            List<Contract> attachedContractList = new ArrayList<Contract>();
            for (Contract contractListContractToAttach : apartment.getContractList()) {
                contractListContractToAttach = em.getReference(contractListContractToAttach.getClass(), contractListContractToAttach.getSAId());
                attachedContractList.add(contractListContractToAttach);
            }
            apartment.setContractList(attachedContractList);
            em.persist(apartment);
            if (BId != null) {
                BId.getApartmentList().add(apartment);
                BId = em.merge(BId);
            }
            for (Contract contractListContract : apartment.getContractList()) {
                Apartment oldAPIdOfContractListContract = contractListContract.getAPId();
                contractListContract.setAPId(apartment);
                contractListContract = em.merge(contractListContract);
                if (oldAPIdOfContractListContract != null) {
                    oldAPIdOfContractListContract.getContractList().remove(contractListContract);
                    oldAPIdOfContractListContract = em.merge(oldAPIdOfContractListContract);
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
            List<Contract> contractListOld = persistentApartment.getContractList();
            List<Contract> contractListNew = apartment.getContractList();
            if (BIdNew != null) {
                BIdNew = em.getReference(BIdNew.getClass(), BIdNew.getBId());
                apartment.setBId(BIdNew);
            }
            List<Contract> attachedContractListNew = new ArrayList<Contract>();
            for (Contract contractListNewContractToAttach : contractListNew) {
                contractListNewContractToAttach = em.getReference(contractListNewContractToAttach.getClass(), contractListNewContractToAttach.getSAId());
                attachedContractListNew.add(contractListNewContractToAttach);
            }
            contractListNew = attachedContractListNew;
            apartment.setContractList(contractListNew);
            apartment = em.merge(apartment);
            if (BIdOld != null && !BIdOld.equals(BIdNew)) {
                BIdOld.getApartmentList().remove(apartment);
                BIdOld = em.merge(BIdOld);
            }
            if (BIdNew != null && !BIdNew.equals(BIdOld)) {
                BIdNew.getApartmentList().add(apartment);
                BIdNew = em.merge(BIdNew);
            }
            for (Contract contractListOldContract : contractListOld) {
                if (!contractListNew.contains(contractListOldContract)) {
                    contractListOldContract.setAPId(null);
                    contractListOldContract = em.merge(contractListOldContract);
                }
            }
            for (Contract contractListNewContract : contractListNew) {
                if (!contractListOld.contains(contractListNewContract)) {
                    Apartment oldAPIdOfContractListNewContract = contractListNewContract.getAPId();
                    contractListNewContract.setAPId(apartment);
                    contractListNewContract = em.merge(contractListNewContract);
                    if (oldAPIdOfContractListNewContract != null && !oldAPIdOfContractListNewContract.equals(apartment)) {
                        oldAPIdOfContractListNewContract.getContractList().remove(contractListNewContract);
                        oldAPIdOfContractListNewContract = em.merge(oldAPIdOfContractListNewContract);
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
            List<Contract> contractList = apartment.getContractList();
            for (Contract contractListContract : contractList) {
                contractListContract.setAPId(null);
                contractListContract = em.merge(contractListContract);
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
