/**
 * 
 */
package com.rii.wp.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author I
 *
 */
public class CropModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7092364956525882525L;
	
	private String simulationType;
	private String selCountry;
	private String selState;
	private String selDistrict;
	private String selCropName;
	private String selCropSeason;
	private String selGeneralCirculationModel;
	private String selGCMSeason;
	private String addinp;
	
	private List<PlantingDetails> planting;
	private List<IrrigationDetails> irrigation;
	private List<FertilizerDetails> fertilizer;
	private GeneticParameters genetic;
	private NHMMInputSeeds nhmmInputSeeds;
	
	
	/**
	 * @return the simulationType
	 */
	public String getSimulationType() {
		return simulationType;
	}

	/**
	 * @param simulationType the simulationType to set
	 */
	public void setSimulationType(String simulationType) {
		this.simulationType = simulationType;
	}

	/**
	 * @return the selCountry
	 */
	public String getSelCountry() {
		return selCountry;
	}

	/**
	 * @param selCountry the selCountry to set
	 */
	public void setSelCountry(String selCountry) {
		this.selCountry = selCountry;
	}

	/**
	 * @return the selState
	 */
	public String getSelState() {
		return selState;
	}

	/**
	 * @param selState the selState to set
	 */
	public void setSelState(String selState) {
		this.selState = selState;
	}

	/**
	 * @return the selDistrict
	 */
	public String getSelDistrict() {
		return selDistrict;
	}

	/**
	 * @param selDistrict the selDistrict to set
	 */
	public void setSelDistrict(String selDistrict) {
		this.selDistrict = selDistrict;
	}

	/**
	 * @return the selCropName
	 */
	public String getSelCropName() {
		return selCropName;
	}

	/**
	 * @param selCropName the selCropName to set
	 */
	public void setSelCropName(String selCropName) {
		this.selCropName = selCropName;
	}

	/**
	 * @return the selCropSeason
	 */
	public String getSelCropSeason() {
		return selCropSeason;
	}

	/**
	 * @param selCropSeason the selCropSeason to set
	 */
	public void setSelCropSeason(String selCropSeason) {
		this.selCropSeason = selCropSeason;
	}

	/**
	 * @return the selGeneralCirculationModel
	 */
	public String getSelGeneralCirculationModel() {
		return selGeneralCirculationModel;
	}

	/**
	 * @param selGeneralCirculationModel the selGeneralCirculationModel to set
	 */
	public void setSelGeneralCirculationModel(String selGeneralCirculationModel) {
		this.selGeneralCirculationModel = selGeneralCirculationModel;
	}

	/**
	 * @return the selGCMSeason
	 */
	public String getSelGCMSeason() {
		return selGCMSeason;
	}

	/**
	 * @param selGCMSeason the selGCMSeason to set
	 */
	public void setSelGCMSeason(String selGCMSeason) {
		this.selGCMSeason = selGCMSeason;
	}

	/**
	 * @return the planting
	 */
	public List<PlantingDetails> getPlanting() {
		return planting;
	}

	/**
	 * @param planting the planting to set
	 */
	public void setPlanting(List<PlantingDetails> planting) {
		this.planting = planting;
	}

	/**
	 * @return the genetic
	 */
	public GeneticParameters getGenetic() {
		return genetic;
	}

	/**
	 * @param genetic the genetic to set
	 */
	public void setGenetic(GeneticParameters genetic) {
		this.genetic = genetic;
	}

	/**
	 * @return the irrigation
	 */
	public List<IrrigationDetails> getIrrigation() {
		return irrigation;
	}

	/**
	 * @param irrigation the irrigation to set
	 */
	public void setIrrigation(List<IrrigationDetails> irrigation) {
		this.irrigation = irrigation;
	}

	/**
	 * @return the fertilizer
	 */
	public List<FertilizerDetails> getFertilizer() {
		return fertilizer;
	}

	/**
	 * @param fertilizer the fertilizer to set
	 */
	public void setFertilizer(List<FertilizerDetails> fertilizer) {
		this.fertilizer = fertilizer;
	}

	/**
	 * @return the nhmmInputSeeds
	 */
	public NHMMInputSeeds getNhmmInputSeeds() {
		return nhmmInputSeeds;
	}

	/**
	 * @param nhmmInputSeeds the nhmmInputSeeds to set
	 */
	public void setNhmmInputSeeds(NHMMInputSeeds nhmmInputSeeds) {
		this.nhmmInputSeeds = nhmmInputSeeds;
	}

	/**
	 * @return the addinp
	 */
	public String getAddinp() {
		return addinp;
	}

	/**
	 * @param addinp the addinp to set
	 */
	public void setAddinp(String addinp) {
		this.addinp = addinp;
	}

	
	
	
	

}
