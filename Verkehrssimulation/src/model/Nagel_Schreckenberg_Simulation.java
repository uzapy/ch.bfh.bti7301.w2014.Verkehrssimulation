/**
 * 
 */
package model;

import java.util.ArrayList;

import util.RandomPool;

/**
 * @author burkt4
 */
public class Nagel_Schreckenberg_Simulation {

	public static int FRAMES_PER_SECOND = 30;	// Simulationsgeschwindigkeit in Frames pro Sekunde
	private Track track;
	private int speedDelta = 5; 				// Standardbeschleunigung in Meter pro Sekunde
	private double trödelFactor = 0.3;
	private int securityDistance = 1;
	ArrayList<Car> carList = new ArrayList<Car>();

	/**
	 * @author bublm1
	 */
	public Nagel_Schreckenberg_Simulation() {
		Lane lane0 = new Lane(39, 150, 0);
		Lane lane1 = new Lane(33, 150, 1);
		Lane lane2 = new Lane(28, 150, 2);
		Lane lane3 = new Lane(22, 150, 3);
		
		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(lane3, lane1);
		lane3.setAdjacentLanes(null,  lane2);

		this.track = new Track();
		this.track.addLane(lane0);
		this.track.addLane(lane1);
		this.track.addLane(lane2);
		this.track.addLane(lane3);
		
		lane0.addCar(new Car(1, 0, trödelFactor, 0,  RandomPool.getNewCarLength(), lane0));
		lane0.addCar(new Car(2, 0, trödelFactor, 30, RandomPool.getNewCarLength(), lane0));
		lane0.addCar(new Car(3, 0, trödelFactor, 40, RandomPool.getNewCarLength(), lane0));
		lane0.addCar(new Car(4, 0, trödelFactor, 60, RandomPool.getNewCarLength(), lane0));
		lane0.addCar(new Car(5, 0, trödelFactor, 90, RandomPool.getNewCarLength(), lane0));
	
		lane1.addCar(new Car(6, 0, trödelFactor, 10, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(7, 0, trödelFactor, 40, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(8, 0, trödelFactor, 50, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(9, 0, trödelFactor, 60, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(10,0, trödelFactor, 80, RandomPool.getNewCarLength(), lane1));

		lane2.addCar(new Car(11, 0, trödelFactor, 10, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(12, 0, trödelFactor, 40, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(13, 0, trödelFactor, 50, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(14, 0, trödelFactor, 60, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(15, 0, trödelFactor, 80, RandomPool.getNewCarLength(), lane2));

		lane3.addCar(new Car(15, 0, trödelFactor, 80, RandomPool.getNewCarLength(), lane3));
	}

	public Track performStep() {
		for (Lane lane : this.track.getLanes()) {
			for (Car car : lane.getCars()) {
				moveCar(lane, car);
			}
			
			for(Car car : carList){
				track.getLane(car.getCurrentLane().getFastLaneIndex()).addCar(car);
			}
			
			carList.clear();
		}

		for (Lane lane : this.track.getLanes()) {
			for (Car car : lane.getCars()) {
				car.setMoved(false);
				int speedOnFastLane = getPossibleMaximumSpeed(lane.getLeftLane(), car);
				int speedOnSlowLane = getPossibleMaximumSpeed(lane.getRightLane(), car);
				int speedOnCurrentLane = getPossibleMaximumSpeed(lane, car);
				
				Car previousCar = null;
				// Soll das Auto auto auf die rechtere Spur wechseln?
				if (speedOnSlowLane == speedOnCurrentLane && lane.isPassableRight()) {
					if (lane.getRightLane().getPreviousCarOrSelf(car) != null) {
						previousCar = lane.getRightLane().getPreviousCarOrSelf(car);
					} else if (lane.getRightLane().getLane().lastEntry() != null) {
						previousCar = lane.getRightLane().getLane().lastEntry().getValue();
					}
					
					if (previousCar != null) {
						int previousCarNextPosition = (previousCar.getPosition() + previousCar.getSpeed() + speedDelta + securityDistance) % lane.getRightLane().getLength();
						int currentCarNextBackPosition = (car.getBackPosition() + speedOnSlowLane) % lane.getRightLane().getLength();
						if (previousCarNextPosition <= currentCarNextBackPosition) {
							car.setNextSpeed(speedOnSlowLane);
							car.setBlinkRight(true);
						} else {
							car.setNextSpeed(speedOnCurrentLane);
							calculateTrödel(car);
						}
					} else {
						car.setNextSpeed(speedOnSlowLane);
						car.setBlinkRight(true);
					}
				// Soll das auto auf die Überholspur wechseln?
				} else if (speedOnFastLane > speedOnCurrentLane && lane.isPassableLeft()) {
					if (lane.getLeftLane().getPreviousCarOrSelf(car) != null) {
						previousCar = lane.getLeftLane().getPreviousCarOrSelf(car);
					} else if (lane.getLeftLane().getLane().lastEntry() != null) {
						previousCar = lane.getLeftLane().getLane().lastEntry().getValue();
					}
					
					if (previousCar != null) {
						int previousCarNextPosition = (previousCar.getPosition() + previousCar.getSpeed() + speedDelta + securityDistance) % lane.getLeftLane().getLength();
						int currentCarNextBackPosition = (car.getBackPosition() + speedOnSlowLane) % lane.getLeftLane().getLength();
						if (previousCarNextPosition <= currentCarNextBackPosition) {
							car.setNextSpeed(speedOnFastLane);
							car.setBlinkLeft(true);
						} else {
							car.setNextSpeed(speedOnCurrentLane);
							calculateTrödel(car);
						}
					} else {
						car.setNextSpeed(speedOnFastLane);
						car.setBlinkLeft(true);
					}
					
				} else {
					car.setNextSpeed(speedOnCurrentLane);
					calculateTrödel(car);
				}
			}
		}
		
		for (Lane lane : this.track.getLanes()) {
			for (Car car : lane.getCars()) {
				car.setSpeed(car.getNextSpeed());
			}
		}
		return track;
	}

	private void moveCar(Lane lane, Car car) {
		if (!car.isMoved()) {
			Lane nextLane;
			if (car.isBlinkLeft()) {
				nextLane = lane.getLeftLane();
			} else if (car.isBlinkRight()) {
				nextLane = lane.getRightLane();
			} else {
				nextLane = lane;
			}

			int oldPosition = car.getPosition();
			car.setPosition((oldPosition + car.getSpeed()) % nextLane.getLength());
			if (lane.equals(nextLane)) {
				lane.getLane().updatePosition(oldPosition, car.getPosition());
			} else {
				lane.removeCar(oldPosition, car);
				//nextLane.addCar(car);
			}

			car.setLane(nextLane);
			car.setBlinkLeft(false);
			car.setBlinkRight(false);
			car.setMoved(true);
			carList.add(car);
		}

	}

	private void calculateTrödel(Car car) {
		// Trödeln
		if (RandomPool.nextDouble() <= car.getTrödelFactor()) {
			if (car.getNextSpeed() > this.speedDelta) {
				car.setNextSpeed(car.getNextSpeed() - this.speedDelta);
			} else {
				car.setNextSpeed(0);
			}
		}
	}

	private int getPossibleMaximumSpeed(Lane lane, Car car) {
		// Lane existiert nicht --> speed auf 0 setzen;
		if (lane == null) {
			return 0;
		}

		int speed = car.getSpeed();
		// Beschleunigen
		if (car.getSpeed() < lane.getMaxVelocity()) {
			if(car.getSpeed() + this.speedDelta > lane.getMaxVelocity()){
				speed = lane.getMaxVelocity();
			}
			else{
				speed = car.getSpeed() + this.speedDelta;				
			}

		}
		
		// nächstes Auto
		if (!(lane.getNextCar(car) == null)) {
			int availableSpace = lane.getNextCar(car).getBackPosition() - car.getPosition() - securityDistance; // Sicherheitsabstand
			speed = setSpeedAccordingToAvaliableSpace(speed, availableSpace);
		// Spur zu Ende
		} else {
			int rest = lane.getLength() - car.getPosition();
			Car firstCar = lane.getFirstCar();
			if (firstCar != null) {
				int availableSpace = rest + firstCar.getBackPosition() - securityDistance;
				speed = setSpeedAccordingToAvaliableSpace(speed, availableSpace);
			}
		}

		return speed;
	}

	/**
	 * @author bublm1
	 * @param speed
	 * @param availableSpace
	 * @return
	 */
	private int setSpeedAccordingToAvaliableSpace(int speed, int availableSpace) {
		if (availableSpace < 0) {
			speed = 0;
		} else if (availableSpace < speed) {
			speed = availableSpace;
		}
		return speed;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public Track getTrack() {
		return this.track;
	}
}
