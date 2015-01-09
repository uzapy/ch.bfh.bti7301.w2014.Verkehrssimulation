package util;

public interface IMeasurementListener {

	/**
	 * Aktualissiert die Messwerte
	 * @author bublm1
	 * @param trafficDensity	Verkehrsdichte
	 * @param trafficFlow		Verkehrsfluss
	 */
	void updateMeasurements(float trafficDensity, float trafficFlow);
	
}
