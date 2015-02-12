package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.Talon;

public class Intake {

	private double speed;
	private Talon left;
	private Talon right;
	
	public Intake(int left, int right) {
		this.left = new Talon(left);
		this.right = new Talon(right);
	}
	
	public void set(double speed) {
		left.set(speed);
		right.set(-speed);
	}
	
}
