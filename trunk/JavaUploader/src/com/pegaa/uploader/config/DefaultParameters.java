/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.config;

/**
 *
 * @author tayfun
 */
public class DefaultParameters {

    /* Max count of target, while initing applet variables
     * we must check this value.
     * 
     * For security reasons we set a max target count.
     */
    public static int MAX_ID_COUNT = 200;
       
    /**
     *  images will be scaled to these values before upload
     * if scaleImages equals true
     */
    public static boolean scaleImages = true;
    public static int UPLOAD_IMAGE_WIDTH = 1024;
    public static int UPLOAD_IMAGE_HEIGHT = 768;
    
    
    /**
     * Max extension type count, it is defaulted to 20.
     * 
     */
    public static int MAX_EXTENSION_COUNT = 20;
    
}
