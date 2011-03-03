/*
 * ImageResizer.java
 *
 * Created on 23 Haziran 2007 Cumartesi, 00:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.pegaa.uploader.tools;

import com.pegaa.uploader.imgfilters.RotateFilter;
import com.pegaa.uploader.ui.filelist.item.ImageItem;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author tayfun
 */
public class ImageFuncs {

    /** Creates a new instance of ImageFuncs */
    private ImageFuncs() {
    }

    /**
     *   Creates ByteArrayOutputStream from given BufferedImage 
     * 
     * @param image
     * @return
     */
    public static ByteArrayOutputStream createImageOutputStream(BufferedImage image) {
        //System.out.println("ByteArrayOutputStream method called.");
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4194304);
        try {
            javax.imageio.ImageIO.write(image, "jpg", baos);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return baos;
    }

    //Raz - Overload this function to accept file extension so that the image uploaded will follow the original file format
    //Previous function can be removed later on
    public static ByteArrayOutputStream createImageOutputStream(BufferedImage image, String rext) {
        /*
        System.out.println("ByteArrayOutputStream with ext method called.");
        System.out.println(rext);
         */
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4194304);
        try {
            javax.imageio.ImageIO.write(image, rext, baos);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return baos;
    }

    /**
     * Returns the image according to scale parameters and also rotates if
     * there is a rotation flag.
     * 
     * @param image original image to be processed
     * @param maxWidth target image width
     * @param maxHeight target image height
     * @param status rotaton flag
     * @param thumb flag of whether target will be used as thumbnail.
     * @return
     */
    public static BufferedImage getScaledAndRotatedImage(BufferedImage image, int maxWidth, int maxHeight, int status, boolean thumb, String rext) {
        BufferedImage retImage = null;
        int newWidth, newHeight;

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();


        if (imageWidth > maxWidth || imageHeight > maxHeight) {
            double widthRatio = (double) imageWidth / (double) maxWidth;
            double heightRatio = (double) imageHeight / (double) maxHeight;

            double ratio = Math.max(widthRatio, heightRatio);

            newWidth = (int) (imageWidth / ratio);
            newHeight = (int) (imageHeight / ratio);
        } else {
            newWidth = imageWidth;
            newHeight = imageHeight;
        }

        BufferedImage thumbImage = null;

        //Raz - Somehow ARGB have issues during upload, color tend to messed up
        //Raz - ARGB is required for png gif to preserve transparency
        //thumbImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        if (rext.equals("png") || rext.equals("gif")) {
            //With transparency
            thumbImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        } else {
            //Without transparency
            thumbImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        }

        Graphics2D graphics2D = thumbImage.createGraphics();

        if (!thumb) {
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        }

        if (thumb) {
            graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        }

        graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);
        retImage = thumbImage;


        if (status != ImageItem.NORMAL) {
            RotateFilter filter = new RotateFilter();
            if (status == ImageItem.LEFT) {
                filter.setAngle((float) Math.PI / 2);
            } else if (status == ImageItem.DOWNSIDE) {
                filter.setAngle((float) Math.PI);
            } else if (status == ImageItem.RIGHT) {
                filter.setAngle((float) (Math.PI + Math.PI / 2));
            }
            retImage = filter.filter(retImage, null);
        }
        return retImage;
    }
}
