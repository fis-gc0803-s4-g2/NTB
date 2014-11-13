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
import ntb.biz.ApartmentManager;
import ntb.biz.BuildingManager;
import ntb.biz.LandManager;
import ntb.entity.Apartment;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class ShowAllApartment {
    @EJB
    private BuildingManager buildingManager;
    @EJB
    private LandManager landManager;
    
    @EJB
    private ApartmentManager apartmentManager;
    
    

    private List<Apartment> list;
    
    private int key1;
    
    private String buildingName;
    
    public String apartmentIndex(){
        return "apartmentManager?faces-redirect=true";
    }
    
    

    public List<Apartment> getList() {
        list=apartmentManager.getApartmentById(key1);
        updateCost(list);
        buildingName=buildingManager.findBuilding(key1).getBBuildingName();
        return list;
    }

    public void setList(List<Apartment> list) {
        this.list = list;
    }

    public int getKey1() {
        return key1;
    }

    public void setKey1(int key1) {
        this.key1 = key1;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    
    
    
    
    private void updateCost(List<Apartment> al){
        for(Apartment a: al){
            int area=a.getAPArea();
            int presentCost=landManager.findLand(buildingManager.findBuilding(a.getBId().getBId()).getBId()).getLPresentCost();
            int newCost=area*presentCost;
            
            Apartment apartment=apartmentManager.findApartment(a.getAPId());
            apartment.setAPCost(newCost);
            apartmentManager.editApartment(apartment);
        }
    } 
    
    
}
