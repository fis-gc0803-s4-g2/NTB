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
import ntb.da.BuildingJpaController;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Building;


/**
 *
 * @author TUNG
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BuildingManager {

     @PersistenceUnit(unitName = "NTB_Stamp_DutyPU")

    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;
    
     private BuildingJpaController daController;
    
    public BuildingJpaController getDaController() {
        if (daController == null) {
            daController = new BuildingJpaController(utx, emf); 
        }
        return daController;
    }
    
    public List<Building> getAllBuilding(){
        return getDaController().getAllBuilding();
    }
    
    public Building find(int id){
        return getDaController().findBuilding(id);
    }
    
   public boolean createBuilding(Building building) {
        try {
            getDaController().create(building);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(LandManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
   
   public boolean editBuilding(Building building){
         try {
             getDaController().edit(building);
             return true;
         } catch (Exception ex) {
             Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return false;
       
   }
   
   public Building findBuilding(int id){
       return getDaController().findBuilding(id);
   }
}
