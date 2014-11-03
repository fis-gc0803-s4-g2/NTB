/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ntb.biz.BuildingManager;
import ntb.biz.LandManager;
import ntb.entity.Building;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class CreateABuilding {

    @EJB
    private LandManager landManager;

    @EJB
    private BuildingManager buildingManager;

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

    private UploadedFile file;
    
    private String m;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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

    
    
    
    public String buildingIndex() {
        buildingName = null;
        buildingType = null;
        floorNumber = 0;
        departmentNumber = 0;
        startDate = null;
        completionDate = null;
        occupancyDate = null;
        image = null;
        description = null;
        status = null;
        m=null;
        return "addBuilding?faces-redirect=true";
    }

    public String createABuilding() {
         if (
                landId != 0
                && floorNumber != 0 && departmentNumber != 0
                && null != buildingName && !buildingName.trim().isEmpty() && buildingName.length() < 100
                && null != buildingType && !buildingType.trim().isEmpty() && buildingType.length() < 100
                && null != startDate && !startDate.trim().isEmpty() && startDate.length() < 100
                && null != completionDate && !completionDate.trim().isEmpty() && completionDate.length() < 100
                && null != occupancyDate && !occupancyDate.trim().isEmpty() && occupancyDate.length() < 200
                && null != description && !description.trim().isEmpty() && description.length() < 200
                && null != status && !status.trim().isEmpty() && status.length() < 200
                && file.getSize() != 0
                ) {
            if (!"".equals(upload())) {
                m = upload();
                return "ok?faces-redirect=true";
            }
            Building b = new Building();
            b.setLId(landManager.findLand(landId));
            b.setBBuildingName(buildingName);
            b.setBBuildingType(buildingType);
            b.setBFloorNumber(floorNumber);
            b.setBDepartmentNumber(departmentNumber);
            b.setBStartDate(startDate);
            b.setBCompletionDate(completionDate);
            b.setBOccupancyDate(occupancyDate);
            b.setBImage(getFile().getFileName());
            b.setBDescription(description);
            b.setBStatus(status);
            if (buildingManager.createBuilding(b)) {
                buildingName = null;
                buildingType = null;
                floorNumber = 0;
                departmentNumber = 0;
                startDate = null;
                completionDate = null;
                occupancyDate = null;
               // image = null;
                description=null;
                status = null;
                return "buildingManager?faces-redirect=true";
            } else {
                m = "Error";
                return "buildingManager?faces-redirect=true";
            }
        }
        m = "Please fill all the blank";
        return "buildingManager?faces-redirect=true";        
//        Building building = new Building();
//
//        building.setLId(landManager.findLand(landId));
//        building.setBBuildingName(buildingName);
//        building.setBBuildingType(buildingType);
//        building.setBFloorNumber(floorNumber);
//        building.setBDepartmentNumber(departmentNumber);
//        building.setBStartDate(startDate);
//        building.setBCompletionDate(completionDate);
//        building.setBOccupancyDate(occupancyDate);
//        building.setBImage(image);
//        building.setBDescription(description);
//        building.setBStatus(status);
//        if (buildingManager.createBuilding(building)) {
//            buildingName = null;
//            buildingType = null;
//            floorNumber = 0;
//            departmentNumber = 0;
//            startDate = null;
//            completionDate = null;
//            occupancyDate = null;
//            image = null;
//            description = null;
//            status = null;
//            return "adminHome?faces-redirect=true";
//        } else {
//            return "addBuilding?faces-redirect=true";
//        }
//
    }

    public String upload() {
        String extValidate;
        String ext = getFile().getFileName();
        if (ext != null) {
            extValidate = ext.substring(ext.indexOf(".") + 1);
        } else {
            extValidate = "null";
        }
        if (extValidate.equalsIgnoreCase("jpg") || extValidate.equalsIgnoreCase("png")) {
            try {
                String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "resources\\uploads\\" + file.getFileName();
                transferFile(destination, getFile().getFileName(), getFile().getInputstream());
            } catch (IOException ex) {
                Logger.getLogger(CreateABuilding.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Wrong", "Error upload file"));
                return "Error upload file";
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", getFile().getFileName() + "is upladed"));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Wrong_ext", "Only extension .pdf"));
            return "Only extension: img,png";
        }
        return "";
    }

    public void transferFile(String destination, String fileName, InputStream in) {
        OutputStream out = null;
        try {
//            out = new FileOutputStream(new File("C:\\EJB_DEMO\\anntgc00492_ejb_assignment_travelnetwork_2\\build\\web\\resources\\uploads\\" + file.getFileName()));
            out = new FileOutputStream(new File(destination));
            int reader = 0;
            byte[] bytes = new byte[(int) getFile().getSize()];
            while ((reader = in.read(bytes)) != -1) {
                out.write(bytes, 0, reader);
            }
            in.close();
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateABuilding.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateABuilding.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(CreateABuilding.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
