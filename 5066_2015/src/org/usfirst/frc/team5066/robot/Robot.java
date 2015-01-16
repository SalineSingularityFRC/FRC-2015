
package org.usfirst.frc.team5066.robot;

import org.salinerobotics.library.SingularityDrive;
import org.salinerobotics.library.SingularityReader;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive; //Unnecessary?
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
	final int LEFT_MOTOR_PORT = 0;
	final int RIGHT_MOTOR_PORT = 1;
	final int DRIVE_STICK_PORT = 0;
	
	Joystick drivestick;
	RobotDrive drive;
	SmartDashboard dash;
    public void robotInit() {
    	drivestick = new Joystick(0);
    	drive = new SingularityDrive(0,1);
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
        drive.arcadeDrive(drivestick);
        updateSmartDashboard();
    }
    
    public void testInit() {
    	SingularityReader propReader = new SingularityReader();
    	//propReader.readProperties("config.properties");
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        updateSmartDashboard();
    }
    
    public void updateSmartDashboard() {
    	SmartDashboard.putNumber("TestA", 1);
    	SmartDashboard.putNumber("TestB", 1);
    	SmartDashboard.putNumber("TestC", 1);
    	SmartDashboard.putNumber("TestD", 1);
    }
}
