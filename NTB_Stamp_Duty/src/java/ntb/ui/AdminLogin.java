/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import ntb.biz.AdminLoginManager;
import ntb.entity.Manager;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class AdminLogin {
    @EJB
    private AdminLoginManager adminLoginManager;
    
    

    private String username;
    private String password;
    private String acc;
    private String role;
    private String pathInfo;
    private String message;

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

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
     public String autoLogin() {
        try {
            if (role == null) {
                FacesContext context = FacesContext.getCurrentInstance();

                //Store to send redirect after login
                pathInfo = context.getExternalContext().getRequestPathInfo();

                String contextPath = context.getExternalContext().getRequestContextPath();
                ((HttpServletResponse) context.getExternalContext().getResponse()).sendRedirect(contextPath + "/faces/login.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

      public String login() {
        if(username!=null&&!username.trim().isEmpty()&&password!=null&&!password.trim().isEmpty()){
            Manager m=adminLoginManager.login(username, password);
           
            if(m!=null){
                role="ok";
                return "adminHome?faces-redirect=true";
            }else{
                acc="This Account doesn't exist";
                return "";
            }
        }
        acc="Username and Password is required";
        return "";
    }
    
       public String logout() {
        role = null;
        return "login?faces-redirect=true";
    }
    
    
}
