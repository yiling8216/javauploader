/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.ui.filelist.item;

import com.pegaa.uploader.config.ConfigHolder;
import java.io.File;

/**
 *
 * @author tayfun
 */
public class FileItem extends ListItem{
    
    public FileItem(ConfigHolder configHolder, File file)
    {
        super(configHolder, file);
    }
}
