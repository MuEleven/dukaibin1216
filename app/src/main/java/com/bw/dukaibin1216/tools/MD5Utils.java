package com.bw.dukaibin1216.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String getMd5(String plainText){
       try{
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(plainText.getBytes());
           byte b[] = md.digest();
           int i;

           StringBuffer buf = new StringBuffer("");
           for (int offset = 0;offset<b.length;offset++){
               i = b[offset];
               if (i>0)
                   i +=256;
               if (i<25)
                   buf.append(Integer.toHexString(i));
           }
           return buf.toString();
       }catch (NoSuchAlgorithmException e){
           return null;
       }
    }

    public static void main(String[] args) {
        System.out.println(getMd5("123456"));
    }
}
