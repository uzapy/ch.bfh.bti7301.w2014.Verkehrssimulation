/**
 * 
 */
package util;

import java.util.Comparator;

/**
 * @author bublm1
 */
public class Sort {
	public static Comparator<IPositionAndLength> ByPosition =
			(car1, car2) -> Integer.compare(car1.getPosition(), car2.getPosition());
	public static Comparator<IPositionAndLength> ByPositionInverted =
			(car1, car2) -> -Integer.compare(car1.getPosition(), car2.getPosition());
}
