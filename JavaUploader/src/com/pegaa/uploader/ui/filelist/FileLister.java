/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.ui.filelist;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.config.policy.ImageUploadPolicy;
import com.pegaa.uploader.config.policy.UploadPolicy;
import com.pegaa.uploader.event.FileListingListener;
import com.pegaa.uploader.event.ItemSelectionListener;
import com.pegaa.uploader.event.SelectedFileListListener;
import com.pegaa.uploader.ui.filelist.item.FileItem;
import com.pegaa.uploader.ui.filelist.item.ImageItem;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import com.pegaa.uploader.ui.filelist.item.ListItemUI;
import com.pegaa.uploader.ui.selectedfilelist.SelectedFileListModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author tayfun
 */
public class FileLister implements ItemSelectionListener, SelectedFileListListener{

    private ConfigHolder configHolder = null;
    
    /**
     *  FileItemUIs holder component 
     */
    private JPanel fileListPanel = null;
    /**
     * parent of fileListPanel
     */
    private JPanel parentPanel = null;
    
    /**
     *  Listeners of listing status 
     */
    private ArrayList<FileListingListener> fileListingListeners = null;
    
    /**
     *  Loads thumbnails of ListItemUIs thumbnail images and displays them
     */
    private ThumbLoaderThread thumbLoaderThread = null;
    
    /**
     * Internal list of active folders ListItemUIs for further use
     */
    private ArrayList<ListItemUI> fileListItemUIs = null;
    
    private UploadPolicy policy = null;
    
    private FileFilter fileFilter = null;
    
    /**
     *  SelectedFileListModel reference 
     */
    private SelectedFileListModel selectedFileListModel = null;
    
    public FileLister(ConfigHolder configHolder, JPanel fileListPanel, JPanel parentPanel)
    {
        this.configHolder = configHolder;
        this.fileListPanel = fileListPanel;
        this.parentPanel = parentPanel;
        this.fileListingListeners = new ArrayList<FileListingListener>(2);
        this.fileListItemUIs = new ArrayList<ListItemUI>(20);

        this.policy = (UploadPolicy)this.configHolder.getObject("global.policy");
        this.fileFilter = policy.getFileFilter();
    }
    
    /**
     *      Adds the listener of file listing events
     * 
     * @param l
     */
    public void addFileListingListener(FileListingListener l)
    {
        this.fileListingListeners.add(l);
    }
       
    /**
     *      
     * @param dir
     */
    public void listDirectory(File dir)
    {
        this.notifyStartedListing();
        //
        this.initSelectedFileListModel();
        this.stopThumbLoader();
        this.removeFileItemUIs();
        this.createAndAddFileItemUIs(dir);
        //
        this.notifyFinishedListing(this.fileListItemUIs.size());
    }
    
    private void stopThumbLoader()
    {
        if(this.thumbLoaderThread != null)
            this.thumbLoaderThread.stopLoadingThumbs();
    }
    
    /**
     *  we must cleanup listeners while removing old itemUI
     * 
     */
    private void removeFileItemUIs()
    {
        for(int i=0,len = this.fileListItemUIs.size(); i<len; i++)
        {
            this.fileListItemUIs.get(i).removeItemSelectionListeners();
        }
        this.fileListItemUIs = new ArrayList<ListItemUI>(20);
    }
    
