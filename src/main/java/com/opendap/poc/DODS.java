/*
 * Copyright 1998-2009 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */
//package com.opendap.poc;

//import opendap.dap.*;
//import opendap.dap.parser.ParseException;

package com.opendap.poc;

import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.rii.wp.controller.CropModelController;
import com.rii.wp.model.CropModel;
import com.rii.wp.model.SimDetailsVO;
import com.rii.wp.model.Simulation;
import com.rii.wp.util.DateUtil;
import com.rii.wp.util.SimulationHelper;

import java.util.Date;
import java.util.Properties;

public class DODS {
	
	public void buildDODS(final CropModel cropModel,
			final Simulation simulation, final SimulationHelper simulationHelper)
			throws IOException {
		long simId = simulation.getSimId();
		int simNum = simulation.getSimNum();
		long userId = simulation.getUserId();
		String simType = simulation.getSimType();

		System.out.println("SimId:" + simId);
		System.out.println("SimNum:" + simNum);
		System.out.println("SimType:" + simType);
		System.out.println("UserId:" + userId);
		
		SimDetailsVO simDetailsVO = CropModelController.getSimDetailsMap().get(simId);
		// simId = 1;
		if (null != simDetailsVO && simId > 0 && simNum > 0 && userId > 0) {

			simDetailsVO.setStatus(SimulationConstants.SIMULATION_STATUS_INPROGRESS);

			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
					.append("Starting Content Downloading&lt;/br&gt;"));
			simulationHelper.setSimulationStatus(simId,
					"Starting Content Downloading",
					DateUtil.getCurrentDateWithLocalTimeZone());
			
			/* Creating simulationArguments object to pass arguments to various methods */
			
			long countryNo = Long.parseLong(cropModel.getSelCountry());
			String crop = cropModel.getSelCropName();
			String cropSeason = cropModel.getSelCropSeason();
			String districtID = cropModel.getSelDistrict().replaceAll("\\s", "");
			String gcm = cropModel.getSelGeneralCirculationModel();

			System.out.println("Crop:" + crop);
			System.out.println("Crop season:" + cropSeason);
			System.out.println("Country:" + countryNo);
			System.out.println("District Id:" + districtID);
			System.out.println("General Circulation model:" + gcm);

			SimulationArguments simulationArguments = new SimulationArguments();
			simulationArguments.setCrop(crop);
			simulationArguments.setCropSeason(cropSeason);
			simulationArguments.setDistrictID(districtID);
			simulationArguments.setGcm(gcm);
			simulationArguments.setSimId(simId);
			simulationArguments.setSimulationHelper(simulationHelper);
			
			Properties properties = new Properties();
	        File file = new File(this.getClass().getResource("/ServerContentDownloader_" + countryNo + ".properties").getFile());
			FileInputStream fis = new FileInputStream(file);
			properties.load(fis);
			String folder = properties.getProperty("folder");
			System.out.println("Folder : " + folder);
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fileStructure = folder + File.separator + userId + File.separator
					+ simpleDateFormat.format(new Date()) + File.separator
					+ simNum + File.separator + countryNo;
	
			System.out.println("fileStructure: " + fileStructure);
			simulationArguments.setFileStructure(fileStructure);
			
	
			String relativeFileStructure = SimulationConstants.DATA_FOLDER + File.separator + userId + File.separator
					+ simpleDateFormat.format(new Date()) + File.separator
					+ simNum + File.separator + countryNo + File.separator + districtID;
	
			System.out.println("relativeFileStructure: " + relativeFileStructure);
			simulationArguments.setRelativeFileStructure(relativeFileStructure);
	
			if (countryNo == SimulationConstants.COUNTRY_INDIA) {
				/************************ Run for India ***************************/
				
				/******************** Download server dataset **********************/
				ServerContentDownloader serverContentDownloader = new ServerContentDownloader();
				serverContentDownloader.createAndSaveUrl(simulationArguments);

				simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
						.append(" -Running Simulation&lt;/br&gt;"));
				simulationHelper.setSimulationStatus(simId, "Running Simulation",
						DateUtil.getCurrentDateWithLocalTimeZone());

				if(simType.equalsIgnoreCase(SimulationConstants.SIM_TYPE_RMNHMM) || simType.equalsIgnoreCase(SimulationConstants.SIM_TYPE_CMNHMM)){
					
					/*********** Call the NHMM to build up the rainfall parameters **************/
					simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
							.append(" -Build NHMM Rainfall&lt;/br&gt;"));
					simulationHelper.setSimulationStatus(simId, "Build NHMM Rainfall",
							DateUtil.getCurrentDateWithLocalTimeZone());
	
					RunNHMM nhmm = new RunNHMM();
					nhmm.runNHMMModel(simulationArguments);
				}
				
