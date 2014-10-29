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
         if(address == null || address.trim().equals("")){
             notice="Required";
             return "";
         }else{
            try {
                landManager.createLand(land);
                 return "admin?faces-redirect=true";
            } catch (Exception ex) {
                Logger.getLogger(CreateALand.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        return null;
        
    }
    
}
