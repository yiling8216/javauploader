/*
 * JpegExif.java
 *
 * Created on 11 Temmuz 2007 Çarşamba, 18:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pegaa.uploader.tools;

import java.io.File;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Tag;
import java.util.Iterator;
import com.pegaa.uploader.common.URLUTF8Encoder;


public class JpegExif {
		
    public String marka,model,tarih,genislik,yukseklik,
                  xcozunurluk,ycozunurluk,brightness,flash,
                  compression,compressionlevel,focallength,
                  colorspace,exposuretime,exposuremode,
                  exposureprogram,exposurebias;

    public String exifdata;

    public void readJPEGMeta(String image_path)
            throws java.lang.InterruptedException
    {
        File jpegDosya = new File(image_path);
        Metadata metadata=null;

        try{
            metadata = JpegMetadataReader.readMetadata(jpegDosya);
        }catch(Exception e){
            return;
        }

        //Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
        StringBuffer exifBuf = new StringBuffer();

        // iterate through metadata directories
        Iterator directories = metadata.getDirectoryIterator();
        while (directories.hasNext()) {
            Directory directory = (Directory)directories.next();
            // iterate through tags
            Iterator tags = directory.getTagIterator();
            while (tags.hasNext()) {
                Tag tag = (Tag)tags.next();
                exifBuf.append(tag.getTagName().toString());
                exifBuf.append("=");
                
                try{
                    exifBuf.append(URLUTF8Encoder.encode(tag.getDescription()));
                }catch(Exception e){
                    //return;
                }

                if(tags.hasNext()){
                    exifBuf.append("::");
                }
            }
        }

        exifdata = exifBuf.toString();

    }
    	
    private String ExifKontrol(String gelen,int limit) {
        if(null==gelen)
            return null;
        else if(gelen.length()>limit){
            gelen=String.copyValueOf(gelen.toCharArray(), 0, limit-1);
        }
		
        gelen = gelen.replaceAll("<", "").replaceAll(">", "");
        return gelen;
    }
    
}
