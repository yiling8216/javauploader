/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.event;

import com.pegaa.uploader.ui.filelist.item.ListItem;

/**
 *
 * @author tayfun
 */
public interface ItemSelectionListener {
        
        public void itemSelected(ListItem item);
    
        public void itemUnSelected(ListItem item);
        
        public void itemUIMouseOverEvent(String s);
}
