package org.usfirst.frc.team5066.robot;

import java.io.IOException;
import java.util.Properties;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;

import org.salinerobotics.library.SingularityController;
import org.salinerobotics.library.SingularityDrive;
import org.salinerobotics.library.SingularityReader;

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

	//create integers for ports, intakes, and camera
	private int backLeft, backRight, frontLeft, frontRight, intakeLeft,
			intakeRight, cameraQuality;
	private String cameraPort;

	//create ultrasonic object
	Ultrasonic us;
	
	//create joystick and joystick button objects
	Joystick js;
	SingularityController xbox;
	JoystickButton jsb2, jsb5, jsb6, jsb7;

	RobotDrive rd;
	Intake intake;

	private SingularityDrive sd;
	private SingularityReader sr;
	private final String propFileURL = "/resources/config.properties", MODES[] = {"Mecanum", "Arcade", "Tank"};
	AnalogTrigger at;

	RangeFinder rf;

	int mode = 0;
	public void robotInit() {
		sr = new SingularityReader();
		try {
			applyProperties(sr.readProperties(propFileURL));
		} catch (IOException e) {
			System.out
					.println("Failed to load properties file, loading defaults");

			// Ports

			frontLeft = 7;
			backLeft = 5;
			frontRight = 6;
			backLeft = 4;
			intakeLeft = 2;
			intakeRight = 5;

			// Initialize input controls
			js = new Joystick(0);
			jsb2 = new JoystickButton(js, 2);
			jsb5 = new JoystickButton(js, 5);
			jsb6 = new JoystickButton(js, 6);
			jsb7 = new JoystickButton(js, 7);
			us = new Ultrasonic(1, 0);
			us.setEnabled(true);
			xbox = new SingularityController(js,SingularityController.XBOX);

			// Initialize the camera properties
			cameraQuality = 50;
			cameraPort = "cam0";

			rf = new RangeFinder(0);
		}
		// TODO delete Vision_2015
		Camera2015 cam = new Camera2015(cameraPort, cameraQuality);
		cam.startSimpleCamera();

		// initialize the intake properties
		intake = new Intake(intakeLeft, intakeRight);
		// Initialize the camera, and start taking video
		// cs = CameraServer.getInstance();
		// cs.setQuality(cameraQuality);
		// cs.startAutomaticCapture(cameraPort);
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
		// SmartDashboard.putNumber("Is enabled",0);
		sd.driveMecanum(js, .35 * (1 - js.getThrottle()),
				.25 * (1 - js.getThrottle()), true);
		// SmartDashboard.putNumber("Ultrasonic Range Inches",
		// us.getRangeInches());
		// SmartDashboard.putNumber("Ultrasonic Range MM", us.getRangeMM());
		// if(us.isEnabled()){
		// SmartDashboard.putNumber("Is enabled",1);

		// }

		/*
		 * if(jsb2.get() == true) { intake.set(0.4); } else intake.set(0.0);
		 */
		
		
		//  Puts the range using the Ultrasonic sensor into the dashboard in inches		
		if(xbox.getStart()) {
			mode = (mode + 1) % 3;
		}
		
		SmartDashboard.putNumber("Z Axis", xbox.getZ());
		SmartDashboard.putNumber("Y Axis", xbox.getY());
		SmartDashboard.putNumber("X Axis", xbox.getX());
		SmartDashboard.putString("Mode", MODES[mode]);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
	}

	/**
	 * Applies values from the properties file that is returned as a Map of
	 * properties
	 * 
	 * @param prop
	 *            - Map of properties
	 */
	private void applyProperties(Properties prop) {
		// TODO: return to default ports
		// Port
		frontLeft = Integer.parseInt(prop.getProperty("talonFrontLeft"));
		backLeft = Integer.parseInt(prop.getProperty("talonBackLeft"));
		frontRight = Integer.parseInt(prop.getProperty("talonFrontRight"));
		backLeft = Integer.parseInt(prop.getProperty("talonBackLeft"));
		intakeLeft = Integer.parseInt(prop.getProperty("intakeLeft"));
		intakeRight = Integer.parseInt(prop.getProperty("intakeRight"));

		// Initialize input controls
		js = new Joystick(0);
		jsb2 = new JoystickButton(js, 2);
		jsb5 = new JoystickButton(js, 5);
		jsb6 = new JoystickButton(js, 6);
		jsb7 = new JoystickButton(js, 7);
		us = new Ultrasonic(1, 0);
		us.setEnabled(true);
		
		//initialize camera
		cameraQuality = Integer.parseInt(prop.getProperty("cameraQuality"));
		cameraPort = prop.getProperty("camID");
	}
}
