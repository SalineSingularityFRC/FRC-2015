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
	Joystick joystick0, joystick1;
	SingularityController intakeController, movementController;
	/*
	 * What is this even used for???? XBox xbox; Logitech logitech;
	 */

	int controlMode, driveMode;
	int LOGITECH_DRIVE = 0, XBOX_DRIVE = 1, DUAL_LOGITECH_DRIVE = 2,
			XBOX_ONE_PERSON_DRIVE = 3;

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
	static double horizontalConstant, verticalConstant, rotationConstant,
			elevatorConstant;

	// Create intake speed constant. This edits the max speed, and is editable
	// in the SmartDashboard
	double intakeMultiplier;

	// Auton mode variables.
	double timerDelay;
	int autonMode;

	CameraServer cs;

	// Recording Stuff
	Recorder movementRecorder, elevatorRecorder;
	Reader reader;
	Player movementPlayer, elevatorPlayer;
	Writer myWriter;

	boolean recordingEnabled, robotMotion, play, recordingNow;
	String recordingsURL, fileType;

	// Used to store initial time from enable for playback of motion
	long initialTime;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 * 
	 * First, we use a SingularityReader to read the properties out of a local
	 * file (local to the robot). If the read fails, we revert to default values
	 * (see try-catch-finally block). Next, we initialize more variables, which
	 * are mostly dependent on the now initialize properties.
	 */

	// ROBOT INIT
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
			cameraPort = "cam0";

			/*
			 * intakeController = xbox; movementController = logitech;
			 */

			autonMode = 0;

			robotMotion = true;

			timerDelay = 0.005;

			recordingEnabled = false;
			recordingNow = false;
			play = false;

			horizontalConstant = 1;
			verticalConstant = 1;
			rotationConstant = 1;
			elevatorConstant = 1;
		} finally {
			SmartDashboard.putString("Properties Loaded", "successfully");
			SmartDashboard.putNumber("Timer Delay", timerDelay);
			try {
				cam = new Camera2015(cameraPort, cameraQuality);
			} catch (VisionException e) {
				e.printStackTrace();
			}

		}

		// TODO FiX tHiS fROm CrASHING WiTh NiViSiON ExCEPtIOn
		// Initialize the camera, and start taking video

		cs = CameraServer.getInstance();
		cs.setQuality(cameraQuality);
		cs.startAutomaticCapture(cameraPort);

		// TODO Add functionality for switching joysticks without rebooring
		// robot
		// Initialize the user inputs.
		cameraQuality = 50;
		joystick0 = new Joystick(0);
		joystick1 = new Joystick(1);

		if (driveMode == LOGITECH_DRIVE) {
			SmartDashboard.putString("Initializing Controllers",
					"Movement: Logitech Joystick; Intake: Xbox");
			movementController = new Logitech(joystick0, 0.04);
			intakeController = new XBox(joystick1, 0.15);
		} else if (driveMode == DUAL_LOGITECH_DRIVE) {
			SmartDashboard.putString("Initializing Controllers",
					"Dual Logitech Joysticks");
			intakeController = new Logitech(joystick1, 0.04);
			movementController = new Logitech(joystick0, 0.04);
		} else if (driveMode == XBOX_DRIVE) {
			SmartDashboard.putString("Test", "XBox Time");
			SmartDashboard.putString("Initializing Controllers",
					"Movement: Xbox; Intake: Logitech Joystick");
			intakeController = new Logitech(joystick0, 0.04);
			movementController = new XBox(joystick1, 0.15);
		} else {
			SmartDashboard.putString("Initializing Controllers",
					"Only XBOX drive");
			intakeController = new XBox(joystick1, 0.04);
			movementController = intakeController;
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

		// Recording stuff
		/*
		 * try { reader = new Reader("/test"); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		if (recordingEnabled) {
			myWriter = new Writer(recordingsURL, fileType);
			movementRecorder = new Recorder(fileType, "mo");
			elevatorRecorder = new Recorder(fileType, "el");
		}
		SmartDashboard.putString("Line Number", "0");

		SmartDashboard.putString("Drive Type", "Mecanum");
	}

	public void autonomousInit() {
		if (play) {
			// movementPlayer = new Player(recordingsURL, fileType);
			reader = new Reader(recordingsURL);
			reader.readJSON("recording");

			movementPlayer = new Player(reader.getMotionCommands(),
					reader.getMotionTimes(), reader.getElevatorCommands(),
					reader.getElevatorTimes(), sd, elevator);
			movementPlayer.play();

			initialTime = System.currentTimeMillis();
			runCommands(movementPlayer);
		}
	}

	/**
	 * This function is called periodically during autonomous
	 * 
	 * We use a
	 */
	public void autonomousPeriodic() {
		switch (autonMode) {
		// After every case that you don't want to repeat put autonMode=0;
		case 0:
			// Don't put anything in this case!
			break;
		case 1:
			autonMode = 0;
			break;
		case 2:
			autonMode = 0;
			break;
		default:
			autonMode = 0;
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopInit() {
		if (recordingEnabled) {
			myWriter.initializeOutput();
			movementRecorder.initializeRecorder(myWriter.getFirstTime());
			elevatorRecorder.initializeRecorder(myWriter.getFirstTime());
			recordingNow = true;
		}
	}

	public void runCommands(Player player) {
		final int movX = 1, movY = 2, movZ = 3, movTime = 4;

		// SmartDashboard.putNumber("Commands Test", player.get(0).length);
		long firstTime = Long.parseLong(player.get(0)[movTime]), nextTime;
		String motion[];

		for (int i = 0; i < player.getLines() - 2; i++) {
			nextTime = Long.parseLong(player.get(i + 1)[movTime]);

			motion = player.get(i);
			SmartDashboard.putString("Auton test", player.get(i)[movTime]);
			while (System.currentTimeMillis() - initialTime < nextTime
					- firstTime) {
				// Motion command magnitude (-1-0-+1): x = motion[0]; y=
				// motion[1]; z = motion[2]
				sd.driveMecanum(Double.parseDouble(motion[movX]),
						Double.parseDouble(motion[movY]),
						Double.parseDouble(motion[movZ]), horizontalConstant,
						verticalConstant, rotationConstant, true);
			}
		}
	}

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
		// if(movementController.getStart()) {
		// sd.driveMecanum(0.5, 0, 0, 1, 1, false);
		// }

		if (robotMotion) {
			if (SmartDashboard.getString("Drive Type").equals("Tank")) {
				sd.metankum(movementController.getLeftY(),
						movementController.getRightY(),
						movementController.getRightX(), verticalConstant, true);
			} else {
				sd.driveMecanum(movementController.getX(),
						movementController.getY(), movementController.getZ(),
						horizontalConstant, verticalConstant, rotationConstant,
						true);
			}
			// TODO make a squareInput method to avoid problems
			elevator.set(intakeController.getElevator()
					* Math.abs(intakeController.getElevator())
					* elevatorConstant);
		}

		SmartDashboard.putNumber("X Axis", movementController.getX());
		SmartDashboard.putNumber("Y Axis", movementController.getY());
		SmartDashboard.putNumber("Z Axis", movementController.getZ());

		if (recordingEnabled) {
			movementRecorder.appendOutput(new String[] {
					Double.toString(movementController.getX()),
					Double.toString(movementController.getY()),
					Double.toString(movementController.getZ()) });
			elevatorRecorder.appendOutput(new String[] { Double
					.toString(intakeController.getElevator()) });
		}

		// Avoid sending commands to the robot too quickly
		Timer.delay(timerDelay);

	}

	public void disabledInit() {
		if (recordingEnabled && recordingNow) {
			movementRecorder.finalize();
			elevatorRecorder.finalize();

			myWriter.writeQueue("mo", movementRecorder.getQueue());
			myWriter.writeQueue("el", elevatorRecorder.getQueue());
			myWriter.finalizeOutput();

			movementRecorder.clearQueue();
			elevatorRecorder.clearQueue();
			recordingNow = false;
		}
	}

	public void testInit() {
		if (play) {
			movementPlayer = new Player(recordingsURL, fileType);
			movementPlayer.dumpRecording();
			initialTime = System.currentTimeMillis();
			runCommands(movementPlayer);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		checkToReapplyProperties();
		if (play) {
			initialTime = System.currentTimeMillis();
			runCommands(movementPlayer);
		}
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

		// Recordings
		recordingEnabled = Boolean.parseBoolean(prop.getProperty("record"));

		fileType = prop.getProperty("fileType");
		recordingsURL = prop.getProperty("recordingsURL") + "." + fileType;
		SmartDashboard.putString("URL", recordingsURL);

		robotMotion = Boolean.parseBoolean(prop.getProperty("move"));

		play = Boolean.parseBoolean(prop.getProperty("play"));
		SmartDashboard.putBoolean("Step 1",
				Boolean.parseBoolean(prop.getProperty("dumpRecording")));

		horizontalConstant = Double.parseDouble(prop
				.getProperty("horizontalConstant"));
		verticalConstant = Double.parseDouble(prop
				.getProperty("verticalConstant"));
		rotationConstant = Double.parseDouble(prop
				.getProperty("rotationConstant"));
		elevatorConstant = Double.parseDouble(prop
				.getProperty("elevatorConstant"));

		// movementPlayer.setDumpRecording(Boolean.parseBoolean(prop
		// .getProperty("dumpRecording")));

		/*
		 * if (driveMode == LOGITECH_DRIVE) { intakeController = xbox;
		 * movementController = logitech; } else if (driveMode == XBOX_DRIVE){
		 * intakeController = logitech; movementController = xbox; } else {
		 * intakeController = logitech;
		 * 
		 * }
		 */

		// Timer delay
		// timerDelay = Double.parseDouble(prop.getProperty("timerDelay"));
		// SmartDashboard.putString(timerDelay, prop.getProperty("timerDelay"));

		SmartDashboard.putString("Properties Loaded", "successfully");
	}
}
