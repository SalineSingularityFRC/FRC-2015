package org.usfirst.frc.team5066.robot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Reader {
	private ArrayList<String[]> motionCommands, elevatorCommands;
	private int lines, currentIndex;
	BufferedReader br;
	FileReader fr;
	String stringBuffer;
	//ArrayList<double[]> cassetteTape = new ArrayList<double[]>();
	int lineCount;
	String fileURL;

	public Reader(String fileURL) throws IOException {
		
		this.fileURL = fileURL;
	}
	
	public void readCSV() throws IOException{
		
		try {
			fr = new FileReader(fileURL);
			br = new BufferedReader(fr);
			SmartDashboard.putString("File reader", "Reading from: " + fileURL);
		} catch (FileNotFoundException e) {
			SmartDashboard.putString("File reader", "File not opened properly");
			return;
		}

		//TODO Delete this if we get working alternative code
		/*
		String[] buffer = new String[4];
		String lineRead;
		lineCount = 0;
		boolean keepGoing = true;
		double[] convertedBuffer = new double[4];

		while (keepGoing) {
			lineRead = br.readLine();
			if (lineRead == null) {
				keepGoing = false;
			} else {
				buffer = lineRead.split(",");
				
				for (int i = 0; i < 5; i++) {
					convertedBuffer[i] = Double.parseDouble(buffer[i]);
				}

				cassetteTape.add(convertedBuffer);
				lineCount++;
			}
		}
		if (lineCount == 0) {
			SmartDashboard.putString("File reader", "No Data");
		}
		SmartDashboard.putNumber("File Reader Test", getLine(3, false)[3]);
*/

		motionCommands = new ArrayList<String[]>();
		elevatorCommands = new ArrayList<String[]>();
		lines = 0;
		currentIndex = 0;
		
		
		while (stringBuffer != null) {
			if (!stringBuffer.isEmpty()
					&& stringBuffer.charAt(0) != '#'
					&& !stringBuffer.substring(0, 2).equals("el")) {
				if (stringBuffer.substring(0, 2).equals("mo")) {
					motionCommands.add(stringBuffer.split(","));
					lines++;
				} else if (stringBuffer.substring(0, 2).equals("el")) {
					elevatorCommands.add(stringBuffer.split(","));
					lines++;
				}
			}
			stringBuffer = br.readLine();
		}

		br.close();
		fr.close();
		
	}

	public void readJSON(int timeIDtoUSE) {
		//TODO IMPORTANT: this assumes that the json file is contained in a set of brackets
		JSONParser parser = new JSONParser();
		try {
			fr = new FileReader(fileURL);
			SmartDashboard.putString("File reader", "Reading from: " + fileURL);
			JSONObject jsonObject = (JSONObject) parser.parse(fr);
			JSONObject recording = ;
			
		} catch (Exception e) {
			SmartDashboard.putString("File reader", "File not opened properly");
			return;
		}/*
		endReached = false;
		while(!endReached) {
			try {
				line = br.readLine();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(line != null) {
			jsonText = jsonText + line;
			}
			else endReached = true;
		}
		*/
	}
	
	public ArrayList<String[]> getMotionCommands() {
		return motionCommands;
	}
	
	public ArrayList<String[]> getElevatorCommands() {
		return elevatorCommands;
	}
	/*
	public double[] getLine(int LineNumber, boolean difference) {
		if (difference) {
			double[] result = cassetteTape.get(LineNumber);
			result[4] = (cassetteTape.get(LineNumber)[4])
					- (cassetteTape.get(LineNumber - 1)[4]);
			return result;
		} else {
			return cassetteTape.get(LineNumber);
		}
	}
	*/

}
