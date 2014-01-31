/**
 * 
 */
package com.rii.wp.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.rii.wp.dao.SimulationDAO;
import com.rii.wp.model.Simulation;
import com.rii.wp.model.SimulationStatus;

/**
 * @author I
 *
 */
public class SimulationHelper {
	
	@Autowired
	SimulationDAO simulationDAO;
	
	/**
	 * @param simulationDAO the simulationDAO to set
	 */
	public void setSimulationDAO(SimulationDAO simulationDAO) {
		this.simulationDAO = simulationDAO;
	}



	public void setSimulationStatus(final long simId, final String stage, final Date statusAt){
		SimulationStatus simulationStatus = new SimulationStatus();
        simulationStatus.setSimId(simId);
        simulationStatus.setSimStage(stage);
        simulationStatus.setStatusAt(statusAt);
        simulationDAO.setSimulationStatus(simulationStatus);
	}

	public void updateSimulationDetails(final long simId, final String status, final String output, final String additionalMsg, final Date completionDate, final Date completedOn){
		Simulation simulation = new Simulation();
		simulation.setStatus(status);
		simulation.setOutput(output);
		simulation.setAddtionalMsg(additionalMsg);
		simulation.setSimId(simId);
		simulation.setCompletionDate(completionDate);
		simulation.setCompletedOn(completedOn);
		
        simulationDAO.updateSimulationDetails(simulation);
	}
	
}
