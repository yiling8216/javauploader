/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.tools;

import javax.swing.JApplet;
import javax.swing.filechooser.FileSystemView;
import netscape.javascript.JSObject;

/**
 * This class checks if the user clicked run or cancel when the security
 * dialog is asked
 * 
 * 
 * @author tayfun
 */
public class SecurityStatusChecker {

    public static boolean isSecurityDialogAccepted(JApplet applet)
    {
        try{
            getFileSystemView().getDefaultDirectory().canRead();
            return true;
        }catch(java.security.AccessControlException eg){
            callSecurityStatusErrorHandler(applet);
        }catch (Exception ex){
        }
        return false;
    }
 
    /*
     * call javascript handler 
     */
    private static void callSecurityStatusErrorHandler(JApplet applet)
    {
        JSObject jso = JSObject.getWindow(applet);
        try{
            jso.call("JUPDeniedHandler", new String[]{""});
        }catch(Exception e){
            e.printStackTrace(); 
        } 
    }
    
    public static FileSystemView getFileSystemView(){
        return FileSystemView.getFileSystemView();
    }
    
}
