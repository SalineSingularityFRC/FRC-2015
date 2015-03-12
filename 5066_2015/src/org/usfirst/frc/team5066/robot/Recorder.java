package org.usfirst.frc.team5066.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.salinerobotics.library.controller.SingularityController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Recorder {
	FileWriter fileWriter;
	PrintWriter printWriter;
	String outputURL;
	SingularityController driveController, intakeController;

	int count;
	double x, y, z, previousX, previousY, previousZ;

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

			count = 1;

			printWriter.println("#" + System.nanoTime());
		} catch (IOException e) {
			// SmartDashboard.putString("Test", "It Failed");
			e.printStackTrace();
		}
	}

	public void appendOutput() {
		x = driveController.getX();
		y = driveController.getY();
		z = driveController.getZ();

		if (previousX == x && previousY == y && previousZ == z) {
			count++;
		} else {
			printWriter.println(previousX + "," + previousY + "," + previousZ
					+ "," + count + "," + System.nanoTime());
			previousX = x;
			previousY = y;
			previousZ = z;
			count = 1;
		}

	}

	public void finalizeOutput() {
		if (printWriter != null) {
			printWriter.println(x + "," + y + "," + z + "," + count);
			printWriter.close();
		}
	}
}
