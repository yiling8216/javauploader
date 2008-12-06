/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.ui.filelist;

import com.pegaa.uploader.ui.filelist.item.ImageItem;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import com.pegaa.uploader.ui.filelist.item.ListItemUI;
import java.util.ArrayList;

/**
 *
 * @author tayfun
 */
public class ThumbLoaderThread extends Thread{

    /**
     *  ThumbnailLoader stop flag
     */
    private boolean cancelLoading = false;
    /**
     *  list of ListItemUIs 
     */
    private ArrayList<ListItemUI> listItemUIs = null;
    
    public ThumbLoaderThread()
    {
    }
    
    /**
     *  Sets the array of ListItemUIs, so that we can load itemUIs'
     * items if they are image.
     * 
     * @param listItemUIs
     */
    public void setListItemUIs(ArrayList<ListItemUI> listItemUIs)
    {
        this.listItemUIs = listItemUIs;
    }
    
    /**
     *  Stop active loading of images
     */
    public void stopLoadingThumbs()
    {
        this.cancelLoading = true;
    }
          
    @Override
    public void run()
    {
        int curIndex = 0;
        int len = this.listItemUIs.size();
        while(this.cancelLoading != true && curIndex<len)
        {
                ListItem item = this.listItemUIs.get(curIndex).getItem();
                ImageItem imgItem = (ImageItem)item;
                imgItem.init();
                curIndex++;
        }
        
    }
}
