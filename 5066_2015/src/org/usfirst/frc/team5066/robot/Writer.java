package org.usfirst.frc.team5066.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Writer {
	private FileWriter fileWriter;
	private PrintWriter printWriter;
	private String outputURL;

	private boolean initialized;

	private long firstTime;
	
	public static final String CSV = "csv", JSON = "json";
	private String fileType;

	public Writer(String fileName, String fileType) {
		initialized = false;
		this.outputURL = fileName;
		this.fileType = fileType;
	}

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

	public void addTimestamp() {
		printWriter.println("#" + System.currentTimeMillis());
	}
	
	public long getFirstTime() {
		return firstTime;
	}

	public void addJSONHead() {
		printWriter.println("{\"recordings\" :");
		printWriter.println("\t{\"starttime\" : " + System.currentTimeMillis()
				+ ",");
		printWriter.println("\t \"actions\" : [");
	}

	public void writeQueue(ArrayList<String> queue) {
		for (String str : queue) {
			printWriter.println(str);
		}
	}

	public void finalizeOutput() {
		if (initialized) {
			try {
				if (fileType.equals(JSON)) {
					printWriter.println("\t\t{\"null\","
							+ (System.currentTimeMillis() - firstTime) + "}");
					printWriter.println("\t\t]\n\t}\n}");
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
