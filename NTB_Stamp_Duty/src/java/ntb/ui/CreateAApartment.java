/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.ui;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import ntb.biz.ApartmentManager;
import ntb.biz.BuildingManager;
import ntb.entity.Apartment;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class CreateAApartment {
    @EJB
    private BuildingManager buildingManager;
    
    @EJB
    private ApartmentManager apartmentManager;
    
    

    private int buildingId;
    private int area;
    private int onFloor;
    
    private String m;
    
    public String addApartmentIndex(){
        area=0;
        onFloor=0;
        return "addApartment?faces-redirect=true";
    }
    
     public String createAnApartment() {
            Apartment apartment = new Apartment();
            apartment.setBId(buildingManager.findBuilding(buildingId));
            apartment.setAPOnFloor(onFloor);
            apartment.setAPArea(area);
            if (apartmentManager.createApartment(apartment)) {
                onFloor = 0;
                area = 0;
                m = null;
                return "buildingManager?faces-redirect=true";
            } else {
                m = "Error";
                return "";
            }
       
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getOnFloor() {
        return onFloor;
    }

    public void setOnFloor(int onFloor) {
        this.onFloor = onFloor;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
    
     public void validateArea(FacesContext f, UIComponent c, Object obj) {
        Integer s = (Integer) obj;
        if (s == 0) {
            throw new ValidatorException(new FacesMessage("Area is required "));
        } else if (s < 0) {
            throw new ValidatorException(new FacesMessage("Area must be greater than 0 "));
        }
    }
     
      public void validateOnFloor(FacesContext f, UIComponent c, Object obj) {
        Integer s = (Integer) obj;
        if (s == 0) {
            throw new ValidatorException(new FacesMessage("On floor is required "));
        } else if (s < 0) {
            throw new ValidatorException(new FacesMessage("On floor must be greater than 0 "));
        }
    }
    
     
}
