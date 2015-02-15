package org.usfirst.frc.team5066.robot;

import java.io.IOException;
import java.util.Properties;

import org.salinerobotics.library.SingularityDrive;
import org.salinerobotics.library.SingularityReader;
import org.salinerobotics.library.controller.Logitech;
import org.salinerobotics.library.controller.SingularityController;
import org.salinerobotics.library.controller.XBox;

import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	// create integers for ports, intakes, and camera
	private int backLeft, backRight, frontLeft, frontRight, intakeLeft,
			intakeRight, cameraQuality;
	private String cameraPort1, cameraPort2;

	// create ultrasonic object
	Ultrasonic us;

	// create joystick and joystick button objects
	Joystick js;
	SingularityController xbox;

	RobotDrive rd;
	Intake intake;
	Camera2015 cam1, cam2;

	private SingularityDrive sd;
	private SingularityReader sr;
	private final String PROP_FILE_URL = "/config.properties";
	AnalogTrigger at;

	RangeFinder rf;
	XBox movementController;
	int mode;
	boolean startWasPressed;

	final double TRANSLATION_CONSTANT = .35, ROTATION_CONSTANT = .25;
	final String[] MODES = { "Mecanum", "Arcade", "Tank" };

	public void robotInit() {
		sr = new SingularityReader();
		try {
			applyProperties(sr.readProperties(PROP_FILE_URL));
		} catch (IOException e) {
			SmartDashboard.putString("Properties Loaded", "unsuccessfully");
			System.out
					.println("Failed to load properties file, loading defaults");

			// Ports

			frontLeft = 7;
			backLeft = 5;
			frontRight = 6;
			backLeft = 4;
			intakeLeft = 2;
			intakeRight = 3;

			// Initialize input controls
			js = new Joystick(0);

			us = new Ultrasonic(1, 0);
			us.setEnabled(true);

			// Initialize the camera properties
			cameraQuality = 50;
			cameraPort1 = "cam0";
			cameraPort2 = "cam1";

			rf = new RangeFinder(0);
		}
		// TODO delete Vision_2015
		// cam1 = new Camera2015(cameraPort1, cameraQuality);
		// cam1.initCameraForProcessing();
		/*
		 * for cam 2 cam2 = new Camera2015(cameraPort2, cameraQuality);
		 * cam2.initCameraForProcessing();
		 */
		// initialize the intake properties
		intake = new Intake(intakeLeft, intakeRight);
		// Initialize the camera, and start taking video
		// cs = CameraServer.getInstance();
		// cs.setQuality(cameraQuality);
		// cs.startAutomaticCapture(cameraPort);
		sd = new SingularityDrive(frontLeft, backLeft, frontRight, backRight);

		movementController = new XBox(js);

		mode = 0;
		SmartDashboard.putString("Mode", "Mecanum");
		startWasPressed = false;
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
		// cam.processImages();
		// SmartDashboard.putNumber("Ultrasonic Range Inches",
		// us.getRangeInches());
		// SmartDashboard.putNumber("Ultrasonic Range MM", us.getRangeMM());
		// if(us.isEnabled()){
		// SmartDashboard.putNumber("Is enabled",1);

		// }

		/*
		 * if(jsb2.get() == true) { intake.set(0.4); } else intake.set(0.0);
		 */

		if (movementController.getStart()) {
			if (!startWasPressed) {
				mode = (mode + 1) % 3;
				SmartDashboard.putString("Mode", MODES[mode]);
			}
			startWasPressed = true;
		} else {
			startWasPressed = false;
		}

		switch (mode) {
		case 0:
			sd.driveMecanum(movementController, TRANSLATION_CONSTANT, ROTATION_CONSTANT,
					false);
			break;
		case 1:
			sd.arcadeDrive(movementController, TRANSLATION_CONSTANT, ROTATION_CONSTANT, false);
			break;
		case 2:
			sd.tankDrive(movementController, TRANSLATION_CONSTANT, false);
			break;
		default:
			break;
		}

		SmartDashboard.putNumber("Z Axis", movementController.getZ());
		SmartDashboard.putNumber("Y Axis", movementController.getLeftY());
		SmartDashboard.putNumber("X Axis", movementController.getLeftX());
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
		backRight = Integer.parseInt(prop.getProperty("talonBackRight"));
		intakeLeft = Integer.parseInt(prop.getProperty("intakeLeft"));
		intakeRight = Integer.parseInt(prop.getProperty("intakeRight"));

		// Initialize input controls
		js = new Joystick(0);
		us = new Ultrasonic(1, 0);
		us.setEnabled(true);

		// initialize camera
		cameraQuality = Integer.parseInt(prop.getProperty("cameraQuality"));
		cameraPort1 = prop.getProperty("camID");
		// for cam 2
		cameraPort2 = prop.getProperty("camID2");
		SmartDashboard.putString("Properties Loaded", "successfully");
	}
}
