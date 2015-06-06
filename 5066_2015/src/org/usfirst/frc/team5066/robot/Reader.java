package org.usfirst.frc.team5066.robot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class to read a file to ArrayLists
 * 
 * @author frc5066
 *
 */
public class Reader {
	private ArrayList<String[]> motionCommands, elevatorCommands;
	private ArrayList<Long> motionTimes, elevatorTimes;
	private int lines, currentIndex;
	BufferedReader br;
	FileReader fr;
	String stringBuffer;
	// ArrayList<double[]> cassetteTape = new ArrayList<double[]>();
	int lineCount;
	String fileURL;

	/**
	 * Create a Reader object
	 * 
	 * @param fileURL
	 *            Which file to read
	 */
	public Reader() {
		this.motionCommands = new ArrayList<String[]>();
		this.motionTimes = new ArrayList<Long>();
		this.elevatorCommands = new ArrayList<String[]>();
		this.elevatorTimes = new ArrayList<Long>();
	}

	// TODO Finish documenting everything in this and Player

	/**
	 * Reads comma separated values
	 * 
	 * @throws IOException
	 *             Thrown if k
	 */
	public void readCSV() throws IOException {

		try {
			fr = new FileReader(fileURL);
			br = new BufferedReader(fr);
			SmartDashboard.putString("File reader", "Reading from: " + fileURL);
		} catch (FileNotFoundException e) {
			SmartDashboard.putString("File reader", "File not opened properly");
			fr.close();
			br.close();
			return;
		}
	}

	// TODO Delete this if we get working alternative code
	/*
	 * String[] buffer = new String[4]; String lineRead; lineCount = 0; boolean
	 * keepGoing = true; double[] convertedBuffer = new double[4];
	 * 
	 * while (keepGoing) { lineRead = br.readLine(); if (lineRead == null) {
	 * keepGoing = false; } else { buffer = lineRead.split(",");
	 * 
	 * for (int i = 0; i < 5; i++) { convertedBuffer[i] =
	 * Double.parseDouble(buffer[i]); }
	 * 
	 * cassetteTape.add(convertedBuffer); lineCount++; } } if (lineCount == 0) {
	 * SmartDashboard.putString("File reader", "No Data"); }
	 * SmartDashboard.putNumber("File Reader Test", getLine(3, false)[3]);
	 * 
	 * 
	 * motionCommands = new ArrayList<String[]>(); elevatorCommands = new
	 * ArrayList<String[]>(); lines = 0; currentIndex = 0;
	 * 
	 * 
	 * while (stringBuffer != null) { if (!stringBuffer.isEmpty() &&
	 * stringBuffer.charAt(0) != '#' && !stringBuffer.substring(0,
	 * 2).equals("el")) { if (stringBuffer.substring(0, 2).equals("mo")) {
	 * motionCommands.add(stringBuffer.split(",")); lines++; } else if
	 * (stringBuffer.substring(0, 2).equals("el")) {
	 * elevatorCommands.add(stringBuffer.split(",")); lines++; } } stringBuffer
	 * = br.readLine(); }
	 * 
	 * br.close(); fr.close();
	 * 
	 * }
	 */

	public void readJSON(String fileName) {
		this.fileURL = "/recordings/" + fileName + ".json";
		SmartDashboard.putString("Recording File Name - UPTODATE", this.fileURL);
		JSONArray motionJSONArray, elevatorJSONArray;
		JSONObject recording, tempObject;
		JSONParser parser = new JSONParser();

		Object[] tempObjectArray;

		String[] tempStringArray;

		try {
			fr = new FileReader(fileURL);
			SmartDashboard.putString("Reading from:", fileURL);
			
			recording = (JSONObject) parser.parse(fr);
			motionJSONArray = (JSONArray) recording.get("mo");
			elevatorJSONArray = (JSONArray) recording.get("el");

			for (Object object : motionJSONArray) {
				tempObject = (JSONObject) object;
				tempObjectArray = ((JSONArray) tempObject.get("values"))
						.toArray();

				tempStringArray = new String[tempObjectArray.length];
				for (int i = 0; i < tempObjectArray.length; i++) {
					tempStringArray[i] = tempObjectArray[i].toString();
				}

				motionCommands.add(tempStringArray);
				motionTimes.add(Long.parseLong(tempObject.get("time")
						.toString()));
			}

			for (Object object : elevatorJSONArray) {
				tempObject = (JSONObject) object;
				tempObjectArray = ((JSONArray) tempObject.get("values"))
						.toArray();

				tempStringArray = new String[tempObjectArray.length];
				for (int i = 0; i < tempObjectArray.length; i++) {
					tempStringArray[i] = tempObjectArray[i].toString();
				}

				elevatorCommands.add(tempStringArray);
				elevatorTimes.add(Long.parseLong(tempObject.get("time")
						.toString()));
			}
		} catch (FileNotFoundException fnfe) {
			SmartDashboard.putString("File reader", "File not found");
			return;
		} catch (IOException ioe) {
			SmartDashboard.putString("File reader", "File not read properly");
			return;
		} catch (ParseException pe) {
			SmartDashboard.putString("File reader", "File unparseable");
			return;
		}
		SmartDashboard.putString("File reader", "File read properly");
		/*
		 * endReached = false; while (!endReached) { try { line = br.readLine();
		 * 
		 * } catch (IOException e) { e.printStackTrace(); } if (line != null) {
		 * 00000000000000000000000000000000000000000000000 * jsonText = jsonText
		 * + line; } else endReached = true; }
		 */

	}

	public ArrayList<String[]> getMotionCommands() {
		return motionCommands;
	}

	// private String cat(ArrayList<String[]> arr) {
	// String toReturn = "";
	// for (int i = 0; i < arr.size(); i++) {
	// toReturn += "[";
	// for (int j = 0; j < arr.get(i).length; j++) {
	// toReturn += arr.get(i)[j] + ", ";
	// }
	// toReturn = toReturn.substring(0, toReturn.length() - 2);
	// toReturn += "], ";
	// }
	//
	// return toReturn.substring(0, toReturn.length() - 2);
	// }
	//
	// private String cat2(ArrayList<Long> arr) {
	// String toReturn = "";
	// for(int i = 0; i<arr.size();i++){
	// toReturn += arr.get(i) + ", ";
	// }
	// return toReturn.substring(0, toReturn.length() - 2);
	// }

	public ArrayList<Long> getMotionTimes() {
		return motionTimes;
	}

	public ArrayList<String[]> getElevatorCommands() {
		return elevatorCommands;
	}

	public ArrayList<Long> getElevatorTimes() {
		return elevatorTimes;
	}

	/*
	 * public double[] getLine(int LineNumber, boolean difference) { if
	 * (difference) { double[] result = cassetteTape.get(LineNumber); result[4]
	 * = (cassetteTape.get(LineNumber)[4]) - (cassetteTape.get(LineNumber -
	 * 1)[4]); return result; } else { return cassetteTape.get(LineNumber); } }
	 */

}
