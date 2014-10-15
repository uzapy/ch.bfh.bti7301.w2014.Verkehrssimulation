/**
 * 
 */
package util;

import java.awt.Color;
import java.util.Random;

import model.Car;

/**
 * @author bublm1
 */
public class RandomPool {

	private static Random r = new Random();
	private static int id = 1; //Car ID

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

	/**
	 * @author bublm1
	 * @return
	 */
	public static int getNewCarLength() {
		int shortest = 3;
		int longest = 6;
		return r.nextInt(longest - shortest) + shortest;
	}
	
	public static Car getNewCar(){
		int speed = r.nextInt(120 - 80) + 80;
		double trödelFactor = r.nextDouble() * (0.8 - 0.2) + 0.2;
		int laneID = r.nextInt(1 - 3) + 3;
		int length = getNewCarLength();
		return new Car(id, speed, trödelFactor, 0, length, laneID);
	}
}
