package com.opendap.poc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimulateCrops {
	
	private static int count=0;
	
	public String callCropModel(final String filename, final String pathname) {
		//System.out.println("SimulateCrops:" + SimulateCrops.count++);
		String cropOutput = "0";

		try {
			// Read the properties file
			File file = new File(pathname + File.separator);
			File afile = new File(file + "DSSATCSM40");
			// File aFile = new
			// File(this.getClass().getResource("/DistrictCoordinates.txt").getFile());
			// File file = new File("Data/"+pathname+"/"+DistrictID);
			// Process
			// p1=Runtime.getRuntime().exec("./DSSATCSM40 D Mo-23.5-73.5-0-SandyClLoam.INP",null,file);
			// Process
			// p1=Runtime.getRuntime().exec("./DSSATCSM40 D Mo-23.5-73.5-0-SandyLoam.INP",null,file);
			String[] command = { "/bin/chmod", "777", afile.getAbsolutePath() + File.separator, };
			Runtime.getRuntime().exec(command);
			// Process p = Runtime.getRuntime().exec("chmod 777 ./DSSATCSM40");
			// p.waitFor();
			// Runtime.getRuntime().exec("./DSSATCSM40");

			Process p1 = Runtime.getRuntime().exec(
					"./DSSATCSM40 D " + filename, null, file);
			p1.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p1.getInputStream()));
			String line = reader.readLine();

			while (line != null) {
				// System.out.println(line);
				if (line.contains("YIELD")) {
					System.out.println(line);
					// System.out.println(line.substring(39,43));
					cropOutput = line.substring(39, 43);
				} else
					cropOutput = "0";
				// CropYeild.concat(line);
				// if(line.contains("Invalid format in file.")) return "Error";
				// if(line.contains("End-of-file encountered in input file."))
				// return "Error";
				line = reader.readLine();
				// System.out.println(CropYeild);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cropOutput;

	}

}
