package org.usfirst.frc.team5066.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.salinerobotics.library.controller.SingularityController;

public class Recorder {
	FileWriter fileWriter;
	PrintWriter printWriter;
	String outputURL;
	SingularityController driveController, intakeController;

	long firstTime;
	double x, y, z, previousX, previousY, previousZ;
	String previousToAdd;

	public Recorder(String savesFolder, SingularityController driveController,
			SingularityController intakeController) {
		this.outputURL = savesFolder;
		this.driveController = driveController;
		this.intakeController = intakeController;

	}

	public void initializeOutput() {
		try {
			fileWriter = new FileWriter("/test", false);
			printWriter = new PrintWriter(fileWriter);

			printWriter.println("#" + System.currentTimeMillis());
		} catch (IOException e) {
			e.printStackTrace();
		}
		firstTime = System.currentTimeMillis();
	}

	public void appendOutput(String[] data) {
		String toAdd = "";
		for (int i = 0; i < data.length; i++) {
			toAdd += data[i] + ",";
		}

		if (!previousToAdd.equals(toAdd)) {
			printWriter.println(toAdd
					+ (System.currentTimeMillis() > firstTime ? System
							.currentTimeMillis() - firstTime
							: "Time Unavailable"));
			previousToAdd = toAdd;
		}
	}

	public void finalizeOutput() {
		if (printWriter != null) {
			printWriter.println(previousToAdd
					+ (System.currentTimeMillis() - firstTime));
			printWriter.close();
		}
	}
}
