/**
 * 
 */
package com.rii.wp.model;

import java.io.Serializable;

/**
 * @author I
 * 
 */
public class NHMMInputSeeds implements Serializable {

	/**
 * 
 */
	private static final long serialVersionUID = 1450315261106136084L;

	private String noHiddenStates;
	private String noPredictors;
	private String noRestarts;
	private String emPrecision;
	private String randomNoSeed;
	private String noSimulations;
	/**
	 * @return the noHiddenStates
	 */
	public String getNoHiddenStates() {
		return noHiddenStates;
	}
	/**
	 * @param noHiddenStates the noHiddenStates to set
	 */
	public void setNoHiddenStates(String noHiddenStates) {
		this.noHiddenStates = noHiddenStates;
	}
	/**
	 * @return the noPredictors
	 */
	public String getNoPredictors() {
		return noPredictors;
	}
	/**
	 * @param noPredictors the noPredictors to set
	 */
	public void setNoPredictors(String noPredictors) {
		this.noPredictors = noPredictors;
	}
	/**
	 * @return the noRestarts
	 */
	public String getNoRestarts() {
		return noRestarts;
	}
	/**
	 * @param noRestarts the noRestarts to set
	 */
	public void setNoRestarts(String noRestarts) {
		this.noRestarts = noRestarts;
	}
	/**
	 * @return the emPrecision
	 */
	public String getEmPrecision() {
		return emPrecision;
	}
	/**
	 * @param emPrecision the emPrecision to set
	 */
	public void setEmPrecision(String emPrecision) {
		this.emPrecision = emPrecision;
	}
	/**
	 * @return the randomNoSeed
	 */
	public String getRandomNoSeed() {
		return randomNoSeed;
	}
	/**
	 * @param randomNoSeed the randomNoSeed to set
	 */
	public void setRandomNoSeed(String randomNoSeed) {
		this.randomNoSeed = randomNoSeed;
	}
	/**
	 * @return the noSimulations
	 */
	public String getNoSimulations() {
		return noSimulations;
	}
	/**
	 * @param noSimulations the noSimulations to set
	 */
	public void setNoSimulations(String noSimulations) {
		this.noSimulations = noSimulations;
	}

	
}
