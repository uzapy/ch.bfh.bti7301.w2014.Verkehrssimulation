package segment;

import java.util.ArrayList;
import util.MessagePool;
import model.Car;

/**
 * Repräsentiert ein Messsegment. Autos die dieses Segment befahren werden in die Rechnung für 
 * Verkehrsfluss und Verkehrsdichte mit einbezogen
 * @author stahr2
 */

public class MeasuringSegment implements Segment {
	
	
	private int start;
	private int end;
	private float trafficDensity;
	private float trafficFlow;
	ArrayList<Car> carsOnSegment = new ArrayList<Car>(); //Autos, welche sich auf dem Messegment befinden
	
	public MeasuringSegment(int start, int end){
		this.start = start;
		this.end = end;
	}

	@Override
	public int start() {
		return this.start;
	}

	@Override
	public int end() {
		return this.end;
	}
	
	/**
	 * Auto auf der Messstrecke anmelden, damit dieses für die Berechnung der Messwerte verwendet werden kann
	 * @author stahr2
	 * @param car
	 */
	
	public void register(Car car) {
		if (!(carsOnSegment.contains(car))) {
			carsOnSegment.add(car);
			calculateTrafficDensity();
			calculateTrafficFlow();
			MessagePool.sendTrafficMeasurments(trafficDensity, trafficFlow);
		}
	}
	/**
	 * Auto auf der Messstrecke abmelden, damit dieses nicht mehr für die Berechnung der Messwerte verwendet wird.
	 * @author stahr2
	 * @param car
	 */
	public void deRegister(Car car) {
		carsOnSegment.remove(car);
		calculateTrafficDensity();
		calculateTrafficFlow();
		MessagePool.sendTrafficMeasurments(trafficDensity, trafficFlow);
	}
	
	/**
	 * Verkehrsdichte berechnen
	 * @author stahr2
	 */
	
	private void calculateTrafficDensity() {
		trafficDensity = (float) (carsOnSegment.size() / (float) (this.end - this.start)) * 100;
	}
	
	/**
	 * Verkehrsfluss berechnen
	 * @author stahr2
	 */
	
	private void calculateTrafficFlow() {
		trafficFlow = (float) carsOnSegment.stream().mapToInt(c -> c.getSpeed()).sum() / (float) (this.end + this.start);
	}

}
