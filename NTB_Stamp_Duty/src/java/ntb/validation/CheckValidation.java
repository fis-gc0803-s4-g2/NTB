/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.validation;

/**
 *
 * @author Phuong Van
 */
public class CheckValidation {
    //Kiem tra validate login, bao gom check ki tu dac biet
    public static boolean checkLoginValidation(String username, String password){
        
        if ("".equals(username) || "".equals(password) || " ".contains(password) || " ".contains(username)) {
            return false;
        }
        if ("-".contains(password) || "=".contains(password) || "-".contains(username) || "=".contains(username)) {
            return false;
        }
        return true;
    }
    
    public boolean checkAccountExist(){
        
        
        return false;
    }
    
}
