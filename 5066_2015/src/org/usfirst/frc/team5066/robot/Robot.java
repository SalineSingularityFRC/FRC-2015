package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.*;

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
	CANTalon left, right;
	final double MULTIPLIER = .7;
	Joystick js;
	
    public void robotInit() {
    	js = new Joystick(0);
    	left = new CANTalon(2);
    	right = new CANTalon(3);
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
    	left.set((-js.getY() + js.getX() + js.getZ()) * MULTIPLIER * (-js.getThrottle() + 1) / 2);
    	right.set((js.getY() + js.getX() + js.getZ()) * MULTIPLIER * (-js.getThrottle() + 1) / 2);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }
    
}