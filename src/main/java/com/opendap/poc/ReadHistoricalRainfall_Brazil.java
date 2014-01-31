package com.opendap.poc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.rii.wp.controller.CropModelController;
import com.rii.wp.model.SimDetailsVO;
import com.rii.wp.util.DateUtil;
import com.rii.wp.util.SimulationHelper;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;


public class ReadHistoricalRainfall_Brazil {
	private String folder = null;
	
	public ReadHistoricalRainfall_Brazil() throws FileNotFoundException{
			try {
				Properties properties = new Properties();
		        File file = new File(this.getClass().getResource("/ServerContentDownloader_Brazil.properties").getFile());
				FileInputStream fis = new FileInputStream(file);
				properties.load(fis);
				folder = properties.getProperty("folder");
				System.out.println("Folder : " + folder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public Vector ReadCDFFile(String folderPath2,String file)
	{
		//String filename = pathname +"/"+filename;
		Vector vec = new Vector();
		String filename = folderPath2+file;
		File aFile = new File(filename);
		StringBuilder contents = new StringBuilder();
	    try {
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null; //not declared within while loop
	        while (( line = input.readLine()) != null){
	        	StringTokenizer st = new StringTokenizer(line,"\t");
	        	while(st.hasMoreElements())
	        		vec.add(st.nextToken().toString());
	        }
	        }catch(Exception e){}
	    }catch(Exception e){}
		return vec;
		  
	}
	
	//public void makeCropInputFiles(String folderPath2,double Lat,double Lon)
	public void makeCropInputFiles(final long userId, final int simNum, final long simId, String Path, long CountryNo, String DistrictID, 
			String Crop, String CropSeason, String GCM, final SimulationHelper simulationHelper)
	{
		
		
		/**************Get the rainfall co-ordinates for the dataset******************/	
	     /*rainParameters = rnhmm.WriteRain(filePath,pathname);
		 NHMMrain.add(rainParameters);(this method could be called here)*/
		SimDetailsVO simDetailsVO = CropModelController.getSimDetailsMap().get(simId); 
		simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Extracting Grid Co-ordinates&lt;/br&gt;"));
		simulationHelper.setSimulationStatus(simId, "Extracting Grid Co-ordinates", DateUtil.getCurrentDateWithLocalTimeZone());
		GridCoordinates grid = new GridCoordinates();
		String folderPath = folder + File.separator + userId + File.separator + Path + File.separator + simNum + File.separator + CountryNo ;
	    //String folderPath = "Data" + "/" + Path;
		String filepath = folderPath + "/"+DistrictID;
	    Vector vecGrid = grid.Coordinates(filepath+"/GridCoordinates.tsv");
		Vector TminNR = new Vector();
		Vector TminR = new Vector();
		Vector TmaxNR = new Vector();
		Vector TmaxR = new Vector();
		Vector SRadNR = new Vector();
		Vector SRadR = new Vector();
		Vector vGrid = new Vector();
		double Lon = 0.0;
		double Lat = 0.0;
		int vYears = 11;
		int vSimulations = 10;
		int vSoil = 0;
		Vector vSoilType;
        System.out.println(vecGrid.size());
		/**************Make an n dimensional dataset for input as cropmodel weather file******************/
		for(int d=0; d<vecGrid.size();d++){
			Vector vGridParams = new Vector();
			//simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append("-Extracting Weather Parameters Per Grid Point&lt;/br&gt;"));
			Lon = Double.parseDouble(vecGrid.get(d).toString());
			Lat = Double.parseDouble(vecGrid.get(d+1).toString());
			TminNR = ReadCDFFile(filepath+"/","TminNR"+vecGrid.get(d+1).toString()+"-"+vecGrid.get(d).toString()+".tsv");
			TminR = ReadCDFFile(filepath+"/","TminR"+vecGrid.get(d+1).toString()+"-"+vecGrid.get(d).toString()+".tsv");
			TmaxNR = ReadCDFFile(filepath+"/","TmaxNR"+vecGrid.get(d+1).toString()+"-"+vecGrid.get(d).toString()+".tsv");
			TmaxR = ReadCDFFile(filepath+"/","TmaxR"+vecGrid.get(d+1).toString()+"-"+vecGrid.get(d).toString()+".tsv");
			SRadR = ReadCDFFile(filepath+"/","SRadR"+vecGrid.get(d+1).toString()+"-"+vecGrid.get(d).toString()+".tsv");
			SRadNR = ReadCDFFile(filepath+"/","SRadNR"+vecGrid.get(d+1).toString()+"-"+vecGrid.get(d).toString()+".tsv");
			vGridParams.add(TminNR);
			vGridParams.add(TminR);
			vGridParams.add(TmaxNR);
			vGridParams.add(TmaxR);
			vGridParams.add(Lat);
			vGridParams.add(Lon);
			vGrid.add(vGridParams);
			d++;			
		}
		/**************Make an n dimensional dataset for input as cropmodel weather file******************/	 
		int pointer = vGrid.size();    //returns the size of the weather file
		
		try {
	      
			/**************Get the number of soil in region******************/
	      	Soil sol = new Soil();
	      	//simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Extracting Number of Soils For Each Unit&lt;/br&gt;"));
	      	vSoilType = (Vector)sol.NumberofSoils(filepath);
	      	vSoil = Integer.parseInt(vSoilType.get(0).toString());
	      	try {
		        String line = null; //not declared within while loop
		        /*Based on the simulations values call the soil files*/
		        Vector vGridSimulations = new Vector();
		 /*************************EachGridPoints*******************************/
		        
		        for (int iPointer=0;iPointer<pointer;iPointer++)
		        {	
		        	
		    	    Vector vGridParams = (Vector) vGrid.get(iPointer);
		    		TminNR = (Vector) vGridParams.get(0);
		    		TmaxNR = (Vector) vGridParams.get(1);
		    		TmaxNR = (Vector) vGridParams.get(2);
		    		TmaxR = (Vector) vGridParams.get(3);
		    		
		    		double vLat = Double.parseDouble(vGridParams.get(4).toString());
		    		double vLon = Double.parseDouble(vGridParams.get(5).toString());
		    		
		    	    File aFile = new File(filepath+"/HMM_rainfall-"+vLat+"-"+vLon+".txt").getAbsoluteFile();
		    	    System.out.println(filepath+"/HMM_rainfall-"+vLat+"-"+vLon+".txt");
		    	    BufferedReader input =  new BufferedReader(new FileReader(aFile));
		    	    Vector vYearSimulations = new Vector();
			    	System.out.println("************************************************");
			    	System.out.println("Gridpoint: " + iPointer);
			    	System.out.println("************************************************");
			        String year = ""; 
		    	    String date = ""; 
		    	   
		    	    //simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Building Weather Input Files For Crop Model&lt;/br&gt;"));
		    	    /*******************Years*******************************/	   
			    	for ( int i = 0; i < 12; i++)//no of years
			        {	
			    		//simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Year "+ i +"&lt;/br&gt;"));
			    		Vector a = new Vector();
				        ReadWithScanner rds = new ReadWithScanner();
				    		
			    		/********************************************************************/
			    		String century = "20";
				        year = Integer.toString(i);
			    		System.out.println("Year:  " + century + year);
			    		
			    				/*************************Days*******************************/
			    				/************************in Brazil for ONDJ thr are 123 days */
			    				for(int j = 0; j <123;j++)
			    					{   
			    						/********************Read From the HMM output********************/
			    						line = input.readLine();
			    						/********************************************************************/
						        		date =  Integer.toString(j+152);//this represents 1st of June
						        		/********************************************************************/
							        	//String inputLine = "05152  27.3  29.6  17.5   0.0";
						        		try
						        		{
						        		/********************Run the Models for Different Months********************/
						        		double gridRainfall = Double.parseDouble(line.toString());
						        		if (gridRainfall < 0.0)
						        			gridRainfall = 0;
						        		//if there is a nan then there comes an error,also use Substring functions
						        		if(j < 31){//consider Oct Month
							        		if(gridRainfall < 1)
							        			a.add(year+date + "  " + SRadNR.get(6).toString().substring(0,4) + "  "+ TmaxNR.get(6).toString().substring(0,4) + "  " +TminNR.get(6).toString().substring(0,4) + "  "+gridRainfall);
							        		else
						        			    a.add(year+date + "  " + SRadR.get(6).toString().substring(0,4) + "  " + TmaxR.get(6).toString().substring(0,4) + "  " +TminR.get(6).toString().substring(0,4) + "  "+gridRainfall);
							        			//System.out.println(year+date + "  " + SRadR.get(6).toString() + "  " + TmaxR.get(6).toString() + "  " +TminR.get(6).toString() + "  "+gridRainfall);
							        	 }
						        		 else if(j < 61){//consider Nov Month
							        		if(gridRainfall < 1)
							        			a.add(year+date + "  " + SRadNR.get(7).toString().substring(0,4) + "  " +TmaxNR.get(7).toString().substring(0,4) + "  " +TminNR.get(7).toString().substring(0,4) + "  "+gridRainfall);
							        		else
							        			a.add(year+date + "  " + SRadR.get(7).toString().substring(0,4) + "  " +TmaxR.get(7).toString().substring(0,4) + "  " +TminR.get(7).toString().substring(0,4) + "  "+gridRainfall);	
							        	}
							        	 else if(j < 92){//consider Dec Month
							        		if(gridRainfall < 1)
							        			a.add(year+date + "  " + SRadNR.get(8).toString().substring(0,4) + "  " +TmaxNR.get(8).toString().substring(0,4) + "  " +TminNR.get(8).toString().substring(0,4) + "  "+gridRainfall);
							        		else
							        			a.add(year+date + "  " + SRadR.get(8).toString().substring(0,4) + "  " +TmaxR.get(8).toString().substring(0,4) + "  " +TminR.get(8).toString().substring(0,4) + "  "+gridRainfall);	
							        	}
							        	 else if(j < 122){//consider Jan Month
							        		if(gridRainfall < 1)
							        			a.add(year+date + "  " + SRadNR.get(9).toString().substring(0,4) + "  " +TmaxNR.get(9).toString().substring(0,4) + "  " +TminNR.get(9).toString().substring(0,4) + "  "+gridRainfall);
							        		else
							        			a.add(year+date + "  " + SRadNR.get(9).toString().substring(0,4) + "  " +TmaxR.get(9).toString().substring(0,4) + "  " +TminR.get(9).toString().substring(0,4) + "  "+gridRainfall);	
							        	}
						        		}catch(Exception e){e.printStackTrace();} 
						        	 
			        			}//end of days loop
			    		
			    	/********************Generate the Crop Input Headers********************/
			    				//simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Writing Weather File for each year and each grid point&lt;/br&gt;"));
			    				String filename = filepath +"/Mo-";
					            System.out.println(filepath);
			    				//String WeatherFileName = "Mo-"+vLat+"-"+vLon+"-"+i+".WTH";
					        	String WeatherFileName = iPointer+"-"+i+".WTH";
					    	    //File writeFile = new File(folderPath2 +"/Mo-"+vLat+"-"+vLon+"-"+i+".WTH");
					        	File writeFile = new File(filepath, WeatherFileName);
							    Writer output = new BufferedWriter(new FileWriter(writeFile));
					        	output.write("*WEATHER DATA : Tonk,Rajasthan,India");  
							    output.write("\n");
							    output.write("@ INSI      LAT     LONG  ELEV   TAV   AMP REFHT WNDHT");
							    output.write("\n");
							    output.write("GATI   "+vLat+"  "+-vLon+"     0  19.1   8.6   2.0   3.5");
							    output.write("\n");
							    output.write("@DATE  SRAD  TMAX  TMIN  RAIN");
							    output.write("\n");
							    output.flush();
			    	/********************Generate the Weather Files Here********************/
							    for(int k = 0; k<a.size();k++)
					    	    {
					    	    	output.write(a.get(k).toString());
					    	    	output.write("\n");
					    	    	output.flush();
					    	    }
					    	    output.close();
					/********************Run the simulations for each soil type********************/
					    	    Vector vSoilSimulation = new Vector();
					    	    //simDetailsVO.setStatusMessage(simDetailsVO.getStatusMessage().append(" -Building INP files for DSSAT&lt;/br&gt;"));
					    	    for(int iSoil = 0; iSoil <vSoil;iSoil++)
					   	    	{
						    	   String Soil = sol.GetSoilByID(filepath,vSoilType.get(iSoil + 1).toString());
						    	   
						    	   System.out.println(vSoilType.get(iSoil + 1).toString());
						    	   System.out.println(Soil.toString());
						    	   
						    	  if(!Soil.equals(""))
						    		  rds.WriteCropInput(Soil, filepath, vLat, vLon, i,WeatherFileName,year,century,Crop);
						 
						    	  //convert the soil in a smaller format to be read by the cropmodel
						    	  if (Soil.equals("Silty Clay")) Soil = "SiltyClay";
								  else if (Soil.equals("Sandy Clay")) Soil = "SandyClay";
								  else if (Soil.equals("Sandy Clay Loam")) Soil = "SandyClLm";	
								  else if (Soil.equals("Clay Loam")) Soil = "ClayLoam";
								  else if (Soil.equals("Silty Clay Loam")) Soil = "SiltyClLm";
								  else if (Soil.equals("Silt Loam")) Soil = "SiltLoam";
								  else if (Soil.equals("Sandy Loam")) Soil = "SandyLoam";
								  else if (Soil.equals("Loamy Sand")) Soil = "LoamySand";					
								
									    	   
						    	  String inputfile = "Mo-"+vLat+"-"+vLon+"-"+i+"-"+Soil+".INP";
						    	  SimulateCrops sim = new SimulateCrops();
						    	  System.out.println(sim.CallCropModel(inputfile, filepath, DistrictID, Crop));
						    	  vSoilSimulation.add(sim.CallCropModel(inputfile, filepath, DistrictID, Crop));
					   	    	}//end of the soil loop 
					    	    vYearSimulations.add(vSoilSimulation);
					/********************Run the simulations for each year (end)********************/
				}//end of the year loop
		        vGridSimulations.add(vYearSimulations);
		       }//run for each grid
		       
		        /********************Writes to an external file the outputs********************/	
	System.out.println("GridPoint\tSimulation\tYear\tSoil\tCropOutput"); 
    //String pathname = "Data/2011-07-21/188/";
    String pathname = filepath;
    File writeFile = new File(pathname,"HistoricalResults.tsv");
    Writer output = new BufferedWriter(new FileWriter(writeFile));
    
    for(int i =0; i < vGridSimulations.size();i++)
    {
 	   Vector b = (Vector) vGridSimulations.get(i);
		   /***************Get the Latitude and Longitude***************/
 	   Vector vGridParams = (Vector) vGrid.get(i);
 	   String Latitude = vGridParams.get(4).toString();
 	   String Longitude = vGridParams.get(5).toString();
 	   System.out.println("********************************************************************");
 	   //for(int j =0; j < a.size();j++)
 	   //{
 		 //  Vector b = (Vector) a.get(j);//vYearSimulations
 		   for(int k =0; k < b.size();k++)
 		   {
 			   /********************************************************************/
		    		String century = "19";
		    		String year = "";
		    		if(k < 50) year = Integer.toString(k+50);
		    		else {year =  "0" + Integer.toString(k-50);century = "20";}
		    		
		    	   Vector c = (Vector) b.get(k);//vCropSimulations
 			   for(int l =0; l < c.size();l++)
	    		   {
 				   String soilType = vSoilType.get(l + 1).toString().substring(0,4);
 				   //System.out.println(c.get(l));
	    			   System.out.println( Latitude + "\t" + Longitude + "\t"+ i +"\t"+ century + year + "\t"+soilType+ "\t"+ c.get(l).toString()); 
	    			   output.write( Latitude + "\t" + Longitude + "\t"+ i +"\t"+ century + year + "\t"+soilType+ "\t"+ c.get(l).toString());
	    			   output.write("\n");
	    			   output.flush();
	    		   }
 		   }
    }
 	   
   }catch(Exception e){e.printStackTrace();}
}catch(Exception e){e.printStackTrace();}

}
	}
