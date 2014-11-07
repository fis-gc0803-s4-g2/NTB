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
import ntb.entity.Manager;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AdminLoginManager {
    
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
    
    
}