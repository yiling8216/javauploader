/*
 * Lang.java
 *
 * Created on 15 Mayıs 2008 Perşembe, 00:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pegaa.uploader.lang;

import com.pegaa.uploader.tools.CustomLog;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JApplet;

/**
 *
 * @author tayfun
 */
public class Lang {
    
    private String defaultLang = "en";
    private Properties props = null;
    
    /** Creates a new instance of Lang */
    public Lang(JApplet applet) 
    {
        init(applet);
    }
    
    /**
     *  
     *
     */
    private void init(JApplet applet)
    {
        props = new Properties();
        String langStr = applet.getParameter("lang");
        if(langStr != null)
        {
            try {
                props.load(Lang.class.getResourceAsStream(this.getPackagePath() + langStr + ".lang"));
            } catch (IOException ex) {
                ex.printStackTrace();
                props = null;
            }
        }else{
            try {
                CustomLog.log("Loading lang = " + this.getPackagePath() + defaultLang + ".lang");
                props.load(Lang.class.getResourceAsStream(this.getPackagePath() + defaultLang + ".lang"));
            } catch (IOException ex) {
                ex.printStackTrace();
                props = null;
            }            
        }
    }
     
    public String get(String id)
    {
        return this.props.getProperty(id);
    }
    
    /**
     *  Returns the package resources' path ie : /com/example/
     * 
     * @return
     */
    private String getPackagePath()
    {
        String packageName = Lang.class.getPackage().getName();
        String fullPath = "/" + packageName.replaceAll("\\.", "/") + "/";
        return fullPath;
    }
}
