/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.da;

import java.io.Serializable;
import java.util.List;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import ntb.da.exceptions.NonexistentEntityException;
import ntb.da.exceptions.PreexistingEntityException;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Manager;
import ntb.validation.CheckValidation;

/**
 *
 * @author TUNG
 */
public class ManagerJpaController implements Serializable {

    public ManagerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean managerLogin(String username, String password) {
        boolean checkValidationResult = CheckValidation.checkLoginValidation(username, password);
        if (checkValidationResult == false) {
            return false;
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            String sqlCommand = "m.mUsername = :mUsername and m.mPassword = :mPassword";
            TypedQuery<Manager> createQuery = em.createQuery(sqlCommand, Manager.class);
            int firstResult = createQuery.getFirstResult();
            if (checkValidationResult) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean checkAccountExist(String username) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            String sqlCommand = "m.mUsername = :mUsername";
            TypedQuery<Manager> createQuery = em.createQuery(sqlCommand, Manager.class);
            createQuery.setParameter("mUsername", username);
            int firstResult = createQuery.getFirstResult();
            if (firstResult > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public void create(Manager manager) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(manager);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findManager(manager.getMId()) != null) {
                throw new PreexistingEntityException("Manager " + manager + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Manager manager) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            manager = em.merge(manager);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = manager.getMId();
                if (findManager(id) == null) {
                    throw new NonexistentEntityException("The manager with id " + id + " no longer exists.");
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
            Manager manager;
            try {
                manager = em.getReference(Manager.class, id);
                manager.getMId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The manager with id " + id + " no longer exists.", enfe);
            }
            em.remove(manager);
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

    public List<Manager> findManagerEntities() {
        return findManagerEntities(true, -1, -1);
    }

    public List<Manager> findManagerEntities(int maxResults, int firstResult) {
        return findManagerEntities(false, maxResults, firstResult);
    }

    private List<Manager> findManagerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Manager.class));
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

    public Manager findManager(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Manager.class, id);
        } finally {
            em.close();
        }
    }

    public int getManagerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Manager> rt = cq.from(Manager.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
