
package org.usfirst.frc.team5066.robot;

import java.io.IOException;
import java.util.Properties;

import org.salinerobotics.library.SingularityDrive;
import org.salinerobotics.library.SingularityReader;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive; //Unnecessary?
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
	
	//values read from properties file
	int testA;
	int testB;
	int testC;
	int testD;
	
	//object initializations
	BuiltInAccelerometer accel;
	Joystick drivestick;
	RobotDrive drive;
	SmartDashboard dash;
	Properties prop;
	
	
    public void robotInit() {
    	drivestick = new Joystick(0);
    	drive = new SingularityDrive(0,1);
    	accel = new BuiltInAccelerometer(Range.k4G);
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
        drive.arcadeDrive(drivestick);
        updateSmartDashboard();
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
    
    public void updateSmartDashboard() {
    	SmartDashboard.putNumber("Accelerometer X", accel.getX());
    	SmartDashboard.putNumber("Accelerometer Y", accel.getY());
    	SmartDashboard.putNumber("Accelerometer Z", accel.getZ());
    	SmartDashboard.putNumber("2048", testD);
    }
    
    public void readProperties() {
    	SingularityReader propReader = new SingularityReader();
    	
    	//gets an instance of the properties object, representing the properties file
    	try {
			prop = propReader.readProperties("/config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	testA = Integer.parseInt(prop.getProperty("testA"));
    	testB = Integer.parseInt(prop.getProperty("testB"));
    	testC = Integer.parseInt(prop.getProperty("testC"));
    	testD = Integer.parseInt(prop.getProperty("testD"));
    }
}
