/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader;

import com.pegaa.uploader.config.DefaultConfigHolder;
import com.pegaa.uploader.ui.MainContainer;
import javax.swing.JApplet;

/**
 *
 * @author tayfun
 */
public class UploaderApplet extends JApplet {

    private DefaultConfigHolder configHolder = null;
    private MainContainer mainContainer = null;
    
    @Override
    public void init() {
        initApplet();
    }


    public void initApplet()
    {
        if(configHolder == null){
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
    
     private void prepareUI(){ 
         this.setLayout(new java.awt.GridLayout(1, 0));
         
         mainContainer = new MainContainer();
         mainContainer.setConfigHolder(this.configHolder);
         this.add(mainContainer);
     }
    
}
