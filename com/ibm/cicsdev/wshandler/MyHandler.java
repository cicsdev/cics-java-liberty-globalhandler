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
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.soap.SOAPMessageContext;

// WAS SPI for global handler
import com.ibm.wsspi.webservices.handler.GlobalHandlerMessageContext;
import com.ibm.wsspi.webservices.handler.Handler;

public class MyHandler implements Handler {

	private static PrintStream out = System.out;

	public void handleFault(GlobalHandlerMessageContext arg0) {
		//  Add fault handle implementation here
	}

	// handleMessage invoked for every message as determined by registration
	public void handleMessage(GlobalHandlerMessageContext msgctxt) throws Exception {

		//Get HTTP request details
		HttpServletRequest htpr = msgctxt.getHttpServletRequest();
		StringBuffer path = htpr.getRequestURL();
		out.println( "Entered com.ibm.cicsdev.wshandler with URL " + path );			

		try {

			// Adapt the handler message to a SOAP message and get message and header
			SOAPMessageContext soapmsgctxt = msgctxt.adapt(SOAPMessageContext.class);
			SOAPMessage message = soapmsgctxt.getMessage();
			SOAPHeader header = message.getSOAPHeader();

			//Output WS operation and service
			out.println("WSDL operation: " + soapmsgctxt.get(SOAPMessageContext.WSDL_OPERATION));
			out.println("WSDL service: " + soapmsgctxt.get(SOAPMessageContext.WSDL_SERVICE));

			// Iterate over the WS header elements and print out
			boolean headerFound = false;
			Iterator<?> headerElements = header.examineAllHeaderElements();
			while (headerElements.hasNext()) {	
			  
				SOAPHeaderElement headerElement = (SOAPHeaderElement) headerElements.next();
				out.println("WS header element: " + headerElement.getValue()); 
				headerFound = true;
			}
			if (!headerFound) {
				out.println("No WS header elements found ");         
			}

		} catch (Exception e) { // Generic catch for brevity
			out.println("Exception in com.ibm.cicsdev.wshandler: " + e);
		}

	}

} 
