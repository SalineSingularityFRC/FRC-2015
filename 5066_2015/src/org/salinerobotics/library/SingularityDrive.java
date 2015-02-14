package org.salinerobotics.library;

//Java Docs: http://first.wpi.edu/FRC/roborio/release/docs/java/
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
	 * @param squaredInputs
	 *            Uses squared inputs if it's true
	 */
	public void driveMecanum(Joystick js, double translationMultiplier,
			double rotationMultiplier, boolean squaredInputs) {

		double x = js.getX(), y = js.getY(), z = js.getTwist()
				* rotationMultiplier;

		// If squaredInputs square the inputs
		if (squaredInputs) {
			x *= Math.abs(x);
			y *= Math.abs(y);
			z *= Math.abs(z);
		}

		// Find magnitude using pythagorean theorem
		double magnitude = Math.sqrt(x * x + y * y) * translationMultiplier;

		// Find direction using arctan
		double direction = Math.atan(-y / x);
		if (x < 0) {
			direction += Math.PI;
		}

		// Use formulas to set wheel speeds.
		m_frontRightMotor
				.set(magnitude * Math.sin(direction + Math.PI / 4) + z);
		m_rearRightMotor.set(magnitude * Math.cos(direction + Math.PI / 4) - z);
		m_frontLeftMotor.set(magnitude * Math.cos(direction + Math.PI / 4) + z);
		m_rearLeftMotor.set(magnitude * Math.sin(direction + Math.PI / 4) - z);
	}

	/**
	 * 
	 * @param controller
	 *            XBox controller to use. Will not be okay if you use a logitech
	 *            one.
	 * @param translationMultiplier
	 *            How much power to use
	 * @param squaredInputs
	 *            Squared inputs or not.
	 */
	public void tankDrive(SingularityController controller,
			double translationMultiplier, boolean squaredInputs) {
		double left = controller.getLeftY(), right = controller.getRightY();

		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
		}

		m_frontLeftMotor.set(left * translationMultiplier);
		m_rearLeftMotor.set(left * translationMultiplier);
		m_frontRightMotor.set(right * translationMultiplier);
		m_rearLeftMotor.set(right * translationMultiplier);
	}

	/**
	 * 
	 * @param controller1
	 *            Logitech (or XBox controller) to use for left wheel set
	 * @param controller2
	 *            Logitech (or XBox controller) to use for right wheel set
	 * @param translationMultiplier
	 *            How much power to use
	 * @param squaredInputs
	 *            Squared inputs or not
	 */
	public void tankDrive(SingularityController controller1,
			SingularityController controller2, double translationMultiplier,
			boolean squaredInputs) {
		double left = controller1.getY(), right = controller2.getY();

		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
		}

		m_frontLeftMotor.set(left * translationMultiplier);
		m_rearLeftMotor.set(left * translationMultiplier);
		m_frontRightMotor.set(right * translationMultiplier);
		m_rearLeftMotor.set(right * translationMultiplier);
	}

	/**
	 * 
	 * @param controller @SingularityController to use
	 * @param translationMultiplier How much power to go forwards/backwards
	 * @param rotationMultiplier How much power to rotate
	 * @param squaredInputs Squared inputs or not
	 */
	public void arcadeDrive(SingularityController controller,
			double translationMultiplier, double rotationMultiplier,
			boolean squaredInputs) {
		double x = controller.getX(), y = controller.getY();
		
		if (squaredInputs) {
			x *= Math.abs(x);
			y *= Math.abs(y);
		}

		m_frontLeftMotor.set(y * translationMultiplier + x * rotationMultiplier);
		m_rearLeftMotor.set(y * translationMultiplier + x * rotationMultiplier);
		m_frontRightMotor.set(y * translationMultiplier - x * rotationMultiplier);
		m_rearLeftMotor.set(y * translationMultiplier - x * rotationMultiplier);
	}

	/**
	 * Old version of mecanum drive. Only for backup purposes.
	 */
	public void oldDriveMecanumOldIsRedundant(Joystick js,
			double translationMultiplier, double rotationMultiplier) {

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
		m_rearLeftMotor.set(magnitude * Math.sin(direction + Math.PI / 4)
				- js.getTwist() * rotationMultiplier);
	}
}
