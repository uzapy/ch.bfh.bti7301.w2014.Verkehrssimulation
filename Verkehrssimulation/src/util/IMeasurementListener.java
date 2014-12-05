/**
 * 
 */
package util;

/**
 * @author bublm1
 */
public interface IMeasurementListener {

	/**
	 * @author bublm1
	 * @param trafficDensity
	 * @param trafficFlow
	 */
	void updateMeasurements(float trafficDensity, float trafficFlow);
	
}