    /**
     *  create fileItemUIs and add them to the fileList. We check if
     * ListItem is already exists in the selectedFileList and if exists
     * we use already added ListItem and set selected state of ListItemUI 
     * to true.Else we create brand new ListItem and use it.
     * 
     * 
     * @param dir
     */
    private void createAndAddFileItemUIs(File dir)
    {
         
         File files[] = dir.listFiles(fileFilter);
         if(files == null)return;
         int len = files.length;
         for(int i=0; i<len; i++)
         {
            ListItem item = null;
            ListItemUI itemUI = new ListItemUI();
            itemUI.setConfigHolder(configHolder);
            
            int ind = this.selectedFileListModel.contains(files[i]);
            
            if(ind == -1){
                //item = new ImageItem(this.configHolder, files[i]);
                item = this.createListItem(files[i]);
            }else{
                item = this.selectedFileListModel.getItem(ind);  
                itemUI.setSelected(true);
            }
            
            itemUI.setItem(item, policy.getPolicyType() == ImageUploadPolicy.POLICY_TYPE_IMG ? true : false);
            itemUI.addItemSelectionListener(this);
            
            this.fileListItemUIs.add(itemUI);
            this.notifyListItemAdded(itemUI);
            
         }

         if(len > 0 && policy.getPolicyType() == ImageUploadPolicy.POLICY_TYPE_IMG)
         {
             this.thumbLoaderThread = new ThumbLoaderThread();
             this.thumbLoaderThread.setListItemUIs(fileListItemUIs);
             this.thumbLoaderThread.start();
         }
         
    }
    
    private ListItem createListItem(File f)
    {
         if(this.policy.getPolicyType() == ImageUploadPolicy.POLICY_TYPE_IMG){
             return new ImageItem(this.configHolder, f);
         }else{
             return new FileItem(this.configHolder, f);
         }
    }
    
    
    /**
     *  get and set local instance of selectedFileListModel from config.
     */
    private void initSelectedFileListModel()
    {
         if(this.selectedFileListModel == null){
                this.selectedFileListModel = (SelectedFileListModel)this.configHolder.getObject("global.selected-file-list-model");
                this.selectedFileListModel.addSelectedFileListListener(this);
         }
    }
    
    /**
     *  Selectes all of the current files listed
     */
    public void selectAll()
    {
        int len = this.fileListItemUIs.size();
        for(int i=0; i<len; i++)
        {
            this.selectedFileListModel.add(this.fileListItemUIs.get(i).getItem());
        }
    }
    
    /**
     *  Removes all selected files.
     */
    public void removeAllSelected()
    {
        int len = this.fileListItemUIs.size();
        for(int i=0; i<len; i++)
        {
            ListItemUI itemUI = this.fileListItemUIs.get(i);
            if(itemUI.isSelected()){
                this.selectedFileListModel.remove(itemUI.getItem());
            }
        }        
    }
    
    
    private void notifyStartedListing()
    {
        int len = this.fileListingListeners.size();
        for(int i=0; i<len; i++)
        {
            this.fileListingListeners.get(i).listingStarted();
        }
    }
    
    private void notifyFinishedListing(int count)
    {
        int len = this.fileListingListeners.size();
        for(int i=0; i<len; i++)
        {
            this.fileListingListeners.get(i).listingFinished(count);
        }       
    }

    private void notifyListItemAdded(ListItemUI itemUI)
    {
        int len = this.fileListingListeners.size();
        for(int i=0; i<len; i++)
        {
            this.fileListingListeners.get(i).listItemAdded(itemUI);
        }            
    }
    
    /**
     *      Listens the file items for selection event and 
     * invokes SelectedFileListModel appropriate function.
     * 
     * @param item is current "event raiser" object
     */
    public void itemSelected(ListItem item) {
        this.selectedFileListModel.add(item);
    }

    public void itemUnSelected(ListItem item) {
        this.selectedFileListModel.remove(item);      
    }

    /**
     *  SelectedFileListModel listener
     * 
     * @param f
     */
    public void fileAdded(ListItem f) {
        int len = this.fileListItemUIs.size();
        for(int i=0; i<len; i++)
        {
            ListItem activeItem = this.fileListItemUIs.get(i).getItem();
            if(activeItem == f){
                    this.fileListItemUIs.get(i).setSelected(true);
            }
        }
    }

    public void fileRemoved(ListItem f) {
        int len = this.fileListItemUIs.size();
        for(int i=0; i<len; i++)
        {
            ListItem activeItem = this.fileListItemUIs.get(i).getItem();
            if(activeItem == f){
                    this.fileListItemUIs.get(i).setSelected(false);
            }
        }
    }

    public void itemUIMouseOverEvent(String s) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
