package org.usfirst.frc.team5066.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Player {
	private ArrayList<String[]> commands;
	private int lines;
	private boolean dumpRecording;

	public Player(String fileURL) {
		BufferedReader br;
		FileReader fr;

		String stringBuffer;

		commands = new ArrayList<String[]>();
		lines = 0;

		try {
			fr = new FileReader(fileURL);
			br = new BufferedReader(fr);

			stringBuffer = br.readLine();

			while (stringBuffer != null) {
				if (!stringBuffer.isEmpty() && stringBuffer.charAt(0) != '#') {
					commands.add(stringBuffer.split(","));
					lines++;
				}
				stringBuffer = br.readLine();
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
			SmartDashboard.putString("Recording Dump", commands.toString());
		}
	}

	public String[] get(int index) {
		try {
			return commands.get(index);
		} catch (IndexOutOfBoundsException ioobe) {
			return new String[] { "U R FAILURE", "", "", "", "", "" };
		}
	}

	public int getLines() {
		return lines;
	}
}
