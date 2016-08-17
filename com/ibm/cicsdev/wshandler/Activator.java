/* Licensed Materials - Property of IBM                                   */
/*                                                                        */
/* SAMPLE                                                                 */
/*                                                                        */
/* (c) Copyright IBM Corp. 2016 All Rights Reserved                       */       
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/*                                                                        */ 

package com.ibm.cicsdev.wshandler;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.ibm.wsspi.webservices.handler.Handler;
import com.ibm.wsspi.webservices.handler.HandlerConstants;

// OSGi bundle activator 
public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {

		printMsg( "Starting com.ibm.cicsdev.wshandler" );

		// Create hashtable to control when this handler feature will be invoked
		final Hashtable<String, Object> handlerProps = new Hashtable<String, Object>();
		handlerProps.put(HandlerConstants.ENGINE_TYPE, HandlerConstants.ENGINE_TYPE_JAXWS);
		handlerProps.put(HandlerConstants.FLOW_TYPE, HandlerConstants.FLOW_TYPE_IN);
		handlerProps.put(HandlerConstants.IS_CLIENT_SIDE, true);
		handlerProps.put(HandlerConstants.IS_SERVER_SIDE, true);
		handlerProps.put(org.osgi.framework.Constants.SERVICE_RANKING, 3);

		// Register the implementation class into the service registry
		MyHandler wsHandler = new MyHandler();
		context.registerService(Handler.class, wsHandler, handlerProps);
	}

	public void stop(BundleContext context) throws Exception {
		printMsg( "Stopping com.ibm.cicsdev.wshandler" );
	}
	
	/**
	 * @param msg - Message to be written to CICS TSQ or System.out
	 */
	private void printMsg (String msg){
			
		// Get time stamp for log message
		Date timestamp = new Date();	
		SimpleDateFormat dfTime = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");	
		System.out.println(MessageFormat.format ("{0} {1}", dfTime.format(timestamp), msg));
				 
	}

}
