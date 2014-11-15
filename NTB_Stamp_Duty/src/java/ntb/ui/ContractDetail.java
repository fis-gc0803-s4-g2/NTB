/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.ui;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ntb.biz.ContractManager;
import ntb.entity.Contract;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class ContractDetail {
    
    @EJB
    private ContractManager contractManager;
 
    private  int contractId;
    private Contract contract;
    
    public String contractDetailIndex(){
        contract=contractManager.findContract(contractId);
        return "contractDetail?redirect=true";
    }
    

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
    
    
    
}
