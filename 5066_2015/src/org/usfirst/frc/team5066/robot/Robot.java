package org.usfirst.frc.team5066.robot;

import java.io.IOException;
import java.util.Properties;

import org.salinerobotics.library.SingularityDrive;
import org.salinerobotics.library.SingularityReader;
import org.salinerobotics.library.controller.Logitech;
import org.salinerobotics.library.controller.SingularityController;
import org.salinerobotics.library.controller.XBox;

import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
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
	// Create a SingularityReader to read the properties file.
	private SingularityReader sr;
	private final String PROP_FILE_URL = "/config.properties";

	// Create stuff for camera
	private int cameraQuality;
	private String cameraPort;
	private Camera2015 cam;
	// Create integer for ultrasonic sensor
	private int rfPort;

	// Create integers for input controllers and settings
	Joystick movementJoystick, intakeJoystick;
	SingularityController intakeController, movementController;
	XBox xbox;
	Logitech logitech;

	int controlMode, driveMode;
	int LOGITECH_DRIVE = 0, XBOX_DRIVE = 1;

	// Create integers for TalonSR and TalonSRX ports
	private int backLeft, backRight, frontLeft, frontRight, intakeInnerLeft,
			intakeInnerRight, intakeOuterLeft, intakeOuterRight, elevatorPort;

	// Create output (elevator, intakes, and chassis drive) objects
	Elevator elevator;
	Intake intake;
	private SingularityDrive sd;

	// These are variables for changing the drive modes
	// int mode;
	// boolean startWasPressed;
	// final String[] MODES = { "Mecanum", "Arcade", "Tank" };

	// Create movement constants. These edit the max speeds.
	final double TRANSLATION_CONSTANT = 1, ROTATION_CONSTANT = 0.5;

	// Create intake speed constant. This edits the max speed, and is editable
	// in the SmartDashboard
	double intakeMultiplier;

	// Auton mode variables.
	double timerDelay;
	int autonMode;

	CameraServer cs;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 * 
	 * First, we use a SingularityReader to read the properties out of a local
	 * file (local to the robot). If the read fails, we revert to default values
	 * (see try-catch-finally block). Next, we initialize more variables, which
	 * are mostly dependent on the now initialize properties.
	 */
	public void robotInit() {
		sr = new SingularityReader();
		try {
			applyProperties(sr.readProperties(PROP_FILE_URL));
		} catch (IOException e) {
			SmartDashboard.putString("Properties Loaded", "unsuccessfully");
			// Ports

			frontLeft = 7;
			backRight = 4;
			frontRight = 6;
			backLeft = 5;

			// CANTalons
			elevatorPort = 2;
			intakeInnerLeft = 3;
			intakeInnerRight = 4;

			// TalonSRs
			intakeOuterLeft = 3;
			intakeOuterRight = 2;

			// Initialize the camera properties
			cameraQuality = 50;
			cameraPort = "cam0";

			intakeController = xbox;
			movementController = logitech;

			autonMode = 0;

			timerDelay = 0.005;
		} finally {
			SmartDashboard.putString("Properties Loaded", "successfully");
			SmartDashboard.putNumber("Timer Delay", timerDelay);
			try{
				cam = new Camera2015(cameraPort, cameraQuality);
			}
			catch(VisionException e) {
				e.printStackTrace();
			}
		}

		// TODO delete Vision_2015

//		// Initialize the camera, and start taking video
		cs = CameraServer.getInstance();
		cs.setQuality(cameraQuality);
		cs.startAutomaticCapture(cameraPort);
		
		
		// Initialize the user inputs.
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

		// Initialize the robot movements.
		elevator = new Elevator(elevatorPort, rfPort);
		intake = new Intake(intakeOuterLeft, intakeOuterRight, intakeInnerLeft,
				intakeInnerRight);
		sd = new SingularityDrive(frontLeft, backLeft, frontRight, backRight);

		// Here's some more stuff for different drive modes
		// mode = 0;
		// SmartDashboard.putString("Mode", "Mecanum");
		// startWasPressed = false;

		// Don't reapply the properties yet, but keep the option open
		SmartDashboard.putBoolean("Reapply Properties", false);
	}

	/**
	 * This function is called periodically during autonomous
	 * 
	 * We use a
	 */
	public void autonomousPeriodic() {
		switch (autonMode) {
		case 0:
			//Don't put anything in this case!
			break;
		case 1:
			autonMode=0;
			break;
		case 2:
			autonMode=0;
			break;
		default:
			autonMode=0;
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// SmartDashboard.putNumber("Is enabled",0);
		// cam.processImages();

		// Keep this if you want to live. Also if you want to have three
		// drive modes.
		/*
		 * if (movementController.getStart()) { if (!startWasPressed) { mode =
		 * (mode + 1) % 3; SmartDashboard.putString("Mode", MODES[mode]); }
		 * startWasPressed = true; } else { startWasPressed = false; }
		 */
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
		elevator.getRangeInches();

		SmartDashboard.putNumber("X Axis", movementController.getX());
		SmartDashboard.putNumber("Y Axis", movementController.getY());
		SmartDashboard.putNumber("Z Axis", movementController.getZ());

		// Avoid sending commands to the robot too quickly
		Timer.delay(timerDelay);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		checkToReapplyProperties();
	}

	private void checkToReapplyProperties() {

		if (SmartDashboard.getBoolean("Reapply Properties")) {
			try {
				applyProperties(sr.readProperties(PROP_FILE_URL));
				SmartDashboard.putString("Properties Loaded", "successfully");
			} catch (IOException e) {
				SmartDashboard.putString("Properties Loaded", "unsuccessfully");
			} finally {
				SmartDashboard.putBoolean("Reapply Properties", false);
			}

		}

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

		// TalonSR and TalonSRX ports
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
		elevatorPort = Integer.parseInt(prop.getProperty("elevator"));

		// Range finder port
		rfPort = Integer.parseInt(prop.getProperty("rfPort"));

		// Initialize camera
		cameraQuality = Integer.parseInt(prop.getProperty("cameraQuality"));
		cameraPort = prop.getProperty("cameraID");

		// Auton properties
		autonMode = Integer.parseInt(prop.getProperty("autonMode"));

		// Teleop control properties
		driveMode = Integer.parseInt(prop.getProperty("driveMode"));

		if (driveMode == LOGITECH_DRIVE) {
			intakeController = xbox;
			movementController = logitech;
		} else {
			intakeController = logitech;
			movementController = xbox;
		}

		// Timer delay
		//timerDelay = Double.parseDouble(prop.getProperty("timerDelay"));
		//SmartDashboard.putString(timerDelay, prop.getProperty("timerDelay"));

		SmartDashboard.putString("Properties Loaded", "successfully");
	}
}
