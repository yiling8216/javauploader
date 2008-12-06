/*
 * MonitoredInputStream.java
 *
 * Created on 24 Haziran 2007 Pazar, 00:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pegaa.uploader.sender;

import com.pegaa.uploader.event.FileUploadListener;
import com.pegaa.uploader.tools.CustomLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author tayfun
 */
public class MonitoredInputStream extends InputStream{
    
    private InputStream source;
    private ArrayList<FileUploadListener> fileUploadListener = null;
    private int readed = 0;
    private int listenerSize = 0;
    
    
    /** Creates a new instance of MonitoredInputStream */
    public MonitoredInputStream(InputStream source, ArrayList<FileUploadListener> fileUploadListener) {
        this.source = source;
        this.fileUploadListener = fileUploadListener;
        this.listenerSize = this.fileUploadListener.size(); /* cache listener size */
    }

    /**
     *  Notify listeners for file read event
     * 
     * @param readed
     */
    private void notifyListeners(int readed)
    {
        //CustomLog.log("MonitoredInputStream.notifyListeners readed = " + readed);
        
        for(int i=0; i<this.listenerSize; i++)
        {
            this.fileUploadListener.get(i).fileReaded(readed);
        }
    }
    
    public int read() throws IOException {
        readed = source.read();
        this.notifyListeners(readed);
        return readed;
    }
    
    @Override
    public int read(byte[] b) throws IOException{
        readed = source.read(b);
        this.notifyListeners(readed);
        return readed;
    }
    
    @Override
    public int read(byte[] b, int offset, int len) throws IOException{
        readed = source.read(b, offset, len);
        this.notifyListeners(readed);
        return readed;
    }
    
}
