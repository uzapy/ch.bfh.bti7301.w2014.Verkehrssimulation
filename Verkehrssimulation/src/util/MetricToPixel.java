/**
 *
 */
package util;

/**
 * @author bublm1
 */
public class MetricToPixel {
	private static int scalingFactor = 8;

	/**
	 * @author bublm1
	 * @param length
	 * @return
	 */
	public static int scale(int distance) {
		return distance * MetricToPixel.scalingFactor;
	}

	/**
	 * @author bublm1
	 * @param stepBackPosition
	 * @return
	 */
	public static int scale(float distance) {
		return (int) (distance * (float)MetricToPixel.scalingFactor);
	}
}
