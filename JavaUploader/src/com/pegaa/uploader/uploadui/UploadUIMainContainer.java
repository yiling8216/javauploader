/*
 * UploadMainContainer.java
 *
 * Created on 12 Haziran 2008 Perşembe, 21:30
 */

package com.pegaa.uploader.uploadui;

import com.pegaa.uploader.common.CustomBoxLayout;
import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.event.FileUploadListener;
import com.pegaa.uploader.lang.Lang;
import com.pegaa.uploader.tools.CustomLog;
import com.pegaa.uploader.ui.selectedfilelist.SelectedFileListModel;
import java.util.ArrayList;
import netscape.javascript.JSObject;

/**
 *     Displays current uploading info.
 * 
 * @author  tayfun
 */
public class UploadUIMainContainer extends javax.swing.JPanel implements FileUploadListener{
    
    private ConfigHolder configHolder = null;
    private SelectedFileListModel model = null;
    
    /**/
    private ArrayList<UploadItemUI> uploadItemUIs = null;
    private int curIndex = 0;
    private String targetID = null;
    
    /* cancel upload flag */
    private boolean cancelUpload = false;
    /* current uplaod item to abort it */
    private UploadItemUI activeItem = null;
    
    /** Creates new form UploadMainContainer */
    public UploadUIMainContainer() {
        initComponents();
        uploadItemUIs = new ArrayList<UploadItemUI>(2);
    }
    
    public void setConfigHolder(ConfigHolder configHolder)
    {
        this.configHolder = configHolder;
        updateStrings();
    }
    
    /**
     * Sets model of this component, model holds informations about all selected
     * file items.
     * 
     * @param model
     */
    public void setModel(SelectedFileListModel model)
    {
        this.model = model;
        this.updateComponents();
    }
    
    private void updateStrings()
    {
        Lang lang = (Lang)this.configHolder.getObject("global.lang");
        this.buttonStop.setText((String)lang.get("uploaderui.stop"));
        this.buttonContinue.setText((String)lang.get("uploaderui.continue"));
        this.buttonUploadAgain.setText((String)lang.get("uploaderui.uploadagain"));
    }
    
    /**
     * Sets the selected folder ID.
     * 
     * @param targetID
     */
    public void setTargetFolderID(String targetID)
    {
        this.targetID = targetID;
    }
    
    /**
     *  creates itemUIs, which show items' information and upload status.
     * UploadItemUIs created for every file items.
     */
    private void updateComponents()
    {
        int len = this.model.getSize();      
        CustomBoxLayout layout = new CustomBoxLayout();
        this.jPanel2.setLayout(layout);
        
        for(int i=0; i<len; i++)
        {           
            UploadItemUI item = new UploadItemUI();
            item.setConfigHolder(configHolder);
            item.setItem(this.model.getItem(i));
            /* we add listener to all file items, but only
             * active file items will raise events.
             * 
             */
            item.addFileUploadListener(this);
           
            this.jPanel2.add(item);
            uploadItemUIs.add(item);
            
        }
        this.jPanel2.revalidate();
        this.jPanel2.repaint();
    }
    
    public void startUpload()
    {
        this.buttonContinue.setEnabled(false);
        this.buttonUploadAgain.setEnabled(false);
        curIndex = 0;
        this.runNext();
    }
    
    /**
     *  Starts next file upload
     * 
     */
    private void runNext()
    {      
        if(cancelUpload != true && curIndex < this.uploadItemUIs.size())
        {
            CustomLog.log("UploadUIMainContainer.runNext targetID=" + targetID);
            
            UploadItemUI item = this.uploadItemUIs.get(curIndex);
            item.setTargetFolderID(targetID);
            activeItem = item;
            item.startUpload();
            curIndex++;
        }else{
            if(curIndex == this.uploadItemUIs.size() || cancelUpload == true){
                this.buttonStop.setEnabled(false);
                this.buttonContinue.setEnabled(true);
                this.buttonUploadAgain.setEnabled(true);
            }
        }
    }

    /**
     * When user clicks continue button we should redirect user to somewhere
     * with this function we call javascript's "relocate" function, relocate
     * function accepts two parameter first is action which are "gofolder" or
     * "again" and second is "target folder id"
     */
    public void doAfterUploadProcess()
    {
        JSObject jso = (JSObject)this.configHolder.getObject("global.jso");
        try{
            jso.call("JUP_eventhandler", new String[]{"gofolder", targetID});
        }catch(Exception e){
            e.printStackTrace(); 
        }      
    }
    
    private void uploadAgain()
    {
        JSObject jso = (JSObject)this.configHolder.getObject("global.jso");
        try{
            jso.call("JUP_eventhandler", new String[]{"again", targetID});
        }catch(Exception e){
            e.printStackTrace(); 
        }  
    }
    
    public void uploadStarted(long fileSize) {
        
    }

    public void fileReaded(int readed) {
        
    }

    /**
     *   
     * @param status
     */
    public void uploadFinished(int status) {
        this.runNext();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonStop = new javax.swing.JButton();
        buttonUploadAgain = new javax.swing.JButton();
        buttonContinue = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        buttonStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonStopMouseClicked(evt);
            }
        });
        jPanel1.add(buttonStop);

        buttonUploadAgain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonUploadAgainMouseClicked(evt);
            }
        });
        jPanel1.add(buttonUploadAgain);

        buttonContinue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonContinueMouseClicked(evt);
            }
        });
        jPanel1.add(buttonContinue);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(null);
        jScrollPane1.setViewportView(jPanel2);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonStopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonStopMouseClicked
        this.cancelUpload = true;
        if(activeItem != null){
            activeItem.cancelUpload();
        }
    }//GEN-LAST:event_buttonStopMouseClicked

    private void buttonContinueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonContinueMouseClicked
        doAfterUploadProcess();
    }//GEN-LAST:event_buttonContinueMouseClicked

    private void buttonUploadAgainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonUploadAgainMouseClicked
       uploadAgain();
    }//GEN-LAST:event_buttonUploadAgainMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonContinue;
    private javax.swing.JButton buttonStop;
    private javax.swing.JButton buttonUploadAgain;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables


    
}
