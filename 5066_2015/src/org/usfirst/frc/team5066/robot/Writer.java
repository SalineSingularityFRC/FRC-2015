package org.usfirst.frc.team5066.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class to write a queue to a file
 * 
 * @author frc5066
 *
 */
public class Writer {
	private FileWriter fileWriter;
	private PrintWriter printWriter;
	private String outputURL;

	private boolean initialized;

	private long firstTime;

	public static final String CSV = "csv", JSON = "json";
	private String fileType;

	/**
	 * Create a Writer object
	 * 
	 * @param fileName
	 *            Name of file to use (include the extension)
	 * @param fileType
	 *            Type of file (csv or json)
	 */
	public Writer(String fileName, String fileType) {
		initialized = false;
		this.outputURL = fileName;
		this.fileType = fileType;
	}

	/**
	 * Set up file for writing. Also print status to SmartDashboard.
	 */
	public void initializeOutput() {
		try {
			fileWriter = new FileWriter(outputURL, true);
			printWriter = new PrintWriter(fileWriter);
			initialized = true;

			SmartDashboard.putString("Opened", outputURL);
			SmartDashboard.putString("Cannot open", "");

			if (fileType.equals(JSON)) {
				addJSONHead();
			} else {
				addTimestamp();
			}
		} catch (IOException ioe) {
			initialized = false;
			SmartDashboard.putString("Opened", "");
			SmartDashboard.putString("Cannot open", outputURL);
		}
		firstTime = System.currentTimeMillis();
	}

	/**
	 * Add timestamp to file.
	 */
	public void addTimestamp() {
		printWriter.println("#" + System.currentTimeMillis());
	}

	/**
	 * Returns time offset
	 * 
	 * @return time offset
	 */
	public long getFirstTime() {
		return firstTime;
	}

	/**
	 * Add json head
	 */
	private void addJSONHead() {
		/*
		 * printWriter.println("{\"recordings\" :");
		 * printWriter.println("\t{\"starttime\" : " +
		 * System.currentTimeMillis() + ",");
		 * printWriter.println("\t \"actions\" : [");
		 */

		printWriter.println("{\"recording\""
				+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"))
						.format(new Date()));
		printWriter.println("\t{\"startTime\" : " + System.currentTimeMillis()
				+ ",");
	}

	/**
	 * Writes an ArrayList to the file
	 * 
	 * @param key
	 *            Name of object to write as
	 * @param queue
	 *            ArrayList to add
	 */
	public void writeQueue(String key, ArrayList<String> queue) {
		if (fileType.equals(JSON))
			printWriter.println("\t " + key + " : [");
		for (String str : queue) {
			printWriter.println(str);
		}
		if (fileType.equals(JSON))
			printWriter.println("\t ]");
	}

	/**
	 * Adds footer if neccessary and closes writers
	 */
	public void finalizeOutput() {
		if (initialized) {
			try {
				if (fileType.equals(JSON)) {
					// TODO Format like the example in notepad++

					/*
					 * printWriter.println("\t\t\t{\"null\":[],\"time\" : " +
					 * (System.currentTimeMillis() - firstTime) + "}");
					 * printWriter.println("\t\t]\n\t}\n}");
					 */

					printWriter.println("\t \"duration\" : "
							+ (firstTime - System.currentTimeMillis()));
				} else {
					printWriter.println("null,"
							+ (System.currentTimeMillis() - firstTime));
				}

				printWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
