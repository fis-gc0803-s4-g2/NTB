/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TUNG
 */
@Entity
@Table(name = "Building")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Building.findAll", query = "SELECT b FROM Building b"),
    @NamedQuery(name = "Building.findByBId", query = "SELECT b FROM Building b WHERE b.bId = :bId"),
    @NamedQuery(name = "Building.findByBBuildingName", query = "SELECT b FROM Building b WHERE b.bBuildingName = :bBuildingName"),
    @NamedQuery(name = "Building.findByBBuildingType", query = "SELECT b FROM Building b WHERE b.bBuildingType = :bBuildingType"),
    @NamedQuery(name = "Building.findByBFloorNumber", query = "SELECT b FROM Building b WHERE b.bFloorNumber = :bFloorNumber"),
    @NamedQuery(name = "Building.findByBDepartmentNumber", query = "SELECT b FROM Building b WHERE b.bDepartmentNumber = :bDepartmentNumber"),
    @NamedQuery(name = "Building.findByBStartDate", query = "SELECT b FROM Building b WHERE b.bStartDate = :bStartDate"),
    @NamedQuery(name = "Building.findByBCompletionDate", query = "SELECT b FROM Building b WHERE b.bCompletionDate = :bCompletionDate"),
    @NamedQuery(name = "Building.findByBOccupancyDate", query = "SELECT b FROM Building b WHERE b.bOccupancyDate = :bOccupancyDate"),
    @NamedQuery(name = "Building.findByBImage", query = "SELECT b FROM Building b WHERE b.bImage = :bImage"),
    @NamedQuery(name = "Building.findByBDescription", query = "SELECT b FROM Building b WHERE b.bDescription = :bDescription"),
    @NamedQuery(name = "Building.findByBStatus", query = "SELECT b FROM Building b WHERE b.bStatus = :bStatus")})
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BId")
    private Integer bId;
    @Size(max = 200)
    @Column(name = "BBuildingName")
    private String bBuildingName;
    @Size(max = 200)
    @Column(name = "BBuildingType")
    private String bBuildingType;
    @Column(name = "BFloorNumber")
    private Integer bFloorNumber;
    @Column(name = "BDepartmentNumber")
    private Integer bDepartmentNumber;
    @Size(max = 100)
    @Column(name = "BStartDate")
    private String bStartDate;
    @Size(max = 100)
    @Column(name = "BCompletionDate")
    private String bCompletionDate;
    @Size(max = 100)
    @Column(name = "BOccupancyDate")
    private String bOccupancyDate;
    @Size(max = 500)
    @Column(name = "BImage")
    private String bImage;
    @Size(max = 500)
    @Column(name = "BDescription")
    private String bDescription;
    @Size(max = 100)
    @Column(name = "BStatus")
    private String bStatus;
    @OneToMany(mappedBy = "bId")
    private List<Apartment> apartmentList;
    @JoinColumn(name = "LId", referencedColumnName = "LId")
    @ManyToOne
    private Land lId;

    public Building() {
    }

    public Building(Integer bId) {
        this.bId = bId;
    }

    public Integer getBId() {
        return bId;
    }

    public void setBId(Integer bId) {
        this.bId = bId;
    }

    public String getBBuildingName() {
        return bBuildingName;
    }

    public void setBBuildingName(String bBuildingName) {
        this.bBuildingName = bBuildingName;
    }

    public String getBBuildingType() {
        return bBuildingType;
    }

    public void setBBuildingType(String bBuildingType) {
        this.bBuildingType = bBuildingType;
    }

    public Integer getBFloorNumber() {
        return bFloorNumber;
    }

    public void setBFloorNumber(Integer bFloorNumber) {
        this.bFloorNumber = bFloorNumber;
    }

    public Integer getBDepartmentNumber() {
        return bDepartmentNumber;
    }

    public void setBDepartmentNumber(Integer bDepartmentNumber) {
        this.bDepartmentNumber = bDepartmentNumber;
    }

    public String getBStartDate() {
        return bStartDate;
    }

    public void setBStartDate(String bStartDate) {
        this.bStartDate = bStartDate;
    }

    public String getBCompletionDate() {
        return bCompletionDate;
    }

    public void setBCompletionDate(String bCompletionDate) {
        this.bCompletionDate = bCompletionDate;
    }

    public String getBOccupancyDate() {
        return bOccupancyDate;
    }

    public void setBOccupancyDate(String bOccupancyDate) {
        this.bOccupancyDate = bOccupancyDate;
    }

    public String getBImage() {
        return bImage;
    }

    public void setBImage(String bImage) {
        this.bImage = bImage;
    }

    public String getBDescription() {
        return bDescription;
    }

    public void setBDescription(String bDescription) {
        this.bDescription = bDescription;
    }

    public String getBStatus() {
        return bStatus;
    }

    public void setBStatus(String bStatus) {
        this.bStatus = bStatus;
    }

    @XmlTransient
    public List<Apartment> getApartmentList() {
        return apartmentList;
    }

    public void setApartmentList(List<Apartment> apartmentList) {
        this.apartmentList = apartmentList;
    }

    public Land getLId() {
        return lId;
    }

    public void setLId(Land lId) {
        this.lId = lId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bId != null ? bId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Building)) {
            return false;
        }
        Building other = (Building) object;
        if ((this.bId == null && other.bId != null) || (this.bId != null && !this.bId.equals(other.bId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.Building[ bId=" + bId + " ]";
    }

}
