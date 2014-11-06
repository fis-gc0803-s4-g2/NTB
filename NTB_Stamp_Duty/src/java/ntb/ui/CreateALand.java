/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import ntb.biz.LandManager;
import ntb.entity.Land;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class CreateALand {

    @EJB
    private LandManager landManager;

    private String address;
    private String nearBy;
    private String dist;
    private String location;
    private int area;
    private int purchaseCost;
    private int presentCost;
    private String buildingPermissionDate;
    private String status;
    private String notice;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNearBy() {
        return nearBy;
    }

    public void setNearBy(String nearBy) {
        this.nearBy = nearBy;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(int purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public int getPresentCost() {
        return presentCost;
    }

    public void setPresentCost(int presentCost) {
        this.presentCost = presentCost;
    }

    public String getBuildingPermissionDate() {
        return buildingPermissionDate;
    }

    public void setBuildingPermissionDate(String buildingPermissionDate) {
        this.buildingPermissionDate = buildingPermissionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void validateAddress(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Address is required"));
        }
        if (" ".contains(s)) {
            throw new ValidatorException(new FacesMessage("Address is not correct format"));
        }
    }

    public void validateNearBy(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Near by landmark is required"));
        }
    }

    public void validateDist(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("District is required "));
        }
    }

    public void validateLocation(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Location is required "));
        }
    }

    public void validateArea(FacesContext f, UIComponent c, Object obj) {
        Integer s = (Integer) obj;
        if (s == 0) {
            throw new ValidatorException(new FacesMessage("Area is required "));
        } else if (s < 0) {
            throw new ValidatorException(new FacesMessage("Area must be greater than 0 "));
        }
    }

    public void validatePurchaseCost(FacesContext f, UIComponent c, Object obj) {
        Integer s = (Integer) obj;
        if (s == 0) {
            throw new ValidatorException(new FacesMessage("Purchase cost is required "));
        } else if (s < 0) {
            throw new ValidatorException(new FacesMessage("Purchase cost must be greater than 0 "));
        }
    }

    public void validatePresentCost(FacesContext f, UIComponent c, Object obj) {
        Integer s = (Integer) obj;
        if (s == 0) {
            throw new ValidatorException(new FacesMessage("Present cost is required "));
        } else if (s < 0) {
            throw new ValidatorException(new FacesMessage("Present cost must be greater than 0 "));
        }
    }

    public void validatePermissionDate(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Building permission date is required "));
        }
    }
    
     public void validateStatus(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Status is required "));
        }
    }

    public String createALand() {
        Land land = new Land();
        land.setLAddress(address);
        land.setLNearByLandmark(nearBy);
        land.setLDist(dist);
        land.setLLocaltion(location);
        land.setLArea(area);
        land.setLPurchasedCost(purchaseCost);
        land.setLPresentCost(presentCost);
        land.setLBuildingPermissionDate(buildingPermissionDate);
        land.setLStatus(status);

        if (landManager.createLand(land)) {
            address = null;
            nearBy = null;
            dist = null;
            location = null;
            area = 0;
            purchaseCost = 0;
            presentCost = 0;
            buildingPermissionDate = null;
            status = null;
            return "adminHome?faces-redirect=true";
        } else {
            notice = "Error";
            return "addLand?faces-redirect=true";
        }
    }

}
