/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.sender;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.config.policy.UploadPolicy;
import com.pegaa.uploader.event.FileUploadListener;
import com.pegaa.uploader.tools.CustomLog;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import java.util.ArrayList;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;

/**
 *
 * @author tayfun
 */
public class Sender extends Thread{

    private final int EVT_STARTED = 1;
    private final int EVT_FINISHED = 2;
    
    private ConfigHolder configHolder = null;
    private ListItem item = null;
    private ArrayList<FileUploadListener> fileUploadListeners = null;
    private PostMethod activeMethod = null;
    private UploadPolicy policy = null;
    private String targetID = null;
    
    public Sender(ConfigHolder configHolder, String targetID, ListItem item)
    {
        this.configHolder = configHolder;
        this.item = item;
        this.targetID = targetID;
        this.fileUploadListeners = new ArrayList<FileUploadListener>(2);
        this.policy = (UploadPolicy)this.configHolder.getObject("global.policy");
    }
    
    /**
     *  Adds FileUploadListener to this object, and this later adds these 
     * listeners to MemoryFilePartSource object.
     * @param l
     */
    public void addFileUploadListener(FileUploadListener l)
    {       
        this.fileUploadListeners.add(l);
    }
    
    /**
     *  Aborts this upload
     */
    public void abort()
    {
        this.activeMethod.abort();
    }
    
    /**
     *  add file upload listeners to the newly created part source
     */
    private void initFileUploadListeners(MemoryFilePartSource mfps)
    {
        int len = this.fileUploadListeners.size();
        for(int i=0; i<len; i++)
        {           
            mfps.addFileUploadListener(this.fileUploadListeners.get(i));
        }
    }
    
    private void removeFileUploadListeners(PartSource mfps)
    {
        MemoryFilePartSource mfps0 = (MemoryFilePartSource)mfps;
        mfps0.removeFileUploadListeners();
    }
    
    /**
     *  
     * @return
     */
    private PartSource getFilePartSource()
    {
        MemoryFilePartSource mfps = new MemoryFilePartSource(this.configHolder, this.item);
        this.initFileUploadListeners(mfps);
        mfps.init();
        return (PartSource)mfps;
    }
    
    /**
     *  Notify upload started or finished event
     * 
     * @param event
     * @param length
     */
    private void notifyListeners(int event, long length, int status)
    {
        int len = this.fileUploadListeners.size();
        for(int i=0; i<len; i++)
        {
            FileUploadListener l = this.fileUploadListeners.get(i);
            if(event == EVT_STARTED) //start event
            {
                l.uploadStarted(length);
            }else{
                l.uploadFinished(status);
            }
        }
    }
    
    @Override
    public void run()
    {       
        String fullPostURL = policy.getPostURL(item, this.targetID);  
        
        CustomLog.log("Sender.run fullPostURL=" + fullPostURL);
        
        activeMethod = new PostMethod(fullPostURL);
        /* Set HTTP Parameter Cookie to the user provided values (actually current session) */
        activeMethod.setRequestHeader("Cookie", policy.getSessionString());
        
        PartSource fps = this.getFilePartSource();
        String fileName = fps.getFileName();
        Part[] parts = {new FilePart(fileName, fps, null, "UTF-8")};

        /* Raise upload started event */
        this.notifyListeners(EVT_STARTED, fps.getLength(), 0);
        
        try{
           activeMethod.setRequestEntity(new MultipartRequestEntity(parts, activeMethod.getParams()));            
           
           HttpClient client = new HttpClient();   
           int status = client.executeMethod(activeMethod);
           
        }catch(Exception e){
           e.printStackTrace();
        }finally{
           activeMethod.releaseConnection();
        } 
        
        /* Raise upload finished event */
        this.notifyListeners(EVT_FINISHED, 0, 0);
        /* remove listeners from this part source */
        removeFileUploadListeners(fps);
    }
    
}
