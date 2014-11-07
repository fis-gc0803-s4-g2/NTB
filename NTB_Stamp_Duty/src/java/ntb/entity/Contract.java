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
@Table(name = "Contract")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contract.findAll", query = "SELECT c FROM Contract c"),
    @NamedQuery(name = "Contract.findBySAId", query = "SELECT c FROM Contract c WHERE c.sAId = :sAId"),
    @NamedQuery(name = "Contract.findBySAPaymentType", query = "SELECT c FROM Contract c WHERE c.sAPaymentType = :sAPaymentType"),
    @NamedQuery(name = "Contract.findBySATotalCost", query = "SELECT c FROM Contract c WHERE c.sATotalCost = :sATotalCost"),
    @NamedQuery(name = "Contract.findBySATotalPayment", query = "SELECT c FROM Contract c WHERE c.sATotalPayment = :sATotalPayment"),
    @NamedQuery(name = "Contract.findBySAAmountPaid", query = "SELECT c FROM Contract c WHERE c.sAAmountPaid = :sAAmountPaid"),
    @NamedQuery(name = "Contract.findBySAAmmountDue", query = "SELECT c FROM Contract c WHERE c.sAAmmountDue = :sAAmmountDue"),
    @NamedQuery(name = "Contract.findBySATax", query = "SELECT c FROM Contract c WHERE c.sATax = :sATax"),
    @NamedQuery(name = "Contract.findBySACreateDate", query = "SELECT c FROM Contract c WHERE c.sACreateDate = :sACreateDate"),
    @NamedQuery(name = "Contract.findBySANote", query = "SELECT c FROM Contract c WHERE c.sANote = :sANote"),
    @NamedQuery(name = "Contract.findBySAStatus", query = "SELECT c FROM Contract c WHERE c.sAStatus = :sAStatus")})
public class Contract implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAId")
    private Integer sAId;
    @Size(max = 100)
    @Column(name = "SAPaymentType")
    private String sAPaymentType;
    @Column(name = "SATotalCost")
    private Integer sATotalCost;
    @Column(name = "SATotalPayment")
    private Integer sATotalPayment;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SAAmountPaid")
    private Double sAAmountPaid;
    @Column(name = "SAAmmountDue")
    private Double sAAmmountDue;
    @Column(name = "SATax")
    private Integer sATax;
    @Size(max = 50)
    @Column(name = "SACreateDate")
    private String sACreateDate;
    @Size(max = 500)
    @Column(name = "SANote")
    private String sANote;
    @Size(max = 100)
    @Column(name = "SAStatus")
    private String sAStatus;
    @OneToMany(mappedBy = "sAId")
    private List<PaymentDetail> paymentDetailList;
    @JoinColumn(name = "CId", referencedColumnName = "CId")
    @ManyToOne
    private Customer cId;
    @JoinColumn(name = "APId", referencedColumnName = "APId")
    @ManyToOne
    private Apartment aPId;

    public Contract() {
    }

    public Contract(Integer sAId) {
        this.sAId = sAId;
    }

    public Integer getSAId() {
        return sAId;
    }

    public void setSAId(Integer sAId) {
        this.sAId = sAId;
    }

    public String getSAPaymentType() {
        return sAPaymentType;
    }

    public void setSAPaymentType(String sAPaymentType) {
        this.sAPaymentType = sAPaymentType;
    }

    public Integer getSATotalCost() {
        return sATotalCost;
    }

    public void setSATotalCost(Integer sATotalCost) {
        this.sATotalCost = sATotalCost;
    }

    public Integer getSATotalPayment() {
        return sATotalPayment;
    }

    public void setSATotalPayment(Integer sATotalPayment) {
        this.sATotalPayment = sATotalPayment;
    }

    public Double getSAAmountPaid() {
        return sAAmountPaid;
    }

    public void setSAAmountPaid(Double sAAmountPaid) {
        this.sAAmountPaid = sAAmountPaid;
    }

    public Double getSAAmmountDue() {
        return sAAmmountDue;
    }

    public void setSAAmmountDue(Double sAAmmountDue) {
        this.sAAmmountDue = sAAmmountDue;
    }

    public Integer getSATax() {
        return sATax;
    }

    public void setSATax(Integer sATax) {
        this.sATax = sATax;
    }

    public String getSACreateDate() {
        return sACreateDate;
    }

    public void setSACreateDate(String sACreateDate) {
        this.sACreateDate = sACreateDate;
    }

    public String getSANote() {
        return sANote;
    }

    public void setSANote(String sANote) {
        this.sANote = sANote;
    }

    public String getSAStatus() {
        return sAStatus;
    }

    public void setSAStatus(String sAStatus) {
        this.sAStatus = sAStatus;
    }

    @XmlTransient
    public List<PaymentDetail> getPaymentDetailList() {
        return paymentDetailList;
    }

    public void setPaymentDetailList(List<PaymentDetail> paymentDetailList) {
        this.paymentDetailList = paymentDetailList;
    }

    public Customer getCId() {
        return cId;
    }

    public void setCId(Customer cId) {
        this.cId = cId;
    }

    public Apartment getAPId() {
        return aPId;
    }

    public void setAPId(Apartment aPId) {
        this.aPId = aPId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sAId != null ? sAId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contract)) {
            return false;
        }
        Contract other = (Contract) object;
        if ((this.sAId == null && other.sAId != null) || (this.sAId != null && !this.sAId.equals(other.sAId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.Contract[ sAId=" + sAId + " ]";
    }
    
}
