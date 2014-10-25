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
import ntb.entity.Customer;
import ntb.entity.Apartment;
import ntb.entity.PaymentDetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import ntb.da.exceptions.NonexistentEntityException;
import ntb.da.exceptions.PreexistingEntityException;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Sale;

/**
 *
 * @author TUNG
 */
public class SaleJpaController implements Serializable {

    public SaleJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sale sale) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (sale.getPaymentDetailList() == null) {
            sale.setPaymentDetailList(new ArrayList<PaymentDetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer CId = sale.getCId();
            if (CId != null) {
                CId = em.getReference(CId.getClass(), CId.getCId());
                sale.setCId(CId);
            }
            Apartment APId = sale.getAPId();
            if (APId != null) {
                APId = em.getReference(APId.getClass(), APId.getAPId());
                sale.setAPId(APId);
            }
            List<PaymentDetail> attachedPaymentDetailList = new ArrayList<PaymentDetail>();
            for (PaymentDetail paymentDetailListPaymentDetailToAttach : sale.getPaymentDetailList()) {
                paymentDetailListPaymentDetailToAttach = em.getReference(paymentDetailListPaymentDetailToAttach.getClass(), paymentDetailListPaymentDetailToAttach.getPDId());
                attachedPaymentDetailList.add(paymentDetailListPaymentDetailToAttach);
            }
            sale.setPaymentDetailList(attachedPaymentDetailList);
            em.persist(sale);
            if (CId != null) {
                CId.getSaleList().add(sale);
                CId = em.merge(CId);
            }
            if (APId != null) {
                APId.getSaleList().add(sale);
                APId = em.merge(APId);
            }
            for (PaymentDetail paymentDetailListPaymentDetail : sale.getPaymentDetailList()) {
                Sale oldSAIdOfPaymentDetailListPaymentDetail = paymentDetailListPaymentDetail.getSAId();
                paymentDetailListPaymentDetail.setSAId(sale);
                paymentDetailListPaymentDetail = em.merge(paymentDetailListPaymentDetail);
                if (oldSAIdOfPaymentDetailListPaymentDetail != null) {
                    oldSAIdOfPaymentDetailListPaymentDetail.getPaymentDetailList().remove(paymentDetailListPaymentDetail);
                    oldSAIdOfPaymentDetailListPaymentDetail = em.merge(oldSAIdOfPaymentDetailListPaymentDetail);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSale(sale.getSAId()) != null) {
                throw new PreexistingEntityException("Sale " + sale + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sale sale) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sale persistentSale = em.find(Sale.class, sale.getSAId());
            Customer CIdOld = persistentSale.getCId();
            Customer CIdNew = sale.getCId();
            Apartment APIdOld = persistentSale.getAPId();
            Apartment APIdNew = sale.getAPId();
            List<PaymentDetail> paymentDetailListOld = persistentSale.getPaymentDetailList();
            List<PaymentDetail> paymentDetailListNew = sale.getPaymentDetailList();
            if (CIdNew != null) {
                CIdNew = em.getReference(CIdNew.getClass(), CIdNew.getCId());
                sale.setCId(CIdNew);
            }
            if (APIdNew != null) {
                APIdNew = em.getReference(APIdNew.getClass(), APIdNew.getAPId());
                sale.setAPId(APIdNew);
            }
            List<PaymentDetail> attachedPaymentDetailListNew = new ArrayList<PaymentDetail>();
            for (PaymentDetail paymentDetailListNewPaymentDetailToAttach : paymentDetailListNew) {
                paymentDetailListNewPaymentDetailToAttach = em.getReference(paymentDetailListNewPaymentDetailToAttach.getClass(), paymentDetailListNewPaymentDetailToAttach.getPDId());
                attachedPaymentDetailListNew.add(paymentDetailListNewPaymentDetailToAttach);
            }
            paymentDetailListNew = attachedPaymentDetailListNew;
            sale.setPaymentDetailList(paymentDetailListNew);
            sale = em.merge(sale);
            if (CIdOld != null && !CIdOld.equals(CIdNew)) {
                CIdOld.getSaleList().remove(sale);
                CIdOld = em.merge(CIdOld);
            }
            if (CIdNew != null && !CIdNew.equals(CIdOld)) {
                CIdNew.getSaleList().add(sale);
                CIdNew = em.merge(CIdNew);
            }
            if (APIdOld != null && !APIdOld.equals(APIdNew)) {
                APIdOld.getSaleList().remove(sale);
                APIdOld = em.merge(APIdOld);
            }
            if (APIdNew != null && !APIdNew.equals(APIdOld)) {
                APIdNew.getSaleList().add(sale);
                APIdNew = em.merge(APIdNew);
            }
            for (PaymentDetail paymentDetailListOldPaymentDetail : paymentDetailListOld) {
                if (!paymentDetailListNew.contains(paymentDetailListOldPaymentDetail)) {
                    paymentDetailListOldPaymentDetail.setSAId(null);
                    paymentDetailListOldPaymentDetail = em.merge(paymentDetailListOldPaymentDetail);
                }
            }
            for (PaymentDetail paymentDetailListNewPaymentDetail : paymentDetailListNew) {
                if (!paymentDetailListOld.contains(paymentDetailListNewPaymentDetail)) {
                    Sale oldSAIdOfPaymentDetailListNewPaymentDetail = paymentDetailListNewPaymentDetail.getSAId();
                    paymentDetailListNewPaymentDetail.setSAId(sale);
                    paymentDetailListNewPaymentDetail = em.merge(paymentDetailListNewPaymentDetail);
                    if (oldSAIdOfPaymentDetailListNewPaymentDetail != null && !oldSAIdOfPaymentDetailListNewPaymentDetail.equals(sale)) {
                        oldSAIdOfPaymentDetailListNewPaymentDetail.getPaymentDetailList().remove(paymentDetailListNewPaymentDetail);
                        oldSAIdOfPaymentDetailListNewPaymentDetail = em.merge(oldSAIdOfPaymentDetailListNewPaymentDetail);
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
                Integer id = sale.getSAId();
                if (findSale(id) == null) {
                    throw new NonexistentEntityException("The sale with id " + id + " no longer exists.");
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
            Sale sale;
            try {
                sale = em.getReference(Sale.class, id);
                sale.getSAId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sale with id " + id + " no longer exists.", enfe);
            }
            Customer CId = sale.getCId();
            if (CId != null) {
                CId.getSaleList().remove(sale);
                CId = em.merge(CId);
            }
            Apartment APId = sale.getAPId();
            if (APId != null) {
                APId.getSaleList().remove(sale);
                APId = em.merge(APId);
            }
            List<PaymentDetail> paymentDetailList = sale.getPaymentDetailList();
            for (PaymentDetail paymentDetailListPaymentDetail : paymentDetailList) {
                paymentDetailListPaymentDetail.setSAId(null);
                paymentDetailListPaymentDetail = em.merge(paymentDetailListPaymentDetail);
            }
            em.remove(sale);
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

    public List<Sale> findSaleEntities() {
        return findSaleEntities(true, -1, -1);
    }

    public List<Sale> findSaleEntities(int maxResults, int firstResult) {
        return findSaleEntities(false, maxResults, firstResult);
    }

    private List<Sale> findSaleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sale.class));
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

    public Sale findSale(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sale.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sale> rt = cq.from(Sale.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
