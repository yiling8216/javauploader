**Applet's configuration variables**
  * **selectedFolder** -> if you user wants to upload files to a selected folder (user clicks uplaod to this folder button then comes to upload applet), when user clicks upload all files (images) will be uploaded to given receiver id (actually folder id)

  * **idCount** -> the list of available folders to user. (if selectedFolder is set it is selected default)

  * **sessionString** -> session identifier, if you are using PHP this is PHPSESSIONID=_session\_id\_of\_user_

  * **UploadPolicy** -> there is two policies "File" or "Image" default is image policy. Policy helps when listing files of selected directory, if image policy is set image's thumbnail is shown as file, if file policy is set applet tries to find related icon by the help of file's extension and shows icon. While uploading files, applet applies compression on files if image policy is set, for now (25-11-2008) defaults are applied to images, but i am trying to make them easily configurable.

  * **logStatus** -> this is for logging purposes, if you want to see debug messages on java console set this to "on".

  * **uploadHandlerUrl** -> this is the url which will accept upload requests it must have a parameter name at the end like "http://localhost:9000/pupload?album_id=", because uploading codes will add selectedfolder id to **uploadHandlerUrl** so your handler script will know which folder to work on.

---

**Sample html source for using applet**

Here is sample applet configuration parameters. You must concentrate on selectedFolder, sessionString, uploadHandlerUrl, logStatus, idCount and desc-... parameter map parameters.
```
  <object type="application/x-java-applet" width= "900" height= "500"  name="JavaUploader" id="jsap">
  <param name="codebase" value="http://localhost:9000/applet/">
  <param name="archive" value="SAlbumYapUploaderv3.jar,SCommonsLibs.jar,SDirectoryChooser.jar,plugin.jar">
  <param name="code" value="com.albumyap.uploader.UploaderApplet">
  <param name="selectedFolder" value="<%=request.getParameter("album_id")%>">
  <param name="sessionString" value="JSESSIONID=<%=session_id%>">
  <param name="uploadHandlerUrl" value="http://localhost:9000/pupload?album_id=">
  <param name="logStatus" value="on">
  <param name="mayscript" value="yes">
  <param name="scriptable" value="true">
  <param name="name" value="JavaUploader Upload Applet">
  <param name="progressbar" value="true">
  <param name="progresscolor" value="#FF0000">
  <param name="idCount" value="<%=albums.Aalbum_id.length%>">
  <%for(int i=0;i<albums.Aalbum_id.length;i++) {%>
    <param name="desc-<%=i%>" value="<%=albums.Afolder_id[i]%>:<%=albums.Afolder_name[i]%>">
  <%}%>
 </object>
```