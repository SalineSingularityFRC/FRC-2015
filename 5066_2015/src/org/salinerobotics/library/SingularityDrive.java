package org.salinerobotics.library;

//Java Docs: http://first.wpi.edu/FRC/roborio/release/docs/java/
import org.salinerobotics.library.controller.SingularityController;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.text.SimpleDateFormat;import java.util.Calendar; //For timing while loops

public class SingularityDrive extends RobotDrive {

	public SingularityDrive(int leftMotorChannel, int rightMotorChannel) {
		super(leftMotorChannel, rightMotorChannel);
	}

	public SingularityDrive(int frontLeftMotorChannel,
			int backLeftMotorChannel, int frontRightMotorChannel,
			int backRightMotorChannel) {
		super(frontLeftMotorChannel, backLeftMotorChannel,
				frontRightMotorChannel, backRightMotorChannel);
	}

	public void arcadeDrive(GenericHID stick, boolean squaredInputs,
			double sensitivity) {
		if (sensitivity > 1) {
			sensitivity = 1;
		}
		if (sensitivity < -1) {
			sensitivity = -1;
		}
		super.arcadeDrive(sensitivity * stick.getY(),
				sensitivity * stick.getX() * -1, squaredInputs);
	}

	public void arcadeDrive(GenericHID stick, boolean squaredInputs) {
		// simply call the full-featured arcadeDrive with the appropriate values
		super.arcadeDrive(stick.getY(), stick.getX() * -1, squaredInputs);
	}

	public void arcadeDrive(GenericHID stick) {
		this.arcadeDrive(stick, true);
	}

	/**
	 * Mecanum drive using one (1) joystick
	 * 
	 * @param js
	 *            Joystick to use
	 * @param translationMultiplier
	 *            How quickly to translate (0 - 1)
	 * @param rotationMultiplier
	 *            How quickly to rotate (0 - 1)
	 * @param squaredInputs
	 *            Uses squared inputs if it's true
	 */
	public void driveMecanum(SingularityController controller,
			double translationMultiplier, double rotationMultiplier,
			boolean squaredInputs) {

		double x = controller.getX(), y = controller.getY(), z = controller
				.getZ() * rotationMultiplier, magnitude, direction, maximum;

		// If squaredInputs square the inputs
		if (squaredInputs) {
			x *= Math.abs(x);
			y *= Math.abs(y);
			z *= Math.abs(z);
		}

		// Find magnitude using pythagorean theorem
		magnitude = Math.sqrt((x * x) + (y * y)) * translationMultiplier;

		// Find direction using arctan
		direction = Math.atan(y / x);
		if (x < 0) {
			direction += Math.PI;
		} else if (x == 0) {
			if (y > 0) {
				direction = Math.PI / 2;
			} else {
				direction = 3 * Math.PI / 2;
			}
		}
		direction += Math.PI / 4;

		maximum = Math.max(
				Math.max(Math.abs(Math.sin(direction)),
						Math.abs(Math.cos(direction)))
						* magnitude + Math.abs(z), 1);

		// Use formulas to set wheel speeds.
		m_frontRightMotor.set((magnitude * Math.cos(direction) + z) / maximum);
		m_rearRightMotor.set((magnitude * -Math.sin(direction) + z) / maximum);
		m_frontLeftMotor.set((magnitude * Math.sin(direction) + z) / maximum);
		m_rearLeftMotor.set((magnitude * -Math.cos(direction) + z) / maximum);

		SmartDashboard.putNumber("Yello", (magnitude * Math.sin(direction) + z)
				/ maximum);
		SmartDashboard.putNumber("Orng", (magnitude * -Math.cos(direction) + z)
				/ maximum);
		SmartDashboard.putNumber("Grean", (magnitude * Math.cos(direction) + z)
				/ maximum);
		SmartDashboard.putNumber("Lavendarererererer",
				(magnitude * -Math.sin(direction) + z) / maximum);
	}

	/**
	 * Tank drive using one controller with two joysticks (i.e. use an XBox
	 * controller if you want to do anything useful)
	 * 
	 * @param controller
	 *            XBox controller to use. Will not be okay if you use a logitech
	 *            one.
	 * @param translationMultiplier
	 *            How much power to use
	 * @param squaredInputs
	 *            Squared inputs or not.
	 */
	public void tankDrive(SingularityController controller,
			double translationMultiplier, boolean squaredInputs) {
		double left = -controller.getOuterIntake(), right = controller.getInnerIntake();

		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
		}

