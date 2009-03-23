/*
 * JpegExif.java
 *
 * Created on 11 Temmuz 2007 Çarşamba, 18:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pegaa.uploader.tools;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.exif.ExifDirectory;
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
    /*public void readJPEGMeta(String image_path)
                throws java.lang.InterruptedException 
    {			
		
        File jpegDosya = new File(image_path);
        Metadata metadata=null;
        try{
            metadata = JpegMetadataReader.readMetadata(jpegDosya);
        }catch(Exception e){
            return;
        }

        Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
		
        try{
            marka=exifDirectory.getDescription(ExifDirectory.TAG_MAKE);
            model=exifDirectory.getDescription(ExifDirectory.TAG_MODEL);
            tarih=exifDirectory.getDescription(ExifDirectory.TAG_DATETIME);
            xcozunurluk=exifDirectory.getDescription(ExifDirectory.TAG_X_RESOLUTION);
            ycozunurluk=exifDirectory.getDescription(ExifDirectory.TAG_Y_RESOLUTION);
            brightness=exifDirectory.getDescription(ExifDirectory.TAG_BRIGHTNESS_VALUE);
            flash=exifDirectory.getDescription(ExifDirectory.TAG_FLASH);
            compression=exifDirectory.getDescription(ExifDirectory.TAG_COMPRESSION);
            compressionlevel=exifDirectory.getDescription(ExifDirectory.TAG_COMPRESSION_LEVEL);
            focallength=exifDirectory.getDescription(ExifDirectory.TAG_FOCAL_LENGTH);
            colorspace=exifDirectory.getDescription(ExifDirectory.TAG_COLOR_SPACE);
            exposuretime=exifDirectory.getDescription(ExifDirectory.TAG_EXPOSURE_TIME);
            exposuremode=exifDirectory.getDescription(ExifDirectory.TAG_EXPOSURE_MODE);
            exposureprogram=exifDirectory.getDescription(ExifDirectory.TAG_EXPOSURE_PROGRAM);
            exposurebias=exifDirectory.getDescription(ExifDirectory.TAG_EXPOSURE_BIAS);
	}catch(Exception e){
	}
        
        marka=ExifKontrol(marka,40);
        model=ExifKontrol(model,40);
        tarih=ExifKontrol(tarih,20);
        xcozunurluk=ExifKontrol(xcozunurluk,20);
        ycozunurluk=ExifKontrol(ycozunurluk,20);
        brightness=ExifKontrol(brightness,20);
        flash=ExifKontrol(flash,75);
        compression=ExifKontrol(compression,20);
        compressionlevel=ExifKontrol(compressionlevel,20);
        focallength=ExifKontrol(focallength,20);
        colorspace=ExifKontrol(colorspace,10);
        exposuretime=ExifKontrol(exposuretime,30);
        exposuremode=ExifKontrol(exposuremode,20);
        exposureprogram=ExifKontrol(exposureprogram,40);
        exposurebias=ExifKontrol(exposurebias,10);

        Image image = Toolkit.getDefaultToolkit().getImage(image_path);
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 0);
            
        mediaTracker.waitForID(0);
        
        genislik=String.valueOf(image.getWidth(null));
        yukseklik=String.valueOf(image.getHeight(null));
    }*/
	
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
