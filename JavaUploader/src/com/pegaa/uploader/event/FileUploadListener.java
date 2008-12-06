/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.event;

/**
 *
 * @author tayfun
 */
public interface FileUploadListener {

    public void uploadStarted(long fileSize);
    
    public void fileReaded(int readed);
    
    public void uploadFinished(int status);
    
}
