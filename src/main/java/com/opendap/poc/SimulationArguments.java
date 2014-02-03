/**
 * 
 */
package com.opendap.poc;

import com.rii.wp.util.SimulationHelper;

/**
 * @author I
 * 
 */
public class SimulationArguments {

	private long simId;
	private String fileStructure;
	private String districtID;
	private String crop;
	private String cropSeason;
	private String gcm;
	private SimulationHelper simulationHelper;
	private String relativeFileStructure;
	
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
	 * @return the fileStructure
	 */
	public String getFileStructure() {
		return fileStructure;
	}
	/**
	 * @param fileStructure the fileStructure to set
	 */
	public void setFileStructure(String fileStructure) {
		this.fileStructure = fileStructure;
	}
	/**
	 * @return the districtID
	 */
	public String getDistrictID() {
		return districtID;
	}
	/**
	 * @param districtID the districtID to set
	 */
	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}
	/**
	 * @return the crop
	 */
	public String getCrop() {
		return crop;
	}
	/**
	 * @param crop the crop to set
	 */
	public void setCrop(String crop) {
		this.crop = crop;
	}
	/**
	 * @return the cropSeason
	 */
	public String getCropSeason() {
		return cropSeason;
	}
	/**
	 * @param cropSeason the cropSeason to set
	 */
	public void setCropSeason(String cropSeason) {
		this.cropSeason = cropSeason;
	}
	/**
	 * @return the gcm
	 */
	public String getGcm() {
		return gcm;
	}
	/**
	 * @param gcm the gcm to set
	 */
	public void setGcm(String gcm) {
		this.gcm = gcm;
	}
	/**
	 * @return the simulationHelper
	 */
	public SimulationHelper getSimulationHelper() {
		return simulationHelper;
	}
	/**
	 * @param simulationHelper the simulationHelper to set
	 */
	public void setSimulationHelper(SimulationHelper simulationHelper) {
		this.simulationHelper = simulationHelper;
	}
	/**
	 * @return the relativeFileStructure
	 */
	public String getRelativeFileStructure() {
		return relativeFileStructure;
	}
	/**
	 * @param relativeFileStructure the relativeFileStructure to set
	 */
	public void setRelativeFileStructure(String relativeFileStructure) {
		this.relativeFileStructure = relativeFileStructure;
	}
	
	

}
