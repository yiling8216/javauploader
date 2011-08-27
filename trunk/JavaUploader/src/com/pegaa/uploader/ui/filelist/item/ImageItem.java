/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pegaa.uploader.ui.filelist.item;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.tools.ImageFuncs;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author tayfun
 */
public class ImageItem extends ListItem {

    /**
     *  current rotation status flags
     */
    public static final int NORMAL = 1;
    public static final int LEFT = 2;
    public static final int DOWNSIDE = 3;
    public static final int RIGHT = 4;
    /**
     * current rotation status
     */
    private int status = NORMAL;
    private BufferedImage thumbImage0 = null;
    private String rext = "";
    /**
     *  WIDTH and HEIGHT is metrics of thumbnail width and height
     */
    public static final int WIDTH = 125;
    public static final int HEIGHT = 100;
    /**
     *  width and height value holders.
     */
    private int width;
    private int height;

    public ImageItem(ConfigHolder configHolder, File file) {
        super(configHolder, file);
    }

    public int getRotationStatus() {
        return this.status;
    }

    /**
     *  Rotates internal thumbnail image to left and updates the
     * internal state.
     */
    public void rotateLeft() {
        if (status == NORMAL) {
            status = LEFT;
        } else if (status == LEFT) {
            status = DOWNSIDE;
        } else if (status == DOWNSIDE) {
            status = RIGHT;
        } else if (status == RIGHT) {
            status = NORMAL;
        }
        updateThumbImage();
        notifyListeners();
    }

    /**
     *  Rotates internal thumbnail image to right and updates the
     * internal state.
     */
    public void rotateRight() {
        if (status == NORMAL) {
            status = RIGHT;
        } else if (status == LEFT) {
            status = NORMAL;
        } else if (status == DOWNSIDE) {
            status = LEFT;
        } else if (status == RIGHT) {
            status = DOWNSIDE;
        }
        updateThumbImage();
        notifyListeners();
    }

    @Override
    public void init() {
        init(WIDTH, HEIGHT);
    }

    /**
     *Init this item, prepare variables, read thumb image from disk.
     *
     * @param width
     * @param height
     */
    public void init(int width, int height) {
        if (thumbImage0 != null) {
            return;
        }
        this.width = width;
        this.height = height;
        BufferedImage image = null;
        String _rext = "";
        try {
            //Raz - Read all imagefile that ImageIO can
            //Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
            String rfilename = this.file.toString();
            _rext = rfilename.substring(rfilename.lastIndexOf('.') + 1, rfilename.length());
            //fix-add
            image = ImageIO.read(new File(rfilename));

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (image == null) {
            return;
        }
        this.rext = _rext;
        thumbImage0 = ImageFuncs.getScaledAndRotatedImage(image, width, height, NORMAL, true, rext);
        this.thumbImage = thumbImage0;
        notifyListeners();
    }

    private void updateThumbImage() {
        if (this.thumbImage0 == null) {
            return;
        }
        BufferedImage image = thumbImage0;
        thumbImage = ImageFuncs.getScaledAndRotatedImage(image, this.width, this.height, status, true, this.rext);
    }
}
