/**
 * 
 */
package com.rii.wp.model;

import java.util.Date;

/**
 * @author I
 * 
 */
public class Simulation {

	private long simId;
	private long userId;
	private int simNum;
	private String simType;
	private String simDesc;
	private Date startDate;
	private Date completionDate;
	private Date startedOn;
	private Date completedOn;
	private String status;
	private String inputParam; // JSON
	private String output;
	private String addtionalMsg;
	/**
	 * @return the simId
	 */
	public long getSimId() {
		return simId;
	}
	/**
	 * @param simId the simId to set
	 */
	public void setSimId(long simId) {
		this.simId = simId;
	}
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/**
	 * @return the simNum
	 */
	public int getSimNum() {
		return simNum;
	}
	/**
	 * @param simNum the simNum to set
	 */
	public void setSimNum(int simNum) {
		this.simNum = simNum;
	}
	/**
	 * @return the simType
	 */
	public String getSimType() {
		return simType;
	}
	/**
	 * @param simType the simType to set
	 */
	public void setSimType(String simType) {
		this.simType = simType;
	}
	/**
	 * @return the simDesc
	 */
	public String getSimDesc() {
		return simDesc;
	}
	/**
	 * @param simDesc the simDesc to set
	 */
	public void setSimDesc(String simDesc) {
		this.simDesc = simDesc;
	}
	/**
	 * @return the inputParam
	 */
	public String getInputParam() {
		return inputParam;
	}
	/**
	 * @param inputParam the inputParam to set
	 */
	public void setInputParam(String inputParam) {
		this.inputParam = inputParam;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the completionDate
	 */
	public Date getCompletionDate() {
		return completionDate;
	}
	/**
	 * @param completionDate the completionDate to set
	 */
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	/**
	 * @return the startedOn
	 */
	public Date getStartedOn() {
		return startedOn;
	}
	/**
	 * @param startedOn the startedOn to set
	 */
	public void setStartedOn(Date startedOn) {
		this.startedOn = startedOn;
	}
	/**
	 * @return the completedOn
	 */
	public Date getCompletedOn() {
		return completedOn;
	}
	/**
	 * @param completedOn the completedOn to set
	 */
	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}
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
	 * @return the addtionalMsg
	 */
	public String getAddtionalMsg() {
		return addtionalMsg;
	}
	/**
	 * @param addtionalMsg the addtionalMsg to set
	 */
	public void setAddtionalMsg(String addtionalMsg) {
		this.addtionalMsg = addtionalMsg;
	}
	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}
	/**
	 * @param output the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
	}

	
}
