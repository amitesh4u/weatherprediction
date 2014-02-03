package com.opendap.poc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.StringTokenizer;
import java.util.Vector;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class ReadNHMMOutput {
	public ReadNHMMOutput(){}
	
	public Vector ReadCDFFile(String folderPath2,String file)
	{
		//String filename = pathname +"/"+filename;
		Vector vec = new Vector();
		String filename = folderPath2+file;
		//System.out.println(filename);
		File aFile = new File(filename);
		StringBuilder contents = new StringBuilder();
	    try {
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null; //not declared within while loop
	        while (( line = input.readLine()) != null){
	        	//System.out.println(line);
	        	StringTokenizer st = new StringTokenizer(line,"\t");
	        	while(st.hasMoreElements())
	        		vec.add(st.nextToken().toString());
	        }
	        }catch(Exception e){}
	    }catch(Exception e){}
		return vec;
		  
	}
	
	//public void makeCropInputFiles(String folderPath2,double Lat,double Lon)
	public void makeCropInputFiles(final SimulationArguments simulationArguments)
	{
		String crop = simulationArguments.getCrop();
		/*Get the rainfall co-ordinates for the dataset */
		GridCoordinates grid = new GridCoordinates();
	    //String folderPath = "Data" + "/" + pathname;
		//String folderPath = "Data" + "/" + "2011-07-12";
		//String folderPath2 = folderPath + "/461/";
		String districtID = simulationArguments.getDistrictID();
		String folderPath = simulationArguments.getFileStructure() + File.separator + districtID + File.separator;
		//System.out.println(folderPath2);
		Vector vec = grid.Coordinates(folderPath);
		Vector TminNR = new Vector();
		Vector TminR = new Vector();
		Vector TmaxNR = new Vector();
		Vector TmaxR = new Vector();
		Vector SRadNR = new Vector();
		Vector SRadR = new Vector();
		Vector vGrid = new Vector();
		double Lon = 0.0;
		double Lat = 0.0;
		int vYears = 59;
		int vSimulations = 2;
		int vSoil = 0;
		Vector vSoilType;
		
		for(int d=0; d<vec.size();d++){
			Vector vGridParams = new Vector();
			Lon = Double.parseDouble(vec.get(d).toString());
			Lat = Double.parseDouble(vec.get(d+1).toString());
			TminNR = ReadCDFFile(folderPath,"TminNR"+vec.get(d+1).toString()+"-"+vec.get(d).toString()+".tsv");
			TminR = ReadCDFFile(folderPath,"TminR"+vec.get(d+1).toString()+"-"+vec.get(d).toString()+".tsv");
			TmaxNR = ReadCDFFile(folderPath,"TmaxNR"+vec.get(d+1).toString()+"-"+vec.get(d).toString()+".tsv");
			TmaxR = ReadCDFFile(folderPath,"TmaxR"+vec.get(d+1).toString()+"-"+vec.get(d).toString()+".tsv");
			SRadR = ReadCDFFile(folderPath,"SRadR"+vec.get(d+1).toString()+"-"+vec.get(d).toString()+".tsv");
			SRadNR = ReadCDFFile(folderPath,"SRadNR"+vec.get(d+1).toString()+"-"+vec.get(d).toString()+".tsv");
			
			vGridParams.add(TminNR);
			vGridParams.add(TminR);
			vGridParams.add(TmaxNR);
			vGridParams.add(TmaxR);
			vGridParams.add(Lat);
			vGridParams.add(Lon);
			vGrid.add(vGridParams);
					
			d++;			
			
		}
		File aFile = new File(folderPath + "HMM_sim_nhmm_ind.txt");
		int pointer = vGrid.size();
	    try {
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	    //Get the number of soils in the region
	      	Soil sol = new Soil();
	      	vSoilType = (Vector)sol.NumberofSoils(folderPath);
	      	vSoil = Integer.parseInt(vSoilType.get(0).toString()); 
	      	
	      	//System.out.println(vSoil);	        
	        //String CropSimulation[][] = new String[vYears][vSoil];
	        
	        
	      
	      try {
	        String line = null; //not declared within while loop
	        /*Based on the simulations values call the soil files*/
	        Vector vGridSimulations = new Vector();
	    	
	       for (int iPointer=0;iPointer<pointer;iPointer++)
	       {
	    	   Vector vYearSimulations = new Vector();
	    	System.out.println("************************************************");
	    	System.out.println("Gridpoint: " + iPointer);
	    	System.out.println("************************************************");
	       /*******************Simulations**************************/	   
	       //for (int l=0;l<100;l++)
	       for (int l=0;l<vSimulations;l++)
	        {
	    	   Vector vCropSimulation = new Vector();
	    	   System.out.println("---------------------------------------------");
	    	   System.out.println("Simulations:  "+l);
	    	   System.out.println("---------------------------------------------");
	    	   String year = ""; 
	    	   String date = ""; 
	    	   int i1;
	        //for ( int i = 0; i < 59; i++)
	       
	       /*******************Years*******************************/	   
		    	for ( int i = 0; i < 59; i++)//no of years
		        {	
		        	Vector a = new Vector();
			        Vector vGridParams = (Vector) vGrid.get(iPointer);
		    		TminNR = (Vector) vGridParams.get(0);
		    		TmaxNR = (Vector) vGridParams.get(1);
		    		TmaxNR = (Vector) vGridParams.get(2);
		    		TmaxR = (Vector) vGridParams.get(3);
		    		
		    		double vLat = Double.parseDouble(vGridParams.get(4).toString());
		    		double vLon = Double.parseDouble(vGridParams.get(5).toString());
		    		ReadWithScanner rds = new ReadWithScanner();
			    		
		    		/********************************************************************/
		    		String century = "19";
		    		if(i < 50) year = Integer.toString(i+50);
		    		else {year =  "0" + Integer.toString(i-50);century = "20";}
		    		System.out.println("Year:  " + century + year);
		    		//year = "50";
		    		/********************************************************************/
		    		
		    		/*************************Days*******************************/
		    		for(int j = 0; j <122;j++)
		        	{   
		    			/********************Read From the HMM output********************/
		        		line = input.readLine();
		        		StringTokenizer st = new StringTokenizer(line," ");
		        		for(int GridPointer = 0; GridPointer < iPointer; GridPointer++)
		        		{
		        			st.nextToken();
		        		}
		        		/********************************************************************/
		        		date =  Integer.toString(j+152);
		        		/********************************************************************/
			        	String inputLine = "05152  27.3  29.6  17.5   0.0";
		        		double gridRainfall = Double.parseDouble(st.nextElement().toString());
		        		if(j < 30){//consider June Month
			        		if(gridRainfall < 1)		        		
			        			a.add(year+date + "  " + SRadNR.get(6).toString().substring(0,4) + "  "+ TmaxNR.get(6).toString().substring(0,4) + "  " +TminNR.get(6).toString().substring(0,4) + "  "+gridRainfall);
			        		else
		        			a.add(year+date + "  " + SRadR.get(6).toString().substring(0,4) + "  " + TmaxR.get(6).toString().substring(0,4) + "  " +TminR.get(6).toString().substring(0,4) + "  "+gridRainfall);	
			        	 }
			        	 else if(j < 61){//consider July Month
			        		if(gridRainfall < 1)
			        			a.add(year+date + "  " + SRadNR.get(7).toString().substring(0,4) + "  " +TmaxNR.get(7).toString().substring(0,4) + "  " +TminNR.get(7).toString().substring(0,4) + "  "+gridRainfall);
			        		else
			        			a.add(year+date + "  " + SRadR.get(7).toString().substring(0,4) + "  " +TmaxR.get(7).toString().substring(0,4) + "  " +TminR.get(7).toString().substring(0,4) + "  "+gridRainfall);	
			        	}
			        	 else if(j < 92){//consider August Month
			        		if(gridRainfall < 1)
			        			a.add(year+date + "  " + SRadNR.get(8).toString().substring(0,4) + "  " +TmaxNR.get(8).toString().substring(0,4) + "  " +TminNR.get(8).toString().substring(0,4) + "  "+gridRainfall);
			        		else
			        			a.add(year+date + "  " + SRadR.get(8).toString().substring(0,4) + "  " +TmaxR.get(8).toString().substring(0,4) + "  " +TminR.get(8).toString().substring(0,4) + "  "+gridRainfall);	
			        	}
			        	 else if(j < 122){//consider September Month
			        		if(gridRainfall < 1)
			        			a.add(year+date + "  " + SRadNR.get(9).toString().substring(0,4) + "  " +TmaxNR.get(9).toString().substring(0,4) + "  " +TminNR.get(9).toString().substring(0,4) + "  "+gridRainfall);
			        		else
			        			a.add(year+date + "  " + SRadNR.get(9).toString().substring(0,4) + "  " +TmaxR.get(9).toString().substring(0,4) + "  " +TminR.get(9).toString().substring(0,4) + "  "+gridRainfall);	
			        	}
			        	 
			        	  
		        	}//end of days loop
		    		for(int j=122;j<153;j++)
		    		{
		    			a.add(year+date + "  " + SRadNR.get(9).toString().substring(0,4) + "  " +TmaxNR.get(9).toString().substring(0,4) + "  " +TminNR.get(9).toString().substring(0,4) + "  "+0);

		    		}
		    		
			    		String filename = folderPath +"/Mo-";
			            //String WeatherFileName = "Mo-"+vLat+"-"+vLon+"-"+i+".WTH";
			        	String WeatherFileName = iPointer+"-"+i+".WTH";
			    	    //File writeFile = new File(folderPath2 +"/Mo-"+vLat+"-"+vLon+"-"+i+".WTH");
			        	File writeFile = new File(folderPath +"/"+WeatherFileName);
			        	Writer output = new BufferedWriter(new FileWriter(writeFile));
					    output.write("*WEATHER DATA : Tonk,Rajasthan,India");  
					    output.write("\n");
					    output.write("@ INSI      LAT     LONG  ELEV   TAV   AMP REFHT WNDHT");
					    output.write("\n");
					    output.write("GATI   "+vLat+"  "+-vLon+"     0  19.1   8.6   2.0   3.5");
					    output.write("\n");
					    output.write("@DATE  SRAD  TMAX  TMIN  RAIN");
					    output.write("\n");
					    
					    /* Generate the weather file at this point */
			    	    for(int k = 0; k<a.size();k++)
			    	    {
			    	    	output.write(a.get(k).toString());
			    	    	output.write("\n");
			    	    	output.flush();
			    	    }
			    	    output.close();
			    	    /*For each Simulation run the crop model for each soil type*/
			    	    Vector vSoilSimulation = new Vector();
			    	    for(int iSoil = 0; iSoil <vSoil;iSoil++)
			   	    	{
				    	   String Soil = sol.GetSoilByID(folderPath, vSoilType.get(iSoil + 1).toString()); //FIXME [Amitesh] - Added folderPath2 
				    	   
				    	   //System.out.println(vSoilType.get(iSoil + 1).toString());
				    	   if(!Soil.equals(""))
								rds.WriteCropInput(Soil, folderPath, vLat, vLon, i,WeatherFileName,year,century,crop);
				    	  //convert the soil in a smaller format to be read by the cropmodel
				    	  if (Soil.equals("Silty Clay")) Soil = "SiltyClay";
						  else if (Soil.equals("Sandy Clay")) Soil = "SandyClay";
						  else if (Soil.equals("Sandy Clay Loam")) Soil = "SandyClLm";	
						  else if (Soil.equals("Clay Loam")) Soil = "ClayLoam";
						  else if (Soil.equals("Silty Clay Loam")) Soil = "SiltyClLm";
						  else if (Soil.equals("Silt Loam")) Soil = "SiltLoam";
						  else if (Soil.equals("Sandy Loam")) Soil = "SandyLoam";
						  else if (Soil.equals("Loamy Sand")) Soil = "LoamySand";					
						
							    	   
				    	  String inputCropModel = "Mo-"+vLat+"-"+vLon+"-"+i+"-"+Soil+".INP";
				    	  /*Call the Simulation of the crop model*/
				    	  SimulateCrops sim = new SimulateCrops();
				    	  //vSoilSimulation.add(sim.CallCropModel(InputCropModel,pathname,districtID,crop));
				    	  String callCropModel = sim.callCropModel(inputCropModel, folderPath);
				    	  System.out.println(callCropModel);
				    	  vSoilSimulation.add(callCropModel);
			   	    	}//end of the soil loop
			    	vCropSimulation.add(vSoilSimulation);
	        }//end of the year loop
	        vYearSimulations.add(vCropSimulation);
	    	
	        
	        }//end of simulations
	       vGridSimulations.add(vYearSimulations);
	       }//run for each grid
	        
	      
	       System.out.println("GridPoint\tSimulation\tYear\tSoil\tCropOutput"); 
	       //String pathname = "Data/2011-07-12/461/";
	       File writeFile = new File(folderPath+"SimulationResults.tsv");
	       Writer output = new BufferedWriter(new FileWriter(writeFile));
	       
	       for(int i =0; i < vGridSimulations.size();i++)
	       {
	    	   Vector a = (Vector) vGridSimulations.get(i);
    		   /***************Get the Latitude and Longitude***************/
	    	   Vector vGridParams = (Vector) vGrid.get(i);
	    	   String Latitude = vGridParams.get(4).toString();
	    	   String Longitude = vGridParams.get(5).toString();
	    	   System.out.println("********************************************************************");
	    	   for(int j =0; j < a.size();j++)
	    	   {
	    		   Vector b = (Vector) a.get(j);//vYearSimulations
	    		   for(int k =0; k < b.size();k++)
	    		   {
	    			   /********************************************************************/
			    		String century = "19";
			    		String year = "";
			    		if(k < 50) year = Integer.toString(k+50);
			    		else {year =  "0" + Integer.toString(k-50);century = "20";}
			    		//System.out.println("Year:  " + century + year);
			    		//year = "50";
			    	   Vector c = (Vector) b.get(k);//vCropSimulations
	    			   for(int l =0; l < c.size();l++)
		    		   {
	    				   String soilType = vSoilType.get(l + 1).toString().substring(0,4);
		    			   System.out.println( Latitude + "\t" + Longitude + "\t"+ j +"\t"+ century + year + "\t"+soilType+ "\t"+ c.get(l).toString()); 
		    			   output.write( Latitude + "\t" + Longitude + "\t"+ j +"\t"+ century + year + "\t"+soilType+ "\t"+ c.get(l).toString());
		    			   output.write("\n");
		    			   output.flush();
		    		   }
	    		   }
	    		   
	    		   	    		   
	    		   //CropSimulation2[1][0] = a.get(j);
	    		  
	    	   }
	    	   
	       }
	    	   
	      }catch(Exception e){System.out.println(e);}
	    }catch(Exception e){}
	}
	
}
