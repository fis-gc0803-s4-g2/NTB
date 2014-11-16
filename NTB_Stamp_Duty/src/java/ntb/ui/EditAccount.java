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
import ntb.biz.AccountManager;
import ntb.entity.Manager;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class EditAccount {

    @EJB
    private AccountManager accountManager;

    private Manager manager;
    private int accId;
    private String username;
    private String password;
    private String confirm;
    private String fullname;
    private String m;

    public String editAccountIndex() {
        manager = accountManager.findAccount(accId);
        username = manager.getMUsername();
        password = manager.getMPassword();
        fullname = manager.getMFullName();
        confirm = null;
        m = null;
        return "editAccount?faces-redirect=true";
    }

    public String editAnAccount() {

        manager.setMUsername(username);
        manager.setMPassword(password);
        manager.setMFullName(fullname);
        if (accountManager.editAccount(manager)) {
            username = null;
            password = null;
            fullname = null;
            confirm = null;
            m = null;
              return "accountManager?faces-redirect=true";
        } else {
            m = "Error";
            return "";
        }

    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
    
     public void validateUsername(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Username is required"));
        }
    }

    public void validatePassword(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Password is required"));
        }
    }

    public void validateFullname(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Full name is required"));
        }
    }

}
