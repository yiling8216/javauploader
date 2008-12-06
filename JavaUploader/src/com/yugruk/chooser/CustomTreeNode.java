package com.yugruk.chooser;

import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author tayfun
 */
public class CustomTreeNode extends DefaultMutableTreeNode{
    
    private File m_file;
    private boolean isExpanded = false;
    
    /** Creates a new instance of CustomTreeNode */
    public CustomTreeNode(File f, String name) {
        super(name);
        this.m_file = f;
    }
    
    public CustomTreeNode(){
        super();
        this.isExpanded = true;
    }
    
    public void setExpanded(){
        this.isExpanded = true;
    }
    
    public boolean isExpanded(){
        return this.isExpanded;
    }
    
    public File getFile(){
        return this.m_file;
    }
  
    private FileSystemView getFileSystemView(){
        return DirectoryTree.getFileSystemView(); 
    }
    
    private int getMode(){
        return DirectoryTree.getMode();
    }
    
    /**
     *  parent node'a cocuklarini (yani alt klasorlerini) ekleyen
     * fonksiyon, eger expanded flagine sahip nesne ise parentin hic cocugu
     * yok demektir ve cocuklar eklenir tersi durumda islem yapilmaz.
     *
     */
    public boolean expand(CustomTreeNode parent)
    {	
      CustomTreeNode flag =  (CustomTreeNode)parent.getFirstChild();
      if (flag == null) {   // No flag
         return false;
      }
        
      if (!flag.isExpanded()){
         return false;      // Already expanded
      }
      parent.removeAllChildren();  // Remove Flag

      File[] files = listFiles();
      if (files == null){
          return true;
      }
      
      Vector<CustomTreeNode> v = new Vector<CustomTreeNode>(); 
      
      for (int k=0; k<files.length; k++)
      {   	 
         File f = files[k];
         if (f.isFile()){
            continue;
         }      
         /*
         if(!f.isDirectory()){
         	continue;
         }
         */
         //windowsta kisa yollar da ilginc sekilde klasor olarak geciyor. uzanti ile elemeliyiz.
         if(getMode() == DirectoryTree.WINDOWS_MODE){
             if(f.getAbsolutePath().toLowerCase().endsWith(".lnk")){
                 continue;
             }
         }
         
         CustomTreeNode newNode = new CustomTreeNode(f, getFileSystemView().getSystemDisplayName(f));
       
         boolean isAdded = false;
         for (int i=0; i<v.size(); i++)
         {
            CustomTreeNode nd = (CustomTreeNode)v.elementAt(i);
            if (newNode.compareTo(nd) < 0)
            {
               v.insertElementAt(newNode, i);
               isAdded = true;
               break;
            }
        }
        
        if (!isAdded){
            v.addElement(newNode);
        }
     }
      
     for (int i=0; i<v.size(); i++)
     {
         CustomTreeNode nd = (CustomTreeNode)v.elementAt(i);
         parent.add(nd);
         //cocuk alt klasorlere sahip ise expanded flagini tasiyan
         //nesneyi cocuga ekleriz boylece, tree de expand butonuna sahip
         //halde goruntulenir aksi halde buton gorunmez ve expand edemeyiz
         //  
         if(getMode() == DirectoryTree.WINDOWS_MODE){
            CustomTreeNode node0 = new CustomTreeNode();
            nd.add(node0);    
         }else{
            if(nd.hasSubDirs()){
              CustomTreeNode node0 = new CustomTreeNode();
              nd.add(node0);                 
            }
         }
     }
     return true;
  }
  
   
  public boolean hasSubDirs()
  {
    File[] files = listFiles();
    if (files == null)
      return false;
    for (int k=0; k<files.length; k++)
    {
      if (files[k].isDirectory())
        return true;
    }
    return false;
  }
  
   
  public int compareTo(CustomTreeNode toCompare)
  { 
      return  m_file.getName().compareToIgnoreCase(toCompare.m_file.getName()); 
  }

  protected File[] listFiles()
  {
    if (!m_file.isDirectory()){
        return null;
    }
    try{
        return getFileSystemView().getFiles(m_file, true);	
    }catch (Exception ex){
    	ex.printStackTrace();
        return null;
    }
  }
  
}
