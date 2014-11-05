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

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class DeleteBuilding {
    @EJB
    private BuildingManager buildingManager;

   
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String deleteBuilding(){
        if(buildingManager.deleteBuilding(id)){
            return "";
        }
        return "";
    }
}
