package org.usfirst.frc.team5066.robot;

import java.io.IOException;
import java.util.Properties;
import java.io.PrintWriter;

import org.salinerobotics.library.SingularityDrive;
import org.salinerobotics.library.SingularityReader;
import org.salinerobotics.library.controller.Logitech;
import org.salinerobotics.library.controller.SingularityController;
import org.salinerobotics.library.controller.XBox;

import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.CameraServer;
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
	private int backLeft, backRight, frontLeft, frontRight, intakeInnerLeft,
			intakeInnerRight, intakeOuterLeft, intakeOuterRight;
	private int cameraQuality;
	private String cameraPort1, cameraPort2;

	// create ultrasonic object
	Ultrasonic us;

	// create input controllers
	Joystick movementJoystick, intakeJoystick;
	SingularityController intakeController;
	SingularityController movementController;
	XBox xbox;
	Logitech logitech;

	int controlMode, driveMode;
	int XBOX_DRIVE = 1, LOGITECH_DRIVE = 0;

	RobotDrive rd;
	Intake intake;
	Camera2015 cam1, cam2;
	CameraServer cs;

	private SingularityDrive sd;
	private SingularityReader sr;
	private final String PROP_FILE_URL = "/config.properties";
	AnalogTrigger at;

	RangeFinder rf;
	int mode;
	boolean startWasPressed;

	final double TRANSLATION_CONSTANT = 1, ROTATION_CONSTANT = 0.5;
	final String[] MODES = { "Mecanum", "Arcade", "Tank" };

	Elevator elevator;

	double intakeMultiplier;

	PrintWriter pr;

	int autonMode;

	// Robot Init
	// ==========================================================================================
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
			backRight = 4;
			frontRight = 6;
			backLeft = 5;
			intakeInnerLeft = 2;
			intakeInnerRight = 3;
			intakeOuterLeft = 3; // CANTalon
			intakeOuterRight = 4; // CANTalon

			us = new Ultrasonic(1, 0);
			us.setEnabled(true);

			// Initialize the camera properties
			cameraQuality = 50;
			cameraPort1 = "cam0";
			cameraPort2 = "cam1";

			rf = new RangeFinder(0);

			intakeController = xbox;
			movementController = logitech;

			autonMode = 0;
		}
		// TODO delete Vision_2015
		// cam1 = new Camera2015(cameraPort1, cameraQuality);
		// cam1.initCameraForProcessing();
		/*
		 * for cam 2 cam2 = new Camera2015(cameraPort2, cameraQuality);
		 * cam2.initCameraForProcessing();
		 */
		// initialize the intake properties
		intake = new Intake(intakeOuterLeft, intakeOuterRight, intakeInnerLeft,
				intakeInnerRight);
		// Initialize the camera, and start taking video
		cs = CameraServer.getInstance();
		cs.setQuality(cameraQuality);
		cs.startAutomaticCapture(cameraPort1);

		sd = new SingularityDrive(frontLeft, backLeft, frontRight, backRight);

		movementJoystick = new Joystick(0);
		intakeJoystick = new Joystick(1);

		if (driveMode == LOGITECH_DRIVE) {
			movementController = new Logitech(movementJoystick, 0.04);
			intakeController = new XBox(intakeJoystick, 0.15);
		} else {
			// SmartDashboard.put("Test", "XBox Time");
			intakeController = new Logitech(movementJoystick, 0.04);
			movementController = new XBox(intakeJoystick, 0.15);
		}

		mode = 0;
		SmartDashboard.putString("Mode", "Mecanum");
		startWasPressed = false;

		elevator = new Elevator(2);

		try {
			pr = new PrintWriter("inputRead.txt", "UTF-8");
		} catch (Exception e) {
			System.exit(1);
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		switch (autonMode) {
		case 1:

			break;
		case 2:
			break;
		default:
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// SmartDashboard.putNumber("Is enabled",0);
		// cam.processImages();

		if (movementController.getStart()) {
			if (!startWasPressed) {
				mode = (mode + 1) % 3;
				SmartDashboard.putString("Mode", MODES[mode]);
			}
			startWasPressed = true;
		} else {
			startWasPressed = false;
		}

		// Keep this if you want to live. Also if you want to have three
		// (sqrt(!4)) drive modes.
		/*
		 * switch (mode) { case 0: sd.driveMecanum(movementController,
		 * TRANSLATION_CONSTANT, ROTATION_CONSTANT, false); break; case 1:
		 * sd.arcadeDrive(movementController, TRANSLATION_CONSTANT,
		 * ROTATION_CONSTANT, false); break; case 2:
		 * sd.tankDrive(intakeController, TRANSLATION_CONSTANT, false); break;
		 * default: break; }
		 */

		sd.driveMecanum(movementController, TRANSLATION_CONSTANT,
				ROTATION_CONSTANT, false);

		intakeMultiplier = SmartDashboard.getNumber("Intake Speed");
		intake.setOuter(intakeController.getOuterIntake() * intakeMultiplier);
		intake.setInner(intakeController.getInnerIntake() * intakeMultiplier);

		elevator.set(intakeController.getElevator());

		SmartDashboard.putNumber("X Axis", movementController.getX());
		SmartDashboard.putNumber("Y Axis", movementController.getY());
		SmartDashboard.putNumber("Z Axis", movementController.getZ());

		SmartDashboard.putNumber("Ultrasonic test", rf.findRangeInches());

		pr.println(movementController.getX() + ", " + movementController.getY()
				+ ", " + movementController.getZ() + ", ");
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
		intakeInnerLeft = Integer.parseInt(prop.getProperty("intakeInnerLeft"));
		intakeInnerRight = Integer.parseInt(prop
				.getProperty("intakeInnerRight"));
		intakeOuterLeft = Integer.parseInt(prop.getProperty("intakeOuterLeft"));
		intakeOuterRight = Integer.parseInt(prop
				.getProperty("intakeOuterRight"));

		us = new Ultrasonic(1, 0);
		us.setEnabled(true);

		// initialize camera
		cameraQuality = Integer.parseInt(prop.getProperty("cameraQuality"));
		cameraPort1 = prop.getProperty("cameraID");
		// for cam 2
		cameraPort2 = prop.getProperty("cameraID2");

		driveMode = Integer.parseInt(prop.getProperty("driveMode"));

		// auton properties
		autonMode = Integer.parseInt(prop.getProperty("autonMode"));

		if (driveMode == LOGITECH_DRIVE) {
			intakeController = xbox;
			movementController = logitech;
		} else {
			intakeController = logitech;
			movementController = xbox;
		}

		rf = new RangeFinder(Integer.parseInt(prop.getProperty("rangeFinder")));

		SmartDashboard.putString("Properties Loaded", "successfully");
	}
}
