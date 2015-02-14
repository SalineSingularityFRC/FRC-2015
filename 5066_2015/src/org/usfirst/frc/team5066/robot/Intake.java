package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.Talon;

/**
 * 
 * @author frc5066
 *
 */
public class Intake {
	private Talon left, right;
	
	/**
	 * 
	 * @param leftPortNumber
	 *            Port number for the left arm
	 * @param rightPortNumber
	 *            Port number for the right arm
	 */
	public Intake(int leftPortNumber, int rightPortNumber) {
		this.left = new Talon(leftPortNumber);
		this.right = new Talon(rightPortNumber);
	}
	
	/**
	 * 
	 * @param speed Speed to set the motors at. Use parameter between -1.0 and 1.0
	 */
	public void set(double speed) {
		left.set(speed);
		right.set(-speed);
	}
}
