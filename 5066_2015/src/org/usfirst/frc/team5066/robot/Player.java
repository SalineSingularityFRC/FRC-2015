package org.usfirst.frc.team5066.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.salinerobotics.library.SingularityDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Player {
	private ArrayList<String[]> motionCommands, elevatorCommands;
	public ArrayList<Long> motionTimes, elevatorTimes;
	private int lines, currentIndex;
	private boolean dumpRecording;
	private String fileType, recording;
	public static final int MOTION_X = 1, MOTION_Y = 2, MOTION_Z = 3,
			ELEVATOR = 4;
	private static final int MOTION_X_INDEX = 1, MOTION_Y_INDEX = 2,
			MOTION_Z_INDEX = 3, ELEVATOR_INDEX = 1;
	private SingularityDrive drive;
	private Elevator elevator;

	public Player(ArrayList<String[]> motionCommands,
			ArrayList<Long> motionTimes, ArrayList<String[]> elevatorCommands,
			ArrayList<Long> elevatorTimes, SingularityDrive drive,
			Elevator elevator) {
		this.motionCommands = motionCommands;
		this.motionTimes = motionTimes;
		this.elevatorCommands = elevatorCommands;
		this.elevatorTimes = elevatorTimes;
		this.drive = drive;
		this.elevator = elevator;
		currentIndex = 0;
	}

	public String[] getCommandAtTime(ArrayList<String[]> commands,
			ArrayList<Long> times, long time) {
		for (int i = 0; i < times.size(); i++) {
			if (times.get(i) > time) {
				return commands.get(i);
			}
		}

		String[] toReturn = new String[commands.get(0).length];
		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = "0.0";
		}

		return toReturn;
	}

	public void play(boolean simulate) {
		String[] currentMotionCommands, currentElevatorCommands;
		long startTime = System.currentTimeMillis(), finalTime = motionTimes
				.get(motionTimes.size() - 1) + startTime;

		while (System.currentTimeMillis() < finalTime) {
			currentMotionCommands = getCommandAtTime(motionCommands,
					motionTimes, System.currentTimeMillis() - startTime);
			currentElevatorCommands = getCommandAtTime(elevatorCommands,
					elevatorTimes, System.currentTimeMillis() - startTime);
			// drive.driveMecanum(Double.parseDouble(currentMotionCommands[0]),
			// Double.parseDouble(currentMotionCommands[1]),
			// Double.parseDouble(currentMotionCommands[2]), 1, 1, 1,
			// true, simulate, false);
			// elevator.set(Double.parseDouble(currentElevatorCommands[0]),
			// simulate);

			drive.driveMecanum(Double.parseDouble(currentMotionCommands[0]),
					Double.parseDouble(currentMotionCommands[1]),
					Double.parseDouble(currentMotionCommands[2]),
					Robot.horizontalConstant, Robot.verticalConstant,
					Robot.rotationConstant, true, simulate, false);
			elevator.set(Double.parseDouble(currentElevatorCommands[0]),
					simulate);
		}
	}

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

	public int getLines() {
		return lines;
	}
}
