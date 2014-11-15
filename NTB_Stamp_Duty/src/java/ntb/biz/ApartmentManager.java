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
import ntb.da.ApartmentJpaController;
import ntb.da.BuildingJpaController;
import ntb.entity.Apartment;
import ntb.entity.Building;

/**
 *
 * @author TUNG
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ApartmentManager {

    @PersistenceUnit(unitName = "NTB_Stamp_DutyPU")

    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    private ApartmentJpaController daController;

    public ApartmentJpaController getDaController() {
        if (daController == null) {
            daController = new ApartmentJpaController(utx, emf);
        }
        return daController;
    }
    
    public List<Apartment> getApartmentById(int bId,int area){
        return getDaController().getApartmentById(bId,area);
    }
    
     public Apartment findApartment(int id){
       return getDaController().findApartment(id);
   }
     
   public boolean createApartment(Apartment apartment) {
        try {
            getDaController().create(apartment);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ApartmentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
   
   public boolean editApartment(Apartment apartment){
         try {
             getDaController().edit(apartment);
             return true;
         } catch (Exception ex) {
             Logger.getLogger(ApartmentManager.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return false;
       
   }  
}
