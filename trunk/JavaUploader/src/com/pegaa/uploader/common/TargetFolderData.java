/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.common;

/**
 *
 * @author tayfun
 */
public class TargetFolderData {

    private String targetFolderName = null;
    private String targetFolderID = null;
    
    /**
     * 
     * @param targetFolderDesc
     */
    public TargetFolderData(String targetFolderDesc)
    {
        if(targetFolderDesc != null)
        {
            int index = targetFolderDesc.indexOf(":");
            this.targetFolderID = targetFolderDesc.substring(0, index);
            this.targetFolderName = targetFolderDesc.substring(index+1);
        }
    }
    
    public String getTargetFolderID(){
        return this.targetFolderID;
    }
    
    public String getTargetFolderName(){
        return this.targetFolderName;
    }
    
    public void setText(String s){
        this.targetFolderName = s;
    }
    
    @Override
    public String toString(){
        return this.targetFolderName;
    }
}
