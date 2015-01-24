package org.salinerobotics.library;
//Java Docks: http://first.wpi.edu/FRC/roborio/release/docs/java/
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;

public class SingularityDrive extends RobotDrive{

	public SingularityDrive(int leftMotorChannel, int rightMotorChannel) {
		super(leftMotorChannel, rightMotorChannel);
	}
	
	public void arcadeDrive(GenericHID stick, boolean squaredInputs, double sensitivity) {
		if (sensitivity > 1) {
			sensitivity = 1;
		}
		if (sensitivity < -1) {
			sensitivity = -1;
		}
	    super.arcadeDrive(sensitivity * stick.getY(), sensitivity * stick.getX() * -1, squaredInputs);
	}
	
	public void arcadeDrive(GenericHID stick, boolean squaredInputs) {
	    // simply call the full-featured arcadeDrive with the appropriate values
		super.arcadeDrive(stick.getY(), stick.getX() * -1, squaredInputs);
	}

	public void arcadeDrive(GenericHID stick) {
	       this.arcadeDrive(stick, true);
    }
	
}
