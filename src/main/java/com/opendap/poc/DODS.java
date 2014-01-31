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
//import opendap.dap.*;
//import opendap.dap.parser.ParseException;

import java.text.SimpleDateFormat;
//import java.util.Vector;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.StringTokenizer;
//import java.util.List;
import java.io.IOException;



//import java.io.PrintStream;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.sql.*;
//
//import org.apache.log4j.Logger;
//
//import com.opendap.poc.OpenDAPmain.MyAuthenticator;
import com.rii.wp.controller.CropModelController;
import com.rii.wp.model.CropModel;
import com.rii.wp.model.SimDetailsVO;
import com.rii.wp.model.Simulation;

//import ucar.ma2.Array;
//import ucar.ma2.DataType;
//import ucar.ma2.Index;
//import ucar.nc2.dods.DODSAttribute;
//import ucar.nc2.dods.DODSNetcdfFile;
//import ucar.unidata.util.StringUtil;
//import ucar.nc2.NetcdfFile;
//import ucar.nc2.iosp.netcdf3.*;
//import ucar.nc2.dataset.*;
//import ucar.nc2.*;
//import ucar.ma2.*;
//import ucar.*;

import com.rii.wp.util.DateUtil;
import com.rii.wp.util.SimulationHelper;



//import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Date;



 public class DODS {
	 public DODS(){}
	 
	 public void buildDODS(final CropModel cropModel, final Simulation simulation, final SimulationHelper simulationHelper ) throws IOException
	 {
		 long simId=simulation.getSimId();
		 int simNum = simulation.getSimNum();
		 long userId = simulation.getUserId();
		 
		 
		 SimDetailsVO simDetailsVO = CropModelController.getSimDetailsMap().get(simId);
		 //simId = 1;
		 if(null != simDetailsVO){
			 
			 simDetailsVO.setStatus("In Progress");
			 
			 simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append("Starting Content Downloading&lt;/br&gt;"));
			 simulationHelper.setSimulationStatus(simId, "Starting Content Downloading", DateUtil.getCurrentDateWithLocalTimeZone());
			 System.out.println("Country:" + cropModel.getSelCountry());
			 System.out.println("Crop:" + cropModel.getSelCropName());
			 
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 //String State = cropModel.getSelState();
			 long CountryNo = Long.parseLong(cropModel.getSelCountry());
			 String UnitID = cropModel.getSelDistrict().replaceAll("\\s","");
			 //String UnitID = "196";//kheda
			 //String UnitID = "188";//kheda
			 String Crop = cropModel.getSelCropName();
			 String CropSeason = cropModel.getSelCropSeason();
			 String GCM =  cropModel.getSelGeneralCirculationModel();
		     System.out.println(simpleDateFormat.format(new Date()));
			 
		     if(CountryNo == 1)
		     {
		     /**************Run for India***************************/
		          /**************Download server dataset******************/
		     		ServerContentDownloader serverContentDownloader =  new ServerContentDownloader();
		     		serverContentDownloader.createAndSaveUrl(userId, simNum, simId, simpleDateFormat.format(new Date()), CountryNo, UnitID, Crop, CropSeason, GCM, simulationHelper);
		     		/***************** Build the crop output based on the historical data ********************/
					 ReadHistoricalRainfall hisrain = new ReadHistoricalRainfall();
					hisrain.makeCropInputFiles(userId, simNum, simId, simpleDateFormat.format(new Date()), CountryNo, UnitID, Crop, CropSeason, GCM, simulationHelper); 
		     }
		     else if(CountryNo == 2)
		     {		
			         System.out.println("**************Run for Brazil***************************");
		    	     
		     		/**************Download server dataset******************/
					ServerContentDownloaderBrazil serverContentDownloaderBrazil =  new ServerContentDownloaderBrazil();
					serverContentDownloaderBrazil.createAndSaveUrl(userId, simNum, simId, simpleDateFormat.format(new Date()), CountryNo, UnitID, Crop, CropSeason, GCM, simulationHelper);
					/***************** Build the crop output based on the historical data ********************/
					ReadHistoricalRainfall_Brazil hisrainBrazil = new ReadHistoricalRainfall_Brazil();
					hisrainBrazil.makeCropInputFiles(userId, simNum, simId, simpleDateFormat.format(new Date()), CountryNo, UnitID, Crop, CropSeason, GCM, simulationHelper);
		     }
					simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Running Simulation&lt;/br&gt;"));
					simulationHelper.setSimulationStatus(simId, "Running Simulation", DateUtil.getCurrentDateWithLocalTimeZone());
			 
			 
			 /***********Call the NHMM to build up the rainfall parameters**************/ 
			 /*RunNHMM nhmm = new RunNHMM();
			nhmm.RunNHMMModel(simpleDateFormat.format(new Date()), DistrictID);
			
			/***************** Build the crop output based on NHMM Output ********************/	
			 /*ReadNHMMOutput rno = new ReadNHMMOutput();
			rno.makeCropInputFiles(simpleDateFormat.format(new Date()),DistrictID,Crop);
			/***************** Write the crop output based on NHMM into NetCDF ********************/
			 /*WriteSimNetCDF writesimfile = new WriteSimNetCDF();
			writesimfile.WriteCDFFile(simpleDateFormat.format(new Date()), DistrictID, Crop);
				
			
			/***************** Build the crop output based on the historical data ********************/
			 //ReadHistoricalRainfall hisrain = new ReadHistoricalRainfall();
			//hisrain.makeCropInputFiles(simId, simpleDateFormat.format(new Date()), CountryNo, UnitID, Crop, CropSeason, GCM);
			//ReadHistoricalRainfall_Brazil hisrain = new ReadHistoricalRainfall_Brazil();
			//hisrain.makeCropInputFiles(simId, simpleDateFormat.format(new Date()), CountryNo, UnitID, Crop, CropSeason, GCM);
			 /***********************Write the output in Netcdf file format*****************************/
			 /*WriteNetCDF writefile = new WriteNetCDF();
			writefile.WriteCDFFile(simpleDateFormat.format(new Date()), DistrictID, Crop);
			
			/******************************************************************************************/
			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append("Generating Links&lt;/br&gt;"));
			simulationHelper.setSimulationStatus(simId, "Generating Links", DateUtil.getCurrentDateWithLocalTimeZone());
			
			simDetailsVO.getLinks().put("http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/188/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=soil+year+fig-+colors+-fig%26my.help=more+options","Historical Crop Yield per grid point");
			simDetailsVO.getLinks().put("http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options","Historical Crop Yield per grid point per soil");
			simDetailsVO.getLinks().put("http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/sim_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html","Rainfall Simulation Output from machine Learning");
			simDetailsVO.getLinks().put("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.NCDC/.ERSST/.version3b/.sst/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/X+Y+fig-+colors+coasts+-fig+//soil/3676./plotvalue//XOVY+null+psdef//plotborder+72+psdef//plotaxislength+432+psdef/figviewer.html?my.help=%26map.soil.plotvalue=3676.%26map.Y.units=degree_north%26map.Y.plotlast=89N%26map.url=+%26map.domain=+%7B+%2Fsoil+3858.+plotvalue+X+-30+330+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.zoom=Zoom%26map.Y.plotfirst=89S%26map.X.plotfirst=30W%26map.X.units=degree_east%26map.X.modulus=360%26map.X.plotlast=30W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200","Corellation of Crop with SSTs per soil");
			simDetailsVO.getLinks().put("http://iridl.ldeo.columbia.edu/expert/SOURCES/.IMD/.NCC1-2005/.v2p0/.rf/T/%28Jun%201971%29%28Sep%202009%29RANGE/T/monthlyAverage/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/73.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options","Corellation of Crop Output with rainfall per soil");
			simDetailsVO.getLinks().put("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.uwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?my.help=more+options%26map.soil.plotvalue=3858.%26map.lat.units=degree_north%26map.lat.plotlast=90N%26map.url=lon+lat+fig-+colors+coasts+lakes+-fig%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+lat+-90.+90.+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef%26map.zoom=Zoom%26map.lat.plotfirst=90S%26map.lon.plotfirst=1W%26map.lon.units=degree_east%26map.lon.modulus=360%26map.lon.plotlast=1W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.newurl.grid0=lon%26map.newurl.grid1=lat%26map.newurl.land=draw+coasts%26map.newurl.plot=colors%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.XOVY=auto%26map.color_smoothing=1%26map.framelbl=framelabelstart%26map.framelabeltext=%26map.iftime=25%26map.mftime=25%26map.fftime=200","Corellation of Crop with u-wind per soil");
			simDetailsVO.getLinks().put("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.vwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/lon/lat/fig-/colors/coasts/-fig//soil/3858./plotvalue/lat/-90/90/plotrange//XOVY/null/psdef//plotborder/72/psdef//plotaxislength/432/psdef/figviewer.html?map.here.x=250%26map.here.y=119%26map.url=+%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.lon.width=360.%26map.lat.width=180.%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200","Corellation of crop model with v-wind per soil");
			simDetailsVO.getLinks().put("http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/72.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/23.5/VALUE/T/fig-/green/line/red/line/blue/line/-fig//rf/1.043575/13.73149/plotrange//rf/1.043575/13.73149/plotrange//plotaxislength/432/psdef//XOVY/null/psdef//plotborder/72/psdef/","Crop Response to soil on historical data");
			
//			StringBuilder sb = new StringBuilder();
//			sb.append("{\"output\":[{\"text\":\"");
//			sb.append("Historical Crop Yield per grid point");
//			sb.append("\",\"url\":\"");
//			sb.append("http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/188/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=soil+year+fig-+colors+-fig%26my.help=more+options");
//			sb.append("\"},{\"text\":\"");
//			sb.append("Historical Crop Yield per grid point per soil");
//			sb.append("\",\"url\":\"");
//			sb.append("http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options");
//			sb.append("\"},{\"text\":\"");
//			sb.append("Rainfall Simulation Output from machine Learning");
//			sb.append("\",\"url\":\"");
//			sb.append("http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/sim_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html");
//			sb.append("\"},{\"text\":\"");
//			sb.append("Corellation of Crop with SSTs per soil");
//			sb.append("\",\"url\":\"");
//			sb.append("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.NCDC/.ERSST/.version3b/.sst/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/X+Y+fig-+colors+coasts+-fig+//soil/3676./plotvalue//XOVY+null+psdef//plotborder+72+psdef//plotaxislength+432+psdef/figviewer.html?my.help=%26map.soil.plotvalue=3676.%26map.Y.units=degree_north%26map.Y.plotlast=89N%26map.url=+%26map.domain=+%7B+%2Fsoil+3858.+plotvalue+X+-30+330+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.zoom=Zoom%26map.Y.plotfirst=89S%26map.X.plotfirst=30W%26map.X.units=degree_east%26map.X.modulus=360%26map.X.plotlast=30W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200");
//			sb.append("\"},{\"text\":\"");
//			sb.append("Corellation of Crop Output with rainfall per soil");
//			sb.append("\",\"url\":\"");
//			sb.append("http://iridl.ldeo.columbia.edu/expert/SOURCES/.IMD/.NCC1-2005/.v2p0/.rf/T/%28Jun%201971%29%28Sep%202009%29RANGE/T/monthlyAverage/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/73.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options");
//			sb.append("\"},{\"text\":\"");
//			sb.append("Corellation of Crop with SSTs per soil");
//			sb.append("\",\"url\":\"");
//			sb.append("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.uwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?my.help=more+options%26map.soil.plotvalue=3858.%26map.lat.units=degree_north%26map.lat.plotlast=90N%26map.url=lon+lat+fig-+colors+coasts+lakes+-fig%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+lat+-90.+90.+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef%26map.zoom=Zoom%26map.lat.plotfirst=90S%26map.lon.plotfirst=1W%26map.lon.units=degree_east%26map.lon.modulus=360%26map.lon.plotlast=1W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.newurl.grid0=lon%26map.newurl.grid1=lat%26map.newurl.land=draw+coasts%26map.newurl.plot=colors%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.XOVY=auto%26map.color_smoothing=1%26map.framelbl=framelabelstart%26map.framelabeltext=%26map.iftime=25%26map.mftime=25%26map.fftime=200");
//			sb.append("\"},{\"text\":\"");
//			sb.append("Corellation of crop model with v-wind per soil");
//			sb.append("\",\"url\":\"");
//			sb.append("http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.vwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/lon/lat/fig-/colors/coasts/-fig//soil/3858./plotvalue/lat/-90/90/plotrange//XOVY/null/psdef//plotborder/72/psdef//plotaxislength/432/psdef/figviewer.html?map.here.x=250%26map.here.y=119%26map.url=+%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.lon.width=360.%26map.lat.width=180.%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200");
//			sb.append("\"},{\"text\":\"");
//			sb.append("Crop Response to soil on historical data");
//			sb.append("\",\"url\":\"");
//			sb.append("http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/72.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/23.5/VALUE/T/fig-/green/line/red/line/blue/line/-fig//rf/1.043575/13.73149/plotrange//rf/1.043575/13.73149/plotrange//plotaxislength/432/psdef//XOVY/null/psdef//plotborder/72/psdef/");
//			sb.append("\"}]}");
//			
			StringBuilder sb = new StringBuilder();
			sb.append("{\"Historical Crop Yield per grid point\"");
			sb.append(":\"http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/188/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=soil+year+fig-+colors+-fig%26my.help=more+options\",");
			sb.append("\"Historical Crop Yield per grid point per soil\"");
			sb.append(":\"http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options\",");
			sb.append("\"Rainfall Simulation Output from machine Learning\"");
			sb.append(":\"http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/sim_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html\",");
			sb.append("\"Corellation of Crop with SSTs per soil\"");
			sb.append(":\"http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.NCDC/.ERSST/.version3b/.sst/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/X+Y+fig-+colors+coasts+-fig+//soil/3676./plotvalue//XOVY+null+psdef//plotborder+72+psdef//plotaxislength+432+psdef/figviewer.html?my.help=%26map.soil.plotvalue=3676.%26map.Y.units=degree_north%26map.Y.plotlast=89N%26map.url=+%26map.domain=+%7B+%2Fsoil+3858.+plotvalue+X+-30+330+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.zoom=Zoom%26map.Y.plotfirst=89S%26map.X.plotfirst=30W%26map.X.units=degree_east%26map.X.modulus=360%26map.X.plotlast=30W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200\",");
			sb.append("\"Corellation of Crop Output with rainfall per soil\"");
			sb.append(":\"http://iridl.ldeo.columbia.edu/expert/SOURCES/.IMD/.NCC1-2005/.v2p0/.rf/T/%28Jun%201971%29%28Sep%202009%29RANGE/T/monthlyAverage/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/73.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig%26my.help=more+options\",");
			sb.append("\"Corellation of Crop with u-wind per soil\"");
			sb.append(":\"http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.uwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?my.help=more+options%26map.soil.plotvalue=3858.%26map.lat.units=degree_north%26map.lat.plotlast=90N%26map.url=lon+lat+fig-+colors+coasts+lakes+-fig%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+lat+-90.+90.+plotrange+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef%26map.zoom=Zoom%26map.lat.plotfirst=90S%26map.lon.plotfirst=1W%26map.lon.units=degree_east%26map.lon.modulus=360%26map.lon.plotlast=1W%26map.correlation.plotfirst=-0.9960159%26map.correlation.units=unitless%26map.correlation.plotlast=0.9960159%26map.newurl.grid0=lon%26map.newurl.grid1=lat%26map.newurl.land=draw+coasts%26map.newurl.plot=colors%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.XOVY=auto%26map.color_smoothing=1%26map.framelbl=framelabelstart%26map.framelabeltext=%26map.iftime=25%26map.mftime=25%26map.fftime=200\",");
			sb.append("\"Corellation of crop model with v-wind per soil\"");
			sb.append(":\"http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.vwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/lon/lat/fig-/colors/coasts/-fig//soil/3858./plotvalue/lat/-90/90/plotrange//XOVY/null/psdef//plotborder/72/psdef//plotaxislength/432/psdef/figviewer.html?map.here.x=250%26map.here.y=119%26map.url=+%26map.domain=+%7B+%2Fsoil+3676.+plotvalue+%7D%26map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef%26map.lon.width=360.%26map.lat.width=180.%26map.plotaxislength=432%26map.plotborder=72%26map.fnt=Helvetica%26map.fntsze=12%26map.color_smoothing=1%26map.XOVY=auto%26map.iftime=25%26map.mftime=25%26map.fftime=200\",");
			sb.append("\"Crop Response to soil on historical data\"");
			sb.append(":\"http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/72.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/23.5/VALUE/T/fig-/green/line/red/line/blue/line/-fig//rf/1.043575/13.73149/plotrange//rf/1.043575/13.73149/plotrange//plotaxislength/432/psdef//XOVY/null/psdef//plotborder/72/psdef/\"}");
			
			simulationHelper.updateSimulationDetails(simId, "COMPLETED", sb.toString(), null, DateUtil.getCurrentDateWithLocalTimeZone(), DateUtil.getCurrentDateWithLocalTimeZone());
			 
			simDetailsVO.setStatus("COMPLETED");
		 }
	 }

