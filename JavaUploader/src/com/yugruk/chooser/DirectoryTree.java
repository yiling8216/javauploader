package com.yugruk.chooser;

import com.pegaa.uploader.config.ConfigHolder;
import com.pegaa.uploader.lang.Lang;
import java.util.Vector;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author tayfun
 */
public class DirectoryTree extends JTree{
    
    public static final int UNIX_MODE = 1;
    public static final int WINDOWS_MODE = 2;
    
    private DefaultTreeModel model;
    private CustomTreeNode rootNode;
    private static FileSystemView view;
    private DirectoryTreeSelectionListener treeListener;
    private static int mode = UNIX_MODE;
    
    private ConfigHolder config = null;
    
    /** Creates a new instance of DirectoryTree */
    public DirectoryTree() {
        super();
        initializeEventListeners();    
    }
    
    public void setConfigHolder(ConfigHolder config)
    {
        this.config = config;
        this.init();
    }
    
    public void init()
    {
        initializeNodes();   
    }
    
    public static FileSystemView getFileSystemView(){
        return view;
    }
    
    public static int getMode(){
        return mode;
    }
       
    private void initializeEventListeners(){
         this.setEditable(true);
         this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
         treeListener = new DirectoryTreeSelectionListener();
         this.addTreeSelectionListener(treeListener);
         this.addTreeExpansionListener(new DirectoryTreeExpansionListener());
    }
    
    private void initializeNodes(){
        view = FileSystemView.getFileSystemView();
        if(File.separator.equals("\\")){
            windowsMode();
        }else{
            unixMode();
        }
        model = new DefaultTreeModel(rootNode);
        this.setModel(model);
    }
    
    private void setMode(int mode){
        DirectoryTree.mode = mode;
    }
    /**
     *  Unix bilgisayarlarda farklı oluşmalı
     */
    private void unixMode()
    {
        setMode(UNIX_MODE);
        prepareUnixModeIcons();
        
        //Unixlerde windowstaki gibi Masaüstü root olmadığı için
        //root icin Bilgisayarım gibi boş file gosteren bir root yaratiriz.
        Lang lang = (Lang)this.config.getObject("global.lang");
        CustomTreeNode rootNode0 = new CustomTreeNode(null, lang.get("directorychooser.mycomputer"));
        
        File homeDir = view.getHomeDirectory();
        CustomTreeNode homeDirNode = new CustomTreeNode(homeDir, homeDir.getAbsolutePath());
        rootNode0.add(homeDirNode);
        CustomTreeNode homeDirNodeChild = new CustomTreeNode();
        homeDirNode.add(homeDirNodeChild);
        
        File rootDir = view.getRoots()[0];
        CustomTreeNode rootDirNode = new CustomTreeNode(rootDir, rootDir.getName());
        rootNode0.add(rootDirNode);  
        CustomTreeNode rootDirNodeChild = new CustomTreeNode();
        rootDirNode.add(rootDirNodeChild);
        
        rootNode = rootNode0;
    }
    
    private void prepareUnixModeIcons(){
        UnixIconRenderer renderer = new UnixIconRenderer(view);
        this.setCellRenderer(renderer);
    }
    
    
    /**
     *  Root node Masaüstü olacak
     *
     */
    private void windowsMode(){
        setMode(WINDOWS_MODE);
        prepareWindowsModeIcons();
        
        File homeDir = view.getRoots()[0]; //Masaustu - Desktop      
        CustomTreeNode rootNode0 = new CustomTreeNode(homeDir, view.getSystemDisplayName(homeDir));
        CustomTreeNode rootNode0Child = new CustomTreeNode();
        rootNode0.add(rootNode0Child);
        rootNode0.expand(rootNode0);
         
        rootNode = rootNode0;
    }
    
    private void prepareWindowsModeIcons(){
        WindowsIconRenderer renderer = new WindowsIconRenderer(view);
        this.setCellRenderer(renderer);        
    }
        
    //LISTENERS
    public void addDirectorySelectionListener(DirectorySelectionListener listener){
        this.treeListener.addDirectorySelectionListener(listener);
    }

     
    private CustomTreeNode getCustomTreeNode(TreePath path){
        return (CustomTreeNode) path.getLastPathComponent();
    }
    
    /**
     * Inner class which listens for selection events on the tree.
     * When one is received, it will get the path of the selected
     * node and add sub directory nodes, if any, to it.
     */
    class DirectoryTreeSelectionListener implements TreeSelectionListener {

        //DirectorySelectionListener arayüzünü implemente eden
        //listenerlarin listesi.
        private Vector<DirectorySelectionListener> listeners;
        
        public DirectoryTreeSelectionListener(){
            listeners = new Vector<DirectorySelectionListener>(1, 2);
        }
        
        public void addDirectorySelectionListener(DirectorySelectionListener listener){
            this.listeners.add(listener);
        }
        
        public void notifyListeners(File f){
            int size = listeners.size();
            for(int i=0; i<size; i++){
                listeners.get(i).directorySelected(f);
            }
        }
        
        public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getNewLeadSelectionPath();
            //check that a path is in fact selected
            if(path == null) {
                return;
            }           
            //get the selected node
            CustomTreeNode curNode = (CustomTreeNode) path.getLastPathComponent();  
            
            if((mode == UNIX_MODE)){
            	if(curNode.isRoot()){
                  return;
            	}
            }else{
            	if(curNode.isRoot()){
                    if(listeners != null){
                        notifyListeners(curNode.getFile());
                    }
                    return;
            	}
            }   
            
            if(listeners != null){
                 notifyListeners(curNode.getFile());
            }  
        }
    }
    
    class DirectoryTreeExpansionListener implements javax.swing.event.TreeExpansionListener{
       
        public void treeExpanded(TreeExpansionEvent e) {
            //get the selected tree path
            TreePath path = e.getPath();//e.getNewLeadSelectionPath();
            
            //check that a path is in fact selected
            if(path == null) {
                return;
            }
            
            //get the selected node
            final CustomTreeNode curNode = getCustomTreeNode(path);  
            /**
             * Unixte root eleman bir file gostermedigi icin herhangi bir islem
             * yapmaya gerek yok, Windowsta root eleman masaustu (Desktop) klasoru
             * oldugu icin DirectorySelectionListener arayuzunden dinleyen sinifi
             * uyarmaliyiz.
             *
             */
            if((mode == UNIX_MODE)){
            	if(curNode.isRoot()){
                   System.out.println("root node");
                   return;
            	}
            }else{
            	if(curNode.isRoot()){
                    System.out.println("root node");
                    return;
            	}
            }
            
            Thread runner = new Thread() 
            {
              public void run() 
              {
                //node expand ediliyor.
                 if (curNode != null && curNode.expand(curNode)) 
                 {
                     Runnable runnable = new Runnable(){
                       public void run(){
                          model.reload(curNode);
                       }
                     };
                     try {
			 javax.swing.SwingUtilities.invokeAndWait(runnable);
	             } catch (Exception ex) {
				ex.printStackTrace();
		     }
                 }
              }
            };
            runner.start();
    
        }

        public void treeCollapsed(TreeExpansionEvent event) {
            
        }
        
    }   
    
}
