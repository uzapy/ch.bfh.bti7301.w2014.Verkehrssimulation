/**
 * 
 */
package util;

import java.awt.Color;
import java.util.Random;

import model.Car;
import model.Lane;

/**
 * Klasse welche Zufallswerte, welche in der Simulation gebraucht werden, produziert
 * @author bublm1, stahr2, burkt4
 */
public class RandomPool {

	private static Random r = new Random();
	private static int id = 0; 				// Car ID Counter

	/**
	 * @author bublm1
	 */
	private RandomPool() { }

	/**
	 * Generiert eine neue zufällige Farbe
	 * @author bublm1
	 * @return Color:	Neue zufällige Farbe
	 */
	public static Color getNewColor() {
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}
	
	public static double nextDouble() {
		return r.nextDouble();
	}
	/**
	 * Generiert ein neues zufälliges Auto
	 * @author stahr2
	 * @param lane:		Die Fahrspur, auf welcher das Auto generiert werden soll
	 * @return car:		Neues zufälliges Auto
	 */
	public static Car getNewCar(Lane lane){
		int speed = getNewCarSpeed(lane.getMaxVelocity(0));
		double trödelFactor = getNewTrödelFactor();
		int length = getNewCarLength();

		return new Car(id++, speed, trödelFactor, 0, length, lane);
	}
	
	/**
	 * Generiert eine neue zufällige Geschwindigkeit
	 * @author burkt4
	 * @return speed:	Neue zufällige Geschwindigkeit
	 */
	
	public static int getNewCarSpeed(int maxVelocity) {
		int fastest = maxVelocity;
		int slowest = 3;
		return r.nextInt(fastest - slowest) + slowest;
	}
	
	/**
	 * Generiert einen neuen zufälligen Trödelfaktor
	 * @author burkt4
	 * @return speed:	Neuer zufälliger Tröderlfaktor
	 */
	
	public static double getNewTrödelFactor() {
		return r.nextDouble() * (0.5 - 0.2) + 0.2;
	}
	
	/**
	 * Generiert eine neue zufällige Länge eines Autos
	 * @author bublm1
	 * @return lenght:	Neue zufällige Länge
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

	/**
	 * @author bublm1
	 * @return
	 */
	public static boolean isSpawning() {
		return r.nextDouble() <= ParameterPool.SPAWN_RATE;
	}
}
