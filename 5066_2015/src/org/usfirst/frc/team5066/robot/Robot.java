
package org.usfirst.frc.team5066.robot;

import java.io.IOException;
import java.util.Properties;

import org.salinerobotics.library.SingularityDrive;
import org.salinerobotics.library.SingularityReader;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	//ports for each motor. To be replaced by property file values
	final int LEFT_MOTOR_PORT = 0;
	final int RIGHT_MOTOR_PORT = 1;
	final int DRIVE_STICK_PORT = 0;
	
	//talion objects
	private int talionFrontRight =1 ; 
	private int talionFrontLeft  =2 ;
	// main dirve objects
	private int mainDriveRight   =7 ;
	private int mainDriveLeft    =8 ;
	
	
	
	
	
	//joystick objects
	private int thumb =0 ;
    private int top1 = 1 ;
    private int top2 = 2 ;
    private int top3 = 3 ;
    private int bouttom1= 4 ;
    private int bouttom2 = 5 ;
    private int bouttom3 = 6 ;
    private int triger=7 ;
    private int left1=8 ;
    private int left2=9 ;
    private int right1=10 ;
    private int right2=11 ;
   
	
	
	
	
	//values read from properties file
	int testA;
	int testB;
	int testC;
	int testD;
	
	boolean squaredInputs;
	boolean reInitSmartDashboard;
	
	double sensitivity;
	//object initializations
	BuiltInAccelerometer accel;
	Joystick drivestick;
	SingularityDrive drive;
	SmartDashboard dash;
	Properties prop;
	
	
    public void robotInit() {
    	drivestick = new Joystick(0);
    	drive = new SingularityDrive(0,1);
    	accel = new BuiltInAccelerometer(Range.k4G);
    	initSmartDashboard();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopInit() {
    	readProperties();
    }
    
    public void teleopPeriodic() {
        updateSmartDashboard();
        drive.arcadeDrive(drivestick, squaredInputs, sensitivity);
    }
    
    public void testInit() {
    	readProperties();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        updateSmartDashboard();
    }
    
    //Below here are methods we made ourselves
    
    public void initSmartDashboard() {
    	SmartDashboard.putNumber("Accelerometer X", accel.getX());
    	SmartDashboard.putNumber("Accelerometer Y", accel.getY());
    	SmartDashboard.putNumber("Accelerometer Z", accel.getZ());
    	SmartDashboard.putNumber("Sensitivity", 0.8);
    	SmartDashboard.putBoolean("Squared Inputs", true);
    	SmartDashboard.putBoolean("Re-initialize SmartDashboard", false);
    }
    
    public void updateSmartDashboard() {
    	
    	SmartDashboard.putNumber("Accelerometer X", accel.getX());
    	SmartDashboard.putNumber("Accelerometer Y", accel.getY());
    	SmartDashboard.putNumber("Accelerometer Z", accel.getZ());
    	sensitivity = SmartDashboard.getNumber("Sensitivity");
    	squaredInputs = SmartDashboard.getBoolean("Squared Inputs");
    	reInitSmartDashboard = SmartDashboard.getBoolean("Re-initialize SmartDashboard");

		if (sensitivity > 1) {
			sensitivity = 1;
	    	SmartDashboard.putNumber("Sensitivity", 1);
		}
		if (sensitivity < -1) {
			sensitivity = -1;
	    	SmartDashboard.putNumber("Sensitivity", -1);
		}
		
    	if(reInitSmartDashboard == true) {
    		initSmartDashboard();
    		reInitSmartDashboard = false;
    	}
    }
    
    public void readProperties() {
    	SingularityReader propReader = new SingularityReader();
    	
    	//gets an instance of the properties object, representing the properties file
    	try {
			prop = propReader.readProperties("/config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	//ports on robot
    	
    	talionFrontRight  = Integer.parseInt(prop.getProperty("talionFrontRight"));
    	//current use (right top motor)
    	talionFrontLeft = Integer.parseInt(prop.getProperty("talionFrontLeft"));
    	//current use (left top motor)
    	
    	mainDriveRight = Integer.parseInt(prop.getProperty("mainDriveRight"));
    	//current use (right wheels)
    	mainDriveLeft = Integer.parseInt(prop.getProperty("mainDriveLeft"));
    	//current use (left wheels)
    	
    	// controls on blue joystick
    	
    	thumb = Integer.parseInt(prop.getProperty("thumb"));
    	// what this dose ()
    	top1 = Integer.parseInt(prop.getProperty("top1"));
    	// what this dose ()
    	top2 = Integer.parseInt(prop.getProperty("top2"));
    	// what this dose ()
    	top3 = Integer.parseInt(prop.getProperty("top3"));
    	// what this dose ()
    	bouttom1 = Integer.parseInt(prop.getProperty("bouttom1"));
    	// what this dose ()
    	bouttom2 = Integer.parseInt(prop.getProperty("bouttom2"));
    	// what this dose ()
    	bouttom3 = Integer.parseInt(prop.getProperty("bouttom3"));
    	// what this dose ()
    	triger = Integer.parseInt(prop.getProperty("triger"));
    	// what this dose ()
    	left1 = Integer.parseInt(prop.getProperty("left1"));
    	// what this dose ()
    	left2 = Integer.parseInt(prop.getProperty("left2"));
    	// what this dose ()
    	right1 = Integer.parseInt(prop.getProperty("right1"));
    	// what this dose ()
    	right2 = Integer.parseInt(prop.getProperty("right2"));
        // what this dose ()
    	
    	
    	
    	
    	
    	
    }
}
