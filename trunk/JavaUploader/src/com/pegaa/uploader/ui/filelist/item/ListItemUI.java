/*
 * ListElement.java
 *
 * Created on 12 Haziran 2008 Perşembe, 21:12
 */
package com.pegaa.uploader.ui.filelist.item;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.config.policy.UploadPolicy;
import com.pegaa.uploader.event.ItemSelectionListener;
import com.pegaa.uploader.event.ListItemListener;
import com.pegaa.uploader.lang.Lang;
import com.pegaa.uploader.tools.CustomLog;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author  tayfun
 */
public class ListItemUI extends javax.swing.JPanel implements ListItemListener {

    private ConfigHolder configHolder = null;
    private FileSystemView fsView;
    /* this items display image */
    private BufferedImage image;
    private ListItem item = null;
    private ArrayList<ItemSelectionListener> selectionListeners = null;
    private int listenerCount = 0;
    private boolean showHideButtons = true;

    /** Creates new form ListElement */
    public ListItemUI() {
        initComponents();
        this.selectionListeners = new ArrayList<ItemSelectionListener>(2);
        fsView = FileSystemView.getFileSystemView();
        showHideButtons(false);
    }

    /**
     * 
     * @param configHolder
     */
    public void setConfigHolder(ConfigHolder configHolder) {
        this.configHolder = configHolder;
        updateButtonPanel();
    }

    /*
     * If rotation is disable we must hide the panel that holds
     * rotation buttons.
     */
    public void updateButtonPanel() {
        super.updateUI();
        UploadPolicy up = (UploadPolicy) this.configHolder.getObject("global.policy");
        if (!up.isShowRotateButtons()) {
            panelButtonHolder.setVisible(false);
            showHideButtons(false);
            showHideButtons = false;
        }
    }

    /**
     *  Sets the internal item (model)
     * 
     * @param item
     */
    public void setItem(ListItem item, boolean isImageMode) {
        this.item = item;
        this.item.addListItemListener(this);
        if (isImageMode) {
            this.labelFileName.setText(((Lang) this.configHolder.getObject("global.lang")).get("listitemui.loading"));
            this.showItemImage();
        } else {
            this.labelFileName.setText(getShortFileName(item.getFile()));
            this.panelButtonHolder.setVisible(false);
            this.showFileIcon();
        }
        this.setToolTipText(item.getPath());
        this.fileNamePanel.setToolTipText(item.getPath());
    }

    /**
     *  Returns short display name of file
     * @param f
     * @return
     */
    private String getShortFileName(File f) {
        String str = f.getName();
        int len = str.length();
        int MAX = 20;
        if (len > MAX) {
            return str.substring(0, MAX - 2) + "..";
        } else {
            return str;
        }
    }

    /**
     * If mode is file mode show the icon of the extension if available
     */
    private void showFileIcon() {
        if (this.image != null) {
            return;
        }

        Icon icon = fsView.getSystemIcon(this.item.getFile());
        BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = img.createGraphics();
        icon.paintIcon(this, g, icon.getIconWidth(), icon.getIconHeight());
        this.image = img;
        this.repaint();
    }

    /**
     *  Sets the selected flag and updates the checkbox
     * @param b
     */
    public void setSelected(boolean b) {
        this.jCheckBox1.setSelected(b);
        this.imagePanel1.setSelection(b);
        this.repaint();
    }

    /**
     *  Returns selection status
     * @return
     */
    public boolean isSelected() {
        return this.jCheckBox1.isSelected();
    }

    /**
     *  returns ListItem object associated with this itemUI
     * @return
     */
    public ListItem getItem() {
        return this.item;
    }

    /**
     * in image mode loads the image and shows it
     */
    private void showItemImage() {
        this.image = this.item.getThumbImage();
        if (this.image != null) {
            this.labelFileName.setText(getShortFileName(item.getFile()));
            this.imagePanel1.setImage(image);
            this.repaint();
        }
    }

    /**
     *      Add FileSelectionListener
     * 
     * @param l
     */
    public void addItemSelectionListener(ItemSelectionListener l) {
        this.selectionListeners.add(l);
        this.listenerCount++;
    }

