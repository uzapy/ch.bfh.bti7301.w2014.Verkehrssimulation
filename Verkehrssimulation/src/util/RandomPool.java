/**
 * 
 */
package util;

import java.awt.Color;
import java.util.Random;

import model.Car;
import model.Lane;
import model.Track;

/**
 * @author bublm1
 */
public class RandomPool {

	private static Random r = new Random();
	private static int id = 0; //Car ID

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

	public static Car getNewCar(Track track, Lane lane){
		int speed = getNewCarSpeed();
		double trödelFactor = r.nextDouble() * (0.8 - 0.2) + 0.2;
		int length = getNewCarLength();
		id++;
		return new Car(id, speed, trödelFactor, 0, length, lane);
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
	
	public static int getNewCarSpeed() {
		int fastest = 18;
		int slowest = 3;
		return r.nextInt(fastest - slowest) + slowest;
	}
}
