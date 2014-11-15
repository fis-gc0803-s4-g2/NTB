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
import ntb.biz.AccountManager;
import ntb.entity.Manager;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class ShowAllAccount {
    @EJB
    private AccountManager accountManager;

   private List<Manager> al;
   
   public String accountIndex(){
       return "accountManager?faces-redirect=true";
   }

    public List<Manager> getAl() {
        al=accountManager.getAllAccount();
        return al;
    }

    public void setAl(List<Manager> al) {
        this.al = al;
    }
   
   
   
    
    
}
