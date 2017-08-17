/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_encrypt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author USER
 */
public class RSA {    

    //Euclides extendido:
    static BigInteger mulInverse(BigInteger N, BigInteger A) {
        boolean flag = true;
        BigInteger x = new BigInteger("1");
        BigInteger y = new BigInteger("0");
        BigInteger x1 = new BigInteger("0");
        BigInteger x2 = new BigInteger("1");
        BigInteger y1 = new BigInteger("1");
        BigInteger y2 = new BigInteger("0");
        BigInteger n = N;
        BigInteger a = A;
        BigInteger d = a;
        BigInteger r;
        BigInteger q;

        while ((flag = n.compareTo(BigInteger.ZERO) == 1)) {
            q = a.divide(n);
            r = a.subtract(q.multiply(n));
            x = x2.subtract(q.multiply(x1));
            y = y2.subtract(q.multiply(y1));
            a = n;
            n = r;
            x2 = x1;
            x1 = x;
            y2 = y1;
            y1 = y;
            d = a;
            x = x2;
            y = y2;
        }
        while (flag = x.compareTo(BigInteger.ZERO) == -1) {
            x = x.add(N);
        }
        return x;
    }

    //Euclides:
    static BigInteger euclides(BigInteger a, BigInteger b) {
        BigInteger r = new BigInteger("0");
        while (!b.equals(BigInteger.ZERO)) {
            r = a.mod(b);
            a = b;
            b = r;
        }
        return a;
    }
    
    //Exponenciación modular
    static BigInteger exponenModular(BigInteger a, BigInteger k1, BigInteger n){
        BigInteger b;
        BigInteger A;
        String k = k1.toString(2);
        b = BigInteger.ONE;
        if (k.equals(BigInteger.ZERO)) {
            return b;
        }
        A = a;

        if (k.charAt(k.length() - 1 ) == '1' ) {
            b = a;
        }
        for (int i = 1; i < k.length() ; i++) {
            A = A.pow(2).mod(n);
            if (k.charAt(k.length() - 1 - i ) == '1') {
                b = A.multiply(b).mod(n);
            }
        }
        return b;
    }
    
    //Generar Primos aleatorios
    static int primoAleatorio(){
        int num = 0;
        Random rand = new Random(); // generate a random number
        num = rand.nextInt(1000) + 1;
 
        while (!isPrime(num)) {          
            num = rand.nextInt(1000) + 1;
        }
        //System.out.println(num);  // print the number
        
        return num;
    }    
    private static boolean isPrime(int inputNum){
        if (inputNum <= 3 || inputNum % 2 == 0) 
            return inputNum == 2 || inputNum == 3; //this returns false if number is <=1 & true if number = 2 or 3
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0)) 
            divisor += 2; //iterates through all possible divisors
        return inputNum % divisor != 0; //returns true/false
    }
    
    //Generacion de claves:
    static ArrayList<BigInteger> getKey( BigInteger p, BigInteger q) {
        BigInteger n = p.multiply(q); // p x q
        BigInteger fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // fi = (p - 1) x (q - 1)
        System.out.println(fi);

        //Obtenemos e:
        /*BigInteger e = new BigInteger("10");
        //TODO: implementar el random!
        while (e.compareTo(fi) == -1) {
            e = e.add(BigInteger.ONE);
            if (euclides(e, fi).equals(BigInteger.ONE)) {
                break;
            }
        }        
        System.out.println(e);*/
        BigInteger e;
        do{
            e = new BigInteger(primoAleatorio()+"");
            if (euclides(e, fi).equals(BigInteger.ONE)) {
                break;
            }
        }while(true);
        
        //Obtener d
        BigInteger d = BigInteger.ZERO;
        //Utilizamos euclides extendido:
        if (euclides(e, fi).equals(BigInteger.ONE)) {
            d = d.add(mulInverse(fi, e));
        }        
        System.out.println(d);

        ArrayList<BigInteger> rpta = new ArrayList<>();
        rpta.add(n);
        rpta.add(e);
        rpta.add(d);
        return rpta;
    }

    static ArrayList<BigInteger> encrypt(String m, BigInteger n, BigInteger e) {
        //Convertimos a ASCII
        
        System.out.println(" ");
        
        
        ArrayList<Integer> ascii = new ArrayList<>();
        for (int i = 0; i < m.length(); i++) {
            ascii.add((int) m.charAt(i)-97); //a -> 97
        }
        
        for (int i = 0; i < m.length(); i++) {
            System.out.println(ascii.get(i));
        }
        System.out.println(" ");

        
        ArrayList<BigInteger> pares = new ArrayList<>();
        for (int i = 0; i < ascii.size(); i++) {
            int par1 = ascii.get(i) * 100;
            int par2;
            if ( i+1 >= ascii.size()) {
                par2 = 0;
            } else {
                par2 = ascii.get(i+1);
            }
            BigInteger num = new BigInteger(par1 + par2 + "");
            pares.add(num);
            i++;
        }
        
        for (int i = 0; i < pares.size(); i++) {
            System.out.println(pares.get(i));
        }
        System.out.println(" ");

        
        ArrayList<BigInteger> c = new ArrayList<>();
        for (int i = 0; i < pares.size(); i++) {
            c.add(exponenModular(pares.get(i), e, n));
        }
        
        for (int i = 0; i < c.size(); i++) {
            System.out.println(c.get(i));
        }

        return c;

    }
    
    static ArrayList<Character> decrypt(ArrayList<BigInteger> c, BigInteger d, BigInteger n){
        System.out.println(" ");
        
        ArrayList<BigInteger> m = new ArrayList<>();
        for (int i = 0; i < c.size(); i++) {
            m.add(exponenModular(c.get(i), d, n));
        }
        
        for (int i = 0; i < m.size(); i++) {
            System.out.println(m.get(i));
        }
        
        
        System.out.println(" ");
        
        ArrayList<Character> text = new ArrayList<>();
        for (int i = 0; i < m.size(); i++) {
            String t,w1,w2;
            t = m.get(i).toString();
            if (t.length() == 4) {
                w1 = t.substring(0,2);
            } else {
                w1 = t.substring(0,1);
            }
            w2 = t.substring(t.length()-2,t.length());
            //System.out.print(w1);
            //System.out.println(w2);
            
            BigInteger palabra1 = new BigInteger(w1);
            int go1 = palabra1.intValue()+97;
            char outPut1 = (char) go1 ;
            
            BigInteger palabra2 = new BigInteger(w2);
            int go2 = palabra2.intValue()+97;
            char outPut2 = (char) go2 ;
            
            System.out.println( outPut1 );
            text.add( outPut1 );
            text.add( outPut2 );
        }
        
        System.out.println("");
        
        for (int i = 0; i < text.size(); i++) {
            System.out.print(text.get(i));
        }
        
        return text;  
        
    }
}
