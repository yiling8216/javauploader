/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.ui.selectedfilelist;

import com.pegaa.uploader.event.SelectedFileListListener;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author tayfun
 */
public class SelectedFileListModel {

    public static final int FILE_ADDED = 1;
    public static final int FILE_REMOVED = 2;
    
    public ArrayList<ListItem> listItems = null;
    public ArrayList<SelectedFileListListener> listeners = null;
    
    public SelectedFileListModel()
    {
        this.listItems = new ArrayList<ListItem>(10);
        this.listeners = new ArrayList<SelectedFileListListener>(2);
    }
    
    public void addSelectedFileListListener(SelectedFileListListener l)
    {
        this.listeners.add(l);
    }
    
    public void add(ListItem f)
    {
        int ind = this.contains(f);
        if(ind == -1){
            this.listItems.add(f);
            this.notifyListeners(FILE_ADDED, f);
        }
    }
    
    public void remove(ListItem f)
    {
        int ind = this.contains(f);
        if(ind >= 0){
            this.listItems.remove(ind);
            this.notifyListeners(FILE_REMOVED, f);
        }
    }
    
    public int getSize()
    {
        return this.listItems.size();
    }
    
    public ListItem getItem(int i)
    {
        return this.listItems.get(i);
    }
    
    /**
     * Checks if item is in list if exists returns index of item else
     * returns -1 to indicate that item is not in list.
     * 
     * 
     * @param item
     * @return
     */
    public int contains(ListItem item)
    {
        return this.contains(item.getFile());
    }
    
    public int contains(File f)
    {
        int i = -1;
        int index = -1;
        int len = this.listItems.size();
        if(len == 0)return index; //to prevent i being zero if len == 0 (if len == 0 we must return -1)
        String fileAbsPath = f.getAbsolutePath(); //cache path string to speed equation control
        for(i = 0; i<len; i++){
            if(this.listItems.get(i).getFile().getAbsolutePath().equals(fileAbsPath)){
                    index = i;
                    break;
            } 
        }
        return index;
    }   
    
    private void notifyListeners(int action, ListItem f)
    {
        int len = this.listeners.size();
        for(int i=0; i<len; i++){
            SelectedFileListListener l = this.listeners.get(i);
            if(action == FILE_ADDED){
                l.fileAdded(f);
            }else{
                l.fileRemoved(f);
            }
        }
    }  
}
