/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.sender;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.config.policy.UploadPolicy;
import com.pegaa.uploader.event.FileUploadListener;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.commons.httpclient.methods.multipart.PartSource;

/**
 *
 * @author tayfun
 */
public class MemoryFilePartSource implements PartSource{

    private ConfigHolder configHolder = null;
    /* listeners of file upload events (bytes send etc.) */
    private ArrayList<FileUploadListener> fileUploadListeners;
    private ListItem item = null;
    
    /* Created from file */
    private InputStreamInfo info = null;
    /* Monitored InputStream created from file's InputStream */
    private InputStream is = null;
    
    public MemoryFilePartSource(ConfigHolder configHolder, ListItem item)
    {
        this.configHolder = configHolder;
        this.item = item;
        this.fileUploadListeners = new ArrayList<FileUploadListener>(2);
    }
    
    /**
     *  Inits input stream of this file
     */
    public void init()
    {
        this.is = this.getMonitoredInputStreamOfFile(this.item);
    }
    
    /**
     *  adds FileUploadListener which listens for read events
     * 
     * @param l
     */
    public void addFileUploadListener(FileUploadListener l)
    {
        this.fileUploadListeners.add(l);
    }

    public void removeFileUploadListeners()
    {
        this.fileUploadListeners.clear();
    }
    
    /**
     *  Creates a Monitored Input Stream of given file by the help of
     * active policy.
     * 
     * @return
     */
    private InputStream getMonitoredInputStreamOfFile(ListItem item)
    {
        try {
            UploadPolicy policy = (UploadPolicy) this.configHolder.getObject("global.policy");
            /* return inputstream created by policy */
            info = policy.getInputStream(item);
            /* add layer to he original inputstream so that we can monitor inputstream's 
             * events.
             */
            MonitoredInputStream mis = new MonitoredInputStream(info.getIs(), this.fileUploadListeners);
            return (InputStream)mis;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *  returns the length of file
     * 
     * @return
     */
    public long getLength() {
        return this.info.getLength();
    }

    /**
     *  returns the filename
     * 
     * @return
     */
    public String getFileName() {
        return this.item.getFile().getName();
    }

    public InputStream createInputStream() throws IOException {
        return this.is;
    }
    
}
