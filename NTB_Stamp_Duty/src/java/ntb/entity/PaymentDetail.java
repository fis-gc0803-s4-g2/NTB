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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PaymentDetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentDetail.findAll", query = "SELECT p FROM PaymentDetail p"),
    @NamedQuery(name = "PaymentDetail.findByPDId", query = "SELECT p FROM PaymentDetail p WHERE p.pDId = :pDId"),
    @NamedQuery(name = "PaymentDetail.findByPDDueDate", query = "SELECT p FROM PaymentDetail p WHERE p.pDDueDate = :pDDueDate"),
    @NamedQuery(name = "PaymentDetail.findByPDAmountDue", query = "SELECT p FROM PaymentDetail p WHERE p.pDAmountDue = :pDAmountDue"),
    @NamedQuery(name = "PaymentDetail.findByPDPaidDate", query = "SELECT p FROM PaymentDetail p WHERE p.pDPaidDate = :pDPaidDate"),
    @NamedQuery(name = "PaymentDetail.findByPDAmountPaid", query = "SELECT p FROM PaymentDetail p WHERE p.pDAmountPaid = :pDAmountPaid")})
public class PaymentDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PDId")
    private Integer pDId;
    @Size(max = 50)
    @Column(name = "PDDueDate")
    private String pDDueDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PDAmountDue")
    private Double pDAmountDue;
    @Size(max = 50)
    @Column(name = "PDPaidDate")
    private String pDPaidDate;
    @Column(name = "PDAmountPaid")
    private Double pDAmountPaid;
    @JoinColumn(name = "SAId", referencedColumnName = "SAId")
    @ManyToOne
    private Contract sAId;

    public PaymentDetail() {
    }

    public PaymentDetail(Integer pDId) {
        this.pDId = pDId;
    }

    public Integer getPDId() {
        return pDId;
    }

    public void setPDId(Integer pDId) {
        this.pDId = pDId;
    }

    public String getPDDueDate() {
        return pDDueDate;
    }

    public void setPDDueDate(String pDDueDate) {
        this.pDDueDate = pDDueDate;
    }

    public Double getPDAmountDue() {
        return pDAmountDue;
    }

    public void setPDAmountDue(Double pDAmountDue) {
        this.pDAmountDue = pDAmountDue;
    }

    public String getPDPaidDate() {
        return pDPaidDate;
    }

    public void setPDPaidDate(String pDPaidDate) {
        this.pDPaidDate = pDPaidDate;
    }

    public Double getPDAmountPaid() {
        return pDAmountPaid;
    }

    public void setPDAmountPaid(Double pDAmountPaid) {
        this.pDAmountPaid = pDAmountPaid;
    }

    public Contract getSAId() {
        return sAId;
    }

    public void setSAId(Contract sAId) {
        this.sAId = sAId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pDId != null ? pDId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentDetail)) {
            return false;
        }
        PaymentDetail other = (PaymentDetail) object;
        if ((this.pDId == null && other.pDId != null) || (this.pDId != null && !this.pDId.equals(other.pDId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.PaymentDetail[ pDId=" + pDId + " ]";
    }
    
}
