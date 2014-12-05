/**
 * 
 */
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author bublm1
 */
public class MessagePool extends Observable {
	
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
