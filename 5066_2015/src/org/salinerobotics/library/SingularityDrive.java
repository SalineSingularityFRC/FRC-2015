package org.salinerobotics.library;

//Java Docks: http://first.wpi.edu/FRC/roborio/release/docs/java/
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class SingularityDrive extends RobotDrive {

	public SingularityDrive(int leftMotorChannel, int rightMotorChannel) {
		super(leftMotorChannel, rightMotorChannel);
	}

	public SingularityDrive(int frontLeftMotorChannel,
			int backLeftMotorChannel, int frontRightMotorChannel,
			int backRightMotorChannel) {
		super(frontLeftMotorChannel, backLeftMotorChannel,
				frontRightMotorChannel, backRightMotorChannel);
	}

	public void arcadeDrive(GenericHID stick, boolean squaredInputs,
			double sensitivity) {
		if (sensitivity > 1) {
			sensitivity = 1;
		}
		if (sensitivity < -1) {
			sensitivity = -1;
		}
		super.arcadeDrive(sensitivity * stick.getY(),
				sensitivity * stick.getX() * -1, squaredInputs);
	}

	public void arcadeDrive(GenericHID stick, boolean squaredInputs) {
		// simply call the full-featured arcadeDrive with the appropriate values
		super.arcadeDrive(stick.getY(), stick.getX() * -1, squaredInputs);
	}

	public void arcadeDrive(GenericHID stick) {
		this.arcadeDrive(stick, true);
	}

	/**
	 * Mecanum drive using one (1) joystick
	 * 
	 * @param js
	 *            Joystick to use
	 * @param translationMultiplier
	 *            How quickly to translate (0 - 1)
	 * @param rotationMultiplier
	 *            How quickly to rotate (0 - 1)
	 */

	public void driveMecanum(Joystick js, double translationMultiplier,
			double rotationMultiplier) {

		// Find magnitude using pythagorean theorem
		double magnitude = Math.sqrt(js.getX() * js.getX() + js.getY()
				* js.getY())
				* translationMultiplier;

		// Find direction using arctan
		double direction = Math.atan(-js.getY() / js.getX());
		if (js.getX() < 0) {
			direction += Math.PI;
		}

		// Use formulas to set wheel speeds.
		m_frontLeftMotor.set(magnitude * Math.sin(direction + Math.PI / 4)
				- js.getTwist() * rotationMultiplier * 2);
		m_rearLeftMotor.set(magnitude * Math.cos(direction + Math.PI / 4)
				- js.getTwist() * rotationMultiplier * 2);
		m_frontRightMotor.set(magnitude * Math.cos(direction + Math.PI / 4)
				+ js.getTwist() * rotationMultiplier * 2);
		m_rearRightMotor.set(-magnitude * Math.sin(direction + Math.PI / 4)
				- js.getTwist() * rotationMultiplier * 2);
	}

}
