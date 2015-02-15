package org.salinerobotics.library.controller;

import edu.wpi.first.wpilibj.Joystick;

public class XBox implements SingularityController {

	Joystick joystick;

	public XBox(Joystick joystick) {
		this.joystick = joystick;
	}

	public double getX() {
		return joystick.getX();
	}

	public double getY() {
		return -joystick.getY();
	}

	public double getZ() {
		return joystick.getRawAxis(4);
	}

	public double getLeftX() {
		return joystick.getRawAxis(0);
	}

	public double getLeftY() {
		return -joystick.getRawAxis(1);
	}

	public double getRightX() {
		return joystick.getRawAxis(4);
	}

	public double getRightY() {
		return -joystick.getRawAxis(5);
	}

	public boolean getStart() {
		return joystick.getRawButton(8);
	}

	public boolean getA() {
		return joystick.getRawButton(1);
	}
}
