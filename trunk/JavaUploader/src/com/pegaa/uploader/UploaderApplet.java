/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader;

import com.pegaa.uploader.config.DefaultConfigHolder;
import com.pegaa.uploader.tools.SecurityStatusChecker;
import com.pegaa.uploader.ui.AppletDeniedMessageContainer;
import com.pegaa.uploader.ui.MainContainer;
import javax.swing.JApplet;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author tayfun
 */
public class UploaderApplet extends JApplet {

    private AppletDeniedMessageContainer fallbackContainer = null;
    private DefaultConfigHolder configHolder = null;
    private MainContainer mainContainer = null;

    @Override
    public void init() {
        //check if security dialog is accepted (if user clicked Run)
        //security status handler javascript function will be called
        //if security dialog is not accepted.
        if(SecurityStatusChecker.isSecurityDialogAccepted(this)){
            initApplet();
        }else{
            prepareSecurityFallbackGUI();
        }
    }


    public void initApplet()
    {
        if (configHolder == null) {
            configHolder = new DefaultConfigHolder();
            configHolder.initParameters(this);
        }
        prepareUI();
    }

    @Override
    public void stop(){
        System.out.println("stopping...");
        this.remove(mainContainer);
    }

     private void prepareUI() {
         
         this.prepareLookAndFeel();
         this.setLayout(new java.awt.GridLayout(1, 0));

         mainContainer = new MainContainer();
         mainContainer.setConfigHolder(this.configHolder);
         this.add(mainContainer);
     }

     /**
      * Manages the look and feel, if nimbus look and feel is configured
      * tries to activate it
      */
     private void prepareLookAndFeel()
     {
         if ("nimbus".equals((String) this.configHolder.getObject("lookandfeel"))) {
             //try to activate Nimbus Look & Feel
             try {
                 for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                     if ("Nimbus".equals(info.getName())) {
                         UIManager.setLookAndFeel(info.getClassName());
                         break;
                     }
                 }
             } catch (Exception e) {
                 //do nothing use default layout
             }
         }
     }

     /**
      * If user does not accept security dialog this function will prepare
      * a message
      */
     private void prepareSecurityFallbackGUI()
     {
         this.setLayout(new java.awt.GridLayout(1, 0));

         fallbackContainer = new AppletDeniedMessageContainer(this);
         this.add(fallbackContainer);
     }

}
