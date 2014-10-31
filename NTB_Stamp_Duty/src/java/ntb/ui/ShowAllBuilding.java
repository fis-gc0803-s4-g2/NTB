/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.ui;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ntb.biz.BuildingManager;
import ntb.entity.Building;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class ShowAllBuilding {
    @EJB
    private BuildingManager buildingManager;
    private List<Building> bl;

    public String buildingIndex(){
        return "buildingManager?faces-redirect=true";
    }
        
    public List<Building> getBl() {
        return buildingManager.getAllBuilding();
    }

    public void setBl(List<Building> bl) {
        this.bl = bl;
    }
    
    public ShowAllBuilding() {
    }    
}
