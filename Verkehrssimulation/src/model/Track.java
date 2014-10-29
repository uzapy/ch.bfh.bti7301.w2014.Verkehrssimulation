package model;

import java.util.ArrayList;

/**
 * @author stahr2
 * 
 *         Repräsentiert die Strecke
 *
 */
public class Track {
	private ArrayList<Lane> lanes;
	private ArrayList<Car> cars;

	public Track() {
		this.lanes = new ArrayList<Lane>();
		this.cars = new ArrayList<Car>();
	}

	public void addLane(Lane lane) {
		this.lanes.add(lane);
	}

	public Lane getLane(int laneIndex) {
		if (laneIndex >= 0 && laneIndex <= (this.lanes.size() - 1)) {
			return this.lanes.get(laneIndex);
		} else {
			return null;
		}
	}

	public ArrayList<Lane> getAllLanes() {
		return this.lanes;
	}

	public void addCar(Car car) {
		this.cars.add(car);
	}
	
	public ArrayList<Car> getAllCars() {
		return this.cars;
	}
}
