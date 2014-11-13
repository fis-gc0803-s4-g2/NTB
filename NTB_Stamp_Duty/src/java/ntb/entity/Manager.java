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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "Manager")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manager.findAll", query = "SELECT m FROM Manager m"),
    @NamedQuery(name = "Manager.findByMId", query = "SELECT m FROM Manager m WHERE m.mId = :mId"),
    @NamedQuery(name = "Manager.findByMUsername", query = "SELECT m FROM Manager m WHERE m.mUsername = :mUsername"),
    @NamedQuery(name = "Manager.findByMPassword", query = "SELECT m FROM Manager m WHERE m.mPassword = :mPassword"),
    @NamedQuery(name = "Manager.findByMFullName", query = "SELECT m FROM Manager m WHERE m.mFullName = :mFullName")})
public class Manager implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MId")
    private Integer mId;
    @Size(max = 100)
    @Column(name = "MUsername")
    private String mUsername;
    @Size(max = 100)
    @Column(name = "MPassword")
    private String mPassword;
    @Size(max = 100)
    @Column(name = "MFullName")
    private String mFullName;

    public Manager() {
    }

    public Manager(Integer mId) {
        this.mId = mId;
    }

    public Integer getMId() {
        return mId;
    }

    public void setMId(Integer mId) {
        this.mId = mId;
    }

    public String getMUsername() {
        return mUsername;
    }

    public void setMUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getMPassword() {
        return mPassword;
    }

    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getMFullName() {
        return mFullName;
    }

    public void setMFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mId != null ? mId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manager)) {
            return false;
        }
        Manager other = (Manager) object;
        if ((this.mId == null && other.mId != null) || (this.mId != null && !this.mId.equals(other.mId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.Manager[ mId=" + mId + " ]";
    }
    
}
