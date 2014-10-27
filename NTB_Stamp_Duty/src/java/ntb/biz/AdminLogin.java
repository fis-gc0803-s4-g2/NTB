/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.biz;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import ntb.da.ManagerJpaController;

/**
 *
 * @author Phuong Van
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AdminLogin {
    
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
    

    public boolean login(String username, String password){
        ManagerJpaController mjc = getDaController();
        return mjc.managerLogin(username, password);
    }
    
    public boolean checkAccountExist(String username){
        return getDaController().checkAccountExist(username);
    }
}
