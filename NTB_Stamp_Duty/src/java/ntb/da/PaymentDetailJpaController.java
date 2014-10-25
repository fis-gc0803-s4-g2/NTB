/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.da;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import ntb.da.exceptions.NonexistentEntityException;
import ntb.da.exceptions.PreexistingEntityException;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.PaymentDetail;
import ntb.entity.Sale;

/**
 *
 * @author TUNG
 */
public class PaymentDetailJpaController implements Serializable {

    public PaymentDetailJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PaymentDetail paymentDetail) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sale SAId = paymentDetail.getSAId();
            if (SAId != null) {
                SAId = em.getReference(SAId.getClass(), SAId.getSAId());
                paymentDetail.setSAId(SAId);
            }
            em.persist(paymentDetail);
            if (SAId != null) {
                SAId.getPaymentDetailList().add(paymentDetail);
                SAId = em.merge(SAId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPaymentDetail(paymentDetail.getPDId()) != null) {
                throw new PreexistingEntityException("PaymentDetail " + paymentDetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PaymentDetail paymentDetail) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PaymentDetail persistentPaymentDetail = em.find(PaymentDetail.class, paymentDetail.getPDId());
            Sale SAIdOld = persistentPaymentDetail.getSAId();
            Sale SAIdNew = paymentDetail.getSAId();
            if (SAIdNew != null) {
                SAIdNew = em.getReference(SAIdNew.getClass(), SAIdNew.getSAId());
                paymentDetail.setSAId(SAIdNew);
            }
            paymentDetail = em.merge(paymentDetail);
            if (SAIdOld != null && !SAIdOld.equals(SAIdNew)) {
                SAIdOld.getPaymentDetailList().remove(paymentDetail);
                SAIdOld = em.merge(SAIdOld);
            }
            if (SAIdNew != null && !SAIdNew.equals(SAIdOld)) {
                SAIdNew.getPaymentDetailList().add(paymentDetail);
                SAIdNew = em.merge(SAIdNew);
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
                Integer id = paymentDetail.getPDId();
                if (findPaymentDetail(id) == null) {
                    throw new NonexistentEntityException("The paymentDetail with id " + id + " no longer exists.");
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
            PaymentDetail paymentDetail;
            try {
                paymentDetail = em.getReference(PaymentDetail.class, id);
                paymentDetail.getPDId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paymentDetail with id " + id + " no longer exists.", enfe);
            }
            Sale SAId = paymentDetail.getSAId();
            if (SAId != null) {
                SAId.getPaymentDetailList().remove(paymentDetail);
                SAId = em.merge(SAId);
            }
            em.remove(paymentDetail);
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

    public List<PaymentDetail> findPaymentDetailEntities() {
        return findPaymentDetailEntities(true, -1, -1);
    }

    public List<PaymentDetail> findPaymentDetailEntities(int maxResults, int firstResult) {
        return findPaymentDetailEntities(false, maxResults, firstResult);
    }

    private List<PaymentDetail> findPaymentDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PaymentDetail.class));
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

    public PaymentDetail findPaymentDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PaymentDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaymentDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PaymentDetail> rt = cq.from(PaymentDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
