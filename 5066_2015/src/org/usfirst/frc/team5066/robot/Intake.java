package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author frc5066
 *
 */
public class Intake {
	private Talon leftOuter, rightOuter;
	private CANTalon leftInner, rightInner;
	
	/**
	 * 
	 * @param leftOuterPortNumber
	 *            Port number for the left arm
	 * @param rightOuterPortNumber
	 *            Port number for the right arm
	 */
	public Intake(int leftOuterPortNumber, int rightOuterPortNumber, int leftInnerPortNumber, int rightInnerPortNumber) {
		this.leftOuter = new Talon(leftOuterPortNumber);
		this.rightOuter = new Talon(rightOuterPortNumber);
		this.leftInner = new CANTalon(leftInnerPortNumber);
		this.rightInner = new CANTalon(rightInnerPortNumber);
		SmartDashboard.putNumber("Intake Speed", 1);
	}
	
	/**
	 * 
	 * @param speed Speed to set the motors at. Use parameter between -1.0 and 1.0
	 */
	public void setInner(double speed) { 
		leftInner.set(-speed);
		rightInner.set(speed);
	}
	public void setOuter(double speed) {
		leftOuter.set(speed);
		rightOuter.set(-speed);
	}
}
