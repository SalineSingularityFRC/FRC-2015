package org.usfirst.frc.team5066.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * 
 * @author frc5066
 *
 */
public class RangeFinder {
	// Add analog input, act as ultrasonic sensor
	private AnalogInput analogInupt;
	
	/**
	 * 
	 * @param portNumber Plug in port number
	 * Instantiates an ultrasonic sensor at the given analog port num
	 */
	RangeFinder(int portNumber) {
		analogInupt = new AnalogInput(portNumber);
	}
	
	
	/**
	 * 
	 * @return the range in millimeters, metric system numero uno
	 * This method is specific to our ultrasonic sensor (others may use a different conversion factor)
	 */
	public double findRangeMillimeters() {
		return analogInupt.getVoltage() * 1024;
	}
	
	/**
	 * 
	 * @return Find range in inches, imperial system sucks
	 * Just converts from millimeters to inches (i.e. relies on findRangeMillimeters() method)
	 */
	public double findRangeInches() {
		return findRangeMillimeters() / 25.4;
	}
	
	/**
	 * 
	 * @param factor
	 * @return testing purposes
	 * For evaluating the voltage multiplied by a certain factor.
	 */
	public double findRange(double factor) {
		return factor * analogInupt.getVoltage();
	}
	
	/**
	 * Returns a sampled mean value of the ultrasonic range sensor in inches
	 * 
	 * @param samples - number of samples to take
	 * @return - inches of object away from sensor
	 */
	public double findRangeInchesSampled(int samples){
		//TODO sort the list
		//TODO reminder for Nick to finish the sampler
		List<Double> sampleList = new ArrayList<Double>();
		for(int i=0; i<samples; i++){
			sampleList.add(findRangeInches());
		}
		//find the median
		double Q2 = 0;
		if(sampleList.size()%2==0){
			//even number
			double a = sampleList.get((samples/2));
			double b = sampleList.get((samples/2+1));
//			Q2 = sampleList.size()/2;
		}else{
			//odd number
			Q2 = sampleList.get((samples/2)-1);
			
		}
		
		
		
		double Q1 = sampleList.size();
		
		double Q3 = sampleList.size();
		
		
		return 0.0;
	}
	
	
	
}
