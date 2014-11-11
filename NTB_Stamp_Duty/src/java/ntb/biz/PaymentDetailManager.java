/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.biz;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import ntb.da.PaymentDetailJpaController;
import ntb.entity.Customer;
import ntb.entity.PaymentDetail;

/**
 *
 * @author TUNG
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PaymentDetailManager {

     @PersistenceUnit(unitName = "NTB_Stamp_DutyPU")

    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    private PaymentDetailJpaController daController;

    public PaymentDetailJpaController getDaController() {
        if (daController == null) {
            daController = new PaymentDetailJpaController(utx, emf);

        }
        return daController;
    }
   
    
    /**
     *Create a new payment detail
     * @param paymentDetail
     * 
     */
    public boolean createPaymentDetail(PaymentDetail paymentDetail) {
        try {
            getDaController().create(paymentDetail);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PaymentDetailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
