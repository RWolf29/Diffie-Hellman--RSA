/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_encrypt;

import java.math.BigInteger;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Alice_autenticate aA = new Alice_autenticate();
        aA.setVisible(true);

        Bob_autenticate bA = new Bob_autenticate();
        bA.setVisible(true);

        if (aA.kA != null && bA.kB != null) {
            if (aA.kA == bA.kB) {
                aA.setVisible(false);
                bA.setVisible(false);
                JOptionPane.showMessageDialog(null, "¡claves validadas!");

                Alice a = new Alice();
                a.setVisible(true);

                Bob b = new Bob();
                b.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "ERROR", "Error al validar las claves", JOptionPane.WARNING_MESSAGE);
            }
        }

        /*Alice a = new Alice();
         a.setVisible(true);
        
         Bob b = new Bob();
         b.setVisible(true);*/
        /*ArrayList<BigInteger> keys; //[n, e, d]
         BigInteger p = new BigInteger("43");
         BigInteger q = new BigInteger("59");
         keys = RSA.getKey(p, q);
        
         ArrayList<BigInteger> encrypt;
         String m = "publickeycryptography";
         BigInteger n = new BigInteger("2537");
         BigInteger e = new BigInteger("13");
         encrypt = RSA.encrypt(m, n, e);
        
         ArrayList<Character> decrypt;       
         BigInteger d = new BigInteger("937");
         decrypt = RSA.decrypt(encrypt, d, n);*/
    }

}
