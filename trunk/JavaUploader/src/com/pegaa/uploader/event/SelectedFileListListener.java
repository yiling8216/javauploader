/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.event;

import com.pegaa.uploader.ui.filelist.item.ListItem;


/**
 *      ListModel 
 * 
 * @author tayfun
 */
public interface SelectedFileListListener {
    
    public void fileAdded(ListItem f);
    
    public void fileRemoved(ListItem f);
    
}
