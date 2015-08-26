**What is signed applet**

Normal applets can not do everything like a tradinional desktop application because applet runs in a sandboxed environment. Applets can not do I/O, socket operations (they can connect to the same origin server but not any other) on host environment.

If your applet needs to do I/O or your socket connection is different from the origin you must sign your applet to break sandbox. When you sign your applet it will be like a traditional desktop and you can do anything that a traditional desktop can.

**How to Sign an Applet**

Step by step how to sign an applet is listed below

  * Package you applet into a jar (Netbeans, Eclipse has tools to help jar'ing your applet easily)

  * Create a public/private key pair.

Command is like this :

keytool -genkey -alias signFiles -keystore _keystorefilename_ -keypass _passofkey_ -dname "cn=Developer's Name" -storepass _passofstore_

for simplicity's sake you can use same password for store and key. Above command will create a file named _keystorefilename_. It will be your reference key file. Do not forget to change italic words to your preferences, they are just sample !

  * After creating keystore file you can sign your jars.

jarsigner -keystore _keystorefilename_ -storepass _passofstore_ -keypass _passofkey_ -signedjar _signedAppletName.jar_ _jarToBeSigned.jar_ signFiles

You can give any name to signed jar except same name of the jar to be signed :). After signing applet you do not have to do anything to break sandbox, your applet user's will do. When applet is started java security system asks if they want to run applet in a untrusted way and then if they choose "Run" your applet will run, if they choose cancel your applet will not run, so you must warn your users to choose "Run" if they want to use applet.

I am using method listed above, but there is some other methods if you want to have a look here is the link : http://java.sun.com/developer/onlineTraining/Programming/JDCBook/signed.html