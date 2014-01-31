/**
 * 
 */
package com.rii.wp.util;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author I
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		testJSONToMap();

	}

	public static void testJSONToMap(){
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
		
		String jsonString = sb.toString();
		System.out.println(jsonString);
		//JsonFactory factory = new JsonFactory(); 
	    ObjectMapper mapper = new ObjectMapper(); 
	    //TypeReference<LinkedHashMap<String,String>> typeRef = ; 
			LinkedHashMap<String, String> outputLinks;
			try {
				outputLinks = mapper.readValue(jsonString, new TypeReference<LinkedHashMap<String,String>>() {});
				System.out.println(outputLinks.size());
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	}
}
