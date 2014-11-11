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
import ntb.da.CustomerJpaController;
import ntb.entity.Customer;

/**
 *
 * @author TUNG
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CustomerManager {

    @PersistenceUnit(unitName = "NTB_Stamp_DutyPU")

    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    private CustomerJpaController daController;

    public CustomerJpaController getDaController() {
        if (daController == null) {
            daController = new CustomerJpaController(utx, emf);

        }
        return daController;
    }
    
    
     /**
     *Create a new customer
     * @param customer
     * 
     */
    public boolean createCustomer(Customer customer) {
        try {
            getDaController().create(customer);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    /**
     * edit a customer
     * @param customer
     */
    public boolean editCustomer(Customer customer){
        try {
            getDaController().edit(customer);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

   public Customer findCustomer(int id){
       return getDaController().findCustomer(id);
   }
   
   public int count(){
       return getDaController().getCustomerCount();
   }
  
}
