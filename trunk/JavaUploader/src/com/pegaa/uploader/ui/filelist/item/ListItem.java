/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.ui.filelist.item;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.event.ListItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


/**
 *
 * @author tayfun
 */
public abstract class ListItem {

    private ConfigHolder configHolder = null;
    
    /* image to represent this item on file list */
    protected BufferedImage thumbImage = null;
    
    /**
     *  The file which this ListItem indicates
     */
    protected File file = null;
    /**
     *  cached presentation fileName of the file
     */
    private String fileName = "";

    
    private ArrayList<ListItemListener> listeners;
    
    /** Creates a new instance of ListItem */
    public ListItem(ConfigHolder configHolder, File file) {
        this.configHolder = configHolder;
        this.file = file;
        listeners = new ArrayList<ListItemListener>(2);
    }
    
    /**
     * Returns name of the inner file
     * @return
     */
    public String getName(){
        return this.fileName;
    }
    
    /**
     * Notifies listeners for image update events
     */
    public void notifyListeners(){
        int size = listeners.size();
        for(int i=0; i<size; i++){
            listeners.get(i).itemImageUpdated();
        }
    }
    
    public void addListItemListener(ListItemListener l){
        listeners.add(l);
    }
    
    public void removeListItemListener(ListItemListener l){
        listeners.remove(l);
    }
    
    /**
     *  
     */
    public BufferedImage getThumbImage(){    
        return thumbImage;
    }
    
    /**
     *  inits thumbnail of this file
     */
    public void init(){
       
    }
        
    /**
     *  gets the internal file
     * 
     * @return
     */
    public File getFile()
    {
        return this.file;
    }
    

    public String getPath(){
         return this.file.getAbsolutePath();
    }
    
}
