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
@Table(name = "Sale")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sale.findAll", query = "SELECT s FROM Sale s"),
    @NamedQuery(name = "Sale.findBySAId", query = "SELECT s FROM Sale s WHERE s.sAId = :sAId"),
    @NamedQuery(name = "Sale.findBySAPaymentType", query = "SELECT s FROM Sale s WHERE s.sAPaymentType = :sAPaymentType"),
    @NamedQuery(name = "Sale.findBySATotalCost", query = "SELECT s FROM Sale s WHERE s.sATotalCost = :sATotalCost"),
    @NamedQuery(name = "Sale.findBySATotalPayment", query = "SELECT s FROM Sale s WHERE s.sATotalPayment = :sATotalPayment"),
    @NamedQuery(name = "Sale.findBySAAmountPaid", query = "SELECT s FROM Sale s WHERE s.sAAmountPaid = :sAAmountPaid"),
    @NamedQuery(name = "Sale.findBySAAmmountDue", query = "SELECT s FROM Sale s WHERE s.sAAmmountDue = :sAAmmountDue"),
    @NamedQuery(name = "Sale.findBySATax", query = "SELECT s FROM Sale s WHERE s.sATax = :sATax"),
    @NamedQuery(name = "Sale.findBySCreateDate", query = "SELECT s FROM Sale s WHERE s.sCreateDate = :sCreateDate"),
    @NamedQuery(name = "Sale.findBySNote", query = "SELECT s FROM Sale s WHERE s.sNote = :sNote"),
    @NamedQuery(name = "Sale.findBySAStatus", query = "SELECT s FROM Sale s WHERE s.sAStatus = :sAStatus")})
public class Sale implements Serializable {
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
    @Column(name = "SCreateDate")
    private String sCreateDate;
    @Size(max = 500)
    @Column(name = "SNote")
    private String sNote;
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

    public Sale() {
    }

    public Sale(Integer sAId) {
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

    public String getSCreateDate() {
        return sCreateDate;
    }

    public void setSCreateDate(String sCreateDate) {
        this.sCreateDate = sCreateDate;
    }

    public String getSNote() {
        return sNote;
    }

    public void setSNote(String sNote) {
        this.sNote = sNote;
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
        if (!(object instanceof Sale)) {
            return false;
        }
        Sale other = (Sale) object;
        if ((this.sAId == null && other.sAId != null) || (this.sAId != null && !this.sAId.equals(other.sAId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ntb.entity.Sale[ sAId=" + sAId + " ]";
    }
    
}
