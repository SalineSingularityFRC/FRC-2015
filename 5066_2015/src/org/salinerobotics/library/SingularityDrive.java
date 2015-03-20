package org.salinerobotics.library;

//Java Docs: http://first.wpi.edu/FRC/roborio/release/docs/java/
import org.salinerobotics.library.controller.SingularityController;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import java.text.SimpleDateFormat;
import java.util.Calendar; //For timing while loops

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
	 * @param x
	 *            Translation magnitude in x direction
	 * @param y
	 *            Translation magnitude in y direction
	 * @param z
	 *            Rotation magnitude
	 * @param translationMultiplier
	 *            How quickly to translate (0 - 1)
	 * @param rotationMultiplier
	 *            How quickly to rotate (0 - 1)
	 * @param squaredInputs
	 *            Uses squared inputs if it's true
	 * @return Returns the actual translation and rotation values
	 */
	public void driveMecanum(double x, double y, double z,
			double translationMultiplier, double rotationMultiplier,
			boolean squaredInputs) {

		double translationMagnitude, direction, maximum, rotationMagnitude;

		// If squaredInputs square the inputs
		if (squaredInputs) {
			x *= Math.abs(x);
			y *= Math.abs(y);
			z *= Math.abs(z);
		}

		// Find magnitudes and direction using pythagorean theorem and atan2
		translationMagnitude = Math.sqrt((x * x) + (y * y))
				* translationMultiplier;
		rotationMagnitude = z * rotationMultiplier;
		direction = Math.PI / 4 + Math.atan2(y, x);

		// Account for too high of inputs (cannot have a motor go at 2.0 speed)
		maximum = Math
				.max(Math.max(Math.abs(Math.sin(direction)),
						Math.abs(Math.cos(direction)))
						* translationMagnitude + Math.abs(rotationMagnitude), 1);

		// Use formulas to set wheel speeds. See the GitHub wiki for more
		// information
		m_frontRightMotor
				.set((translationMagnitude * Math.cos(direction) + rotationMagnitude)
						/ maximum);
		m_rearRightMotor
				.set((translationMagnitude * -Math.sin(direction) + rotationMagnitude)
						/ maximum);
		m_frontLeftMotor
				.set((translationMagnitude * Math.sin(direction) + rotationMagnitude)
						/ maximum);
		m_rearLeftMotor
				.set((translationMagnitude * -Math.cos(direction) + rotationMagnitude)
						/ maximum);
	}

	/**
	 * Translate at a certain magnitude and direction
	 * 
	 * @param magnitude
	 *            Magnitude to translate at
	 * @param direction
	 *            Direction to travel. Use radians relative to positive x-axis
	 */
	public void translate(double magnitude, double direction) {
		driveMecanum(magnitude * Math.cos(direction),
				magnitude * Math.sin(direction), 0, 0.75, 0, false);
	}

	/**
	 * Rotate at a given speed. Use value between -1.0 and 1.0
	 * 
	 * @param magnitude
	 */
	public void rotate(double magnitude) {
		driveMecanum(0, 0, magnitude, 0, 0.75, false);
	}

	/**
	 * Tank drive using one controller with two joysticks (i.e. use an XBox
	 * controller if you want to do anything useful)
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
		double left = -controller.getOuterIntake(), right = controller
				.getInnerIntake();

		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
		}

		m_frontLeftMotor.set(left * translationMultiplier);
		m_rearLeftMotor.set(left * translationMultiplier);
		m_frontRightMotor.set(right * translationMultiplier);
		m_rearRightMotor.set(right * translationMultiplier);
	}

	/**
	 * Tank drive using two joystick inputs (can be XBox controllers if you want
	 * to do it the stupid way...)
	 * 
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
		double left = -controller1.getY(), right = controller2.getY();

		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
		}

		m_frontLeftMotor.set(left * translationMultiplier);
		m_rearLeftMotor.set(left * translationMultiplier);
		m_frontRightMotor.set(right * translationMultiplier);
		m_rearRightMotor.set(right * translationMultiplier);
	}

	/**
	 * Arcade drive using a single controller. Simple and easy....
	 * 
	 * @param controller
	 *            SingularityController to use
	 * @param translationMultiplier
	 *            How much power to go forwards/backwards
	 * @param rotationMultiplier
	 *            How much power to rotate
	 * @param squaredInputs
	 *            Squared inputs or not
	 */
	public void arcadeDrive(SingularityController controller,
			double translationMultiplier, double rotationMultiplier,
			boolean squaredInputs) {
		double x = controller.getX(), y = controller.getY();

		if (squaredInputs) {
			x *= Math.abs(x);
			y *= Math.abs(y);
		}

		m_frontLeftMotor.set(-y * translationMultiplier - x
				* rotationMultiplier);
		m_rearLeftMotor
				.set(-y * translationMultiplier - x * rotationMultiplier);
		m_frontRightMotor.set(y * translationMultiplier - x
				* rotationMultiplier);
		m_rearRightMotor
				.set(y * translationMultiplier - x * rotationMultiplier);
	}

	public void metankum(double left, double right, double strafe,
			double translationMultiplier, double rotationMultiplier,
			boolean squaredInputs) {
		
	}

	public void forward() {
		m_frontRightMotor.set(.5);
		m_rearRightMotor.set(.5);
		m_frontLeftMotor.set(-.5);
		m_rearLeftMotor.set(-.5);
	}

	public void backward() {
		m_frontRightMotor.set(-.5);
		m_rearRightMotor.set(-.5);
		m_frontLeftMotor.set(.5);
		m_rearLeftMotor.set(.5);
	}

	public void stop() {
		m_frontRightMotor.set(0);
		m_rearRightMotor.set(0);
		m_frontLeftMotor.set(0);
		m_rearLeftMotor.set(0);
	}

	/**
	 * Rotate clockwise or counterclockwise
	 * 
	 * @param direction
	 *            Direction to turn in
	 * @return sucsessfulness
	 */
	public boolean turn(String direction) {
		double turnVar = 0;
		if (direction.equals("clockwise")) {
			turnVar = -.5;
		} else if (direction.equals("counterclockwise")) {
			turnVar = .5;
		} else {
			return false;
		}
		m_frontRightMotor.set(turnVar);
		m_rearRightMotor.set(turnVar);
		m_frontLeftMotor.set(turnVar);
		m_rearLeftMotor.set(turnVar);
		return true;
	}

	/**
	 * Used for general movement
	 * 
	 * @param milSeconds
	 *            How long to more in milliseconds
	 * @param direction
	 *            Direction to turn
	 * @param stopAfter
	 *            Whether or not to stop the robot after command has executed
	 * @return
	 */
	public boolean move(int milSeconds, String direction, boolean stopAfter) {
		boolean result = true;
		if (milSeconds > 14999) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat mil = new SimpleDateFormat("ssmm");
		boolean cont = true;
		int start = 0;
		while (cont) {
			start = Integer.parseInt(mil.format(cal.getTime()));
			if (direction.equals("clockwise")
					|| direction.equals("counterclockwise")) {
				result = turn(direction);
			} else if (direction.equals("forward")) {
				forward();
			} else if (direction.equals("back")
					|| direction.equals("backwards")) {
				backward();
			} else {
				return false;
			}
			milSeconds = Integer.parseInt(mil.format(cal.getTime())) - start;
			if (Integer.parseInt(mil.format(cal.getTime())) - start < 0) {
				result = false;
				cont = false;
			}
			if (milSeconds <= 0) {
				cont = false;
			}
		}
		if (stopAfter == true) {
			stop();
		}
		return result;
	}
}
