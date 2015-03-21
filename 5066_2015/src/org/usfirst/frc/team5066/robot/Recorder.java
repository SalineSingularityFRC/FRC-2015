package org.usfirst.frc.team5066.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.io.StringWriter;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Recorder {
	private long firstTime;
	private String previous = "";

	private ArrayList<String> queue;
	private boolean initialized;

	public static final String CSV = "csv", JSON = "json";
	private String fileType;

	public Recorder(String fileType) {
		this.fileType = fileType;

		queue = new ArrayList<String>();
		initialized = false;
	}

	public ArrayList<String> getQueue() {
		return queue;
	}
	
	public void clearQueue() {
		queue = new ArrayList<String>();
	}

	public void initializeRecorder(long firstTime) {
		if (!initialized) {
			initialized = true;
			this.firstTime = firstTime;
		}
	}

	public void addTimestamp() {
		queue.add("#" + System.currentTimeMillis());
	}

	// TODO add counter again.
	public void appendOutput(String key, String[] data) {
		String entry = "";
		if (fileType.equals(CSV)) {
			// This block is for CSV
			for (int i = 0; i < data.length; i++) {
				entry += data[i] + ",";
			}

			if (!previous.equals(entry) && !previous.isEmpty()) {
				queue.add(key + "," + entry
						+ (System.currentTimeMillis() - firstTime));
				SmartDashboard.putString(key, entry);
				previous = entry;
			} else if (previous.isEmpty()) {
				previous = entry;
			}
		} else if (fileType.equals(JSON)) {
			// This block is for json
			for (int i = 0; i < data.length; i++) {
				entry += data[i] + ",";
			}

			if (!previous.equals(entry) && !previous.isEmpty()) {
				queue.add("\t\t\t{\"" + key + "\" : ["
						+ entry.substring(0, entry.length() - 1)
						+ "],\"time\" : "
						+ (System.currentTimeMillis() - firstTime) + "},");
				previous = entry;
			} else if (previous.isEmpty()) {
				previous = entry;
			}
		}
	}
}
