/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.biz;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import ntb.da.ContractJpaController;
import ntb.entity.Contract;

/**
 *
 * @author TUNG
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ContractManager {

    @PersistenceUnit(unitName = "NTB_Stamp_DutyPU")

    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    private ContractJpaController daController;

    public ContractJpaController getDaController() {
        if (daController == null) {
            daController = new ContractJpaController(utx, emf);

        }
        return daController;
    }
    
    public List<Contract> getAllContract(int bId,String paymentType){
        return getDaController().getAllContractById(bId,paymentType);
    }
    
    public List<Contract> searchContract(int bId, String paymentType, String status){
        
        return getDaController().searchContract(bId, paymentType, status);    
    }
    
    public boolean searchContractById(int aId){
        
        return  getDaController().searchContractById(aId);
        
    }
    
     /**
     *Create a new contract
     * @param contract
     * 
     */
    public boolean createContract(Contract contract) {
        try {
            getDaController().create(contract);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ContractManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    /**
     * edit a contract
     * @param contract
     */
    public boolean editContract(Contract contract){
        try {
            getDaController().edit(contract);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ContractManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

   public Contract findContract(int id){
       return getDaController().findContract(id);
   }

}
