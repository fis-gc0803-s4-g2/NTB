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
import ntb.da.LandJpaController;
import ntb.entity.Land;

/**
 *
 * @author TUNG
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LandManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceUnit(unitName = "NTB_Stamp_DutyPU")

    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;
    
    private LandJpaController daController;
    
    public LandJpaController getDaController() {
        if (daController == null) {
            daController = new LandJpaController(utx, emf); 

        }
        return daController;
    }
    
    /**
     * 
     * @param status
     * @return list
     */
    public List<Land> searchLandByStatus(String status){
        return getDaController().searchLandByStatus(status);
    }
    
    /**
     *Create a new land
     * @param land
     * @return 
     * 
     */
    public boolean createLand(Land land) {
        try {
            getDaController().create(land);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(LandManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
  
    
    /**
     * edit a land
     * @param land
     * @return 
     */
    public boolean editLand(Land land){
        try {
            getDaController().edit(land);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(LandManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    /**
     * delete a land by Id
     * @param lId
     * @return 
     
     */
    public boolean deleteLand(int lId) {
        try {
            getDaController().destroy(lId);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(LandManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    public Land findLand(int id){
        return getDaController().findLand(id);
    }
}
