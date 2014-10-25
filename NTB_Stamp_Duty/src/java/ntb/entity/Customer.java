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
@Table(name = "Customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCId", query = "SELECT c FROM Customer c WHERE c.cId = :cId"),
    @NamedQuery(name = "Customer.findByCUsername", query = "SELECT c FROM Customer c WHERE c.cUsername = :cUsername"),
    @NamedQuery(name = "Customer.findByCPassword", query = "SELECT c FROM Customer c WHERE c.cPassword = :cPassword"),
    @NamedQuery(name = "Customer.findByCFullName", query = "SELECT c FROM Customer c WHERE c.cFullName = :cFullName"),
    @NamedQuery(name = "Customer.findByCAddress", query = "SELECT c FROM Customer c WHERE c.cAddress = :cAddress"),
    @NamedQuery(name = "Customer.findByCEmail", query = "SELECT c FROM Customer c WHERE c.cEmail = :cEmail"),
    @NamedQuery(name = "Customer.findByCPhone", query = "SELECT c FROM Customer c WHERE c.cPhone = :cPhone")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CId")
    private Integer cId;
    @Size(max = 100)
    @Column(name = "CUsername")
    private String cUsername;
    @Size(max = 100)
    @Column(name = "CPassword")
    private String cPassword;
    @Size(max = 100)
    @Column(name = "CFullName")
    private String cFullName;
    @Size(max = 100)
    @Column(name = "CAddress")
    private String cAddress;
    @Size(max = 100)
    @Column(name = "CEmail")
    private String cEmail;
    @Size(max = 50)
    @Column(name = "CPhone")
    private String cPhone;
    @OneToMany(mappedBy = "cId")
    private List<Sale> saleList;

    public Customer() {
    }

    public Customer(Integer cId) {
        this.cId = cId;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public String getCUsername() {
        return cUsername;
    }

    public void setCUsername(String cUsername) {
        this.cUsername = cUsername;
    }

    public String getCPassword() {
        return cPassword;
    }

    public void setCPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    public String getCFullName() {
        return cFullName;
    }

    public void setCFullName(String cFullName) {
        this.cFullName = cFullName;
    }

    public String getCAddress() {
        return cAddress;
    }

    public void setCAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public String getCEmail() {
        return cEmail;
    }

    public void setCEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getCPhone() {
        return cPhone;
    }

    public void setCPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    @XmlTransient
    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cId != null ? cId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.cId == null && other.cId != null) || (this.cId != null && !this.cId.equals(other.cId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.Customer[ cId=" + cId + " ]";
    }
    
}
