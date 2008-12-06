/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.tools;

/**
 *
 * @author tayfun
 */
public class CustomLog {
    
    /* mode == 0 no output, 1 to console */
    private static int mode = 0;
            
    private CustomLog()
    {
        
    }
    
    /**
     * We can enable or disable custom log when initing applet
     * 
     * @param mode
     */
    public static void setMode(int mode)
    {
        CustomLog.mode = mode;
    }
    
    public static void log(String s)
    {
        if(mode != 0)
        {
            System.out.println(s);
        }
    }
}
