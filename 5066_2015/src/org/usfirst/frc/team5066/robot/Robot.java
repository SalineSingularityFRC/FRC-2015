package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import org.salinerobotics.library.SingularityDrive;

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

	final double MULTIPLIER = 1;

	Joystick js;

	RobotDrive rd;
	Talon backLeft, backRight, frontLeft, frontRight;
	private CameraServer cs;

	SingularityDrive sd;

	public void robotInit() {
		// Initialize input controls
		js = new Joystick(0);
		
		// Initialize the camera, and start taking video
		cs = CameraServer.getInstance();
		cs.setQuality(50);
		cs.startAutomaticCapture("cam0");
		
		// Initialize TalonSRs by channel numbers.
		/*
		 * backLeft = new Talon(2); backRight = new Talon(5); frontLeft = new
		 * Talon(6); frontRight = new Talon(7);
		 */
		sd = new SingularityDrive(7, 5, 6, 2);
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
		sd.driveMecanum(js, .7, .5);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}
}
