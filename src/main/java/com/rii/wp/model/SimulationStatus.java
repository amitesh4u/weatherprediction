/**
 * 
 */
package com.rii.wp.model;

import java.util.Date;

/**
 * @author I
 * 
 */
public class SimulationStatus {

	private long simId;
	private String simStage;
	private Date statusAt;

	/**
	 * @return the simId
	 */
	public long getSimId() {
		return simId;
	}

	/**
	 * @param simId
	 *            the simId to set
	 */
	public void setSimId(long simId) {
		this.simId = simId;
	}

	/**
	 * @return the simStage
	 */
	public String getSimStage() {
		return simStage;
	}

	/**
	 * @param simStage
	 *            the simStage to set
	 */
	public void setSimStage(String simStage) {
		this.simStage = simStage;
	}

	/**
	 * @return the statusAt
	 */
	public Date getStatusAt() {
		return statusAt;
	}

	/**
	 * @param statusAt
	 *            the statusAt to set
	 */
	public void setStatusAt(Date statusAt) {
		this.statusAt = statusAt;
	}

}
