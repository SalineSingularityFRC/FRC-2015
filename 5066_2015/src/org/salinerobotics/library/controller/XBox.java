package org.salinerobotics.library.controller;

import edu.wpi.first.wpilibj.Joystick;

public class XBox implements SingularityController {

	private double sensitivityThreshhold;
	private Joystick joystick;

	public XBox(Joystick joystick) {
		this.joystick = joystick;
		this.sensitivityThreshhold = 0.15;
	}

	public XBox(Joystick joystick, double sensitivityThreshhold) {
		this.joystick = joystick;
		this.sensitivityThreshhold = sensitivityThreshhold;
	}

	public void setSensitivity(double sensitivityThreshhold) {
		this.sensitivityThreshhold = sensitivityThreshhold;
	}

	public Joystick getJoystick() {
		return joystick;
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

	public double getOuterIntake() {
		return eliminateSmall(-joystick.getRawAxis(1));
	}

	public double getRightX() {
		return eliminateSmall(joystick.getRawAxis(4));
	}

	public double getInnerIntake() {
		return eliminateSmall(-joystick.getRawAxis(5));
	}

	public boolean getStart() {
		return joystick.getRawButton(8);
	}

	public boolean getAButton() {
		return joystick.getRawButton(1);
	}

	public boolean getBButton() {
		return joystick.getRawButton(2);
	}

	public boolean getXButton() {
		return joystick.getRawButton(3);
	}

	public double getElevator() {
		return joystick.getRawAxis(2) - joystick.getRawAxis(3);
	}

	private double eliminateSmall(double x) {
		if (x > sensitivityThreshhold || x < -sensitivityThreshhold) {
			return x;
		} else {
			return 0;
		}
	}
	
	public int getControllerType() {
		return SingularityController.XBOX;
	}
}
