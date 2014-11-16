/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.biz;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import ntb.da.ManagerJpaController;
import ntb.entity.Manager;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AccountManager {
    
    @PersistenceUnit(unitName = "NTB_Stamp_DutyPU")

    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;
    
    private ManagerJpaController daController;
    
    public ManagerJpaController getDaController() {
        if (daController == null) {
            daController = new ManagerJpaController(utx, emf);

        }
        return daController;
    }
    

    public Manager login(String username, String password){
        return getDaController().managerLogin(username, password);
    }
    
    public List<Manager> getAllAccount(){
        return getDaController().getAllAccount();
    }
    
    /**
     *Create a new account
     * @param manager
     * @return 
     * 
     */
    public boolean createAccount(Manager manager) {
        try {
            getDaController().create(manager);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
  
    
    /**
     * edit a manager
     * @param manager
     * @return 
     */
    public boolean editAccount(Manager manager){
        try {
            getDaController().edit(manager);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    /**
     * delete a account by id
     * @param id
     * @return 
     
     */
    public boolean deleteAccount(int id) {
        try {
            getDaController().destroy(id);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    public Manager findAccount(int id){
        return getDaController().findManager(id);
    }
}
