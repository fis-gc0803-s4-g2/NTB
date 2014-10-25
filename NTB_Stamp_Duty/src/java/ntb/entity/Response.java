/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TUNG
 */
@Entity
@Table(name = "Response")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Response.findAll", query = "SELECT r FROM Response r"),
    @NamedQuery(name = "Response.findByRId", query = "SELECT r FROM Response r WHERE r.rId = :rId"),
    @NamedQuery(name = "Response.findByRFullName", query = "SELECT r FROM Response r WHERE r.rFullName = :rFullName"),
    @NamedQuery(name = "Response.findByRAddress", query = "SELECT r FROM Response r WHERE r.rAddress = :rAddress"),
    @NamedQuery(name = "Response.findByREmail", query = "SELECT r FROM Response r WHERE r.rEmail = :rEmail"),
    @NamedQuery(name = "Response.findByRPhone", query = "SELECT r FROM Response r WHERE r.rPhone = :rPhone"),
    @NamedQuery(name = "Response.findByRContent", query = "SELECT r FROM Response r WHERE r.rContent = :rContent")})
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RId")
    private Integer rId;
    @Size(max = 100)
    @Column(name = "RFullName")
    private String rFullName;
    @Size(max = 100)
    @Column(name = "RAddress")
    private String rAddress;
    @Size(max = 100)
    @Column(name = "REmail")
    private String rEmail;
    @Size(max = 50)
    @Column(name = "RPhone")
    private String rPhone;
    @Size(max = 500)
    @Column(name = "RContent")
    private String rContent;

    public Response() {
    }

    public Response(Integer rId) {
        this.rId = rId;
    }

    public Integer getRId() {
        return rId;
    }

    public void setRId(Integer rId) {
        this.rId = rId;
    }

    public String getRFullName() {
        return rFullName;
    }

    public void setRFullName(String rFullName) {
        this.rFullName = rFullName;
    }

    public String getRAddress() {
        return rAddress;
    }

    public void setRAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public String getREmail() {
        return rEmail;
    }

    public void setREmail(String rEmail) {
        this.rEmail = rEmail;
    }

    public String getRPhone() {
        return rPhone;
    }

    public void setRPhone(String rPhone) {
        this.rPhone = rPhone;
    }

    public String getRContent() {
        return rContent;
    }

    public void setRContent(String rContent) {
        this.rContent = rContent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rId != null ? rId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Response)) {
            return false;
        }
        Response other = (Response) object;
        if ((this.rId == null && other.rId != null) || (this.rId != null && !this.rId.equals(other.rId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.Response[ rId=" + rId + " ]";
    }
    
}
