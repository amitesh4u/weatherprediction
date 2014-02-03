package com.opendap.poc;
/**
 *
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.DocumentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.ParserFactory;









import java.io.File;
import java.util.*;

import org.w3c.dom.*;

import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.rii.wp.controller.CropModelController;
import com.rii.wp.model.SimDetailsVO;
import com.rii.wp.util.DateUtil;
import com.rii.wp.util.SimulationHelper;

import javax.xml.*;

//import sun.jdbc.odbc.JdbcOdbcDriver;
import java.sql.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.lang.*;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.channels.FileChannel;


/**
 * @author Arindam Bhattacharjee
 *
 */
public class ServerContentDownloader {


	private static String servletURLPattern_rainfall = null;
	private static String servletURLPattern_soil = null;
	private static String servletURLPattern_Tmax = null;
	private static String servletURLPattern_Tmin = null;
	private static String servletURLPattern_Coordinates = null;
	private static String servletURLPattern_GCM = null;
	private static String servletURLPattern_TmaxAvgNR = null;
	private static String servletURLPattern_TmaxAvgR = null;
	private static String servletURLPattern_TminAvgNR = null;
	private static String servletURLPattern_TminAvgR = null;
	private static String servletURLPattern_SRadNR = null;
	private static String servletURLPattern_SRadR = null;

	private boolean useProxy = false;

	private String defaultProxyHost = "10.66.184.116";

	private String defaultProxyPort = "80";

	private String proxyHost = null;

	private String proxyPort = null;

	//private String folder = null;

	/**
	 * @throws IOException
	 *
	 */
	public ServerContentDownloader() throws IOException {
		Authenticator.setDefault(new MyAuthenticator());
		configure();
	}

	/**
	 * @param args
	 */
	/*public static void main(String[] args) throws IOException{
	   	Authenticator.setDefault(new MyAuthenticator());
		try {
			ServerContentDownloader serverContentDownloader =  new ServerContentDownloader();
			serverContentDownloader.createAndSaveUrl();
			DODS dds = new DODS();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 *
	 * @throws IOException
	 */
	private void configure()  throws IOException {
		//Load the properties file
		Properties properties = new Properties();
		// Read the properties file
		//File file = new File("ServerContentDownloader.properties");
		File file = new File(this.getClass().getResource("/ServerContentDownloader_1.properties").getFile());
		FileInputStream fis = new FileInputStream(file);
		properties.load(fis);
		useProxy = Boolean.parseBoolean(properties.getProperty("UseProxy"));
		if(useProxy){
			// Read the Proxy Host
			proxyHost = properties.getProperty("ProxyHost");
			System.out.println("ProxyHost : " + proxyHost);
			if(null == proxyHost || proxyHost.trim().length() < 1){
				proxyHost = defaultProxyHost;
				System.out.println("Using DefaultProxyHost : " + proxyHost);
			}
			// Read the Proxy Port
			proxyPort = properties.getProperty("ProxyPort");
			System.out.println("\nProxyPort : " + proxyPort);
			if(null == proxyPort || proxyPort.trim().length() < 1){
				proxyPort = defaultProxyPort;
				System.out.println("Using DefaultProxyPort : " + proxyPort);
			}
		}
		// Read the HTTP URL
		servletURLPattern_rainfall = properties.getProperty("servletURLPattern_rainfall");
		System.out.println("servletURLPattern : " + servletURLPattern_rainfall);
		servletURLPattern_soil = properties.getProperty("servletURLPattern_soil");
		System.out.println("servletURLPattern : " + servletURLPattern_soil);
		servletURLPattern_Tmax = properties.getProperty("servletURLPattern_Tmax");
		System.out.println("servletURLPattern : " + servletURLPattern_Tmax);
		servletURLPattern_Coordinates = properties.getProperty("servletURLPattern_Coordinates");
		System.out.println("servletURLPattern : " + servletURLPattern_Coordinates);
		servletURLPattern_Tmin = properties.getProperty("servletURLPattern_Tmin2");
		System.out.println("servletURLPattern : " + servletURLPattern_Tmin);
		servletURLPattern_GCM = properties.getProperty("servletURLPattern_GCM");
		System.out.println("servletURLPattern : " + servletURLPattern_GCM);
		servletURLPattern_TmaxAvgNR = properties.getProperty("servletURLPattern_TmaxAvgNR");
		System.out.println("servletURLPattern: " + servletURLPattern_TmaxAvgNR);
		servletURLPattern_TmaxAvgR = properties.getProperty("servletURLPattern_TmaxAvgR");
		System.out.println("servletURLPattern: " + servletURLPattern_TmaxAvgR);
		servletURLPattern_TminAvgNR = properties.getProperty("servletURLPattern_TminAvgNR");
		System.out.println("servletURLPattern: " + servletURLPattern_TminAvgNR);
		servletURLPattern_TminAvgR = properties.getProperty("servletURLPattern_TminAvgR");
		System.out.println("servletURLPattern: " + servletURLPattern_TminAvgR);
		servletURLPattern_SRadNR = properties.getProperty("servletURLPattern_SRadNR");
		System.out.println("servletURLPattern: " + servletURLPattern_SRadNR);
		servletURLPattern_SRadR = properties.getProperty("servletURLPattern_SRadR");
		System.out.println("servletURLPattern: " + servletURLPattern_SRadR);
		

		//Read folder name
		//folder = properties.getProperty("folder");
		//System.out.println("Folder : " + folder);

		System.out.println("\n************************************************\n");

	}


