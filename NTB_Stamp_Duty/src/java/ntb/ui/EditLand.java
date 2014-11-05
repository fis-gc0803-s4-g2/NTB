/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.ui;


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
public class EditLand {

    @EJB
    private LandManager landManager;

    private int landId;
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

    private Land land;

    public String editLandIndex() {
        land = landManager.getLandById(landId);
        address = land.getLAddress();
        nearBy = land.getLNearByLandmark();
        dist = land.getLDist();
        location = land.getLLocaltion();
        area = land.getLArea();
        purchaseCost = land.getLPurchasedCost();
        presentCost = land.getLPresentCost();
        buildingPermissionDate = land.getLBuildingPermissionDate();
        status = land.getLStatus();
        return "editLand?faces-redirect=true";
    }

    public String editLandById() {

        land.setLAddress(address);
        land.setLNearByLandmark(nearBy);
        land.setLDist(dist);
        land.setLLocaltion(location);
        land.setLArea(area);
        land.setLPurchasedCost(purchaseCost);
        land.setLPresentCost(presentCost);
        land.setLBuildingPermissionDate(buildingPermissionDate);
        land.setLStatus(status);
        if (landManager.editLand(land)) {
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
        }
        return null;

    }

    

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

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

}
