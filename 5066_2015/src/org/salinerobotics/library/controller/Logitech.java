package org.salinerobotics.library.controller;

import edu.wpi.first.wpilibj.Joystick;

public class Logitech implements SingularityController{

	private Joystick joystick;
	private double sensitivityThreshhold;
	
	public Logitech(Joystick joystick){
		this.joystick = joystick;
		this.sensitivityThreshhold = 0.15;
	}
	
	public Logitech(Joystick joystick, double sensitivityThreshhold) {
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
		return eliminateSmall(joystick.getTwist());
	}

	public double getLeftX() {
		return eliminateSmall(joystick.getX());
	}

	public double getLeftY() {
		return eliminateSmall(-joystick.getY());
	}

	public double getRightX() {
		return eliminateSmall(joystick.getX());
	}

	public double getRightY() {
		return eliminateSmall(-joystick.getY());
	}

	public boolean getStart() {
		return joystick.getRawButton(10);
	}
	
	public double getElevator() {
		return -joystick.getY();
	}
	
	private double eliminateSmall(double x) {
		if (x > sensitivityThreshhold || x < -sensitivityThreshhold) {
			return x;
		} else {
			return 0;
		}
	}
	
	public int getControllerType() {
		return SingularityController.LOGITECH;
	}
	
	public int getAutonMode(int current) {
		if(joystick.getRawButton(1)) {
			return 1;
		}
		else if(joystick.getRawButton(2)) {
			return 2;
		}
		else if(joystick.getRawButton(3)) {
			return 4;
		}
		else if(joystick.getRawButton(4)) {
			return 5;
		}
		else if(joystick.getRawButton(5)) {
			return 5;
		}
		else if(joystick.getRawButton(6)) {
			return 6;
		}
		else
			return current;
	}
	
	public boolean getDriveSpeedHalved(){
		return (joystick.getRawButton(5) || joystick.getRawButton(6));
	}
}
