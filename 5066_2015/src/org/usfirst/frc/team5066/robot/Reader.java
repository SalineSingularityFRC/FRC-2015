package org.usfirst.frc.team5066.robot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Reader {
	BufferedReader br;
	FileReader fr;
	

	ArrayList<String[]> cassetteTape = new ArrayList<String[]>();
	
	Reader(String fileURL) throws IOException {
		
		
		try {
			fr = new FileReader(fileURL);
			br = new BufferedReader(fr);
			SmartDashboard.putString("File reader", "Reading from: " + fileURL);
		} catch (FileNotFoundException e) {
			SmartDashboard.putString("File reader", "File not opened properly");
		}

		String[] buffer=new String[4];
		String nulChecker="";
		int lineCount=-1;
		//decode.add("1");
		//decode.get(0);
		//decode.set(0, "2");
		boolean keepGoing = true;
		
		while (keepGoing) {
			lineCount+=1;
			nulChecker = br.readLine();
			if (nulChecker==null){keepGoing=false;}else{
				buffer=nulChecker.split(",");
				cassetteTape.set(lineCount,buffer);
			}
		}
		if(lineCount==0){SmartDashboard.putString("File reader", "No Data");}
			
		
		}
		
		
		
	}
