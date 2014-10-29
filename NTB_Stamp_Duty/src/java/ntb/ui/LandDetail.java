/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ntb.biz.LandManager;
import ntb.entity.Land;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class LandDetail {
    @EJB
    private LandManager landManager;

    
    
    //private int id;
    
    private Land land;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }
    
//    public void landDetail(){
//        land=landManager.getLandById(id);
//    }
    
    public void landDetailById(int id){
        land = landManager.getLandById(id);
       
    }
    
    
    public void deleteLandById(int id){
        try {
            landManager.deleteLand(id);
        } catch (Exception ex) {
            Logger.getLogger(LandDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