		m_frontLeftMotor.set(left * translationMultiplier);
		m_rearLeftMotor.set(left * translationMultiplier);
		m_frontRightMotor.set(right * translationMultiplier);
		m_rearRightMotor.set(right * translationMultiplier);
	}

	/**
	 * Tank drive using two joystick inputs (can be XBox controllers if you want
	 * to do it the stupid way...)
	 * 
	 * 
	 * @param controller1
	 *            Logitech (or XBox controller) to use for left wheel set
	 * @param controller2
	 *            Logitech (or XBox controller) to use for right wheel set
	 * @param translationMultiplier
	 *            How much power to use
	 * @param squaredInputs
	 *            Squared inputs or not
	 */
	public void tankDrive(SingularityController controller1,
			SingularityController controller2, double translationMultiplier,
			boolean squaredInputs) {
		double left = -controller1.getY(), right = controller2.getY();

		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
		}

		m_frontLeftMotor.set(left * translationMultiplier);
		m_rearLeftMotor.set(left * translationMultiplier);
		m_frontRightMotor.set(right * translationMultiplier);
		m_rearRightMotor.set(right * translationMultiplier);
	}

	/**
	 * Arcade drive using a single controller. Simple and easy....
	 * 
	 * @param controller
	 *            SingularityController to use
	 * @param translationMultiplier
	 *            How much power to go forwards/backwards
	 * @param rotationMultiplier
	 *            How much power to rotate
	 * @param squaredInputs
	 *            Squared inputs or not
	 */
	public void arcadeDrive(SingularityController controller,
			double translationMultiplier, double rotationMultiplier,
			boolean squaredInputs) {
		double x = controller.getX(), y = controller.getY();

		if (squaredInputs) {
			x *= Math.abs(x);
			y *= Math.abs(y);
		}

		m_frontLeftMotor.set(-y * translationMultiplier - x
				* rotationMultiplier);
		m_rearLeftMotor
				.set(-y * translationMultiplier - x * rotationMultiplier);
		m_frontRightMotor.set(y * translationMultiplier - x
				* rotationMultiplier);
		m_rearRightMotor
				.set(y * translationMultiplier - x * rotationMultiplier);
	}

	/**
	 * Old version of mecanum drive. Only for backup purposes.
	 */
	public void oldDriveMecanumOldIsRedundant(Joystick js,
			double translationMultiplier, double rotationMultiplier) {

		// Find magnitude using pythagorean theorem
		double magnitude = Math.sqrt(js.getX() * js.getX() + js.getY()
				* js.getY())
				* translationMultiplier;

		// Find direction using arctan
		double direction = Math.atan(-js.getY() / js.getX());
		if (js.getX() < 0) {
			direction += Math.PI;
		}

		// Use formulas to set wheel speeds.
		m_frontRightMotor.set(magnitude * Math.sin(direction + Math.PI / 4)
				+ js.getTwist() * rotationMultiplier);
		m_rearRightMotor.set(magnitude * Math.cos(direction + Math.PI / 4)
				- js.getTwist() * rotationMultiplier);
		m_frontLeftMotor.set(magnitude * Math.cos(direction + Math.PI / 4)
				+ js.getTwist() * rotationMultiplier);
		m_rearLeftMotor.set(magnitude * Math.sin(direction + Math.PI / 4)
				- js.getTwist() * rotationMultiplier);
	}

	public void forward() {
		m_frontRightMotor.set(.5);
		m_rearRightMotor.set(.5);
		m_frontLeftMotor.set(-.5);
		m_rearLeftMotor.set(-.5);
	}
	
	public void backward(){
		m_frontRightMotor.set(-.5);
		m_rearRightMotor.set(-.5);
		m_frontLeftMotor.set(.5);
		m_rearLeftMotor.set(.5);
	}

	public void stop() {
		m_frontRightMotor.set(0);
		m_rearRightMotor.set(0);
		m_frontLeftMotor.set(0);
		m_rearLeftMotor.set(0);
	}
	public boolean turn(String direction){  //turns robot use clockwise or counterclockwise as direction. Returns whether succseful or not.
		double turnVar=0;
		if(direction.equals("clockwise")){
			turnVar=-.5;
		}else if(direction.equals("counterclockwise")){
			turnVar=.5;
		}else{return false;}
		m_frontRightMotor.set(turnVar);
		m_rearRightMotor.set(turnVar);
		m_frontLeftMotor.set(turnVar);
		m_rearLeftMotor.set(turnVar);
		return true;
	}
/*Auton use. use: move([How long to move in milliseconds],[direction:clockwise,counterclockwise,forward,back,backwards],[wether to stop robot after moving])
*/
	public boolean move(int milSeconds,String direction,boolean stopAfter){ 
		boolean result=true;
		if(milSeconds>14999){return false;}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat mil = new SimpleDateFormat("ssmm");
		boolean cont=true;
		int start=0;
		while(cont){
			start = Integer.parseInt(mil.format(cal.getTime()));
			if(direction.equals("clockwise")||direction.equals("counterclockwise")){result=turn(direction);
			}else if(direction.equals("forward")){forward();
			}else if(direction.equals("back")||direction.equals("backwards")){backward();}else{return false;}
			milSeconds = Integer.parseInt(mil.format(cal.getTime())) - start;
			if(Integer.parseInt(mil.format(cal.getTime()))-start<0){result=false;cont=false;}
			if(milSeconds<=0){cont=false;}
		}
		if(stopAfter==true){stop();}
		return result;
	}
}
