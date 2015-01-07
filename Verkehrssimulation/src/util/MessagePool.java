/**
 * 
 */
package util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bublm1
 */
public class MessagePool {
	
	private static List<IMeasurementListener> measurmentListeners = new ArrayList<IMeasurementListener>();

	/**
	 * @author bublm1
	 * @param trafficDensity
	 */
	public static void sendTrafficMeasurments(float trafficDensity, float trafficFlow) {
		for (IMeasurementListener listener : measurmentListeners) {
			listener.updateMeasurements(trafficDensity, trafficFlow);
		}
	}

	/**
	 * @author bublm1
	 * @param controlPanel
	 */
	public static void addMeasurementListener(IMeasurementListener measurementListener) {
		measurmentListeners.add(measurementListener);
	}

}
