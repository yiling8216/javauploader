/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.ui.selectedfilelist;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.config.policy.ImageUploadPolicy;
import com.pegaa.uploader.config.policy.UploadPolicy;
import com.pegaa.uploader.event.ItemSelectionListener;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import com.pegaa.uploader.event.SelectedFileListListener;
import com.pegaa.uploader.ui.filelist.item.ListItemUI;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 *
 * @author tayfun
 */
public class SelectedFileLister implements SelectedFileListListener, ItemSelectable, ItemSelectionListener{

    public static final int EVENT_ADD = 1;
    public static final int EVENT_REMOVE = 2;
    
    private ConfigHolder configHolder = null;
    private SelectedFileListModel selectedFileListModel = null;
    private ArrayList<ListItemUI> listItemUIs = null;
    private ArrayList<java.awt.event.ItemListener> fileSelectionListeners = null;
    
    public SelectedFileLister(ConfigHolder configHolder, SelectedFileListModel selectedFileListModel)
    {
        this.configHolder = configHolder;
        this.selectedFileListModel = selectedFileListModel;
        this.selectedFileListModel.addSelectedFileListListener(this);
        listItemUIs = new ArrayList<ListItemUI>(20);
        fileSelectionListeners = new ArrayList<java.awt.event.ItemListener>(2);
    }
    
    public void addFileSelectionListeners(ItemListener l)
    {
        this.fileSelectionListeners.add(l);
    }
    
    public void itemSelected(ListItem item) {
        this.selectedFileListModel.add(item);
    }

    public void itemUnSelected(ListItem item) {
        this.selectedFileListModel.remove(item);
    }
    
    /**
     *      SelectedFileListModel event fileAdded indicates that a new ListItem
     * is added to list
     * 
     * @param f
     */
    public void fileAdded(ListItem f) {
        UploadPolicy policy = (UploadPolicy)this.configHolder.getObject("global.policy");
        ListItemUI itemUI = new ListItemUI();
        itemUI.setItem(f, policy.getPolicyType() == ImageUploadPolicy.POLICY_TYPE_IMG ? true : false);
        itemUI.setSelected(true);
        itemUI.addItemSelectionListener(this);
        
        this.notifyFileAdded(itemUI);
        this.listItemUIs.add(itemUI);
    }

    public void fileRemoved(ListItem f) {
        
        int len = this.listItemUIs.size();
        
        for(int i=0; i<len; i++)
        {
            if(this.listItemUIs.get(i).getItem() == f)
            {
                this.notifyFileRemoved(this.listItemUIs.get(i));
                this.listItemUIs.get(i).removeItemSelectionListeners();
                this.listItemUIs.remove(i);
                len--;
            }
        }
    }   

    /**
     *  we notify UI presentation of ListItemUIs so that UI will reflect
     * SelectedFileListModel's current list.
     * 
     * @param itemUI
     */
    private void notifyFileAdded(ListItemUI itemUI)
    {
        int len = this.fileSelectionListeners.size();
        for(int i=0; i<len; i++)
        {
            this.fileSelectionListeners.get(i).itemStateChanged(new ItemEvent(this, EVENT_ADD, itemUI, 0));
        }
    }
    
    private void notifyFileRemoved(ListItemUI itemUI)
    {
        int len = this.fileSelectionListeners.size();
        for(int i=0; i<len; i++)
        {
            this.fileSelectionListeners.get(i).itemStateChanged(new ItemEvent(this, EVENT_REMOVE, itemUI, 0));
        }
    }

    public Object[] getSelectedObjects() {
        //empty
        return null;
    }

    public void addItemListener(ItemListener l) {
        //empty
    }

    public void removeItemListener(ItemListener l) {
        //empty
    }

    public void itemUIMouseOverEvent(String s) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }


}
