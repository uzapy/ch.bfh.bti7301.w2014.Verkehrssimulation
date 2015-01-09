package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Empfängt Nachrichten und leitet diese an abonnierte Teilnehmer weiter
 * @author bublm1
 */
public class MessagePool {
	
	private static List<IMeasurementListener> measurmentListeners = new ArrayList<IMeasurementListener>();

	/**
	 * Sendet aktuelle Messwerte an die Teilnehmer weiter
	 * @author bublm1
	 * @param trafficDensity	Verkehrsdichte
	 * @param trafficFlow		Verkehrsfluss
	 */
	public static void sendTrafficMeasurments(float trafficDensity, float trafficFlow) {
		for (IMeasurementListener listener : measurmentListeners) {
			listener.updateMeasurements(trafficDensity, trafficFlow);
		}
	}

	/**
	 * Fügt Teilnehmer zur Kollektion hinzu
	 * @author bublm1
	 * @param measurementListener Teilnehmer
	 */
	public static void addMeasurementListener(IMeasurementListener measurementListener) {
		measurmentListeners.add(measurementListener);
	}

}
