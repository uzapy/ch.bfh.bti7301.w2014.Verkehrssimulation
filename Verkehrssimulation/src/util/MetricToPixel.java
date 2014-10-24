/**
 *
 */
package util;

/**
 * @author bublm1
 */
public class MetricToPixel {
	public static int SCALING_FACTOR = 8;

	/**
	 * @author bublm1
	 * @param length
	 * @return
	 */
	public static int scale(int distance) {
		return distance * MetricToPixel.SCALING_FACTOR;
	}

	/**
	 * @author bublm1
	 * @param stepBackPosition
	 * @return
	 */
	public static int scale(float distance) {
		return (int) (distance * (float)MetricToPixel.SCALING_FACTOR);
	}
}
