/**
 * 
 */
package com.rii.wp.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author I
 *
 */
public class SimDetailsVO {
	
	private String status = "In Progress";
	private StringBuilder statusMessage = new StringBuilder("");
	
	private Map<String,String> links = new LinkedHashMap<String,String>(); // linkURL,linkText
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the statusMessage
	 */
	public StringBuilder getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(StringBuilder statusMessage) {
		this.statusMessage = statusMessage;
	}
	/**
	 * @return the links
	 */
	public Map<String, String> getLinks() {
		return links;
	}
	/**
	 * @param links the links to set
	 */
	public void setLinks(Map<String, String> links) {
		this.links = links;
	}
	
	
}
