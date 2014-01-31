/**
 * 
 */
package com.rii.wp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rii.wp.dao.SimulationDAO;
import com.rii.wp.model.CropModel;
import com.rii.wp.model.SimDetailsVO;
import com.rii.wp.model.Simulation;
import com.rii.wp.servlet.CallDODS;
import com.rii.wp.util.DateUtil;
import com.rii.wp.util.SimulationHelper;

/**
 * @author I
 *
 */
/**
 * Handles requests for the application Simulation page.
 */
@Controller
public class CropModelController {
	
	private static final Logger logger = LoggerFactory.getLogger(CropModelController.class);
	private static Map<Long, SimDetailsVO> simDetailsMap = new LinkedHashMap<Long, SimDetailsVO>();

	@Autowired
	SimulationDAO simulationDAO;
	
	@Autowired
	SimulationHelper simulationHelper;
	
	@RequestMapping(value = "/CropmodelStatus", method = RequestMethod.GET)
	public void returnCurrentSimulationStatus(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		StringBuilder responseVal = new StringBuilder();
		SimDetailsVO simDetailsVO = getSimDetailsMap().get(Long.parseLong(request.getParameter("id")));
		if(simDetailsVO != null){
			StringBuilder sb = new StringBuilder(
					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><simulation>");
			sb.append("<status>" + simDetailsVO.getStatus() + "</status>");
			sb.append("<statusMessage>" + simDetailsVO.getStatusMessage().toString() + "</statusMessage>");
			sb.append("<links>");
			
			Set<Entry<String, String>> linksEntrySet = simDetailsVO.getLinks().entrySet();
			for(Entry<String, String> linkEntry : linksEntrySet){
				sb.append("<link><linkUrl>").append(linkEntry.getKey()).append("</linkUrl><linkText>").append(linkEntry.getValue()).append("</linkText></link>");
				
			}
			
//			sb.append("<link><linkUrl>http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/188/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=soil+year+fig-+colors+-fig%26my.help=more+options</linkUrl><linkText>Historical Crop Yield per grid point</linkText></link>");
//			sb.append("<link><linkUrl>http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options</linkUrl><linkText>Historical Crop Yield per grid point per soil</linkText></link>");
//			sb.append("<link><linkUrl>http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/sim_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html</linkUrl><linkText>Rainfall Simulation Output from machine Learning</linkText></link>");
//			sb.append("<link><linkUrl>http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.NCDC/.ERSST/.version3b/.sst/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/X+Y+fig-+colors+coasts+-fig+//soil/3676./plotvalue//XOVY+null+psdef//plotborder+72+psdef//plotaxislength+432+psdef/figviewer.html?my.help=%26map.soil.plotvalue=3676.%26map.Y.units=degree_north%26map.Y.plotlast=89N%26map.url=+%26map.domain=+%7B+%2Fsoil+3858.+plotvalue+X+-30+330+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.zoom=Zoom%26map.Y.plotfirst=89S%26map.X.plotfirst=30W%26map.X.units=degree_east%26map.X.modulus=360%26map.X.plotlast=30W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200</linkUrl><linkText>Corellation of Crop with SSTs per soil</linkText></link>");
//			sb.append("<link><linkUrl>http://iridl.ldeo.columbia.edu/expert/SOURCES/.IMD/.NCC1-2005/.v2p0/.rf/T/%28Jun%201971%29%28Sep%202009%29RANGE/T/monthlyAverage/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/73.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options</linkUrl><linkText>Corellation of Crop Output with rainfall per soil</linkText></link>");
//			sb.append("<link><linkUrl>http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.uwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?my.help=more+options%26map.soil.plotvalue=3858.%26map.lat.units=degree_north%26map.lat.plotlast=90N%26map.url=lon+lat+fig-+colors+coasts+lakes+-fig%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+lat+-90.+90.+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef%26map.zoom=Zoom%26map.lat.plotfirst=90S%26map.lon.plotfirst=1W%26map.lon.units=degree_east%26map.lon.modulus=360%26map.lon.plotlast=1W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.newurl.grid0=lon%26map.newurl.grid1=lat%26map.newurl.land=draw+coasts%26map.newurl.plot=colors%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.XOVY=auto%26map.color_smoothing=1%26map.framelbl=framelabelstart%26map.framelabeltext=%26map.iftime=25%26map.mftime=25%26map.fftime=200</linkUrl><linkText>Corellation of Crop with u-wind per soil</linkText></link>");
//			sb.append("<link><linkUrl>http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.vwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/lon/lat/fig-/colors/coasts/-fig//soil/3858./plotvalue/lat/-90/90/plotrange//XOVY/null/psdef//plotborder/72/psdef//plotaxislength/432/psdef/figviewer.html?map.here.x=250%26map.here.y=119%26map.url=+%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.lon.width=360.%26map.lat.width=180.%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200</linkUrl><linkText>Corellation of crop model with v-wind per soil</linkText></link>");
//			sb.append("<link><linkUrl>http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/72.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/23.5/VALUE/T/fig-/green/line/red/line/blue/line/-fig//rf/1.043575/13.73149/plotrange//rf/1.043575/13.73149/plotrange//plotaxislength/432/psdef//XOVY/null/psdef//plotborder/72/psdef/</linkUrl><linkText>Crop Response to soil on historical data</linkText></link>");
			sb.append("</links></simulation>");
			responseVal = sb;
		}
		logger.info(responseVal.toString());
		out.println(responseVal);
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	@RequestMapping(value = "/Cropmodel", method = RequestMethod.POST)
	public void runCropModelSim(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession(false);
	    if (session != null && session.getAttribute("is_logged_in") != null) {
	    	logger.info("session.getAttribute('is_logged_in') is {} " , session.getAttribute("is_logged_in"));
	    	boolean isLoggedIn = (Boolean) session.getAttribute("is_logged_in");
	    	logger.info("isLoggedIn {}",  isLoggedIn);
	    	if(isLoggedIn){
	    		// user is logged in
	    		try{
    	    	   // 1. get received JSON data from request
	    			logger.info("Received JSON data from request");
    	    	    BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
    		        String jsonInputString = "";
    		        if(br != null){
    		            jsonInputString = br.readLine();
    		        }
    		        logger.info("jsonInputString: {}", jsonInputString);
    		        jsonInputString = jsonInputString.replaceAll(",}","}");
    		        jsonInputString = jsonInputString.replaceAll("null,","");
    		        jsonInputString = jsonInputString.replaceAll(",null","");
    		        logger.info("jsonInputString after removing null and additional commas: {}", jsonInputString);
    		        
    		        // 2. Initiate jackson mapper
    		        ObjectMapper mapper = new ObjectMapper();
    		 
    		        // 3. Convert received JSON to CropModel
    		        logger.info("Convert received JSON to CropModel");
    		        CropModel cropModel = mapper.readValue(jsonInputString, CropModel.class);
    		        logger.info("SelDistrict:" + cropModel.getSelDistrict());
    		        logger.info("Cmga().getExpNo: " + (cropModel.getGenetic() == null ? "" : cropModel.getGenetic().getExpNo()));
    		        logger.info("Planting().size(): " + (cropModel.getPlanting() == null?0: cropModel.getPlanting().size()));
    		        logger.info("Irrigation().get(0).getDate(): " + (cropModel.getIrrigation() == null?0: cropModel.getIrrigation().get(0).getDate()));
    		        logger.info("Fertilizer().size(): " + (cropModel.getFertilizer() == null?0: cropModel.getFertilizer().size()));
    		        logger.info("NhmmInputSeeds(): " + (cropModel.getNhmmInputSeeds() == null ? "": cropModel.getNhmmInputSeeds()));
    		        
    		        /* To convert CropModel object to Bytestream for writing in DB */
//    		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    		        ObjectOutputStream oos = new ObjectOutputStream(baos);
//    		        oos.writeObject(cropModel);
//    		        oos.close();
//    		        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

    		        // 4. Create Simulation Object and store in DB
    		        logger.info("Create Simulation Object and store in DB");
    		        Simulation simulation = new Simulation();
    		        simulation.setUserId(Long.valueOf((String)session.getAttribute("userId")));
    		        simulation.setSimType(cropModel.getSimulationType());
    		        simulation.setInputParam(jsonInputString);
    		        Date startDateTime = DateUtil.getCurrentDateWithLocalTimeZone();
    		        simulation.setStartDate(startDateTime);
    		        simulation.setStartedOn(startDateTime);
    		        simulation.setStatus("In Process");
    		        simulation = simulationDAO.setSimulationDetails(simulation);
    		        if(simulation.getSimId() == 0){
    		        	throw new Exception("Error inserting Simulation details");
    		        }
    		        
    		        // 5. Create Simulation Status Object and store in DB
    		        logger.info("Create Simulation Status Object and store in DB");
    		        simulationHelper.setSimulationStatus(simulation.getSimId(), "Initiated", startDateTime);
    		        
    		        // 6. Run the simulation in a Thread
    		        logger.info("Run the simulation in a Thread");
    		        runSimulation(cropModel, simulation);
    		        
    		        // 7. Return JSON Response with Simulation Id
    		        logger.info("Return JSON Response with Simulation Id:{}", simulation.getSimId());
    		        response.setContentType("application/json");
    		        response.getWriter().write("{\"status\":\"success\",\"id\":\""+ simulation.getSimId() + "\",\"num\":\"" + simulation.getSimNum() + "\"}");
    	       }catch(Exception e){
    	    	   logger.error("Error running simulation: {}, {} ", e.getMessage(), e.getCause());
    	    	   response.setContentType("application/json");
    	    	   response.getWriter().write("{\"status\":\"failure\"}");
    	       }
	    	}
	    }
	}

	/**
	 * Starts the Simulation in a new Thread
	 * 
	 * @param cropModel
	 * @param simulation
	 */
	private void runSimulation(final CropModel cropModel, final Simulation simulation) {
		SimDetailsVO simDetailsVO = new SimDetailsVO();
		getSimDetailsMap().put(simulation.getSimId(), simDetailsVO);
		Runnable r = new CallDODS(cropModel, simulation, simulationHelper);
		new Thread(r).start();
	}
	
	@RequestMapping(value = "/SimulationReport", method = RequestMethod.GET)
	public void returnSimulationReport(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(false);
	    if (session != null && session.getAttribute("is_logged_in") != null) {
	    	logger.info("session.getAttribute('is_logged_in') is {} " , session.getAttribute("is_logged_in"));
	    	boolean isLoggedIn = (Boolean) session.getAttribute("is_logged_in");
	    	logger.info("isLoggedIn {}",  isLoggedIn);
	    	if(isLoggedIn){
	    		// user is logged in
	    		try{
	    			PrintWriter out = response.getWriter();
	    			StringBuilder responseVal = new StringBuilder();
	    			
	    			List<Simulation> simulationDetailsByUser = simulationDAO.getSimulationDetailsByUser(Long.valueOf((String)session.getAttribute("userId")), 
	    					DateUtil.getNthDateWithLocalTimeZone(6),DateUtil.getCurrentDateWithLocalTimeZone(), 5);
	    			
	    			StringBuilder sb = new StringBuilder(
	    					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><simulationReport>");
	    				sb.append("<simulations>");
	    			for(Simulation simulation : simulationDetailsByUser){
	    				sb.append("<simulation>");
	    				sb.append("<id>" + simulation.getSimId() + "</id>");
	    				sb.append("<date>" + DateUtil.convertDateToString(simulation.getStartDate(),"dd-MM-yy") + "</date>");
	    				sb.append("<num>" + simulation.getSimNum() + "</num>");
	    				sb.append("<type>" + simulation.getSimDesc() + "</type>");
	    				sb.append("<status>" + simulation.getStatus() + "</status>");
	    				sb.append("<links>");
	    				
	    				if(StringUtils.hasText(simulation.getOutput())){
	    					ObjectMapper mapper = new ObjectMapper();
		    				LinkedHashMap<String, String> outputLinks;
		    				try {
		    					outputLinks = mapper.readValue(simulation.getOutput(), new TypeReference<LinkedHashMap<String,String>>() {});
		    					//System.out.println(outputLinks.size());
		    					Set<Entry<String, String>> linksEntrySet = outputLinks.entrySet();
			    				for(Entry<String, String> linkEntry : linksEntrySet){
			    					sb.append("<link><linkUrl>").append(linkEntry.getValue()).append("</linkUrl><linkText>").append(linkEntry.getKey()).append("</linkText></link>");
			    				}
		    				} catch (JsonParseException e) {
		    					logger.error("Simulation Report - JsonParseException: {}, {}", e.getMessage(), e.getCause());
		    				} catch (JsonMappingException e) {
		    					logger.error("Simulation Report - JsonMappingException: {}, {}", e.getMessage(), e.getCause());
		    				} catch (IOException e) {
		    					logger.error("Simulation Report - IOException: {}, {}", e.getMessage(), e.getCause());
		    				}	
	    				}
	    				sb.append("</links></simulation>");
	    			}
	    			sb.append("</simulations></simulationReport>");
	    			responseVal = sb;
	    			logger.info(responseVal.toString());
	    			out.println(responseVal);
	    			
	    		}catch(Exception e){
	    	    	logger.error("Error getting Simulation Report: {}, {} ", e.getMessage(), e.getCause());
	    	    }
	    	}
	    }
	}
	/**
	 * @return the simDetailsMap
	 */
	public static Map<Long, SimDetailsVO> getSimDetailsMap() {
		return simDetailsMap;
	}

//	/**
//	 * @param simDetailsMap the simDetailsMap to set
//	 */
//	public static void setSimDetailsMap(Map<Long, SimDetailsVO> simDetailsMap) {
//		CropModelController.simDetailsMap = simDetailsMap;
//	}
	
}
