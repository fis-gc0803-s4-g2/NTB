/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.ui;

import java.util.List;
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
public class ShowAllContract {
    @EJB
    private ContractManager contractManager;

   private List<Contract> cl;
   
   private int buildingId;
   
   
   
   public String contractIndex(){
   
        return "contractManager?redirect=true";
   }
   
//   public String searchContract(){
//       cl=contractManager.searchContract(key1, key2, key3);
//        return "contractManager?redirect=true";
//   }

    public List<Contract> getCl() {
        return contractManager.getAllContract(buildingId);
    }

    public void setCl(List<Contract> cl) {
        this.cl = cl;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
  
    
    
}
