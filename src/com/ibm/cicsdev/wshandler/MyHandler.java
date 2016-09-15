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


import java.io.PrintStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.ibm.cics.server.IsCICS;
import com.ibm.cics.server.Task;
import com.ibm.wsspi.webservices.handler.GlobalHandlerMessageContext; // WAS SPI for global handler
import com.ibm.wsspi.webservices.handler.Handler;

public class MyHandler implements Handler {

	private static PrintStream out = System.out;

	public void handleFault(GlobalHandlerMessageContext arg0) {
		//  Add fault handle implementation here
	}

	/*  
	 * handleMessage invoked for every message as determined by registration class
	 * 
	 * @see com.ibm.wsspi.webservices.handler.Handler#handleMessage(com.ibm.wsspi.webservices.handler.GlobalHandlerMessageContext)
	 */
	public void handleMessage(GlobalHandlerMessageContext msgctxt) throws Exception {

		//Get HTTP request details
		HttpServletRequest htpr = msgctxt.getHttpServletRequest();
		StringBuffer path = htpr.getRequestURL();

		printMsg("Entered com.ibm.cicsdev.wshandler with URL " + path);

		try {

			// Adapt the handler message to a SOAP message and get message and header
			SOAPMessageContext soapmsgctxt = msgctxt.adapt(SOAPMessageContext.class);
			SOAPMessage message = soapmsgctxt.getMessage();
			SOAPHeader header = message.getSOAPHeader();

			//Output WS operation and service
			printMsg("WSDL operation: " + soapmsgctxt.get(SOAPMessageContext.WSDL_OPERATION));
			printMsg("WSDL service: " + soapmsgctxt.get(SOAPMessageContext.WSDL_SERVICE));

			// Iterate over the WS header elements and print out
			boolean headerFound = false;
			Iterator<?> headerElements = header.examineAllHeaderElements();
			while (headerElements.hasNext()) {	

				SOAPHeaderElement headerElement = (SOAPHeaderElement) headerElements.next();
				String msg = "WS header element: " + headerElement.getValue();
				printMsg(msg);
				headerFound = true;
			}
			if (!headerFound) {
				printMsg("No WS header elements found ");         
			}

		} catch (Exception e) { // Generic catch for brevity
			printMsg("Exception in com.ibm.cicsdev.wshandler: " + e);
		}

	}


	/**
	 * @param msg - Message to be written to System.out
	 */
	private void printMsg (String msg){

		// Get time stamp for log message
		SimpleDateFormat dfTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	
		Date timestamp = new Date();		

		// Check thread is a CICS enable task and and add CICS task ID to message
		if (IsCICS.getApiStatus() == IsCICS.CICS_REGION_AND_API_ALLOWED) {
			Task task = Task.getTask();
			int taskid = task.getTaskNumber();

			task.out.println(MessageFormat.format ("{0} Task({1}) {2}", dfTime.format(timestamp), taskid, msg));
			task.out.flush();

		}
		else {
			out.println(MessageFormat.format ("{0} {2}", dfTime.format(timestamp), msg));
			out.flush();
		}		 
	}

} 
