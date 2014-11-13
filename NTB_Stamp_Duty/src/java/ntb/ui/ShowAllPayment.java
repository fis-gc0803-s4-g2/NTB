/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ntb.biz.ContractManager;
import ntb.biz.PaymentDetailManager;
import ntb.entity.Contract;
import ntb.entity.PaymentDetail;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class ShowAllPayment {

    @EJB
    private ContractManager contractManager;

    @EJB
    private PaymentDetailManager paymentDetailManager;

    private List<PaymentDetail> pl;
    private int contractId;
    private String m;
    private double ap;
    private double ad;

    public String paymentDetailIndex() {
        m = null;
        pl = paymentDetailManager.getPaymentByCId(contractId);
        return "paymentDetailManager?faces-redirect=true";
    }

    public List<PaymentDetail> getPl() {
        ap = contractManager.findContract(contractId).getSAAmountPaid();
        ad = contractManager.findContract(contractId).getSAAmmountDue();
         pl = paymentDetailManager.getPaymentByCId(contractId);
        return pl;
    }
    
     public String addPaymentDetail() {
        if (contractId != 0) {
            if (contractManager.findContract(contractId).getSAAmmountDue() == 0) {
                m = "Payment through Installments done already";
                return "paymentDetailManager?faces-redirect=true";
            }
            PaymentDetail paymentDetail = new PaymentDetail();
            paymentDetail.setSAId(contractManager.findContract(contractId));
            String paymentType =contractManager.findContract(contractId).getSAPaymentType();
            double totalPayment = contractManager.findContract(contractId).getSATotalPayment();
            switch (paymentType) {
                case "Payment through Installments on a monthly basis for 2 years":
                    paymentDetail.setPDAmountPaid(totalPayment / (double) 24);
                    paymentDetail.setPDPaidDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                    break;
                case "Payment through Installments on a yearly basis for 2 years":
                    paymentDetail.setPDAmountPaid(totalPayment / (double) 2);
                    paymentDetail.setPDPaidDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                    break;
            }
            if (paymentDetailManager.createPaymentDetail(paymentDetail)) {
                List<PaymentDetail> pl2 = paymentDetailManager.getPaymentByCId(contractId);
                double s = 0.0;
                for (PaymentDetail pl21 : pl2) {
                    s += pl21.getPDAmountPaid();
                }
                Contract contract =contractManager.findContract(contractId);
               // Sale sale = saleSessionBean.findSale(saleId);
                contract.setSAAmountPaid(s);
               // sale.setSAAmountPaid(s);
                contract.setSAAmmountDue((double)contract.getSATotalPayment()-s);
               // sale.setSAAmmountDue((double) sale.getSATotalPayment() - s);
                if (s == contractManager.findContract(contractId).getSATotalPayment()) {
                    contract.setSAStatus("Yet to be registered");
                }
                contractManager.editContract(contract);
                return "paymentDetailManager?faces-redirect=true";
            } else {
                return "paymentDetailManager?faces-redirect=true";
            }
        }
        return "paymentDetailManager?faces-redirect=true";
    }

    public void setPl(List<PaymentDetail> pl) {
        this.pl = pl;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public double getAp() {
        return ap;
    }

    public void setAp(double ap) {
        this.ap = ap;
    }

    public double getAd() {
        return ad;
    }

    public void setAd(double ad) {
        this.ad = ad;
    }

}