    /**
     *      clears the FileSelectionListener list.
     * 
     */
    public void removeItemSelectionListeners() {
        int len = this.listenerCount;
        for (int i = 0; i < len; i++) {
            this.selectionListeners.remove(i);
        }
    }

    /*
     * Notify checkbox status listeners
     */
    private void notifyItemSelectionListeners() {
        int len = this.listenerCount;
        for (int i = 0; i < len; i++) {
            if (this.jCheckBox1.isSelected()) {
                if (!this.selectionListeners.get(i).itemSelected(item)) {
                    this.setSelected(false);
                }
            } else {
                this.selectionListeners.get(i).itemUnSelected(item);
            }
        }
    }

    /**
     *  ListItem image init listener
     */
    public void itemImageUpdated() {
        this.showItemImage();
    }

    private void showHideButtons(boolean b) {
        this.rotateLeft.setVisible(b);
        this.rotateRight.setVisible(b);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileNamePanel = new javax.swing.JPanel();
        labelFileName = new javax.swing.JLabel();
        imagePanel1 = new com.pegaa.uploader.ui.filelist.item.ImagePanel();
        jPanel1 = new javax.swing.JPanel();
        panelButtonHolder = new javax.swing.JPanel();
        rotateLeft = new javax.swing.JButton();
        rotateRight = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();

        setPreferredSize(new java.awt.Dimension(150, 125));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        fileNamePanel.setPreferredSize(new java.awt.Dimension(100, 30));
        fileNamePanel.add(labelFileName);

        add(fileNamePanel, java.awt.BorderLayout.SOUTH);

        imagePanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imagePanel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                imagePanel1MouseExited(evt);
            }
        });
        imagePanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panelButtonHolder.setOpaque(false);
        panelButtonHolder.setPreferredSize(new java.awt.Dimension(100, 30));
        panelButtonHolder.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        rotateLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pegaa/uploader/images/rotate_left.gif"))); // NOI18N
        rotateLeft.setPreferredSize(new java.awt.Dimension(22, 22));
        rotateLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rotateLeftMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rotateLeftMouseEntered(evt);
            }
        });
        panelButtonHolder.add(rotateLeft);

        rotateRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pegaa/uploader/images/rotate_right.gif"))); // NOI18N
        rotateRight.setPreferredSize(new java.awt.Dimension(22, 22));
        rotateRight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rotateRightMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rotateRightMouseEntered(evt);
            }
        });
        panelButtonHolder.add(rotateRight);

        jPanel1.add(panelButtonHolder, java.awt.BorderLayout.CENTER);

        imagePanel1.add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel2.add(jCheckBox1, java.awt.BorderLayout.EAST);

        imagePanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        add(imagePanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void rotateLeftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rotateLeftMouseClicked
        ImageItem imgItem = (ImageItem) this.item;
        imgItem.rotateLeft();
        this.revalidate();
    }//GEN-LAST:event_rotateLeftMouseClicked

    private void rotateRightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rotateRightMouseClicked
        ImageItem imgItem = (ImageItem) this.item;
        imgItem.rotateRight();
        this.revalidate();
    }//GEN-LAST:event_rotateRightMouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        this.notifyItemSelectionListeners();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
    }//GEN-LAST:event_formMouseClicked

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
    }//GEN-LAST:event_formMouseEntered

    private void imagePanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanel1MouseEntered
        if (showHideButtons) {
            showHideButtons(true);
        }
    }//GEN-LAST:event_imagePanel1MouseEntered

    private void imagePanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanel1MouseExited
        if (showHideButtons) {
            showHideButtons(false);
        }
    }//GEN-LAST:event_imagePanel1MouseExited

    private void rotateLeftMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rotateLeftMouseEntered
        if (showHideButtons) {
            showHideButtons(true);
        }
    }//GEN-LAST:event_rotateLeftMouseEntered

    private void rotateRightMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rotateRightMouseEntered
        if (showHideButtons) {
            showHideButtons(true);
        }
    }//GEN-LAST:event_rotateRightMouseEntered
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fileNamePanel;
    private com.pegaa.uploader.ui.filelist.item.ImagePanel imagePanel1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelFileName;
    private javax.swing.JPanel panelButtonHolder;
    private javax.swing.JButton rotateLeft;
    private javax.swing.JButton rotateRight;
    // End of variables declaration//GEN-END:variables
}
