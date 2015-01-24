package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.RobotDrive;


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
	CANTalon cant2, cant3, cant4, cant5;
	final double MULTIPLIER = .7;
	
	Joystick js;
	JoystickButton jsb2, jsb3, jsb4, jsb5;
	
	RobotDrive rd;
	
    public void robotInit() {
    	js = new Joystick(0);
    	cant2 = new CANTalon(2);
    	cant3 = new CANTalon(3);
    	cant4 = new CANTalon(4);
    	cant5 = new CANTalon(5);

    	jsb2 = new JoystickButton(js, 2);
    	jsb3 = new JoystickButton(js, 3);
    	jsb4 = new JoystickButton(js, 4);
    	jsb5 = new JoystickButton(js, 5);
    	
    	rd = new RobotDrive(cant5, cant2, cant3, cant4);
    	
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
//    	frontLeft.set((-js.getY() + js.getX() + js.getZ()) * MULTIPLIER * (-js.getThrottle() + 1) / 2);
//    	backLeft.set((js.getY() + js.getX() + js.getZ()) * MULTIPLIER * (-js.getThrottle() + 1) / 2);
//    	frontRight.set((-js.getY() + js.getX() + js.getZ()) * MULTIPLIER * (-js.getThrottle() + 1) / 2);
//    	backRight.set((js.getY() + js.getX() + js.getZ()) * MULTIPLIER * (-js.getThrottle() + 1) / 2
    	rd.mecanumDrive_Cartesian(js.getX() / 4, -js.getZ() / 4, -js.getY() / 4, 0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }
    
}