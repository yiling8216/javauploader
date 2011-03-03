package com.pegaa.uploader.config.policy;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.sender.InputStreamInfo;
import com.pegaa.uploader.tools.CustomLog;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OriginalImageUploadPolicy extends FileUploadPolicy {

    /* FileUpload policy type flag */
    public static final int POLICY_TYPE_IMAGE = 1;

    public OriginalImageUploadPolicy(ConfigHolder configHolder) {
        super(configHolder);
    }

     /**
     *
     *
     * @param targetID
     * @return
     */
    @Override
    public String getPostURL(ListItem item, String targetID) {
        String uploadHandlerUrl = (String)this.configHolder.getObject("global.uploadHandlerUrl");
        uploadHandlerUrl += targetID;
        CustomLog.log("OriginalImageUploadPolicy.getPostURL.uploadHandlerUrl = " + uploadHandlerUrl);
        return uploadHandlerUrl;
    }

    /**
     *
     *
     * @param item
     * @return
     * @throws java.io.FileNotFoundException
     */
    @Override
    public InputStreamInfo getInputStream(ListItem item) throws FileNotFoundException{
        FileInputStream fis = null;
        InputStreamInfo info = null;

        try {
            fis = new FileInputStream(item.getFile());
            info = new InputStreamInfo(fis, item.getFile().length());
            return info;
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException();
        }
    }

    @Override
    public int getPolicyType()
    {
        return POLICY_TYPE_IMAGE;
    }

    /**
     * If we are trying to upload original image we must not
     * allow user to rotate image
     * 
     * @return
     */
    @Override
    public boolean isShowRotateButtons()
    {
        return false;
    }
}
