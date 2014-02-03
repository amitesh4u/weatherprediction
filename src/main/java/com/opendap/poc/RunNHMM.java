package com.opendap.poc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunNHMM  {

	public void runNHMMModel (final SimulationArguments simulationArguments) throws IOException
	{
			File file = new File(simulationArguments.getFileStructure() + File.separator + simulationArguments.getDistrictID());
			//File file = new File("/Users/Arindam/Desktop/HMMTool-2.1/c++");
			//File file = new File("Data/"+pathname+"/"+DistrictID);
			Process p=Runtime.getRuntime().exec("./mvnhmm  ./lrn_nhmm_ind_delexp_params.txt",null,file);
			//p.waitFor(); 
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line=reader.readLine(); 
			while(line!=null) { 
				System.out.println(line); 
				line=reader.readLine(); 
			}
			Process q=Runtime.getRuntime().exec("./mvnhmm ./sim_nhmm_ind_delexp_params.txt",null,file); 
			
			//Process q=Runtime.getRuntime().exec("./Data/2011-07-11/461/DSSATCSM40 D ./Data/2011-07-11/461/DSSAT40.INP"); 
			//Process q=Runtime.getRuntime().exec("./mvnhmm sim_nhmm_ind_delexp_params.txt"); 
			//q.waitFor();
			
			BufferedReader readerq=new BufferedReader(new InputStreamReader(q.getInputStream())); 		
			String lineq=readerq.readLine(); 
			while(lineq!=null) { 
				System.out.println(lineq); 
				lineq=readerq.readLine(); 
			}
		} 
}

