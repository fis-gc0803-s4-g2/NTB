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
public class ShowAllLand {
    
    @EJB
    private LandManager landManager;

    
   private List<Land> list;

    public List<Land> getList() {
        return landManager.getAllLand();
    }

    public void setList(List<Land> list) {
        this.list = list;
    }
   
   
    
}
