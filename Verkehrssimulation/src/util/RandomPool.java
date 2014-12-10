/**
 * 
 */
package util;

import java.awt.Color;
import java.util.Random;

import model.Car;
import model.Lane;

/**
 * @author bublm1
 */
public class RandomPool {

	private static Random r = new Random();
	private static int id = 0; 				// Car ID Counter

	/**
	 * @author bublm1
	 */
	private RandomPool() { }

	/**
	 * @author bublm1
	 * @return
	 */
	public static Color getNewColor() {
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}
	
	public static double nextDouble() {
		return r.nextDouble();
	}

	public static Car getNewCar(Lane lane){
		int speed = getNewCarSpeed(lane.getMaxVelocity(0));
		double trödelFactor = getNewTrödelFactor();
		int length = getNewCarLength();

		return new Car(id++, speed, trödelFactor, 0, length, lane);
	}
	
	public static int getNewCarSpeed(int maxVelocity) {
		int fastest = maxVelocity;
		int slowest = 3;
		return r.nextInt(fastest - slowest) + slowest;
	}
	
	public static double getNewTrödelFactor() {
		return r.nextDouble() * (0.8 - 0.2) + 0.2;
	}
	
	/**
	 * @author bublm1
	 * @return
	 */
	public static int getNewCarLength() {
		int shortest = 3;
		int longest = 6;
		return r.nextInt(longest - shortest) + shortest;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public static double getNextGaussian() {
		return r.nextGaussian();
	}
}
