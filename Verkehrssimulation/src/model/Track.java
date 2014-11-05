package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import util.Sort;

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
	
	public List<Car> getAllCars() {
		return this.cars;
	}

	/**
	 * @author bublm1
	 * @param car
	 */
	public void createNeighborhood(Car car) {
		List<Car> neighbors = new ArrayList<Car>();
		int radius = 30;
		
		// Autos auf anderen Spuren finden
		this.cars.stream()
			.sorted(Sort.ByPosition)
			.filter(c -> c.getId() != car.getId())
			.filter(c -> (Math.abs(c.getPosition() - car.getBackPosition()) < radius)
					  || (Math.abs(Math.abs(c.getBackPosition() - car.getPosition()) - 150) < radius)
					  || (Math.abs(c.getBackPosition() - car.getPosition()) < radius)
					  || (Math.abs(Math.abs(c.getPosition() - car.getBackPosition()) - 150) < radius))
			.forEach(c -> neighbors.add(c));
		
		car.setNeigborhood(neighbors, radius);
	}
}
