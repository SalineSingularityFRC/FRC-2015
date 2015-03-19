package org.usfirst.frc.team5066.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.io.StringWriter;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Recorder {
	private FileWriter fileWriter;
	private PrintWriter printWriter;
	private String outputURL;

	private long firstTime;
	private String previous = "";

	private ArrayList<String> queue;
	private boolean initialized;

	public static final String CSV = "csv", JSON = "json";
	private String fileType;

	public Recorder(String fileName, String fileType) {
		initialized = false;
		this.outputURL = fileName;
		this.fileType = fileType;
		queue = new ArrayList<String>();
	}

	public void initializeOutput() {
		initialized = true;
		firstTime = System.currentTimeMillis();
	}

	public void addJSONHead() {
		queue.add("{\"recordings\" :");
		queue.add("\t{\"starttime\" : " + System.currentTimeMillis() + ",");
		queue.add("\t \"actions\" : [");
	}

	public void addTimestamp() {
		queue.add("#" + System.currentTimeMillis());
	}

	// TODO add counter again.

	// public void appendOutput(String[] data) {
	// String toAdd = "";
	// for (int i = 0; i < data.length; i++) {
	// toAdd += data[i] + ",";
	// }
	//
	// if (!previous.equals(toAdd) && !previous.isEmpty()) {
	// printWriter.println(toAdd
	// + (System.currentTimeMillis() > firstTime ? System
	// .currentTimeMillis() - firstTime
	// : "Time Unavailable"));
	// previous = toAdd;
	// }
	//
	// if (previous.isEmpty()) {
	// previous = toAdd;
	// }
	// }

	public void appendOutput(String key, String[] data) {
		String entry = "";
		if (fileType.equals(CSV)) {
			for (int i = 0; i < data.length; i++) {
				entry += data[i] + ",";
			}

			if (!previous.equals(entry) && !previous.isEmpty()) {
				queue.add(key + "," + entry
						+ (System.currentTimeMillis() - firstTime));
				SmartDashboard.putString(key, entry);
				previous = entry;

				/*
				 * try { printWriter.println(key + "," + entry +
				 * (System.currentTimeMillis() - firstTime));
				 * 
				 * } catch (NullPointerException npe) { StringWriter sw = new
				 * StringWriter(); PrintWriter pw = new PrintWriter(sw);
				 * npe.printStackTrace(pw); SmartDashboard.putString("Test",
				 * sw.toString()); }
				 */

			} else if (previous.isEmpty()) {
				previous = entry;
			}
		} else if (fileType.equals(JSON)) {
			for (int i = 0; i < data.length; i++) {
				entry += data[i] + ",";
			}

			if (!previous.equals(entry) && !previous.isEmpty()) {
				queue.add("\t\t{\"" + key + "\" : [" + entry
						+ (System.currentTimeMillis() - firstTime) + "]},");
				SmartDashboard.putString(key, entry);
				previous = entry;
			} else if (previous.isEmpty()) {
				previous = entry;
			}
		} else {

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
	public void finalizeOutput(boolean end) {
		if (initialized) {
			try {
				if (fileType.equals(JSON)) {
					fileWriter = new FileWriter(outputURL, true);
				} else {
					fileWriter = new FileWriter(outputURL, true);
				}
				printWriter = new PrintWriter(fileWriter);

				for (String str : queue) {
					printWriter.println(str);
				}
				if (end) {
					if (fileType.equals(JSON)) {
						printWriter.println("\t\t{\"null\"," + previous
								+ (System.currentTimeMillis() - firstTime)
								+ "}");
						printWriter.println("\t\t]\n\t}\n}");
					} else {
						printWriter.println("null," + previous
								+ (System.currentTimeMillis() - firstTime));
					}
				}

				printWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