	/**
	 * @param DistrictID 
	 *
	 *
	 */
	public void createAndSaveUrl(final SimulationArguments simulationArguments){

		/* build connection to the database */
		try	{
			//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//String folderPath = folder + File.separator + userId + File.separator + Path + File.separator + simNum + File.separator + CountryNo ;
			String folderPath = simulationArguments.getFileStructure();
			//String folderPath = folder + "/" + simpleDateFormat.format(new Date());
			File dir = new File(folderPath);
			dir.mkdirs();
			int id = 1;
			//String DistrictID ="188";
			File aFile = new File(this.getClass().getResource("/DistrictCoordinates.txt").getFile());
			//File aFile = new File("DistrictCoordinates.txt");
			 String LatLong = "";
			double maxLat = 0.0;
			double minLat = 0.0;
			double maxLon = 0.0;
			double minLon =0.0;
 			
 			Double GCMLat = 0.0;
 			Double GCMLon = 0.0;
 			double GCMLatMin = 0.0; 
 			double GCMLatMax = 0.0;
 			double GCMLonmin = 0.0;
 			double GCMLonMax = 0.0;
 			Vector LatVec = new Vector();
				Vector LonVec = new Vector();
			String districtID = simulationArguments.getDistrictID();
			try	{
				 BufferedReader input =  new BufferedReader(new FileReader(aFile));
				 String line = null; //not declared within while loop
				     while (( line = input.readLine()) != null){
				    	 StringTokenizer sta = new StringTokenizer(line.toString(),"@");
			    		 //sta.nextToken();
			    		    
				    	 if(sta.nextToken().toString().equals(districtID ))
				          {  	
				    		    sta.nextToken();
				    		 	//System.out.println(line);
				    		    LatLong = sta.nextToken();
				    		    System.out.println(LatLong);
				    		    LatLong = LatLong.replace("MULTIPOLYGON(((", "");
								LatLong = LatLong.replace(")))", "");
								LatLong = LatLong.replace("))", ",");
								LatLong = LatLong.replace("((", "");

								//defining the area of the resterization
								
				            	StringTokenizer st = new StringTokenizer(LatLong,",");
				            	StringTokenizer stRaster = new StringTokenizer(LatLong,",");
				            	while(stRaster.hasMoreElements() ){
									String next = stRaster.nextElement().toString();
									StringTokenizer st3 = new StringTokenizer(next);
									Double Lat = Double.parseDouble(st3.nextElement().toString());
									Double Lon = Double.parseDouble(st3.nextElement().toString());
									LatVec.add(Lat);
									LonVec.add(Lon);
				    			}
								maxLat = Double.parseDouble(Collections.max(LatVec).toString());
				    			minLat = Double.parseDouble(Collections.min(LatVec).toString());
				    			maxLon = Double.parseDouble(Collections.max(LonVec).toString());
				    			minLon = Double.parseDouble(Collections.min(LonVec).toString());
				    			
				    			GCMLat = (maxLat+minLat)/2;
				    			GCMLon = (maxLon + minLon)/2;
				    			GCMLatMin = GCMLat - 5; 
				    			GCMLatMax = GCMLat + 5;
				    			GCMLonmin = GCMLon - 5;
				    			GCMLonMax = GCMLon + 5;
				    			
				          }
				     }
			}catch(Exception e){}
				     
				    	 
			    			
    		int dist = Integer.parseInt(districtID);
			id = dist;
			//the id in database is 1 more than the normal id
			id++;
			/**************Get Grid coordinates******************/
			double Lon = 0.0;
			double Lat = 0.0;
			String filePath = folderPath + File.separator + districtID;
			File dir2 = new File(filePath);
			dir2.mkdirs();
			String filename = "";
			String urlString = "";
			String pathname = "";
			filename = filePath + "/GridCoordinates.tsv";
			 
		    long simId = simulationArguments.getSimId();
			/*********** Get the soil parameters from the Datalibrary **************/
			SimDetailsVO simDetailsVO = CropModelController.getSimDetailsMap().get(simId );
			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Downloading Soil paramters&lt;/br&gt;"));
			SimulationHelper simulationHelper = simulationArguments.getSimulationHelper();
			simulationHelper .setSimulationStatus(simId, "Downloading Soil paramters", DateUtil.getCurrentDateWithLocalTimeZone());
			
			filename = filePath+ "/soil.cdf";
			urlString = servletURLPattern_soil.replace("{{minLat}}", String.valueOf(minLat))
						.replace("{{maxLat}}",String.valueOf(maxLat)).replace("{{minLon}}", String.valueOf(minLon)).
						replace("{{maxLon}}", String.valueOf(maxLon)).replace("{{id}}",String.valueOf(id));
		    System.out.println("urlString :" + urlString);
		    saveUrl(filename , urlString);
		    
		    /*Call the soil function to convert the soil into ID*/
		    //Soil sol = new Soil();			
			
		    /*********** Get the grid coordinates from the Datalibrary **************/
		    simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Downloading Grid Co-ordinates&lt;/br&gt;"));
		    simulationHelper.setSimulationStatus(simId, "Downloading Grid Co-ordinates", DateUtil.getCurrentDateWithLocalTimeZone());
		    filename = filePath+ "/GridCoordinates.tsv";
		    urlString = servletURLPattern_Coordinates.replace("{{minLat}}", String.valueOf(minLat))
					.replace("{{maxLat}}",String.valueOf(maxLat)).replace("{{minLon}}", String.valueOf(minLon)).
					replace("{{maxLon}}", String.valueOf(maxLon)).replace("{{id}}",String.valueOf(id));
	        System.out.println("urlString :" + urlString); 
			saveUrl(filename , urlString);
			GridCoordinates grid = new GridCoordinates();
			Vector vec = grid.Coordinates(filename);
			/**************Get the rainfall co-ordinates for the dataset******************/
			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Downloading Rainfall Co-ordinates&lt;/br&gt;"));
			simulationHelper.setSimulationStatus(simId, "Downloading Rainfall Co-ordinates", DateUtil.getCurrentDateWithLocalTimeZone());
		    Vector rainParameters = new Vector();
			Vector NHMMrain = new Vector();
			RainNHMM rnhmm = new RainNHMM();
			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Downloading TMax/Tmin and SRad values&lt;/br&gt;"));
			simulationHelper.setSimulationStatus(simId, "Downloading TMax/Tmin and SRad values", DateUtil.getCurrentDateWithLocalTimeZone());
			
			for(int i = 0; i < vec.size() ;i++)
			{
				Lon = Double.parseDouble(vec.get(i).toString());
				Lat = Double.parseDouble(vec.get(i+1).toString());
				i++;
				
				pathname = "rainfall"+"-"+Lat+"-"+Lon;
				filename = filePath+"/"+pathname+".cdf";
				urlString = servletURLPattern_rainfall.replace("{{Lat}}", String.valueOf(Lat))
						.replace("{{Lon}}",String.valueOf(Lon)).replace("{{id}}",String.valueOf(id));
			    System.out.println("urlString :" + urlString);
			    saveUrl(filename , urlString);
                			    
			    /**************make the rainfall files for HMM use ******************/	
			     rainParameters = rnhmm.WriteRain(filePath,pathname);
				 NHMMrain.add(rainParameters);
			    
				 /********** get Tmax values when it rained *****************/
				pathname = "TmaxR"+Lat+"-"+Lon;
			    filename = filePath+"/"+pathname+".tsv";
				urlString = servletURLPattern_TmaxAvgR.replace("{{Lat}}", String.valueOf(Lat))
								.replace("{{Lon}}",String.valueOf(Lon));
				System.out.println("urlString :" + urlString);
				saveUrl(filename , urlString);
				
				/**********get Tmax values when it did not rain *****************/
				 pathname = "TmaxNR"+Lat+"-"+Lon;
			     filename = filePath+"/"+pathname+".tsv";
			     urlString = servletURLPattern_TmaxAvgR.replace("{{Lat}}", String.valueOf(Lat))
							.replace("{{Lon}}",String.valueOf(Lon));
				 System.out.println("urlString :" + urlString);
				 saveUrl(filename , urlString);
				
				 /***********get the Tmin values when it rained *******************/
				  //System.out.println(Lat + 0.1);
				  //System.out.println(Lon);
				  pathname = "TminR"+Lat+"-"+Lon;
				  filename = filePath+"/"+pathname+".tsv";
				  urlString = servletURLPattern_TminAvgR.replace("{{Lat}}", String.valueOf(Lat))
							.replace("{{Lon}}",String.valueOf(Lon));
				  System.out.println("urlString :" + urlString);
				  saveUrl(filename , urlString);
				
				  /********** get the Tmin values when it not rained *************/
				  pathname = "TminNR"+Lat+"-"+Lon;
				  filename = filePath+"/"+pathname+".tsv";
				  urlString = servletURLPattern_TminAvgNR.replace("{{Lat}}", String.valueOf(Lat))
							.replace("{{Lon}}",String.valueOf(Lon));
				  System.out.println("urlString :" + urlString);
				  saveUrl(filename , urlString);
				  
				  /*********** get the SRad values when it not rained ************/
				   pathname = "SRadNR"+Lat+"-"+Lon;
				   filename = filePath+"/"+pathname+".tsv";
				   urlString = servletURLPattern_SRadNR.replace("{{Lat}}", String.valueOf(Lat))
							.replace("{{Lon}}",String.valueOf(Lon));
				   System.out.println("urlString :" + urlString);
				   saveUrl(filename , urlString);
				   
				   /*********** get the SRad values when it rained ***************/
				   pathname = "SRadR"+Lat+"-"+Lon;
				   filename = filePath+"/"+pathname+".tsv";
				   urlString = servletURLPattern_SRadR.replace("{{Lat}}", String.valueOf(Lat))
							.replace("{{Lon}}",String.valueOf(Lon));
				   System.out.println("urlString :" + urlString);
				   saveUrl(filename , urlString);
				   /***********END OF METHOD***************/
				   
				  
		      }
			
			//rnhmm.WriteInputFiles(folderPath2, "sim_nhmm_ind_delexp_params.txt");
			//rnhmm.WriteInputFiles(folderPath2, "lrn_nhmm_ind_delexp_params.txt");

			/**************Builds up the rainfall file to be read by NHMM *************************/
			/*File writeFile = new File(folderPath2+"/HMM_Input.txt");
			Writer output = new BufferedWriter(new FileWriter(writeFile));
				for(int k = 0; k <((Vector) NHMMrain.get(0)).size();k++)
				{
					output.write(((Vector) NHMMrain.get(0)).get(k) + "\t" +((Vector) NHMMrain.get(1)).get(k) + "\t" + ((Vector) NHMMrain.get(2)).get(k));
					output.write("\n");
				}
			/******************************************************************/
			
		    /*for(int iYear=1982;iYear<2010;iYear++)
		    {
			filename = folderPath2+ "/GCM_"+iYear+".txt";
			
			urlString = servletURLPattern_GCM.replace("{{year}}", String.valueOf(iYear)).replace("{{minLat}}", String.valueOf(GCMLatMin))
							.replace("{{maxLat}}",String.valueOf(GCMLatMax)).replace("{{minLon}}", String.valueOf(GCMLonmin)).
							replace("{{maxLon}}", String.valueOf(GCMLonMax));
			 System.out.println("urlString :" + urlString);
			 saveUrl(filename , urlString);
		    }
		    Vector vGCM = new Vector();
		    for(int iYear=1982;iYear<2010;iYear++)
		    {
		    	BufferedReader input =  new BufferedReader(new FileReader(folderPath2+ "/GCM_"+iYear+".txt"));
		    	
		    	String line = null; //not declared within while loop
		         while (( line = input.readLine()) != null){
		        	 System.out.println(line);
		        	 vGCM.add(line);
		         }
		    }
		    Writer outputGCM = new BufferedWriter(new FileWriter(folderPath2+ "/GCM.txt"));
		    for (int k = 0; k < vGCM.size(); k++){
		    	 outputGCM.write(vGCM.get(k).toString());
		    	 outputGCM.write("\n");
		    	 outputGCM.flush();
       		}
		    outputGCM.close();
			 //Call the HMM simulations at this point 
			 
			 //Once the dataset for HMM is run...call the NHMM to run at this point*/
			 //System.out.println("THere");
			 //ReadNHMMOutput rd = new ReadNHMMOutput();
			 
			 	 	
			 
			 
			 /* At this point Call the 
			/* Write the input files for the crop models */
				
				/********Read the crop Model File into the Folder*********/
				/*FileChannel ic = new FileInputStream("DSSATCSM40").getChannel();
				System.out.println(folderPath2);
				FileChannel oc = new FileOutputStream(folderPath2+"/DSSATCSM40").getChannel();
				ic.transferTo(0,ic.size(),oc);
				ic.close();
				oc.close();*/
				
			WriteModelInput modelINP = new WriteModelInput();
			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Downloading DSSAT BASIC FILES&lt;/br&gt;"));
			simulationHelper.setSimulationStatus(simId, "Downloading DSSAT BASIC FILES", DateUtil.getCurrentDateWithLocalTimeZone());
			modelINP.WriteInputFiles(filePath, "DATA.CDE");
			modelINP.WriteInputFiles(filePath, "DETAIL.CDE");
			modelINP.WriteInputFiles(filePath, "DSMODEL.ERR");
			modelINP.WriteInputFiles(filePath, "GCOEFF.CDE");
			modelINP.WriteInputFiles(filePath, "GRSTAGE.CDE");
			modelINP.WriteInputFiles(filePath, "JDATE.CDE");
			modelINP.WriteInputFiles(filePath, "PEST.CDE");
			modelINP.WriteInputFiles(filePath, "Simulation.cde");
			modelINP.WriteInputFiles(filePath, "SOIL.CDE");
			modelINP.WriteInputFiles(filePath, "WEATHER.CDE");
			modelINP.WriteInputFiles(filePath, "soil.sol");
			modelINP.WriteInputFiles(filePath, "GATI0301.WTH");
			modelINP.WriteInputFiles(filePath, "SoilType.tsv");
			modelINP.WriteInputFiles(filePath, "DSSATCSM40");
			//rnhmm.WriteInputFiles(filePath, "GCM.txt");
			//rnhmm.WriteInputFiles(filePath, "DSSATCSM40");
			modelINP.WriteInputFiles(filePath, "1-21.WTH");
			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Downloading DSSAT CROP FILES&lt;/br&gt;"));
			simulationHelper.setSimulationStatus(simId, "Downloading DSSAT CROP FILES", DateUtil.getCurrentDateWithLocalTimeZone());
			String crop = simulationArguments.getCrop();
			if (crop .equalsIgnoreCase("Sorghum"))
			{
				modelINP.WriteInputFiles(filePath, "SGCER040.CUL");
				modelINP.WriteInputFiles(filePath, "SGCER040.ECO");
				modelINP.WriteInputFiles(filePath, "SGCER040.SPE");
				modelINP.WriteInputFiles(filePath, "DSSAT40_Sorghum.INP");
			}
			else if (crop.equalsIgnoreCase("Maize"))
			{
				modelINP.WriteInputFiles(filePath, "MZCER.CUL");
				modelINP.WriteInputFiles(filePath, "MZCER.ECO");
				modelINP.WriteInputFiles(filePath, "MZCER.SPE");
				modelINP.WriteInputFiles(filePath, "DSSAT40_Maize.INP");
			}
			else if (crop.equalsIgnoreCase("Wheat"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_wheat.INP");
			}
			else if (crop.equalsIgnoreCase("Rice"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_rice.INP");
			}
			else if (crop.equalsIgnoreCase("Maize"))
			{
				modelINP.WriteInputFiles(filePath, "MZCER.CUL");
				modelINP.WriteInputFiles(filePath, "MZCER.ECO");
				modelINP.WriteInputFiles(filePath, "MZCER.SPE");
				modelINP.WriteInputFiles(filePath, "DSSAT40_Maize.INP");
			}
			else if (crop.equalsIgnoreCase("Millet"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_millet.INP");
			}
			else if (crop.equalsIgnoreCase("Barley"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_barley.INP");
			}
			else if (crop.equalsIgnoreCase("Bajra"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_bajra.INP");
			}
			else if (crop.equalsIgnoreCase("Peanut"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_peanut.INP");
			}
			else if (crop.equalsIgnoreCase("Sugarcane"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_sugarcane.INP");
			}
			else if (crop.equalsIgnoreCase("Soyabean"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_Soyabean.INP");
			}
			else if (crop.equalsIgnoreCase("Cabbage"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_Cabbage.INP");
			}
			else if (crop.equalsIgnoreCase("Chickpea"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_chickpea.INP");
			}
			else if (crop.equalsIgnoreCase("Pineapple"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_pineapple.INP");
			}
			else if (crop.equalsIgnoreCase("Potato"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_potato.INP");
			}
			else if (crop.equalsIgnoreCase("GreenBean"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_greenbean.INP");
			}
			else if (crop.equalsIgnoreCase("Cotton"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_cotton.INP");
			}
			else if (crop.equalsIgnoreCase("CowPea"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_cowpea.INP");
			}
			else if (crop.equalsIgnoreCase("DryBean"))
			{
				modelINP.WriteInputFiles(filePath, "DSSAT40_drybean.INP");
			}
			
			
			/***********END OF METHOD***************/
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}			
			

		    
		    
		    

	/**
	 *
	 * @param filename
	 * @param urlString
	 */
	private void saveUrl(String filename, String urlString){
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try
		{
			try {
				HttpURLConnection conn = null;
				if(useProxy){
					System.getProperties().put("http.proxyHost", proxyHost);
					System.getProperties().put("http.proxyPort", proxyPort);
				}
				URL url = new URL(urlString);
				conn = (HttpURLConnection) url.openConnection();
				boolean isConnected = (conn.getContentLength() > 0);
				System.out.println("isConnected: " + isConnected);
				in = new BufferedInputStream(conn.getInputStream());
				fout = new FileOutputStream(filename);
				System.out.println("filename: " + filename);
				byte data[] = new byte[1024];
				int count;
				while ((count = in.read(data, 0, 1024)) != -1)
				{
					fout.write(data, 0, count);
				}
				fout.flush();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		finally
		{
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (fout != null)
				try {
					fout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}	   
	   static final String kuser = "ab3466"; // your account name
	   static final String kpass = "fqqlwe"; // your password for the account

		static class MyAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
		System.err.println("Feeding username and password for " + getRequestingScheme());
		return (new PasswordAuthentication(kuser, kpass.toCharArray()));
	}
}

}
