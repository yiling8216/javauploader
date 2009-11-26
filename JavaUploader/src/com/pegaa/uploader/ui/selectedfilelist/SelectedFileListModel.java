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

    private Integer maxFileCount = null;

    private ArrayList<ListItem> listItems = null;
    public ArrayList<SelectedFileListListener> listeners = null;
    
    public SelectedFileListModel()
    {
        this.listItems = new ArrayList<ListItem>(10);
        this.listeners = new ArrayList<SelectedFileListListener>(2);
    }

    /**
     * Optional parameter, that limits the max selected file size
     *
     * @param maxFileCount
     */
    public void setMaxFileCount(Integer maxFileCount)
    {
        this.maxFileCount = maxFileCount;
    }

    public void addSelectedFileListListener(SelectedFileListListener l)
    {
        this.listeners.add(l);
    }

    /**
     * This method first checks if max file count reached if max is reached it
     * returns false to indicate this, if max is not reached it adds the file to
     * list.
     *
     * @param f
     * @return
     */
    public boolean add(ListItem f)
    {
        if(isMaxFileCountReached()){
            return false;
        }
        int ind = this.contains(f);
        if(ind == -1){
            this.listItems.add(f);
            this.notifyListeners(FILE_ADDED, f);
            return true;
        }
        return false;
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

    /**
     * If max file count is set, this functions checks if list size is reached
     * to max file count
     * 
     * @return
     */
    private boolean isMaxFileCountReached()
    {
        if(this.maxFileCount != null)
        {
            if(this.getSize() == this.maxFileCount){
                return true;
            }
        }
        return false;
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
