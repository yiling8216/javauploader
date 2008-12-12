/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pegaa.uploader.config.policy;

import com.pegaa.uploader.common.URLUTF8Encoder;
import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.tools.CustomLog;
import com.pegaa.uploader.tools.JpegExif;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import java.io.File;

/**
 * This class is intended to be used for exif data passing. If your application
 * requires exif data of images you should use this policy.
 * This policy appends exif data to URL so you should parse URL to extract
 * exif data.
 * 
 * Format of exif data is 
 * 
 * @author tayfun
 */
public class ImageUploadWihExifDataPolicy extends ImageUploadPolicy {

    /**
     * ImageUploadWihExifDataPolicy  policy type flag is same type as image
     * upload policy
     */
    public static final int POLICY_TYPE_IMG_WITH_EXIF = 1;
    private static String splitChar = "::";
    
    
    public ImageUploadWihExifDataPolicy(ConfigHolder configHolder)
    {
        super(configHolder);
    }
 
    @Override
    public int getPolicyType()
    {
        return POLICY_TYPE_IMG_WITH_EXIF;
    }   
    
         /**
     * Returns full target upload URL, this function is called from
     * Sender.java's run method.
     * 
     * @param targetID Target folder's ID
     * @return
     */
    @Override
    public String getPostURL(ListItem item, String targetID)
    {
        String uploadHandlerUrl = (String)this.configHolder.getObject("global.uploadHandlerUrl");
        
        if(uploadHandlerUrl == null){
            uploadHandlerUrl = "";
        }
        
        uploadHandlerUrl += targetID;
        uploadHandlerUrl += getUploadURLwithExif(getExif(item));
        
        CustomLog.log("ImageUploadWihExifDataPolicy.getPostURL.uploadHandlerUrl = " + uploadHandlerUrl);
        return uploadHandlerUrl;
    }
    
    
    private JpegExif getExif(ListItem item)
    {
        JpegExif exif = new JpegExif();
        File file = item.getFile();
        try{
            exif.readJPEGMeta(file.getAbsolutePath());
	}catch(Exception e){   
			 e.printStackTrace();
        }
        return exif;
    }
    
    private static String getUploadURLwithExif(JpegExif exif)
    {
        StringBuffer buf = new StringBuffer();
        buf.append("&exif=");
                        
        StringBuffer exifBuf = new StringBuffer();
                
        exifBuf.append(getURLEncodedString(exif.brightness));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.colorspace));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.compression));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.compressionlevel));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.exposurebias));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.exposuremode));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.exposureprogram));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.flash));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.focallength));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.marka));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.model));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.tarih));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.xcozunurluk));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.ycozunurluk));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.yukseklik));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.genislik));
        exifBuf.append(splitChar).append(getURLEncodedString(exif.exposuretime));
               
        buf.append(exifBuf.toString());
        
        return buf.toString();		
    }
    
    /**
     * Encodes given string as utf8 encoded url string
     * 
     * @param s
     * @return
     */
    private static String getURLEncodedString(String s){
        if(s == null)return "";
        if(s.equals(" "))return "";
        s = s.replace(splitChar, "-").replace("/", "-").replace("?", "-".replace("\\","-"));
        return URLUTF8Encoder.encode(s);
    }
}
