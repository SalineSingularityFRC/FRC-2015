package org.salinerobotics.library.controller;

import edu.wpi.first.wpilibj.Joystick;

public class Logitech implements SingularityController{

	Joystick joystick;
	
	public Logitech(Joystick joystick){
		this.joystick = joystick;
	}
	
	public double getX() {
		return joystick.getX();
	}

	public double getY() {
		return joystick.getY();
	}

	public double getZ() {
		return joystick.getZ();
	}

	public double getLeftX() {
		return joystick.getX();
	}

	public double getLeftY() {
		return joystick.getY();
	}

	public double getRightX() {
		return joystick.getX();
	}

	public double getRightY() {
		return joystick.getY();
	}

	public boolean getStart() {
		return joystick.getRawButton(1);
	}

	public boolean getA() {
		return joystick.getRawButton(1);
	}
}
