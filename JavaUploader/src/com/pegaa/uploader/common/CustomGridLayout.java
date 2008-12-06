/*
 * CustomGridLayout.java
 *
 * Created on 17 Temmuz 2007 Salı, 01:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pegaa.uploader.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 *
 * @author tayfun
 */
public class CustomGridLayout implements LayoutManager {
    
    private int COMPONENT_PER_ROW = 3;
    private int DEFAULT_WIDTH = 160;
    private int DEFAULT_HEIGHT = 125;
    private int gap = 2; 
    
    
    /** Creates a new instance of CustomGridLayout */
    public CustomGridLayout() {
    }
    
    
    public CustomGridLayout(int element_width, int element_height) {
        this.DEFAULT_WIDTH = element_width;
        this.DEFAULT_HEIGHT = element_height;
    }

    private void calculateComponentPerRow(Component comp){ 
         int width = comp.getWidth();
         int result = width / DEFAULT_WIDTH;
         
         if(result >= 1){
            COMPONENT_PER_ROW = width / DEFAULT_WIDTH;
         }else{
            COMPONENT_PER_ROW = 3;
         }
    }
    
    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) 
        {    
            Insets insets = parent.getInsets();
            int count = parent.getComponentCount();
            
            if(count > 0){
                int calcWidth = COMPONENT_PER_ROW * (DEFAULT_WIDTH + gap);
                int calc = count / COMPONENT_PER_ROW;
                if((count % COMPONENT_PER_ROW) > 0){
                    calc++;
                }
                int calcHeight = calc * (DEFAULT_HEIGHT + gap);
                
                int newWidth = calcWidth + insets.left + insets.right;
                int newHeight = calcHeight + insets.top + insets.bottom;
                return new Dimension(newWidth, newHeight);
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
            calculateComponentPerRow(parent);
            Insets insets = parent.getInsets();
            int count = parent.getComponentCount();
            if(count > 0){
                if(count > COMPONENT_PER_ROW){
                    int comp_count = 0;
                    int row_count = count / COMPONENT_PER_ROW;
                    int left_count = count % COMPONENT_PER_ROW;
                    for(int i=0; i<row_count; i++){
                        int top = i * (DEFAULT_HEIGHT + gap);
                        for(int j=0; j<COMPONENT_PER_ROW; j++){
                            Component c = parent.getComponent(comp_count);
                            c.setBounds((j * (DEFAULT_WIDTH + gap)) + insets.left + gap, top + insets.top + gap, DEFAULT_WIDTH, DEFAULT_HEIGHT);
                            comp_count++;
                        }
                    }
                    int top = row_count * (DEFAULT_HEIGHT + gap);
                    for(int i=0; i<left_count; i++){
                        Component c = parent.getComponent(comp_count);
                        c.setBounds((i * (DEFAULT_WIDTH + gap)) + insets.left + gap, top + insets.top + gap, DEFAULT_WIDTH, DEFAULT_HEIGHT);
                        comp_count++;                    
                    }
                }else{
                    for(int i=0; i<count; i++) {
                        Component c = parent.getComponent(i);
                        c.setBounds( (i * (DEFAULT_WIDTH + gap)) + insets.left + gap, insets.top + gap, DEFAULT_WIDTH, DEFAULT_HEIGHT);                
                    }
                }
            }
        }
    }
    
}
