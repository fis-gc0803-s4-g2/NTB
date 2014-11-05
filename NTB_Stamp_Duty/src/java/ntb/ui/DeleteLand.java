/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.ui;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ntb.biz.BuildingManager;
import ntb.biz.LandManager;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class DeleteLand {
   
    @EJB
    private LandManager landManager;
    
    

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public String deleteLand(){
        if(landManager.deleteLand(id)){
            return "";
        }
        return "";
    }
    
}
