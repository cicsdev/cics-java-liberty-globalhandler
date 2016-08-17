# cics-java-liberty-globalhandler

This project provides a sample Liberty feature with an OSGi bundle project that implements the Handler interface from the WebSphere
Liberty global handler SPI. The sample implementation MyHandler will output details about the HTTP request, SOAP operation and WS headers
for inbound JAXWS messages.  

The WebSphere Liberty global handler SPI supports both JAXWS and JAXRS web service request for inbound and outbound requests and the sample can 
be easily modified to intercept different request types by modifying the handler properties set in the bundle activator. The supplied bundle activator 
sets properties to support JAXWS inbound requests for client or server side calls. 


#Pre-reqs

    CICS TS V5.2 or later with WebSphere Liberty 8.5.5 or later for JAXWS support.
    Eclipse with WebSphere Developer Tools installed
    Configured Liberty JVM server

#Configuration

1. Install WDT tooling into Eclipse
2. Set Eclipse Target Platform to "WebSphere Application Server Liberty Profile with SPI" to make the Liberty SPI available
3. Create an OSGi bundle project called com.ibm.cicsdev.wshandler using the **OSGi bundle project** wizard  ensuring you create an Activator class
4. Import the supplied Activator and MyHandler classes into this project
5. Update the manifest for the OSGI bundle project using the supplied META-INF/MANIFEST.MF file
6. Create a Liberty feature project called com.ibm.cicsdev.wshandler.feature and add the OSGi bundle project created in step 3 to this
7. Update the Liberty feature manifest using the the supplied sample OSGI-INF/SUBSYSTEM.MF 

#Deployment
1. Export the Liberty feature project to an .esa file using the wizard **Export -> Liberty** feature, or alternatively use the sample wshandlerfeature.esa
provided
2. Transfer the .esa file to zFS in binary
3. Install the feature using the CICS wlpenv script located in the Liberty ${server.output.dir} directory as follows  
     `>wlpenv featureManager install --offlineOnly --location=<zfs_dir> wshandler-1.0 `  
4. Add the user wshandler-1.0 feature to the feature manger list in server.xml along with the jaxws-2.2 feature as follows  
     `<feature>usr:wshandler-1.0</feature> `    
     `<feature>jaxws-2.2</feature> `
  


#Reference

* WebSphere Liberty global handler [SPI] (http://www.ibm.com/support/knowledgecenter/en/SSAW57_8.5.5/com.ibm.websphere.javadoc.liberty.doc/com.ibm.websphere.appserver.spi.globalhandler_1.0-javadoc/index.html)
* Liberty featureManager [command] (http://www.ibm.com/support/knowledgecenter/en/SSAW57_8.5.5/com.ibm.websphere.wlp.nd.doc/ae/rwlp_command_featuremanager.html)
* Adding web services global handlers Knowledge Center [topic] (http://www.ibm.com/support/knowledgecenter/en/SSAW57_8.5.5/com.ibm.websphere.wlp.nd.multiplatform.doc/ae/twlp_web_services_global_handlers.html)



