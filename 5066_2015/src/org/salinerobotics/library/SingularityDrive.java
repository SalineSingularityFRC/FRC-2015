package org.salinerobotics.library;

//Java Docks: http://first.wpi.edu/FRC/roborio/release/docs/java/
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

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
		m_frontRightMotor.set(magnitude * Math.sin(direction + Math.PI / 4)
				+ js.getTwist() * rotationMultiplier);
		m_rearRightMotor.set(magnitude * Math.cos(direction + Math.PI / 4)
				- js.getTwist() * rotationMultiplier);
		m_frontLeftMotor.set(magnitude * Math.cos(direction + Math.PI / 4)
				+ js.getTwist() * rotationMultiplier);
		m_rearLeftMotor.set(-magnitude * Math.sin(direction + Math.PI / 4)
				+ js.getTwist() * rotationMultiplier);
	}

	/**
	 * Captures buttons pressed and corresponds to talons. Testing purposes only.
	 * 
	 * @param number
	 * @param go
	 */
	public void tester(int number, boolean go) {
		// 7562
		switch (number) {
		case (7):
			m_frontLeftMotor.set(go ? .3 : 0);
		//actually 6
			break;
		case (5):
			m_rearLeftMotor.set(go ? .3 : 0);
		//actually 2
			break;
		case (6):
			m_frontRightMotor.set(go ? .3 : 0);
		//actually 7
			break;
		case (2):
			m_rearRightMotor.set(go ? .3 : 0);
		//actually 5
			break;
		default:
			break;
		}

	}
}
