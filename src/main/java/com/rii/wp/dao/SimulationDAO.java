/**
 * 
 */
package com.rii.wp.dao;

import java.util.Date;
import java.util.List;

import com.rii.wp.model.Simulation;
import com.rii.wp.model.SimulationStatus;

/**
 * @author I
 *
 */
public interface SimulationDAO extends BaseDAO {
	
	Simulation setSimulationDetails(Simulation simulation);
	
	List<Simulation> getSimulationDetailsByUser(long userId, Date fromDate, Date toDate, int fetchCount);
	
	Simulation getSimulationDetailsById(long simId);
	
	void updateSimulationDetails(Simulation simulation);
	
	void setSimulationStatus(SimulationStatus simulationStatus);
	
	List<SimulationStatus> getSimulationStatusBySimId(long simId);
	
	int getCurrentSimulationNum(long userId, Date inputDate);
	

}
