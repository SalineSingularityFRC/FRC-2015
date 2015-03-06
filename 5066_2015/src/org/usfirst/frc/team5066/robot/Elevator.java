package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class Elevator {	
	CANTalon elevatorMotor;
	RangeFinder rf;
	
	public Elevator(int port, int rfPort) {
		elevatorMotor = new CANTalon(port);
		rf = new RangeFinder(rfPort);
	}
	
	public double getRangeInches() {
		return rf.findRangeInches();
	}
	
	public double getRangeMillimeters() {
		return rf.findRangeMillimeters();
	}
	
	public void set(double speed) {
		elevatorMotor.set(speed);
	}
	
	
}
