/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.ui;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import ntb.biz.ContractManager;
import ntb.entity.Contract;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class CreateTax {

    @EJB
    private ContractManager contractManager;

    private int contractId;
    private int tax;
    private Contract contract;

    private String m;

    public String addTaxIndex() {
        contract = contractManager.findContract(contractId);
        tax = contract.getSATax();
        return "addTax?faces-redirect=true";
    }

    public String addTax() {
        contract.setSATax(tax);
        if (contractManager.editContract(contract)) {
            Contract con = contractManager.findContract(contractId);
            con.setSAStatus("Registration and stamp duty done");
            contractManager.editContract(con);
            tax = 0;
            m = null;
            return "contractManager?faces-redirect=true";
        }else{
            m="Error";
             return "";
        }
       

    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
    
    public void validateTax(FacesContext f, UIComponent c, Object obj) {
        Integer s = (Integer) obj;
        if (s == 0) {
            throw new ValidatorException(new FacesMessage("Tax is required "));
        } else if (s < 0) {
            throw new ValidatorException(new FacesMessage("Tax must be greater than 0 "));
        }
    }
}
