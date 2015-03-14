package org.usfirst.frc.team5066.robot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Reader {
	BufferedReader br;
	FileReader fr;

	ArrayList<double[]> cassetteTape = new ArrayList<double[]>();
	int lineCount;

	Reader(String fileURL) throws IOException {

		try {
			fr = new FileReader(fileURL);
			br = new BufferedReader(fr);
			SmartDashboard.putString("File reader", "Reading from: " + fileURL);
		} catch (FileNotFoundException e) {
			SmartDashboard.putString("File reader", "File not opened properly");
			return;
		}

		String[] buffer = new String[4];
		String lineRead;
		lineCount = 0;
		boolean keepGoing = true;
		double[] convertedBuffer = new double[4];

		while (keepGoing) {
			lineRead = br.readLine();
			if (lineRead == null) {
				keepGoing = false;
			} else {
				buffer = lineRead.split(",");
				
				for (int i = 0; i < 5; i++) {
					convertedBuffer[i] = Double.parseDouble(buffer[i]);
				}

				cassetteTape.add(convertedBuffer);
				lineCount++;
			}
		}
		if (lineCount == 0) {
			SmartDashboard.putString("File reader", "No Data");
		}
		SmartDashboard.putNumber("File Reader Test", getLine(3, false)[3]);

	}

	public double[] getLine(int LineNumber, boolean difference) {
		if (difference) {
			double[] result = cassetteTape.get(LineNumber);
			result[4] = (cassetteTape.get(LineNumber)[4])
					- (cassetteTape.get(LineNumber - 1)[4]);
			return result;
		} else {
			return cassetteTape.get(LineNumber);
		}
	}

}
