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
@Table(name = "Land")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Land.findAll", query = "SELECT l FROM Land l"),
    @NamedQuery(name = "Land.findByLId", query = "SELECT l FROM Land l WHERE l.lId = :lId"),
    @NamedQuery(name = "Land.findByLAddress", query = "SELECT l FROM Land l WHERE l.lAddress = :lAddress"),
    @NamedQuery(name = "Land.findByLNearByLandmark", query = "SELECT l FROM Land l WHERE l.lNearByLandmark = :lNearByLandmark"),
    @NamedQuery(name = "Land.findByLDist", query = "SELECT l FROM Land l WHERE l.lDist = :lDist"),
    @NamedQuery(name = "Land.findByLLocaltion", query = "SELECT l FROM Land l WHERE l.lLocaltion = :lLocaltion"),
    @NamedQuery(name = "Land.findByLArea", query = "SELECT l FROM Land l WHERE l.lArea = :lArea"),
    @NamedQuery(name = "Land.findByLPurchasedCost", query = "SELECT l FROM Land l WHERE l.lPurchasedCost = :lPurchasedCost"),
    @NamedQuery(name = "Land.findByLPresentCost", query = "SELECT l FROM Land l WHERE l.lPresentCost = :lPresentCost"),
    @NamedQuery(name = "Land.findByLBuildingPermissionDate", query = "SELECT l FROM Land l WHERE l.lBuildingPermissionDate = :lBuildingPermissionDate"),
    @NamedQuery(name = "Land.findByLStatus", query = "SELECT l FROM Land l WHERE l.lStatus = :lStatus")})
public class Land implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Basic(optional = false)
    // @NotNull
    @Column(name = "LId")
    private Integer lId;
    @Size(max = 500)
    @Column(name = "LAddress")
    private String lAddress;
    @Size(max = 500)
    @Column(name = "LNearByLandmark")
    private String lNearByLandmark;
    @Size(max = 100)
    @Column(name = "LDist")
    private String lDist;
    @Size(max = 100)
    @Column(name = "LLocaltion")
    private String lLocaltion;
    @Column(name = "LArea")
    private Integer lArea;
    @Column(name = "LPurchasedCost")
    private Integer lPurchasedCost;
    @Column(name = "LPresentCost")
    private Integer lPresentCost;
    @Size(max = 50)
    @Column(name = "LBuildingPermissionDate")
    private String lBuildingPermissionDate;
    @Size(max = 100)
    @Column(name = "LStatus")
    private String lStatus;
    @OneToMany(mappedBy = "lId")
    private List<Building> buildingList;

    public Land() {
    }

    public Land(Integer lId) {
        this.lId = lId;
    }

    public Integer getLId() {
        return lId;
    }

    public void setLId(Integer lId) {
        this.lId = lId;
    }

    public String getLAddress() {
        return lAddress;
    }

    public void setLAddress(String lAddress) {
        this.lAddress = lAddress;
    }

    public String getLNearByLandmark() {
        return lNearByLandmark;
    }

    public void setLNearByLandmark(String lNearByLandmark) {
        this.lNearByLandmark = lNearByLandmark;
    }

    public String getLDist() {
        return lDist;
    }

    public void setLDist(String lDist) {
        this.lDist = lDist;
    }

    public String getLLocaltion() {
        return lLocaltion;
    }

    public void setLLocaltion(String lLocaltion) {
        this.lLocaltion = lLocaltion;
    }

    public Integer getLArea() {
        return lArea;
    }

    public void setLArea(Integer lArea) {
        this.lArea = lArea;
    }

    public Integer getLPurchasedCost() {
        return lPurchasedCost;
    }

    public void setLPurchasedCost(Integer lPurchasedCost) {
        this.lPurchasedCost = lPurchasedCost;
    }

    public Integer getLPresentCost() {
        return lPresentCost;
    }

    public void setLPresentCost(Integer lPresentCost) {
        this.lPresentCost = lPresentCost;
    }

    public String getLBuildingPermissionDate() {
        return lBuildingPermissionDate;
    }

    public void setLBuildingPermissionDate(String lBuildingPermissionDate) {
        this.lBuildingPermissionDate = lBuildingPermissionDate;
    }

    public String getLStatus() {
        return lStatus;
    }

    public void setLStatus(String lStatus) {
        this.lStatus = lStatus;
    }

    @XmlTransient
    public List<Building> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<Building> buildingList) {
        this.buildingList = buildingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lId != null ? lId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Land)) {
            return false;
        }
        Land other = (Land) object;
        if ((this.lId == null && other.lId != null) || (this.lId != null && !this.lId.equals(other.lId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.Land[ lId=" + lId + " ]";
    }

}
