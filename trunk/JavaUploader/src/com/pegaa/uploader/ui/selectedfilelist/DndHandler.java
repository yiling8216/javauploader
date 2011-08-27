/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pegaa.uploader.ui.selectedfilelist;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.config.policy.ImageUploadPolicy;
import com.pegaa.uploader.config.policy.UploadPolicy;
import com.pegaa.uploader.ui.filelist.item.FileItem;
import com.pegaa.uploader.ui.filelist.item.ImageItem;
import com.pegaa.uploader.ui.filelist.item.ListItem;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author tayfun
 */
public class DndHandler implements DropTargetListener {

    private ConfigHolder configHolder;
    private ArrayList<DropTarget> dropTarget;
    private SelectedFileListModel selectedFileListModel;
    private UploadPolicy policy = null;

    public DndHandler() {
        if (dropTarget == null) {
            dropTarget = new ArrayList<DropTarget>();
        }
    }

    public void addDropTarget(JComponent comp) {
        dropTarget.add(new DropTarget(comp, this));
    }

    public void setConfigHolder(ConfigHolder configHolder) {
        this.configHolder = configHolder;
        selectedFileListModel = (SelectedFileListModel) this.configHolder.getObject("global.selected-file-list-model");
        this.policy = (UploadPolicy) this.configHolder.getObject("global.policy");
    }

    public void dragEnter(DropTargetDragEvent dtde) {
    }

    public void dragOver(DropTargetDragEvent dtde) {
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void drop(DropTargetDropEvent evt) {
        //final List result = new ArrayList();
        int action = evt.getDropAction();
        evt.acceptDrop(action);
        try {
            Transferable data = evt.getTransferable();
            DataFlavor flavors[] = data.getTransferDataFlavors();
            if (data.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                List<File> list = (List<File>) data.getTransferData(
                        DataFlavor.javaFileListFlavor);
                processFiles(list);
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            evt.dropComplete(true);
        }
    }

    private void processFiles(List<File> list) {
        for (File file : list) {
            ListItem item = null;
            int ind = this.selectedFileListModel.contains(file);

            System.out.println("Adding file : " + file.getAbsolutePath());

            if (ind == -1) {
                item = this.createListItem(file);
            } else {
                item = this.selectedFileListModel.getItem(ind);
            }
            selectedFileListModel.add(item);
        }
    }

    private ListItem createListItem(File file) {
        if (this.policy.getPolicyType() == ImageUploadPolicy.POLICY_TYPE_IMG) {
            ImageItem item = new ImageItem(this.configHolder, file);
            item.init();
            return item;
        } else {
            return new FileItem(this.configHolder, file);
        }
    }
}
