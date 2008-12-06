/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.sender;

import java.io.InputStream;

/**
 *
 * @author tayfun
 */
public class InputStreamInfo {

    private InputStream is = null;
    private long length = 0;
    
    public InputStreamInfo(InputStream is, long length)
    {
        this.is = is;
        this.length = length;
    }
    
    /**
     *  Returns internal InputStream reference
     * @return
     */
    public InputStream getIs() {
        return is;
    }

    /**
     *  Returns length of inputStream object's length
     * @return
     */
    public long getLength() {
        return length;
    }    
    
}
