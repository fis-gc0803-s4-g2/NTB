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
import ntb.entity.Contract;

/**
 *
 * @author TUNG
 */
public class ContractJpaController implements Serializable {

    public ContractJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contract contract) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (contract.getPaymentDetailList() == null) {
            contract.setPaymentDetailList(new ArrayList<PaymentDetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer CId = contract.getCId();
            if (CId != null) {
                CId = em.getReference(CId.getClass(), CId.getCId());
                contract.setCId(CId);
            }
            Apartment APId = contract.getAPId();
            if (APId != null) {
                APId = em.getReference(APId.getClass(), APId.getAPId());
                contract.setAPId(APId);
            }
            List<PaymentDetail> attachedPaymentDetailList = new ArrayList<PaymentDetail>();
            for (PaymentDetail paymentDetailListPaymentDetailToAttach : contract.getPaymentDetailList()) {
                paymentDetailListPaymentDetailToAttach = em.getReference(paymentDetailListPaymentDetailToAttach.getClass(), paymentDetailListPaymentDetailToAttach.getPDId());
                attachedPaymentDetailList.add(paymentDetailListPaymentDetailToAttach);
            }
            contract.setPaymentDetailList(attachedPaymentDetailList);
            em.persist(contract);
            if (CId != null) {
                CId.getContractList().add(contract);
                CId = em.merge(CId);
            }
            if (APId != null) {
                APId.getContractList().add(contract);
                APId = em.merge(APId);
            }
            for (PaymentDetail paymentDetailListPaymentDetail : contract.getPaymentDetailList()) {
                Contract oldSAIdOfPaymentDetailListPaymentDetail = paymentDetailListPaymentDetail.getSAId();
                paymentDetailListPaymentDetail.setSAId(contract);
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
            if (findContract(contract.getSAId()) != null) {
                throw new PreexistingEntityException("Contract " + contract + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contract contract) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contract persistentContract = em.find(Contract.class, contract.getSAId());
            Customer CIdOld = persistentContract.getCId();
            Customer CIdNew = contract.getCId();
            Apartment APIdOld = persistentContract.getAPId();
            Apartment APIdNew = contract.getAPId();
            List<PaymentDetail> paymentDetailListOld = persistentContract.getPaymentDetailList();
            List<PaymentDetail> paymentDetailListNew = contract.getPaymentDetailList();
            if (CIdNew != null) {
                CIdNew = em.getReference(CIdNew.getClass(), CIdNew.getCId());
                contract.setCId(CIdNew);
            }
            if (APIdNew != null) {
                APIdNew = em.getReference(APIdNew.getClass(), APIdNew.getAPId());
                contract.setAPId(APIdNew);
            }
            List<PaymentDetail> attachedPaymentDetailListNew = new ArrayList<PaymentDetail>();
            for (PaymentDetail paymentDetailListNewPaymentDetailToAttach : paymentDetailListNew) {
                paymentDetailListNewPaymentDetailToAttach = em.getReference(paymentDetailListNewPaymentDetailToAttach.getClass(), paymentDetailListNewPaymentDetailToAttach.getPDId());
                attachedPaymentDetailListNew.add(paymentDetailListNewPaymentDetailToAttach);
            }
            paymentDetailListNew = attachedPaymentDetailListNew;
            contract.setPaymentDetailList(paymentDetailListNew);
            contract = em.merge(contract);
            if (CIdOld != null && !CIdOld.equals(CIdNew)) {
                CIdOld.getContractList().remove(contract);
                CIdOld = em.merge(CIdOld);
            }
            if (CIdNew != null && !CIdNew.equals(CIdOld)) {
                CIdNew.getContractList().add(contract);
                CIdNew = em.merge(CIdNew);
            }
            if (APIdOld != null && !APIdOld.equals(APIdNew)) {
                APIdOld.getContractList().remove(contract);
                APIdOld = em.merge(APIdOld);
            }
            if (APIdNew != null && !APIdNew.equals(APIdOld)) {
                APIdNew.getContractList().add(contract);
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
                    Contract oldSAIdOfPaymentDetailListNewPaymentDetail = paymentDetailListNewPaymentDetail.getSAId();
                    paymentDetailListNewPaymentDetail.setSAId(contract);
                    paymentDetailListNewPaymentDetail = em.merge(paymentDetailListNewPaymentDetail);
                    if (oldSAIdOfPaymentDetailListNewPaymentDetail != null && !oldSAIdOfPaymentDetailListNewPaymentDetail.equals(contract)) {
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
                Integer id = contract.getSAId();
                if (findContract(id) == null) {
                    throw new NonexistentEntityException("The contract with id " + id + " no longer exists.");
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
            Contract contract;
            try {
                contract = em.getReference(Contract.class, id);
                contract.getSAId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contract with id " + id + " no longer exists.", enfe);
            }
            Customer CId = contract.getCId();
            if (CId != null) {
                CId.getContractList().remove(contract);
                CId = em.merge(CId);
            }
            Apartment APId = contract.getAPId();
            if (APId != null) {
                APId.getContractList().remove(contract);
                APId = em.merge(APId);
            }
            List<PaymentDetail> paymentDetailList = contract.getPaymentDetailList();
            for (PaymentDetail paymentDetailListPaymentDetail : paymentDetailList) {
                paymentDetailListPaymentDetail.setSAId(null);
                paymentDetailListPaymentDetail = em.merge(paymentDetailListPaymentDetail);
            }
            em.remove(contract);
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

    public List<Contract> findContractEntities() {
        return findContractEntities(true, -1, -1);
    }

    public List<Contract> findContractEntities(int maxResults, int firstResult) {
        return findContractEntities(false, maxResults, firstResult);
    }

    private List<Contract> findContractEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contract.class));
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

    public Contract findContract(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contract.class, id);
        } finally {
            em.close();
        }
    }

    public int getContractCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contract> rt = cq.from(Contract.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
