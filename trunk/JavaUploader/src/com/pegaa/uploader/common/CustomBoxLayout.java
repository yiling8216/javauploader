/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

/**
 *  List components one per row
 * 
 * @author tayfun
 */
public class CustomBoxLayout implements LayoutManager{
    
    private int DEFAULT_HEIGHT = 60;
    private int gap = 2; 
    
    /** Creates a new instance of CustomGridLayout */
    public CustomBoxLayout() {
    }
   
    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {    
            Insets insets = parent.getInsets();
            Rectangle r = parent.getBounds();
            int count = parent.getComponentCount();
            
            if(count > 0){
                int calcWidth = r.width;
                int calc = count;
                int calcHeight = calc * (DEFAULT_HEIGHT + gap);
                return new Dimension(calcWidth + insets.left + insets.right, calcHeight + insets.top + insets.bottom);
            }else{
                return new Dimension(200, 200);    
            }
        }
    }

    public Dimension minimumLayoutSize(Container parent) {
         return new Dimension(200, 200);
    }

    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Rectangle r = parent.getBounds();
            r.width -= 2;
            int count = parent.getComponentCount();
            
            if(count > 0){                 
                
                for(int i=0; i<count; i++){
                    int top = (i * DEFAULT_HEIGHT) + gap ;
                    Component c = parent.getComponent(i);
                    c.setBounds(0, top , r.width, DEFAULT_HEIGHT);                   
                } 
                
            }
        }
    }
}
