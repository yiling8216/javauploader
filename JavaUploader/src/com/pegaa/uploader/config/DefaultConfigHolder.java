/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pegaa.uploader.config;

import com.pegaa.uploader.common.TargetFolderData;
import com.pegaa.uploader.config.policy.FileUploadPolicy;
import com.pegaa.uploader.config.policy.ImageUploadPolicy;
import com.pegaa.uploader.config.policy.ImageUploadWihExifDataPolicy;
import com.pegaa.uploader.config.policy.OriginalImageUploadPolicy;
import com.pegaa.uploader.lang.Lang;
import com.pegaa.uploader.tools.CustomLog;
import java.util.ArrayList;
import javax.swing.JApplet;
import netscape.javascript.JSObject;

/**
 *
 * @author tayfun
 */
public class DefaultConfigHolder extends ConfigHolder {

    public DefaultConfigHolder() {
    }

    /*
     *Prepares global map with the common variables
     *
     */
    @Override
    public void initParameters(JApplet applet) {
        /* cache applet reference */
        this.map.put("global.applet", applet);

        this.initLogging(applet);
        this.initJsBridge(applet);
        this.initTargetIdVariables(applet);
        this.initUploadHandlerUrl(applet);
        this.initUploadPolicy(applet);
        this.initLang(applet);
        this.initSessionVariables(applet);
        this.initFileFilterVariables(applet);
        this.initMaxUploadCount(applet);
    }

    /*
     * Prepares the target folders (albums) which can be used when creating
     * POST requests.
     * For example in Image Upload case we use these variables as album_ids.
     *
     * Description of album should block " chars in name becasue it will cause
     * parsing errors while using it as parameter in html page.
     *
     */
    public void initTargetIdVariables(JApplet applet) {
        String currentTargetFolderDesc = null;
        String idCountStr = applet.getParameter("idcount");
        int idCount = 0;
        int i = 0;

        CustomLog.log("DefaultConfigHolder.idcount = " + idCountStr);

        try {
            idCount = Integer.parseInt(idCountStr);

            this.map.put("global.selectedFolder", applet.getParameter("selected-folder"));

            CustomLog.log("DefaultConfigHolder.selected-folder = " + applet.getParameter("selected-folder"));

            ArrayList<TargetFolderData> idParameterMap = new ArrayList<TargetFolderData>(idCount);

            CustomLog.log("** initing target folder list **");
            while ((i < idCount) && (i < DefaultParameters.MAX_ID_COUNT)) {
                //target_folder_no:target_folder_short_name
                currentTargetFolderDesc = applet.getParameter("desc-" + i);
                CustomLog.log("TargetFolder desc = " + currentTargetFolderDesc);
                idParameterMap.add(new TargetFolderData(currentTargetFolderDesc));
                i++;
            }

            this.map.put("global.idMap", idParameterMap);

        } catch (NumberFormatException e) {
            return;
        }
    }

    /**
     *    Session Name parameter must be given by hand for the target server's
     * scripting type for example if u are using JSP this will be JSESSIONID
     * and PHPSESSIONID if using PHP
     *
     * @param applet
     */
    public void initSessionVariables(JApplet applet) {
        String sessionString = null;
        sessionString = applet.getParameter("session-string");
        this.map.put("global.session-string", sessionString);
    }

    /*
     *     Init JSO object which will be used for calling javascript functions
     * from applet.
     *
     */
    public void initJsBridge(JApplet applet) {
        JSObject jso = null;
        try {
            jso = JSObject.getWindow(applet);
            this.map.put("global.jso", jso);
        } catch (Exception e) {
            jso = null;
        }
    }

    /**
     *  Default policy init function. Checks if UploadPolicy parameter
     * exists and chooses one of the default policies(File or Image Policy).
     *
     * @param applet
     */
    public void initUploadPolicy(JApplet applet) {
        String policy = applet.getParameter("upload-policy");

        if (policy != null && policy.equals("file")) {
            FileUploadPolicy fup = new FileUploadPolicy(this);
            this.map.put("global.policy", fup);
        } else if (policy != null && policy.equals("image-with-exif")) {
            ImageUploadWihExifDataPolicy iedp = new ImageUploadWihExifDataPolicy(this);
            this.map.put("global.policy", iedp);
            putImagePolicyRelatedParamsToConfig(applet);
        } else if (policy != null && policy.equals("original-image")) {
            OriginalImageUploadPolicy oiup = new OriginalImageUploadPolicy(this);
            this.map.put("global.policy", oiup);
        } else /* in all other cases we will user image upload policy */ {
            ImageUploadPolicy iup = new ImageUploadPolicy(this);
            this.map.put("global.policy", iup);
            putImagePolicyRelatedParamsToConfig(applet);
        }

        //TEST
        OriginalImageUploadPolicy oiup = new OriginalImageUploadPolicy(this);
        this.map.put("global.policy", oiup);
    }

    /**
     * Parameters to be used by file filter classes are being put to map
     *
     * @param applet
     */
    public void initFileFilterVariables(JApplet applet) {
        this.map.put("filefilter.extensions", applet.getParameter("file-extensions"));
    }

    /**
     * If we will use one of image uplaod policies we must set max width and
     * max height to config holder.
     */
    private void putImagePolicyRelatedParamsToConfig(JApplet applet) {
        this.map.put("image.maxwidth", applet.getParameter("max-width"));
        this.map.put("image.maxheight", applet.getParameter("max-height"));
    }

    public void initLang(JApplet applet) {
        Lang lang = new Lang(applet);
        this.map.put("global.lang", lang);
    }

    public void initLogging(JApplet applet) {
        String logging = applet.getParameter("logging");

        //TEST
        logging = "enable";

        if (logging != null) {
            if (logging.equals("enable")) {
                CustomLog.setMode(1);
            }
        }
    }

    /**
     * when uploading files a target service will handle file upload requests
     * this is where we init upload service url.
     * @param applet
     */
    private void initUploadHandlerUrl(JApplet applet) {
        String uploadHandlerUrl = applet.getParameter("upload-handler-url");
        this.map.put("global.uploadHandlerUrl", uploadHandlerUrl);
    }

    private void initMaxUploadCount(JApplet applet) {
        String fileUploadLimit = applet.getParameter("file-upload-limit");
        this.map.put("global.fileUploadLimit", fileUploadLimit);
    }
}
