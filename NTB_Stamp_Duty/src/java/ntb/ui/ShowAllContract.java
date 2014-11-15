/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.ui;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ntb.biz.BuildingManager;
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

    @EJB
    private BuildingManager buildingManager;

    private List<Contract> cl;

    private int buildingId;
    private String paymentType = "";
    private String status = "";
    private String day = "";
    private String month = "";
    private String year = "";

    private String buildingName;

    public String contractIndex() {
        return "contractManager?redirect=true";
    }

    public String searchContract() {
        cl = contractManager.searchContract(buildingId, paymentType, status, day, month, year);
        return "contractManager?redirect=true";
    }

    public List<Contract> getCl() {
        cl = contractManager.searchContract(buildingId, paymentType, status, day, month, year);
        buildingName = buildingManager.findBuilding(buildingId).getBBuildingName();
        return cl;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }



    public List<String> dayList() {
        List<String> dayList=new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add(i + "");
        }
        return dayList;
    }

    public List<String> monthList() {
        List<String> monthList=new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthList.add(i + "");
        }
        return monthList;
    }

    public List<String> yearList() {
        List<String> yearList=new ArrayList<>();
        for (int i = 2010; i <= 2020; i++) {
            yearList.add(i + "");
        }
        return yearList;
    }

   
}
