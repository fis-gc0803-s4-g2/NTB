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
import ntb.entity.Building;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class EditBuilding {
    @EJB
    private LandManager landManager;
    
    @EJB
    private BuildingManager buildingManager;

    
    private Building building;
    
    private int buildingId;
    private int landId;
    private String buildingName;
    private String buildingType;
    private int floorNumber;
    private int departmentNumber;
    private String startDate;
    private String completionDate;
    private String occupancyDate;
    private String image;
    private String description;
    private String status;
    private String m;
    
    
    public String editBuildingIndex() {
        building = buildingManager.findBuilding(buildingId);
        landId = building.getLId().getLId();
        buildingName = building.getBBuildingName();
        buildingType = building.getBBuildingType();
        floorNumber = building.getBFloorNumber();
        departmentNumber = building.getBDepartmentNumber();
        startDate = building.getBStartDate();
        completionDate = building.getBCompletionDate();
        occupancyDate = building.getBCompletionDate();
        image = building.getBImage();
        description = building.getBDescription();
        status = building.getBStatus();
        return "editBuilding?faces-redirect=true";
    }
    
     public String edit() {
            building.setLId(landManager.findLand(landId));
            building.setBBuildingName(buildingName);
            building.setBBuildingType(buildingType);
            building.setBFloorNumber(floorNumber);
            building.setBDepartmentNumber(departmentNumber);
            building.setBStartDate(startDate);
            building.setBCompletionDate(completionDate);
            building.setBOccupancyDate(occupancyDate);
           // building.setBImage(getFile().getFileName());
            building.setBDescription(description);
            building.setBStatus(status);
            if (buildingManager.editBuilding(building)) {
                buildingName = null;
                buildingType = null;
                floorNumber = 0;
                departmentNumber = 0;
                startDate = null;
                completionDate = null;
                occupancyDate = null;
                //image = null;
                description = null;
                status = null;
                m = null;
                return "buildingManager?faces-redirect=true";
            } else {
                m = "Error";
                return "";
            }
        
     }
    

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(int departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getOccupancyDate() {
        return occupancyDate;
    }

    public void setOccupancyDate(String occupancyDate) {
        this.occupancyDate = occupancyDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
    
    
}
