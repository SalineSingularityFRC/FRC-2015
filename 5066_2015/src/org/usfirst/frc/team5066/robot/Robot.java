package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;

import org.salinerobotics.library.SingularityDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * In other words: screw with the name, you screw with the manifest
 */
public class Robot extends IterativeRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	final double MULTIPLIER = 1;
	
	int backLeft, backRight, frontLeft, frontRight, intakeLeft, intakeRight;
	
	Ultrasonic us;
	Joystick js;
	JoystickButton jsb2, jsb5, jsb6, jsb7;

	RobotDrive rd;
	Intake intake;
	private CameraServer cs;

	SingularityDrive sd;

	public void robotInit() {
		
		//Ports
		frontLeft = 7;
		backLeft = 5;
		frontRight = 6;
		backLeft = 2;
		intakeLeft = 0;
		intakeRight = 1;
		
		// Initialize input controls
		js = new Joystick(0);
		jsb2 = new JoystickButton(js, 2);
		jsb5 = new JoystickButton(js, 5);
		jsb6 = new JoystickButton(js, 6);
		jsb7 = new JoystickButton(js, 7);
		us = new Ultrasonic(1,0);
		us.setEnabled(true);
		
		intake = new Intake(intakeLeft, intakeRight);
		
		// Initialize the camera, and start taking video
		cs = CameraServer.getInstance();
		cs.setQuality(100);
		cs.startAutomaticCapture("cam0");

		sd = new SingularityDrive(frontLeft, backLeft, frontRight, backRight);
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
		SmartDashboard.putNumber("Is enabled",0);
		sd.driveMecanum(js, .35 * (1 - js.getThrottle()), .25 * (1 - js.getThrottle()));
		SmartDashboard.putNumber("Ultrasonic Range Inches", us.getRangeInches());
		SmartDashboard.putNumber("Ultrasonic Range MM", us.getRangeMM());
		if(us.isEnabled()){
			SmartDashboard.putNumber("Is enabled",1);
			
		}
		
		if(jsb2.get() == true) {
			intake.set(0.4);
		}
		else
			intake.set(0.0);
		
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		sd.tester(2, jsb2.get());
		sd.tester(5, jsb5.get());
		sd.tester(6, jsb6.get());
		sd.tester(7, jsb7.get());
	}
}
