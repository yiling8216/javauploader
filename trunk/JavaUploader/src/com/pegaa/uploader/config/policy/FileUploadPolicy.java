/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.config.policy;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.common.CustomFileFilter;
import com.pegaa.uploader.config.DefaultParameters;
import com.pegaa.uploader.sender.InputStreamInfo;
import com.pegaa.uploader.tools.CustomLog;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 *
 * @author tayfun
 */
public class FileUploadPolicy extends UploadPolicy{

    /* FileUpload policy type flag */
    public static final int POLICY_TYPE_FILE = 2;
    
    public FileUploadPolicy(ConfigHolder configHolder)
    { 
         super(configHolder);
    }
    
    /**
     * 
     * 
     * @param targetID
     * @return
     */
    @Override
    public String getPostURL(ListItem item, String targetID) {
        /*
          Raz - This was missing hence why it fail to upload file over
        */
        String uploadHandlerUrl = (String)this.configHolder.getObject("global.uploadHandlerUrl");
        uploadHandlerUrl += targetID;
        CustomLog.log("UploadPolicy.getPostURL.uploadHandlerUrl = " + uploadHandlerUrl);
        return uploadHandlerUrl;

    }

    /**
     * FileUploadPolicy treats all items as files so we cannot apply 
     * 
     * @param item
     * @return
     * @throws java.io.FileNotFoundException
     */
    @Override
    public InputStreamInfo getInputStream(ListItem item) throws FileNotFoundException{
        FileInputStream fis = null;
        InputStreamInfo info = null;
        
        try {
            fis = new FileInputStream(item.getFile());
            info = new InputStreamInfo(fis, item.getFile().length());
            return info;
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException();
        }
    }

    @Override
    public FileFilter getFileFilter() {
        if(filter != null){
             return filter;
        }
        filter = new CustomFileFilter();
        String fileExtensions = (String)this.configHolder.getObject("filefilter.extensions");
        
        //if any extension given we use them
        if(fileExtensions != null){
            String[] extensions = fileExtensions.split(",");
            /*
              Raz - This line dont work for some reason, so lets try the same code for image
            */
            for(int i=0; i<DefaultParameters.MAX_EXTENSION_COUNT && i<extensions.length; i++){
                filter.addExtension(extensions[i]);
            }
         }
        
        return filter;
    }
    
    @Override
    public int getPolicyType()
    {
        return POLICY_TYPE_FILE;
    } 
}
