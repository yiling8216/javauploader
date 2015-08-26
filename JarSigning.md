# Jar Signing Guide #
1. You will need the latest JavaUploader and Java JDK (_Available at http://java.sun.com/javase/_). In the screenshot below I am using JDK 5.0

http://razier.com/code/uploader/guide/1.PNG


2. Perform the Installation of the JDK

http://razier.com/code/uploader/guide/2.PNG

http://razier.com/code/uploader/guide/3.PNG

http://razier.com/code/uploader/guide/4.PNG

http://razier.com/code/uploader/guide/5.PNG

3. Click on start->run

http://razier.com/code/uploader/guide/6.PNG

4. Type CMD to go into the command line

http://razier.com/code/uploader/guide/7.PNG

5. Change the directory to the downloaded JavaUploader bin directory and create a key by typing
```
    keytool -genkey -alias signFiles -keystore keystorefilename -keypass passofkey -dname "cn=yourname" -storepass passofkey
```
http://razier.com/code/uploader/guide/8.PNG

6. Sign the Jar files one by one by typing
```
    jarsigner -keystore keystorefilename -storepass passofkey -keypass passofkey -signedjar rJavaUploader.jar JavaUploader.jar signFiles
    jarsigner -keystore keystorefilename -storepass passofkey -keypass passofkey -signedjar rmetadata-extractor-2.3.1.jar metadata-extractor-2.3.1.jar signFiles
    jarsigner -keystore keystorefilename -storepass passofkey -keypass passofkey -signedjar rCommonsLibs.jar CommonsLibs.jar signFiles
    jarsigner -keystore keystorefilename -storepass passofkey -keypass passofkey -signedjar rswing-layout-1.0.3.jar swing-layout-1.0.3.jar signFiles
```
http://razier.com/code/uploader/guide/9.PNG

7. The signed jar file is in the downloaded JavaUploader bin directory

http://razier.com/code/uploader/guide/10.PNG