/**
 * 
 */
package com.opendap.poc;

import java.io.File;

/**
 * @author I
 *
 */
public class SimulationConstants {

	public static final String SIM_TYPE_CMH = "cmh";
	public static final String SIM_TYPE_CMNHMM = "cmnhmm";
	public static final String SIM_TYPE_RMNHMM = "rmnhmm";
	
	public static final int COUNTRY_INDIA = 1;
	public static final int COUNTRY_BRAZIL = 2;
	
	public static final String SIMULATION_STATUS_COMPLETED = "COMPLETED";
	public static final String SIMULATION_STATUS_FAILED = "FAILED";
	public static final String SIMULATION_STATUS_INPROGRESS = "IN PROGRESS";
	public static final String SIMULATION_STATUS_INITIATED = "INITIATED";
	
	public static final String DATA_FOLDER = "Data";
	
	public static final String BELUGA_PATH = "E:" + File.separator + "Topol" + File.separator + "beluga" + File.separator + "data" + File.separator + "arindam" + File.separator + "CropModelOutput" + File.separator;
}
