#Usage of applet in JSP

# Usage of Applet in JSP #

Follow the guidelines in [Usage](Usage.md) 1 to 4. This java code for the 5th step.


```
public synchronized void doFileUpload(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException{
request.setCharacterEncoding("UTF-8");
	      
// Create a factory for disk-based file items
FileItemFactory factory = new DiskFileItemFactory();

// Create a new file upload handler
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    List items = null;
	      
	    try{
	    	items = upload.parseRequest(request);
	    }catch(org.apache.commons.fileupload.FileUploadException e){
	    	e.printStackTrace();
	    	return;
	    }
	    
	    FileItem fileItem=null;
	    
	    for (Iterator i = items.iterator(); i.hasNext();){ 
	    	fileItem = (FileItem) i.next();
	    	if(!fileItem.isFormField())
	    	{
	    		item.write(new File("PATH_TO_FILE"));
	    	}
            else{
	    		fileItem.delete();	  	
      }
   }

  }
```

# Spring Framework Sample #

thnx to **sid.maz73**

```
   public ModelAndView uploadFiles(HttpServletRequest request, HttpServletResponse
response){
	    try{ 
	    	final List files = new ArrayList();
	    	if(request instanceof MultipartHttpServletRequest){
	    		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
	    		files.addAll(mRequest.getFileMap().values());
	    	}
	    	if(files.size()>0){
	    		for (int i=0;i<files.size();i++){
	    			CommonsMultipartFile file = (CommonsMultipartFile) files.get(i);
	    			log.debug("file name="+file.getName()+"size="+file.getBytes());

	    		}
	    	}
	        return new ModelAndView(uploaderShowTemplate);
	    }catch (Exception e){
	        System.out.println("Upload File Exception Show : "+e);
	    }
        return new ModelAndView(uploaderShowTemplate);
    }
```