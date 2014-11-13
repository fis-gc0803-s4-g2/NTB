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
import ntb.biz.LandManager;
import ntb.entity.Land;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class ShowAllLand{
    
    @EJB
    private LandManager landManager;
    
    private List<Land> list;
    
    private String status="";
    
    public String landManagerIndex(){
            
        return "landManager?faces-redirect=true";
        
    }
    
    public String searchLand(){
        list=landManager.searchLandByStatus(status);
        return "landManager?faces-redirect=true";
        
    }


    public List<Land> getList() {
        return landManager.searchLandByStatus(status);
    }

    public void setList(List<Land> list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
    
   
    
}
