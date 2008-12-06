/*
 * WindowsIconRenderer.java
 *
 * Created on 18 Temmuz 2007 Çarşamba, 17:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.yugruk.chooser;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author tayfun
 */
public class UnixIconRenderer extends DefaultTreeCellRenderer {
    
    private FileSystemView view;
    //private Icon folder_icon =  ImageIconLoader.getInstance().getIcon("unix-folder.png");
    private Icon computer_icon =ImageIconLoader.getInstance().getIcon("unix-computer.gif");
    //private Icon home_icon = ImageIconLoader.getInstance().getIcon("unix-userhome.png"); 
    
    /** Creates a new instance of WindowsIconRenderer */
    public UnixIconRenderer(FileSystemView view) {
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
            
            if(isRoot(value)){
               setIcon(computer_icon);
            }else{
               if(value instanceof CustomTreeNode){
               	  CustomTreeNode node0 = (CustomTreeNode)value;
               	  setIcon(view.getSystemIcon(node0.getFile()));
               }
            }
            
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
    
    private boolean isRoot(Object node){
        DefaultMutableTreeNode node0 = (DefaultMutableTreeNode)node;
        return node0.isRoot();
    }
    
    /*
    private boolean isHomeDirectory(Object node){
        if(node instanceof CustomTreeNode){
            CustomTreeNode node0 = (CustomTreeNode)node;
            if(node0.isExpanded()){
                return false;
            }
            File homeDir = view.getHomeDirectory();
            if(node0.getFile().getAbsolutePath().equals(homeDir.getAbsolutePath())){
               return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    */
    
}
