package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author stahr2
 * 
 *         Repräsentiert die Strecke
 *
 */
public class Track {
	private ArrayList<Lane> lanes;
	private ArrayList<Car> cars;
	private Comparator<Car> byPosition = (car1, car2) -> Integer.compare(car1.getPosition(), car2.getPosition());

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
		return this.cars.stream().sorted(byPosition).collect(Collectors.toList());
	}

	/**
	 * @author bublm1
	 * @param car
	 */
	public void createNeighborhood(Car car) {
		List<Car> neighbors = new ArrayList<Car>();
		
		// Auto weiter vorne finden
		List<Car> carsOnCurrentLane = this.getAllCars().stream()
				.filter(c -> c.getCurrentLane().getFastLaneIndex() == car.getCurrentLane().getFastLaneIndex())
				.collect(Collectors.toList());
		
		Optional<Car> carInFront = carsOnCurrentLane.stream().filter(c -> c.getPosition() > car.getPosition()).findFirst();
		
		if (carInFront.isPresent()) {
			neighbors.add(carInFront.get());
		} else if (carsOnCurrentLane.size() > 1) {
			neighbors.add(carsOnCurrentLane.stream().findFirst().get());
		}
		
		// Autos auf anderen Spuren finden
		this.getAllCars().stream()
				.filter(c -> c.getCurrentLane().getFastLaneIndex() != car.getCurrentLane().getFastLaneIndex())
				.filter(c -> (Math.abs(c.getPosition() - car.getBackPosition())       < 30)
						  || (Math.abs(Math.abs(c.getBackPosition() - car.getPosition()) - 150) < 30)
						  || (Math.abs(c.getBackPosition() - car.getPosition())       < 30)
						  || (Math.abs(Math.abs(c.getPosition() - car.getBackPosition()) - 150) < 30))
				.forEach(c -> neighbors.add(c));
		
		car.setNeigborhood(neighbors);
	}
}
