package org.salinerobotics.library.controller;

import edu.wpi.first.wpilibj.Joystick;

public class XBox implements SingularityController {

	private Joystick joystick;

	public XBox(Joystick joystick) {
		this.joystick = joystick;
	}

	public double getX() {
		return eliminateSmall(joystick.getX());
	}

	public double getY() {
		return eliminateSmall(-joystick.getY());
	}

	public double getZ() {
		return eliminateSmall(joystick.getRawAxis(4));
	}

	public double getLeftX() {
		return eliminateSmall(joystick.getRawAxis(0));
	}

	public double getLeftY() {
		return eliminateSmall(-joystick.getRawAxis(1));
	}

	public double getRightX() {
		return eliminateSmall(joystick.getRawAxis(4));
	}

	public double getRightY() {
		return eliminateSmall(-joystick.getRawAxis(5));
	}

	public boolean getStart() {
		return joystick.getRawButton(8);
	}

	public boolean getA() {
		return joystick.getRawButton(1);
	}

	private double eliminateSmall(double x) {
		if (x > 0.1 || x < -0.1) {
			return x;
		} else {
			return 0;
		}
	}
}