public static void main(String args[]) throws IOException 
{
	//DODS obj = new DODS();
	/* calls the downloading information */
//	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	String DistrictID = "187";//kheda
//	String Crop = "Sorghum";
	/**************Download server dataset******************/
	//ServerContentDownloader serverContentDownloader =  new ServerContentDownloader();
	//serverContentDownloader.createAndSaveUrl(simpleDateFormat.format(new Date()), DistrictID);
	//ServerContentDownloaderBrazil serverContentDownloader =  new ServerContentDownloaderBrazil();
	//serverContentDownloader.createAndSaveUrl(1,simpleDateFormat.format(new Date()), DistrictID);
	
	/***********Call the NHMM to build up the rainfall parameters**************/ 
	/*RunNHMM nhmm = new RunNHMM();	nhmm.RunNHMMModel(simpleDateFormat.format(new Date()), DistrictID);
	
	/***************** Build the crop output based on NHMM Output ********************/	
	/*ReadNHMMOutput rno = new ReadNHMMOutput();
	rno.makeCropInputFiles(simpleDateFormat.format(new Date()),DistrictID,Crop);
	/***************** Write the crop output based on NHMM into NetCDF ********************/
	/*WriteSimNetCDF writesimfile = new WriteSimNetCDF();
	writesimfile.WriteCDFFile(simpleDateFormat.format(new Date()), DistrictID, Crop);
		
	
	/***************** Build the crop output based on the historical data ********************/
	/*ReadHistoricalRainfall hisrain = new ReadHistoricalRainfall();
	hisrain.makeCropInputFiles(simpleDateFormat.format(new Date()),DistrictID,Crop);*/
	/*ReadHistoricalRainfall_Brazil hisrain = new ReadHistoricalRainfall_Brazil();
	hisrain.makeCropInputFiles(simpleDateFormat.format(new Date()),DistrictID,Crop);
	/***********************Write the output in Netcdf file format*****************************/
	/*WriteNetCDF writefile = new WriteNetCDF();
	writefile.WriteCDFFile(simpleDateFormat.format(new Date()), DistrictID, Crop);
	
	/******************************************************************************************/
	
 }
 


}