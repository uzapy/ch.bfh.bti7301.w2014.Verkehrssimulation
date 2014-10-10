package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @author stahr2
 * 
 * Repräsentiert die Strecke
 *
 */
public class Track {
	private ArrayList<Lane> Lanes;
	private Random random = new Random();
		
	public Track(){
		this.Lanes = new ArrayList<Lane>();		
	}

	public ArrayList<Lane> getTrack() {
		return Lanes;
	}
	
	public void addLane(Lane lane){
		this.Lanes.add(lane);
	}

	public void setTrack(ArrayList<Lane> lanes) {
		Lanes = lanes;
	}
	
	public Lane getLane(int Lane){
		return this.Lanes.get(Lane);
	}
	
	public void update(){
		for (Lane lane : this.Lanes) {
			Car car = lane.getLane().firstEntry().getValue();

			int speedDelta = 5;
			
			while (!(car == null)) {
				if (car.getSpeed() < lane.getMaxVelocity()) {
					car.setSpeed(car.getSpeed() + speedDelta);
				}

				if (!(lane.getNextCar(car) == null)) {
					int availableSpace = lane.getNextCar(car).getBackPosition() - car.getPosition() - 1; // Sicherheitsabstand
					if (availableSpace < car.getSpeed()) {
						car.setSpeed(availableSpace);
					}
				} else {
					int rest = lane.getLength() - car.getPosition();
					int firstCarPosition = lane.getFirstCar().getBackPosition();
					int availableSpace = rest + firstCarPosition - 1;
					
					if (availableSpace < car.getSpeed()) {
						car.setSpeed(availableSpace);
					}
				}

				double result = this.random.nextDouble();
				if (result <= car.getTrödelFactor()) {
					if (car.getSpeed() > speedDelta) {
						car.setSpeed(car.getSpeed() - speedDelta);						
					}else {
						car.setSpeed(0);
					}
				}

				car = lane.getNextCar(car);
			}

			Collection<Car> cars = lane.getAllCars();
			for (Car currentCar : cars) {
				int oldPosition = currentCar.getPosition();
				currentCar.setPosition((currentCar.getPosition() + currentCar.getSpeed()) % lane.getLength());
				lane.getLane().updatePosition(oldPosition,currentCar.getPosition());
			}
		}	
	}

	public ArrayList<Lane> getAllLanes() {
		return this.Lanes;
	}

}
