/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.event;

import com.pegaa.uploader.ui.filelist.item.ListItemUI;

/**
 *
 * @author tayfun
 */
public interface FileListingListener {

    public void listingFinished(int count);
    
    public void listingStarted();
    
    public void listItemAdded(ListItemUI c);
}
