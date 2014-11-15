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
import ntb.validation.Encryption;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class CreateAnAccount {

    @EJB
    private AccountManager accountManager;

    private String username;
    private String password;
    private String confirm;
    private String fullname;
    private String m;

    public String createAccountIndex() {
        username = null;
        password = null;
        fullname = null;
        confirm = null;
        m = null;
        return "addAccount?faces-redirect=true";
    }

    public String createAccount() {
       // if (confirm.equals(password)) {
            Manager manager = new Manager();
            manager.setMUsername(username);
            String passMd5 = Encryption.encryptPass(password);
            manager.setMPassword(passMd5);
            manager.setMFullName(fullname);
            if (accountManager.createAccount(manager)) {
                username = null;
                password = null;
                fullname = null;
                confirm = null;
                m = null;
                return "accountManager?faces-redirect=true";
            }
       // }
       // m = "Password not match";
        return "";
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
    
     public void validateAddress(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Address is required"));
        }
    }
     
      public void validateEmail(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Email is required"));
        }
    }
      
       public void validatePhone(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Phone is required"));
        }
    }
       
        public void validateConfirm(FacesContext f, UIComponent c, Object obj) {
        String s = (String) obj;
        if (s.length() == 0) {
            throw new ValidatorException(new FacesMessage("Confirm password is required"));
        }
    }

}
