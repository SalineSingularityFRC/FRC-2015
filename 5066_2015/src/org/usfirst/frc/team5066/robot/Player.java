package org.usfirst.frc.team5066.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Player {
	private ArrayList<String[]> motionCommands, elevatorCommands;
	private int lines, currentIndex;
	private boolean dumpRecording;
	private String fileType, recording;
	public static final int MOTION_X = 1, MOTION_Y = 2, MOTION_Z = 3,
			ELEVATOR = 4;
	private static final int MOTION_X_INDEX = 1, MOTION_Y_INDEX = 2,
			MOTION_Z_INDEX = 3, ELEVATOR_INDEX = 1;

	public Player(String fileURL, String fileType) {
		BufferedReader br;
		FileReader fr;

		String stringBuffer;

		this.fileType = fileType;

		motionCommands = new ArrayList<String[]>();
		elevatorCommands = new ArrayList<String[]>();
		lines = 0;
		currentIndex = 0;

		try {
			fr = new FileReader(fileURL);
			br = new BufferedReader(fr);

			stringBuffer = br.readLine();

			if (fileType.equals(Recorder.CSV)) {
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
			} else {
				while (stringBuffer != null) {
					if (!stringBuffer.isEmpty()
							&& stringBuffer.charAt(0) != '#'
							&& !stringBuffer.substring(0, 2).equals("el")) {
						recording += stringBuffer;
					}
					stringBuffer = br.readLine();
				}
			}

			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			SmartDashboard.putString("Player", "File Not Found");
			return;
		} catch (IOException ioe) {
			SmartDashboard.putString("Player", "Input/Output Error");
			return;
		}
		SmartDashboard.putString("Player", "Playback file sucessfully loaded");
		SmartDashboard.putBoolean("Dump Recording?", dumpRecording);
	}

	public void setDumpRecording(boolean dump) {
		dumpRecording = dump;
	}

	public void dumpRecording() {
		if (dumpRecording) {
			SmartDashboard.putString("Recording Dump",
					motionCommands.toString());
		}
	}

	public String[] get(int index) {
		try {
			return motionCommands.get(index);
		} catch (IndexOutOfBoundsException ioobe) {
			return new String[] { "U R FAILURE" };
		}
	}

	public String[] get(int index, int type) {
		try {
			return motionCommands.get(index);
		} catch (IndexOutOfBoundsException ioobe) {
			return new String[] { "U R FAILURE" };
		}
	}

	public double get(long time, int valueToGet, boolean restartSearch) {
		String[] current;

		if (restartSearch) {
			currentIndex = 0;
		}

		if (fileType.equals(Recorder.CSV)) {
			switch (valueToGet) {
			case MOTION_X:
				for (int i = currentIndex; i < motionCommands.size(); i++) {
					current = motionCommands.get(i);
					if (Integer.parseInt(current[current.length - 1]) > time) {
						currentIndex = i;
						return Double.parseDouble(current[MOTION_X_INDEX]);
					}
				}
				return 0.0;
			case MOTION_Y:
				for (int i = currentIndex; i < elevatorCommands.size(); i++) {
					current = elevatorCommands.get(i);
					if (Integer.parseInt(current[current.length - 1]) > time) {
						currentIndex = i;
						return Double.parseDouble(current[MOTION_Y_INDEX]);
					}
				}
				return 0.0;
			case MOTION_Z:
				for (int i = currentIndex; i < elevatorCommands.size(); i++) {
					current = elevatorCommands.get(i);
					if (Integer.parseInt(current[current.length - 1]) > time) {
						currentIndex = i;
						return Double.parseDouble(current[MOTION_Z_INDEX]);
					}
				}
				return 0.0;
			case ELEVATOR:
				for (int i = currentIndex; i < elevatorCommands.size(); i++) {
					current = elevatorCommands.get(i);
					if (Integer.parseInt(current[current.length - 1]) > time) {
						currentIndex = i;
						return Double.parseDouble(current[ELEVATOR_INDEX]);
					}
				}
				return 0.0;
			default:
				return 0.0;
			}
		} else {
			// See 
		}

		return 0;
	}

	public int getLines() {
		return lines;
	}
}
