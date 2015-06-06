package org.usfirst.frc.team5066.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.io.StringWriter;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for recording input to a queue. This does not write to a file, merely
 * sets up an array
 * 
 * @author frc5066
 *
 */
public class Recorder {
	private long firstTime;
	private String previous = "";

	private ArrayList<String> queue;
	private boolean initialized;

	public static final String CSV = "csv", JSON = "json";
	private String fileType, key;

	/**
	 * Create a recorder object
	 * 
	 * @param fileType
	 *            Use "csv" or "json"
	 * @param key
	 *            String to name the object array or the csv line
	 */
	public Recorder(String fileType, String key) {
		this.fileType = fileType;
		this.key = key;

		queue = new ArrayList<String>();
		initialized = false;
	}

	/**
	 * returns the recorded queue
	 * 
	 * @return The queue
	 */
	public ArrayList<String> getQueue() {
		return queue;
	}

	/**
	 * Empties out the queue
	 */
	public void clearQueue() {
		queue = new ArrayList<String>();
		initialized = false;
	}

	/**
	 * Sets up the recorder for use
	 * 
	 * @param firstTime
	 *            What time to offset the recorder to. Use
	 *            System.currentTimeMillis() for a normal offset
	 */
	public void initializeRecorder(long firstTime) {
		if (!initialized) {
			initialized = true;
			this.firstTime = firstTime;
		}
	}

	/**
	 * Add a timestamp comment. For csv use only.
	 */
	public void addTimestamp() {
		queue.add("#" + System.currentTimeMillis());
	}

	// TODO add counter again.
	/**
	 * Add data to the queue
	 * 
	 * @param data
	 *            Array of data to add
	 */
	public void appendOutput(String[] data) {
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

			// TODO make format like exmaple format in notepad++
			for (int i = 0; i < data.length; i++) {
				entry += data[i] + ",";
			}

			if (!previous.equals(entry) && !previous.isEmpty()) {
				queue.add("\t\t{\"values\" : ["
						+ previous.substring(0, previous.length() - 1)
						+ "], \"time\" : "
						+ (System.currentTimeMillis() - firstTime) + "},");
				previous = entry;
			} else if (previous.isEmpty()) {
				previous = entry;
			}
		}
	}

	/**
	 * Finish off the data adding. Will add the very last element
	 */
	public void finalize() {
		if (initialized) {
			if (fileType.equals(CSV)) {
				queue.add(key + "," + previous
						+ (System.currentTimeMillis() - firstTime));
			} else if (fileType.equals(JSON)) {
				SmartDashboard.putString("Test", previous);
				queue.add("\t\t{\"values\" : ["
						+ previous.substring(0, previous.length() - 1)
						+ "], \"time\" : "
						+ (System.currentTimeMillis() - firstTime) + "}");
			}
		}
	}
}
