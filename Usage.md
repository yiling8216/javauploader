# Usage - Quick Guide #

1. Download the latest binary http://code.google.com/p/javauploader/downloads/list

2. Extract the zip file

3. Sign the Jar files in the bin folder _(To sign the Jar you require Java JDK http://java.sun.com/javase/ as the executable for keytool & jarsigner are provided together with the Java compiler)_

> 3.1 If you had not create a key yet, type this to generate a key
```
    keytool -genkey -alias signFiles -keystore keystorefilename -keypass passofkey -dname "cn=yourname" -storepass passofkey
```

> 3.2 Sign the Jar files one by one by typing
```
    jarsigner -keystore keystorefilename -storepass passofkey -keypass passofkey -signedjar rJavaUploader.jar JavaUploader.jar signFiles
    jarsigner -keystore keystorefilename -storepass passofkey -keypass passofkey -signedjar rmetadata-extractor-2.3.1.jar metadata-extractor-2.3.1.jar signFiles
    jarsigner -keystore keystorefilename -storepass passofkey -keypass passofkey -signedjar rCommonsLibs.jar CommonsLibs.jar signFiles
    jarsigner -keystore keystorefilename -storepass passofkey -keypass passofkey -signedjar rswing-layout-1.0.3.jar swing-layout-1.0.3.jar signFiles
```

> _A more detailed steps to sign the jar is provided at http://code.google.com/p/javauploader/wiki/JarSigning_

4. Insert the following html to embed the java applet in a html page.
```
<script>
var album_id= '_SELECTED_FOLDER_ID_'; /* target folder (album) */
//This script will be called after the upload is done and user click on //continue
function JUP_eventhandler(action, albumid)
{
  if(action == 'gofolder'){
    if(album_no == 'null'){
      location.href = '/albums'; /*  */
    }else{
      location.href = '/album/' + album_no; /* */
    }
  }else if(action == 'again'){
    if(album_no == 'null'){
      location.href = '/photo-upload/';
    }else{
      location.href = '/photo-upload/' + album_no;
    }    
  }else if(action == 'uploadfinished') {
    //handle upload finish event
  }else if(action == 'applet_denied') {
    //this is called if user does not accept security dialog
  }
}

</script>
    

    <script type="text/javascript">
        //Dummy ie problem and solution...
        var _app = navigator.appName;
        if (_app == 'Netscape') {
           document.write('<object type="application/x-java-applet" width= "900" height= "500" id="jsap">');
        }
        else if (_app == 'Microsoft Internet Explorer') {
            document.write('<object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" width= "900" height= "500" id="jsap">');
        }
        else {
           document.write('<object type="application/x-java-applet" width= "900" height= "500" id="jsap">');
        }
    </script>
      <param name="codebase" value="./">

      <!-- If you are using the pre-signed binary, remember to change the filenames below to reflect the signed binary names e.g sCommonsLibs.jar,smetadata-extractor-2.3.1.jar,sJavaUploader.jar,sswing-layout-1.0.3.jar -->

      <param name="archive" value="rCommonsLibs.jar,rmetadata-extractor-2.3.1.jar,rJavaUploader.jar,rswing-layout-1.0.3.jar">

      <param name="code" value="com.pegaa.uploader.UploaderApplet">
      <param name="UploadPolicy" value="image-with-exif">
      <param name="selectedFolder" value="images">
      <param name="sessionString" value="">
      <param name="uploadHandlerUrl" value="http://example.com/upload.php?album_id=">
      <param name="maxWidth" value="800">
      <param name="maxHeight" value="600">
      <param name="fileUploadLimit" value="100">
      <param name="fileExtensions" value="jpg,jpeg,gif,png">
      <param name="mayscript" value="yes">
      <param name="scriptable" value="true">
      <param name="name" value="Java Uploader">
      <param name="progressbar" value="true">
      <param name="progresscolor" value="#FF0000">
      <param name="logStatus" value="on">
      <param name="idCount" value="3">

      <param name="desc-0" value="1:Images">
      <param name="desc-1" value="2:Images2">
      <param name="desc-2" value="3:Images3">

    </object>
```

5. Code a php to accept the file and output it to the necessary folder (Make sure the folder is writeable!)
```
    <?php

        if($_GET['album_id']=="1"){
            $tofold="/images/";
        }else if($_GET['album_id']=="2"){
            $tofold="/images2/";
        }else{
            $tofold="/images3/";
        }
        
        if($_GET['do']=="redirect"){
            $host  = $_SERVER['HTTP_HOST'];
            $uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');

            header("location: http://".$host.$uri.$tofold);
            die();
        }    

        $filekeys = array_keys($_FILES);
        $filekey=$filekeys[0];
        
	//Not all server configuration supports PATH_TRANSLATED
	if($_SERVER['PATH_TRANSLATED']!=""){
		$uploaddir = dirname($_SERVER['PATH_TRANSLATED']).$tofold;
	}else if($_SERVER["SCRIPT_FILENAME"]!=""){
		$uploaddir = dirname($_SERVER["SCRIPT_FILENAME"]).$tofold;
	}else{
		//Type in your full filesystem path e.g.
		//$uploaddir = "/var/www/home".$tofold;
	}

        $uploadfile = $uploaddir . basename($_FILES[$filekey]['name']);

        if (move_uploaded_file($_FILES[$filekey]['tmp_name'], $uploadfile)) {
            //echo "File is valid, and was successfully uploaded.\n";
        } else {
            //echo "Possible file upload attack!\n";
        }

    ?>
```

6. Upload all the files to the Web server

7. Done!