/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.config.policy;

import com.pegaa.uploader.common.CustomFileFilter;
import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.sender.InputStreamInfo;
import com.pegaa.uploader.tools.CustomLog;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import javax.swing.JApplet;

/**
 *  This interface will explain all the base functions that will be used by
 * classes.
 * 
 * 
 * @author tayfun
 */
public abstract class UploadPolicy {

    protected ConfigHolder configHolder = null;
    protected JApplet applet = null;
    protected CustomFileFilter filter = null;
    
    public UploadPolicy(ConfigHolder configHolder)
    {
        this.configHolder = configHolder;
        this.applet = (JApplet)this.configHolder.getObject("global.applet");
    }
    
    /**
     * Returns full target upload URL, this function is called from
     * Sender.java's run method.
     * 
     * @param targetID Target folder's ID
     * @return
     */
    public String getPostURL(ListItem item, String targetID)
    {
        String uploadHandlerUrl = (String)this.configHolder.getObject("global.uploadHandlerUrl");
        uploadHandlerUrl += targetID;
        CustomLog.log("UploadPolicy.getPostURL.uploadHandlerUrl = " + uploadHandlerUrl);
        return uploadHandlerUrl;
    }
    
    /**
     *  Returns session-id cookie parameter
     * 
     * @return
     */
    public String getSessionString()
    {
        String sessionString = (String)this.configHolder.getObject("global.session-string");
        return sessionString; 
    }
    
    /**
     *  Returns an InputStream object of given file. InputStream either directly
     * created from file or dynamically in memory.
     * 
     * @param f 
     * @return
     */
    public InputStreamInfo getInputStream(ListItem item) throws FileNotFoundException{
        return null;
    }
    
    /**
     * Returns FileFilter to be used by FileLister, FileFilter lists only the
     * files FileFilter configured.
     * 
     * 
     * @return
     */
    public FileFilter getFileFilter(){
        return null;
    }
    
    /**
     * Returns the policy type. This function is intended to be used by file
     * lister classes (FileLister.java etc.), so if policy type is image we
     * can show file's image, if policy is file we can show an icon for the
     * files by the help of their extensions.
     * 
     * @return
     */
    public int getPolicyType()
    {
        return 0;
    }
}
