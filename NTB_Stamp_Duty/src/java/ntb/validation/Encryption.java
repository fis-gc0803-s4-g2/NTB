/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.validation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TUNG
 */
public class Encryption {
     public static String encryptPass(String str){
        if(str==null||str.length()==0){
            throw new IllegalArgumentException("String to encrypt cannot be null or zero length");
        }
            StringBuffer hexString=new StringBuffer();
            try {
                MessageDigest md=MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte[] hash=md.digest();
                for (int i = 0; i < hash.length; i++) {
                    if((0xff & hash[i])<0x10){
                        hexString.append("0"+Integer.toHexString((0xFF & hash[i])));
                    } else {
                         hexString.append(Integer.toHexString((0xFF & hash[i])));
                    }
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }         
        return hexString.toString();
    }
}
