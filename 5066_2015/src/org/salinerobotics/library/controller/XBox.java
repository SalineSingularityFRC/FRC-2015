package org.salinerobotics.library.controller;

import edu.wpi.first.wpilibj.Joystick;

public class XBox implements SingularityController {

	Joystick joystick;

	public XBox(Joystick joystick) {
		this.joystick = joystick;
	}

	public double getX() {
		
		return emperorHirohito(joystick.getX());
	}

	public double getY() {
		return emperorHirohito(-joystick.getY());
	}

	public double getZ() {
		return emperorHirohito(joystick.getRawAxis(4));
	}

	public double getLeftX() {
		return emperorHirohito(joystick.getRawAxis(0));
	}

	public double getLeftY() {
		return emperorHirohito(-joystick.getRawAxis(1));
	}

	public double getRightX() {
		return emperorHirohito(joystick.getRawAxis(4));
	}

	public double getRightY() {
		return emperorHirohito(-joystick.getRawAxis(5));
	}

	public boolean getStart() {
		return joystick.getRawButton(8);
	}

	public boolean getA() {
		return joystick.getRawButton(1);
	}

	private double emperorHirohito(double x) {
		if (x > 0) {
			return Math.max(x, 0.1);
		} else {
			return Math.min(x, -0.1);
		}
	}
}
