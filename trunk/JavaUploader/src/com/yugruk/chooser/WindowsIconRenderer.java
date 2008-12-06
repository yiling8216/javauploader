/*
 * WindosIconRenderer.java
 *
 * Created on 18 Temmuz 2007 Çarşamba, 17:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.yugruk.chooser;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
/**
 *
 * @author tayfun
 */
public class WindowsIconRenderer  extends DefaultTreeCellRenderer{

    private FileSystemView view;
     
    /** Creates a new instance of WindosIconRenderer */
    public WindowsIconRenderer(FileSystemView view) {
        this.view = view;
    }
    
    public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus) {

            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);

            //Expanded flagini tutan nesne ise islem yapma
            if(isExpansionFlag(value)){
                setIcon(null);
                return this;
            }
            setFileSystemIcon(value);
            return this;
    }
    
    private boolean isExpansionFlag(Object node){
    	if(node instanceof CustomTreeNode){
            CustomTreeNode node0 = (CustomTreeNode)node;
            if(node0.isExpanded()){
                return true;
            }else{
                return false;
            }
    	}else{
    		return false;
    	}
    }
    
    /**
     *  isletim sisteminden klasore ait ikon getirilir.
     * @param node
     */
    private void setFileSystemIcon(Object node){
        if(node instanceof CustomTreeNode){
        	CustomTreeNode node0 = (CustomTreeNode)node;
        	File active_file = node0.getFile();
        	setIcon(view.getSystemIcon(active_file));
        }   	
    } 
}
