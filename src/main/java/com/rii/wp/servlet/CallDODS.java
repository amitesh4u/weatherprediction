package com.rii.wp.servlet;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opendap.poc.DODS;
import com.opendap.poc.SimulationConstants;
import com.rii.wp.controller.CropModelController;
import com.rii.wp.model.CropModel;
import com.rii.wp.model.SimDetailsVO;
import com.rii.wp.model.Simulation;
import com.rii.wp.util.DateUtil;
import com.rii.wp.util.SimulationHelper;


public class CallDODS implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(CallDODS.class);
	
	private SimulationHelper simulationHelper;
	private CropModel cropModel;
	private Simulation simulation;
	
	public CallDODS(final CropModel cropModel, final Simulation simulation, final SimulationHelper simulationHelper){
		this.cropModel = cropModel;
		this.simulation = simulation;
		this.simulationHelper = simulationHelper;
	}
	
	public static void main(String[] args){
		new CallDODS(new CropModel(), new Simulation(), new SimulationHelper()).run();
	}

	@Override
	public void run() {
		DODS dods = new DODS();
	    try {
	    	dods.buildDODS(cropModel, simulation, simulationHelper);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in Simulation " + simulation.getSimId() + ": {}, {}",e.getMessage(), e.getCause());
			
			Date currentDateWithLocalTimeZone = DateUtil.getCurrentDateWithLocalTimeZone();
			simulationHelper.updateSimulationDetails(simulation.getSimId(), SimulationConstants.SIMULATION_STATUS_FAILED, null, "Message:" + e.getMessage() + ", Cause:" + e.getCause(), currentDateWithLocalTimeZone, currentDateWithLocalTimeZone);
			
			SimDetailsVO simDetailsVO = CropModelController.getSimDetailsMap().get(simulation.getSimId());
			if(simDetailsVO != null){
				simDetailsVO.setStatus(SimulationConstants.SIMULATION_STATUS_FAILED);
				simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append("Simulation Failed. Please try again: " + e.getMessage()));
				
				CropModelController.getSimDetailsMap().put(simulation.getSimId(), simDetailsVO);
			}
		}
	}
}