				if(simType.equalsIgnoreCase(SimulationConstants.SIM_TYPE_CMNHMM)){
				
					/***************** Build the crop output based on NHMM Output *******************/
					
					 ReadNHMMOutput rno = new ReadNHMMOutput();
					 rno.makeCropInputFiles(simulationArguments); 

					 /***************** Write the crop  output based on NHMM into NetCDF ************/
					
					 WriteSimNetCDF writesimfile = new WriteSimNetCDF();
					 writesimfile.writeCDFFile(simulationArguments);
					 
				} 
				
				if(simType.equals(SimulationConstants.SIM_TYPE_CMH)){
					
					/******************* Build the crop output based on the historical data *****************/
					
					simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
							.append(" -Running Crop Model with Input&lt;/br&gt;"));
					simulationHelper.setSimulationStatus(simId, "Running Crop Model with Input",
							DateUtil.getCurrentDateWithLocalTimeZone());

					/***************** Build the crop output based on the historical data ********************/
					ReadHistoricalRainfall hisrain = new ReadHistoricalRainfall();
					hisrain.makeCropInputFiles(simulationArguments);
					
					/*********************** Write the output in Netcdf file format **************************/
					simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
							.append(" -Writing Netcdf file into Data library&lt;/br&gt;"));
					simulationHelper.setSimulationStatus(simId, "Writing Netcdf file into Data library",
							DateUtil.getCurrentDateWithLocalTimeZone());

					WriteNetCDF writefile = new WriteNetCDF();
					writefile.writeCDFFile(simulationArguments);
				 
				}
			} else if (countryNo == SimulationConstants.COUNTRY_BRAZIL) {
				System.out.println("**************Run for Brazil*************");

				/************** Download server dataset ******************/
				ServerContentDownloaderBrazil serverContentDownloaderBrazil = new ServerContentDownloaderBrazil();
				serverContentDownloaderBrazil.createAndSaveUrl(simulationArguments);

				simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
						.append(" -Running Simulation&lt;/br&gt;"));
				simulationHelper.setSimulationStatus(simId, "Running Simulation",
						DateUtil.getCurrentDateWithLocalTimeZone());

				if(simType.equalsIgnoreCase(SimulationConstants.SIM_TYPE_RMNHMM) || simType.equalsIgnoreCase(SimulationConstants.SIM_TYPE_CMNHMM)){
					
					/*********** Call the NHMM to build up the rainfall parameters **************/
					simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
							.append(" -Build NHMM Rainfall&lt;/br&gt;"));
					simulationHelper.setSimulationStatus(simId, "Build NHMM Rainfall",
							DateUtil.getCurrentDateWithLocalTimeZone());
	
					RunNHMM nhmm = new RunNHMM();
					nhmm.runNHMMModel(simulationArguments);
				}
				
				if(simType.equalsIgnoreCase(SimulationConstants.SIM_TYPE_CMNHMM)){
				
					/***************** Build the crop output based on NHMM Output *******************/
					
					 ReadNHMMOutput rno = new ReadNHMMOutput();
					 rno.makeCropInputFiles(simulationArguments); 

					 /***************** Write the crop  output based on NHMM into NetCDF ************/
					
					 WriteSimNetCDF writesimfile = new WriteSimNetCDF();
					 writesimfile.writeCDFFile(simulationArguments);
					 
				} 
				
				if(simType.equals(SimulationConstants.SIM_TYPE_CMH)){
					
					/******************* Build the crop output based on the historical data *****************/
					
					simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
							.append(" -Running Crop Model with Input&lt;/br&gt;"));
					simulationHelper.setSimulationStatus(simId, "Running Crop Model with Input",
							DateUtil.getCurrentDateWithLocalTimeZone());

					/***************** Build the crop output based on the historical data ********************/
					ReadHistoricalRainfall_Brazil hisrainBrazil = new ReadHistoricalRainfall_Brazil();
					hisrainBrazil.makeCropInputFiles(simulationArguments);
					
					/*********************** Write the output in Netcdf file format **************************/
					simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
							.append(" -Writing Netcdf file into Data library&lt;/br&gt;"));
					simulationHelper.setSimulationStatus(simId, "Writing Netcdf file into Data library",
							DateUtil.getCurrentDateWithLocalTimeZone());

					WriteNetCDF writefile = new WriteNetCDF();
					writefile.writeCDFFile(simulationArguments);
				 
				}
			}
			
			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage()
					.append("Generating Links&lt;/br&gt;"));
			simulationHelper.setSimulationStatus(simId, "Generating Links",
					DateUtil.getCurrentDateWithLocalTimeZone());
			
			String outputFolderPath = SimulationConstants.BELUGA_PATH + relativeFileStructure;
			StringBuilder sb = new StringBuilder();
			if(simType.equalsIgnoreCase(SimulationConstants.SIM_TYPE_CMNHMM)){
				simDetailsVO
				.getLinks()
				.put("http://iridl.ldeo.columbia.edu/expert/%28" + outputFolderPath + "/sim_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html",
						"Rainfall Simulation Output from machine Learning");
				
				sb.append("\"Rainfall Simulation Output from machine Learning\"");
				sb.append(":\"http://iridl.ldeo.columbia.edu/expert/%28" + outputFolderPath + "/sim_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html\",");
				
			}else if(simType.equalsIgnoreCase(SimulationConstants.SIM_TYPE_CMH)){
				
				simDetailsVO
				.getLinks()
				.put("http://iridl.ldeo.columbia.edu/expert/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=soil+year+fig-+colors+-fig%26my.help=more+options",
						"Historical Crop Yield per grid point");
				simDetailsVO
				.getLinks()
				.put("http://iridl.ldeo.columbia.edu/expert/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options",
						"Historical Crop Yield per grid point per soil");
				simDetailsVO
				.getLinks()
				.put("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.NCDC/.ERSST/.version3b/.sst/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/X+Y+fig-+colors+coasts+-fig+//soil/3676./plotvalue//XOVY+null+psdef//plotborder+72+psdef//plotaxislength+432+psdef/figviewer.html?my.help=%26map.soil.plotvalue=3676.%26map.Y.units=degree_north%26map.Y.plotlast=89N%26map.url=+%26map.domain=+%7B+%2Fsoil+3858.+plotvalue+X+-30+330+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.zoom=Zoom%26map.Y.plotfirst=89S%26map.X.plotfirst=30W%26map.X.units=degree_east%26map.X.modulus=360%26map.X.plotlast=30W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200",
						"Corellation of Crop with SSTs per soil");
				simDetailsVO
				.getLinks()
				.put("http://iridl.ldeo.columbia.edu/expert/SOURCES/.IMD/.NCC1-2005/.v2p0/.rf/T/%28Jun%201971%29%28Sep%202009%29RANGE/T/monthlyAverage/T/4/runningAverage/T/12/STEP/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/73.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options",
						"Corellation of Crop Output with rainfall per soil");
				simDetailsVO
				.getLinks()
				.put("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.uwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?my.help=more+options%26map.soil.plotvalue=3858.%26map.lat.units=degree_north%26map.lat.plotlast=90N%26map.url=lon+lat+fig-+colors+coasts+lakes+-fig%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+lat+-90.+90.+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef%26map.zoom=Zoom%26map.lat.plotfirst=90S%26map.lon.plotfirst=1W%26map.lon.units=degree_east%26map.lon.modulus=360%26map.lon.plotlast=1W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.newurl.grid0=lon%26map.newurl.grid1=lat%26map.newurl.land=draw+coasts%26map.newurl.plot=colors%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.XOVY=auto%26map.color_smoothing=1%26map.framelbl=framelabelstart%26map.framelabeltext=%26map.iftime=25%26map.mftime=25%26map.fftime=200",
						"Corellation of Crop with u-wind per soil");
				simDetailsVO
				.getLinks()
				.put("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.vwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/lon/lat/fig-/colors/coasts/-fig//soil/3858./plotvalue/lat/-90/90/plotrange//XOVY/null/psdef//plotborder/72/psdef//plotaxislength/432/psdef/figviewer.html?map.here.x=250%26map.here.y=119%26map.url=+%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.lon.width=360.%26map.lat.width=180.%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200",
						"Corellation of crop model with v-wind per soil");
				simDetailsVO
				.getLinks()
				.put("http://iridl.ldeo.columbia.edu/expert/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/72.5/VALUE/Y/22.5/VALUE/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/22.5/VALUE/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/23.5/VALUE/T/fig-/green/line/red/line/blue/line/-fig//rf/1.043575/13.73149/plotrange//rf/1.043575/13.73149/plotrange//plotaxislength/432/psdef//XOVY/null/psdef//plotborder/72/psdef/",
						"Crop Response to soil on historical data");
				
				sb.append("{\"Historical Crop Yield per grid point\"");
				sb.append(":\"http://iridl.ldeo.columbia.edu/expert/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=soil+year+fig-+colors+-fig%26my.help=more+options\",");
				sb.append("\"Historical Crop Yield per grid point per soil\"");
				sb.append(":\"http://iridl.ldeo.columbia.edu/expert/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options\",");
				sb.append("\"Corellation of Crop with SSTs per soil\"");
				sb.append(":\"http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.NCDC/.ERSST/.version3b/.sst/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/X+Y+fig-+colors+coasts+-fig+//soil/3676./plotvalue//XOVY+null+psdef//plotborder+72+psdef//plotaxislength+432+psdef/figviewer.html?my.help=%26map.soil.plotvalue=3676.%26map.Y.units=degree_north%26map.Y.plotlast=89N%26map.url=+%26map.domain=+%7B+%2Fsoil+3858.+plotvalue+X+-30+330+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.zoom=Zoom%26map.Y.plotfirst=89S%26map.X.plotfirst=30W%26map.X.units=degree_east%26map.X.modulus=360%26map.X.plotlast=30W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200\",");
				sb.append("\"Corellation of Crop Output with rainfall per soil\"");
				sb.append(":\"http://iridl.ldeo.columbia.edu/expert/SOURCES/.IMD/.NCC1-2005/.v2p0/.rf/T/%28Jun%201971%29%28Sep%202009%29RANGE/T/monthlyAverage/T/4/runningAverage/T/12/STEP/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/73.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options\",");
				sb.append("\"Corellation of Crop with u-wind per soil\"");
				sb.append(":\"http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.uwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?my.help=more+options%26map.soil.plotvalue=3858.%26map.lat.units=degree_north%26map.lat.plotlast=90N%26map.url=lon+lat+fig-+colors+coasts+lakes+-fig%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+lat+-90.+90.+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef%26map.zoom=Zoom%26map.lat.plotfirst=90S%26map.lon.plotfirst=1W%26map.lon.units=degree_east%26map.lon.modulus=360%26map.lon.plotlast=1W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.newurl.grid0=lon%26map.newurl.grid1=lat%26map.newurl.land=draw+coasts%26map.newurl.plot=colors%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.XOVY=auto%26map.color_smoothing=1%26map.framelbl=framelabelstart%26map.framelabeltext=%26map.iftime=25%26map.mftime=25%26map.fftime=200\",");
				sb.append("\"Corellation of crop model with v-wind per soil\"");
				sb.append(":\"http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.vwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/lon/lat/fig-/colors/coasts/-fig//soil/3858./plotvalue/lat/-90/90/plotrange//XOVY/null/psdef//plotborder/72/psdef//plotaxislength/432/psdef/figviewer.html?map.here.x=250%26map.here.y=119%26map.url=+%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.lon.width=360.%26map.lat.width=180.%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200\",");
				sb.append("\"Crop Response to soil on historical data\"");
				sb.append(":\"http://iridl.ldeo.columbia.edu/expert/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/72.5/VALUE/Y/22.5/VALUE/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/22.5/VALUE/%28" + outputFolderPath + "/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/23.5/VALUE/T/fig-/green/line/red/line/blue/line/-fig//rf/1.043575/13.73149/plotrange//rf/1.043575/13.73149/plotrange//plotaxislength/432/psdef//XOVY/null/psdef//plotborder/72/psdef/\"}");

			}
			
			simulationHelper.updateSimulationDetails(simId, SimulationConstants.SIMULATION_STATUS_COMPLETED,
					sb.toString(), null,
					DateUtil.getCurrentDateWithLocalTimeZone(),
					DateUtil.getCurrentDateWithLocalTimeZone());

			simDetailsVO.setStatus(SimulationConstants.SIMULATION_STATUS_COMPLETED);
		}
	}

	public static void main(String args[]) throws IOException {

	}

}