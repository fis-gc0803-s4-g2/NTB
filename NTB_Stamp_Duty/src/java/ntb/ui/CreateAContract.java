/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import ntb.biz.ApartmentManager;
import ntb.biz.ContractManager;
import ntb.biz.CustomerManager;
import ntb.entity.Contract;
import ntb.entity.Customer;

/**
 *
 * @author TUNG
 */
@ManagedBean
@SessionScoped
public class CreateAContract {

    @EJB
    private ApartmentManager apartmentManager;
    @EJB
    private CustomerManager customerManager;

    @EJB
    private ContractManager contractManager;

    private String username;
    private String password;
    private String fullname;
    private String address;
    private String email;
    private String phone;

    private int apartmentId;
    private String paymentType;

    private String confirmPassword;
    private String m;

    public String contractIndex() {
        username = null;
        password = null;
        fullname = null;
        address = null;
        email = null;
        phone = null;
        paymentType = "";
        confirmPassword = null;
        m = null;
        return "addContract?faces-redirect=true";
    }

    public String CancelCreateAContract() {
        username = null;
        password = null;
        fullname = null;
        address = null;
        email = null;
        phone = null;
        paymentType = "";
        confirmPassword = null;
        m = null;
        return "contractManager?faces-redirect=true";
    }

    public String createAContract() {

        Customer customer = new Customer();

        customer.setCUsername(username);
        customer.setCPassword(password);
        customer.setCFullName(fullname);
        customer.setCAddress(address);
        customer.setCEmail(email);
        customer.setCPhone(phone);
        if (customerManager.createCustomer(customer)) {

            Contract contract = new Contract();

            contract.setAPId(apartmentManager.findApartment(apartmentId));
            contract.setCId(customerManager.findCustomer(customerManager.findCustomer(customerManager.count()).getCId()));
            contract.setSAPaymentType(paymentType);
            int totalCost = apartmentManager.findApartment(apartmentId).getAPCost();
            contract.setSATotalCost(totalCost);
            double totalPayment = 0.0;
            switch (paymentType) {
                case "Payment through Installments on a monthly basics for 2 years":
                    totalPayment = totalCost * 1.05;
                    break;

                case "Payment through Installments on a yearly basics for 2 years":
                    totalPayment = totalCost * 1.03;
                    break;

                case "One time payment":
                    totalPayment = totalCost;
                    break;
            }
            contract.setSATotalPayment((int) totalPayment);
            switch (paymentType) {
                case "One time payment":
                    contract.setSAAmountPaid((double) totalPayment);
                    contract.setSAAmmountDue(0.0);
                    break;

                case "Payment through Installments on a monthly basics for 2 years":
                    contract.setSAAmountPaid(0.0);
                    contract.setSAAmmountDue((double) totalPayment);
                    break;

                case "Payment through Installments on a yearly basics for 2 years":
                    contract.setSAAmountPaid(0.0);
                    contract.setSAAmmountDue((double) totalPayment);
                    break;
            }
            contract.setSATax(0);
            contract.setSACreateDate(new SimpleDateFormat("dd/mm/yyyy").format(new Date()));
            contract.setSANote("");
            switch (paymentType) {
                case "One time payment":
                    contract.setSAStatus("Yet to be registered");
                    break;
                case "Payment through Installments on a monthly basics for 2 years":
                    contract.setSAStatus("Payment not received");
                    break;

                case "Payment through Installments on a yearly basics for 2 years":
                    contract.setSAStatus("Payment not received");
                    break;
            }
            if (contractManager.createContract(contract)) {
                username = null;
                password = null;
                fullname = null;
                address = null;
                email = null;
                phone = null;
                paymentType = "";
                confirmPassword = null;
                m = null;
                return "ok?faces-redirect=true";
            }

        }
//        }
//        m = "Password not match !"+password+","+confirmPassword;
//        return "";
        return "";

    }

    public boolean searchContractById(int aId) {
        return contractManager.searchContractById(aId);
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
