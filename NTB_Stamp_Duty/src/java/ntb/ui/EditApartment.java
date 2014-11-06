/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.ui;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ntb.biz.ApartmentManager;
import ntb.biz.BuildingManager;
import ntb.entity.Apartment;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class EditApartment {
    
    @EJB
    private ApartmentManager apartmentManager;
    
    

    private Apartment apartment;
    private int apartmentId;
    private int bId;
    private int onFloor;
    private int area;

    private String m;
    
     public String editApartmentIndex() {
        apartment=apartmentManager.findApartment(apartmentId);
        onFloor = apartment.getAPOnFloor();
        area = apartment.getAPArea();
        m = null;
        return "editApartment?faces-redirect=true";
    }
    
     public String editAnApartment() {
       
            apartment.setAPOnFloor(onFloor);
            apartment.setAPArea(area);
            if (apartmentManager.editApartment(apartment)) {
                onFloor = 0;
                area = 0;
                m = null;
                return "apartmentManager?faces-redirect=true";
            } else {
                m = "Error";
                return "";
            }
      
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public int getOnFloor() {
        return onFloor;
    }

    public void setOnFloor(int onFloor) {
        this.onFloor = onFloor;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
    
    
    
    
}
