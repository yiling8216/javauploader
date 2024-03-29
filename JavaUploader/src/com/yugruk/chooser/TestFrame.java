/*
 * NewJFrame.java
 *
 * Created on 17 Temmuz 2007 Salı, 17:52
 */

package com.yugruk.chooser;

import java.io.File;
import javax.swing.UIManager;

/**
 *
 * @author  tayfun
 */
public class TestFrame extends javax.swing.JFrame {
    
    /** Creates new form NewJFrame */
    public TestFrame() {
        initComponents();
        initEventHandlers();
    }
    
    private void initEventHandlers(){
        this.directoryTree1.addDirectorySelectionListener(new DirectorySelectionListener(){
            public void directorySelected(File dir) {
                System.out.println("listener 1 " + dir.getAbsolutePath());
            }  
        });
        this.directoryTree1.addDirectorySelectionListener(new DirectorySelectionListener(){
            public void directorySelected(File dir) {
                System.out.println("listener 2 " + dir.getAbsolutePath());
            }  
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        directoryTree1 = new com.yugruk.chooser.DirectoryTree();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jScrollPane1.setViewportView(directoryTree1);

        getContentPane().add(jScrollPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

       try {
	    // Set System L&F
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } 
       catch (Exception e) {
           e.printStackTrace();
       // handle exception
       }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.yugruk.chooser.DirectoryTree directoryTree1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
