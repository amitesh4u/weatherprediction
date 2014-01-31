package com.opendap.poc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import ucar.ma2.Array;
import ucar.ma2.ArrayDouble;
import ucar.ma2.Index;
import ucar.nc2.NCdump;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class RainNHMM  {
	
	public RainNHMM(){}
	public Vector WriteRain(String folderPath, String fname)
	{
		//Getting the Soil Information 	
		String filename = folderPath + "/" + fname+".cdf";  
		System.out.println(filename);
		Vector vec = new Vector();
		
		NetcdfFile ncfile = null;
		  	try {
		  		 ncfile = NetcdfDataset.openFile(filename, null);  
			     } catch (IOException ioe) {} 
			try{
				  String varName = "aprod"; 
				  Variable v = ncfile.findVariable(varName);
				  
				  //if (null == v) return;
				  try {
					  
					  Array data = v.read();
					  File writeFile = new File(folderPath+"/HMM_"+fname+".txt");
					  Writer output = new BufferedWriter(new FileWriter(writeFile));
					  int[] shape = data.getShape();
					  Index index = data.getIndex();
					  for (int i=0; i<shape[0]; i++) {
					    for (int j=0; j<shape[1]; j++) {
					      double dval = data.getDouble(index.set(i,j));
					      vec.add(dval);	
					    }
					  }
					  for (int k = 0; k < vec.size(); k++){
							
					  		 	//System.out.println(k + "\t" + vec.get(k));
					    		 //System.out.println(k + "\t" + vec1.get(k) + "\t" + vec2.get(k) +"\t" + vec3.get(k));
					    		 output.write(vec.get(k).toString());
					    		 output.write("\n");
					    		 output.flush();
							 } 		  
					  
					  } 
					  catch (IOException ioe) {
					   System.out.println(ioe);
					  }
					}
				  catch (Exception e) {
					  System.out.println(e);
				  }
				//doit(urlName);
			return vec;
	}
	
	public void WriteInputFiles(String folderPath, String fname)
	{
		 try {
		      BufferedReader input =  new BufferedReader(new FileReader(fname));
		      File writeFile = new File(folderPath+"/" + fname);
		      Writer output = new BufferedWriter(new FileWriter(writeFile));
		      try {
		        String line = null; //not declared within while loop
		        System.out.println(fname);
		        while ((( line = input.readLine()) != null) ){
		        		output.write(line);
		        		output.write("\n");		        		
		        		output.flush();
		        }
		       
        		output.close();
		      }catch(Exception e){}
		 }catch(Exception e){}
		        					
	}
	
	
			


}





