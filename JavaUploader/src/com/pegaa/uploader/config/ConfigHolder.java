/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.config;

import java.util.HashMap;
import javax.swing.JApplet;

/**
 * Holds the applet configuration and objects. This class is the most important
 * class of the system because this class provides critical parameters, with 
 * these parameters we can change whole system's behaviour.
 * 
 * @author tayfun
 */
public abstract class ConfigHolder {

    protected HashMap<String, Object> map;
    
    public ConfigHolder()
    {
            map = new HashMap<String, Object>(50);
    }
    
    public void add(String name, Object o)
    {
        this.map.put(name, o);
    }
    
    /**
     *      This function retrieves the object from per-applet-instance-global
     * map, so that objects can be holded in a common per-applet-instance global
     * variable.
     * 
     * @param name of the object to get from global object map.
     * @return  the object referenced by name
     */
    public Object getObject(String name)
    {
            return this.map.get(name);
    }
    
    
    public void initParameters(JApplet applet)
    {
            
    }
}
