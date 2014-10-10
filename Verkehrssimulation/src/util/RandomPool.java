/**
 * 
 */
package util;

import java.awt.Color;
import java.util.Random;

/**
 * @author bublm1
 */
public class RandomPool {

	private static Random r = new Random(); // TODO: Globales random!

	/**
	 * @author bublm1
	 */
	private RandomPool() {
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public static Color getNewColor() {
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
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
}
