/*
 * JpegController.java
 *
 * Created on 25 Temmuz 2007 Çarşamba, 16:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pegaa.uploader.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author tayfun
 */
public class JpegController {
    
    private static final byte ff = (byte)-1;
    private static final byte d8 = (byte)-40;
    
    /** Creates a new instance of JpegController */
    public JpegController() {
    }
 
    public static boolean isJpegFile(File f){
       byte[] buffer = new byte[2];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            return false;
        }
        if(fis == null){
            return false;
        }
        try{
            fis.read(buffer, 0, 2);
        }catch(Exception e){
               try {
                   fis.close();
               } catch (IOException ex) {
               }
               return false;
        }
        try {
            fis.close();
        } catch (IOException ex) { 
               try {
                   fis.close();
               } catch (IOException ex1) {
               }            
        }
        if((buffer[0] == ff) && (buffer[1] == d8)){      
            return true;
        }
        return false;
    }
    
}
