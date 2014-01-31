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
public class ServerContentDownloaderBrazil {


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

	private String folder = null;

	/**
	 * @throws IOException
	 *
	 */
	public ServerContentDownloaderBrazil() throws IOException {
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
		//File file = new File("/ServerContentDownloader_Brazil.properties");
		File file = new File(this.getClass().getResource("/ServerContentDownloader_Brazil.properties").getFile());
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
		folder = properties.getProperty("folder");
		System.out.println("Folder : " + folder);


		System.out.println("\n************************************************\n");

	}


	/**
	 * @param simId 
	 * @param DistrictID 
	 *
	 *
	 */
	public void createAndSaveUrl(final long userId, final int simNum, final long simId, String Path, long CountryNo, String DistrictID, 
			String Crop, String CropSeason, String GCM, final SimulationHelper simulationHelper){

		/* build connection to the database */
		try	{
			//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String folderPath = folder + File.separator + userId + File.separator + Path + File.separator + simNum + File.separator + CountryNo ;
			File dir = new File(folderPath);
			dir.mkdirs();
			int id = 1;
			File aFile = new File("DistrictCoordinates.txt");
			String LatLong = "";
			/**************Get Grid coordinates******************/
			double Lon = 0.0;
			double Lat = 0.0;
			String filePath = folderPath+"/"+DistrictID;
			File dir2 = new File(filePath);
			dir2.mkdirs();
			String filename = "";
			String urlString = "";
			String pathname = "";
			
		    /*********** Get the soil parameters from the Datalibrary **************/
			SimDetailsVO simDetailsVO = CropModelController.getSimDetailsMap().get(simId);
			simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Downloading Soil paramters&lt;/br&gt;"));
			simulationHelper.setSimulationStatus(simId, "Downloading Soil paramters", DateUtil.getCurrentDateWithLocalTimeZone());
			filename = filePath+ "/soil.cdf";
			urlString = servletURLPattern_soil;
		    System.out.println("urlString :" + urlString);
		    saveUrl(filename , urlString);
		    /*********** Get the grid coordinates from the Datalibrary **************/
		    simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Downloading Grid Co-ordinates&lt;/br&gt;"));
		    simulationHelper.setSimulationStatus(simId, "Downloading Grid Co-ordinates", DateUtil.getCurrentDateWithLocalTimeZone());
		    filename = filePath+ "/GridCoordinates.tsv";
			urlString = servletURLPattern_Coordinates; 
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
							.replace("{{Lon}}",String.valueOf(Lon));
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
				 urlString = servletURLPattern_TmaxAvgNR.replace("{{Lat}}", String.valueOf(Lat))
										.replace("{{Lon}}",String.valueOf(Lon));
				 System.out.println("urlString :" + urlString);
				 saveUrl(filename , urlString);
				 
				 /***********get the Tmin values when it rained *******************/
				  System.out.println(Lat + 0.1);
				  System.out.println(Lon);
				  pathname = "TminR"+Lat+"-"+Lon;
				  filename = filePath+"/"+pathname+".tsv";
				  urlString = servletURLPattern_TminAvgR.replace("{{Lat}}", String.valueOf(Lat+0.1))
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
			
			//simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Creating Data Files&lt;/br&gt;"));
			
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
			if (Crop.equalsIgnoreCase("Sorghum"))
			{
				modelINP.WriteInputFiles(filePath, "SGCER040.CUL");
				modelINP.WriteInputFiles(filePath, "SGCER040.ECO");
				modelINP.WriteInputFiles(filePath, "SGCER040.SPE");
				modelINP.WriteInputFiles(filePath, "DSSAT40_Sorghum.INP");
			}
			else if (Crop.equalsIgnoreCase("Maize"))
			{
				modelINP.WriteInputFiles(filePath, "MZCER.CUL");
				modelINP.WriteInputFiles(filePath, "MZCER.ECO");
				modelINP.WriteInputFiles(filePath, "MZCER.SPE");
				modelINP.WriteInputFiles(filePath, "DSSAT40_Maize.INP");
			}
			else if (Crop.equalsIgnoreCase("Soyabean"))
			{
				//modelINP.WriteInputFiles(filePath, "MZCER.CUL");
				//modelINP.WriteInputFiles(filePath, "MZCER.ECO");
				//modelINP.WriteInputFiles(filePath, "MZCER.SPE");
				modelINP.WriteInputFiles(filePath, "DSSAT40_Soyabean.INP");
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
	   static final String kuser = "kjensen"; // your account name
	   static final String kpass = "7o29tkhc"; // your password for the account
	   
	   //static final String kuser = "ab3466"; // your account name
	   //static final String kpass = "fqqlwe"; // your password for the account

		static class MyAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
		System.err.println("Feeding username and password for " + getRequestingScheme());
		return (new PasswordAuthentication(kuser, kpass.toCharArray()));
	}
}

}
