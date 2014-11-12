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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TUNG
 */
@Entity
@Table(name = "Apartment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apartment.findAll", query = "SELECT a FROM Apartment a"),
    @NamedQuery(name = "Apartment.findByAPId", query = "SELECT a FROM Apartment a WHERE a.aPId = :aPId"),
    @NamedQuery(name = "Apartment.findByAPOnFloor", query = "SELECT a FROM Apartment a WHERE a.aPOnFloor = :aPOnFloor"),
    @NamedQuery(name = "Apartment.findByAPArea", query = "SELECT a FROM Apartment a WHERE a.aPArea = :aPArea"),
    @NamedQuery(name = "Apartment.findByAPCost", query = "SELECT a FROM Apartment a WHERE a.aPCost = :aPCost")})
public class Apartment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Basic(optional = false)
    //@NotNull
    @Column(name = "APId")
    private Integer aPId;
    @Column(name = "APOnFloor")
    private Integer aPOnFloor;
    @Column(name = "APArea")
    private Integer aPArea;
    @Column(name = "APCost")
    private Integer aPCost;
    @JoinColumn(name = "BId", referencedColumnName = "BId")
    @ManyToOne
    private Building bId;
    @OneToMany(mappedBy = "aPId")
    private List<Contract> contractList;

    public Apartment() {
    }

    public Apartment(Integer aPId) {
        this.aPId = aPId;
    }

    public Integer getAPId() {
        return aPId;
    }

    public void setAPId(Integer aPId) {
        this.aPId = aPId;
    }

    public Integer getAPOnFloor() {
        return aPOnFloor;
    }

    public void setAPOnFloor(Integer aPOnFloor) {
        this.aPOnFloor = aPOnFloor;
    }

    public Integer getAPArea() {
        return aPArea;
    }

    public void setAPArea(Integer aPArea) {
        this.aPArea = aPArea;
    }

    public Integer getAPCost() {
        return aPCost;
    }

    public void setAPCost(Integer aPCost) {
        this.aPCost = aPCost;
    }

    public Building getBId() {
        return bId;
    }

    public void setBId(Building bId) {
        this.bId = bId;
    }

    @XmlTransient
    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aPId != null ? aPId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apartment)) {
            return false;
        }
        Apartment other = (Apartment) object;
        if ((this.aPId == null && other.aPId != null) || (this.aPId != null && !this.aPId.equals(other.aPId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.Apartment[ aPId=" + aPId + " ]";
    }

}
