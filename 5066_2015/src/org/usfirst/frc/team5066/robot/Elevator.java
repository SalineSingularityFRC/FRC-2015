package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {
	CANTalon elevatorMotor;
	RangeFinder rf;
	DigitalInput topLimitSwitch;
	DigitalInput bottomLimitSwitch;
	double brakeConstant;

	public Elevator(int port, int rfPort, double brakeConstant,
			DigitalInput topLimitSwitch, DigitalInput bottomLimitSwitch) {
		elevatorMotor = new CANTalon(port);
		rf = new RangeFinder(rfPort);
		this.topLimitSwitch = topLimitSwitch;
		this.bottomLimitSwitch = bottomLimitSwitch;
		this.brakeConstant = brakeConstant;
	}

	public double getRangeInches() {
		return rf.findRangeInches();
	}

	public double getRangeMillimeters() {
		return rf.findRangeMillimeters();
	}

	// speed value is negative when going up, positive when going down
	public void set(double speed) {
		// if(!((topLimitSwitch.get() && speed < 0) || (bottomLimitSwitch.get()
		// && speed > 0)))
		// if(!(topLimitSwitch.get() && speed < 0))

		SmartDashboard.putString("Top limit switch",
				Boolean.valueOf(topLimitSwitch.get()).toString());
		SmartDashboard.putString("Bottom limit switch",
				Boolean.valueOf(bottomLimitSwitch.get()).toString());
		if ((!topLimitSwitch.get() && speed > 0)
				|| (!bottomLimitSwitch.get() && speed < 0)
				|| (bottomLimitSwitch.get() && topLimitSwitch.get())) {
			elevatorMotor.set(speed);
		} else {
			elevatorMotor.set(0);
		}
	}

	public void set(double speed, boolean simulate) {
		if (simulate) {
			SmartDashboard.putNumber("Elevator motor speed", speed);
		} else if ((!topLimitSwitch.get() && speed > 0)
				|| (!bottomLimitSwitch.get() && speed < 0)
				|| (bottomLimitSwitch.get() && topLimitSwitch.get())) {
			elevatorMotor.set(speed);
		} else {
			elevatorMotor.set(0);
		}
	}
}
