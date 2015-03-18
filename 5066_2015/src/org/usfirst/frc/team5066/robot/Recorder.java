package org.usfirst.frc.team5066.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Recorder {
	FileWriter fileWriter;
	PrintWriter printWriter;
	String outputURL;

	long firstTime;
	double x, y, z, previousX, previousY, previousZ;
	String previous = "";

	public Recorder(String fileName) {
		this.outputURL = fileName;

	}

	public void initializeOutput() {
		try {
			fileWriter = new FileWriter(outputURL, false);
			printWriter = new PrintWriter(fileWriter);
			printWriter.println("#" + System.currentTimeMillis());
		} catch (IOException e) {
			e.printStackTrace();
		}
		firstTime = System.currentTimeMillis();
	}

	// TODO add counter again. Also fix the repeated outputs

	public void appendOutput(String[] data) {
		String toAdd = "";
		for (int i = 0; i < data.length; i++) {
			toAdd += data[i] + ",";
		}

		if (!previous.equals(toAdd) && !previous.isEmpty()) {
			printWriter.println(toAdd
					+ (System.currentTimeMillis() > firstTime ? System
							.currentTimeMillis() - firstTime
							: "Time Unavailable"));
			previous = toAdd;
		}

		if (previous.isEmpty()) {
			previous = toAdd;
		}
	}

	public void appendOutput(String key, String[] data) {
		String entry = "";
		for (int i = 0; i < data.length; i++) {
			entry += data[i] + ",";
		}

		if (!previous.equals(entry) && !previous.isEmpty()) {
		//	try {
				printWriter.println(key + "," + entry
						+ (System.currentTimeMillis() - firstTime));
		/*	} catch (NullPointerException npe) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				npe.printStackTrace(pw);
				SmartDashboard.putString("Test", sw.toString());
			}*/
			previous = entry;
		} else if (previous.isEmpty()) {
			previous = entry;
		}
	}

	/*
	 * public void appendOutput(String key, String[] data) { String entry = "",
	 * previous = getValue(store, key); for (int i = 0; i < data.length; i++) {
	 * entry += data[i] + ","; }
	 * 
	 * if (!previous.equals(entry) && !previous.isEmpty()) {
	 * printWriter.println(key + "," + entry + (System.currentTimeMillis() -
	 * firstTime)); store = putValue(store, key, entry); }
	 * 
	 * if (previous.isEmpty()) { store = putValue(store, key, entry); } }
	 * 
	 * private String getValue(ArrayList<String[]> pairs, String key) { for
	 * (String[] current : pairs) { if (current[0].equals(key)) return
	 * current[1]; } return ""; }
	 * 
	 * private ArrayList<String[]> putValue(ArrayList<String[]> pairs, String
	 * key, String entry) { for (int i = 0; i < pairs.size(); i++) { if
	 * (pairs.get(i)[0].equals(key)) { pairs.get(i)[1] = entry; return pairs; }
	 * } pairs.add(new String[] { key, entry }); return pairs; }
	 */
	public void finalizeOutput() {
		if (printWriter != null) {
			printWriter.println(previous
					+ (System.currentTimeMillis() - firstTime));
			printWriter.close();
		}
	}
}
