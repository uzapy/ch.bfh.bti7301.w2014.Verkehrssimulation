/**
 * 
 */
package util;

import java.awt.Color;
import java.util.Random;

/**
 * @author bublm1
 */
public class ColorPool {
	
	private static Random r = new Random(); //TODO: Globales random!
	
	/**
	 * @author bublm1
	 */
	private ColorPool() { }
	
	/**
	 * @author bublm1
	 * @return
	 */
	public static Color getNewColor() {
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}
}
