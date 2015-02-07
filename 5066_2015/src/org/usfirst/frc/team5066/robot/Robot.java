package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	final double MULTIPLIER = .5;

	Joystick js;
	RobotDrive rd;
	Talon backLeft, backRight, frontLeft, frontRight;

	public void robotInit() {
		js = new Joystick(0);

		backLeft = new Talon(2);
		backRight = new Talon(5);
		frontLeft = new Talon(6);
		frontRight = new Talon(7);

		rd = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		rd.mecanumDrive_Cartesian(js.getX() * MULTIPLIER, js.getY()
				* MULTIPLIER, js.getZ() * MULTIPLIER, 0);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

	public static void driveMecanum(Joystick js, Talon frontRight,
			Talon backRight, Talon frontLeft, Talon backLeft, double multiplier) {
		double magnitude = Math.sqrt(js.getX() * js.getX() + js.getY()
				+ js.getY()) * multiplier,
				direction = Math.atan(js.getY() / js.getX())
				+ (js.getX() > 0 ? Math.PI : 0);

		frontLeft.set(magnitude * Math.sin(direction + Math.PI / 4));
		backLeft.set(magnitude * Math.cos(direction + Math.PI / 4));
		frontRight.set(magnitude * Math.cos(direction + Math.PI / 4));
		backRight.set(magnitude * Math.sin(direction   + Math.PI / 4));
	}
}
