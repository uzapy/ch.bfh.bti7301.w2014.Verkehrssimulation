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
	private int speedDelta = 3; 				// Standardbeschleunigung in Meter pro Sekunde
	private double trödelFactor = 0.3;
	private int securityDistance = 1;
	ArrayList<Car> carList = new ArrayList<Car>();

	/**
	 * @author bublm1
	 */
	public Nagel_Schreckenberg_Simulation() {
		Lane lane0 = new Lane(20, 150, 0);
		Lane lane1 = new Lane(20, 150, 1);
		Lane lane2 = new Lane(20, 150, 2);
//		Lane lane3 = new Lane(20, 150, 3);
		
		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);
//		lane3.setAdjacentLanes(null,  lane2);

		this.track = new Track();
		this.track.addLane(lane0);
		this.track.addLane(lane1);
		this.track.addLane(lane2);
//		this.track.addLane(lane3);
		
		lane0.addCar(new Car( 1, 1, trödelFactor,  9, RandomPool.getNewCarLength(), lane0));
		lane0.addCar(new Car( 2, 3, trödelFactor, 37, RandomPool.getNewCarLength(), lane0));
		lane0.addCar(new Car( 3, 5, trödelFactor, 68, RandomPool.getNewCarLength(), lane0));
		lane0.addCar(new Car( 4, 7, trödelFactor, 95, RandomPool.getNewCarLength(), lane0));
		lane0.addCar(new Car( 5, 9, trödelFactor, 40, RandomPool.getNewCarLength(), lane0));
	
		lane1.addCar(new Car( 6, 11, trödelFactor, 43, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car( 7, 13, trödelFactor, 54, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car( 8, 15, trödelFactor, 62, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car( 9,  0, trödelFactor, 14, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(10,  2, trödelFactor, 97, RandomPool.getNewCarLength(), lane1));

		lane2.addCar(new Car(11,  4, trödelFactor, 25, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(12,  6, trödelFactor, 85, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(13,  8, trödelFactor, 52, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(14, 10, trödelFactor,  4, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(15, 12, trödelFactor, 26, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(16, 14, trödelFactor, 71, RandomPool.getNewCarLength(), lane2));

	}

	public Track performStep() {
		for (Lane lane : this.track.getLanes()) {
			Car car = lane.getFirstCar();
			Car nextCar;
			while (car != null) {
				nextCar = lane.getNextCar(car);
				moveCar(lane, car);
				car = nextCar;
			}
			
//			for(Car car : carList){
//				while(car.getCurrentLane().getLane(car))
//				{
//					car.setPosition(car.getPosition()+5);
//				}
//				car.getCurrentLane().addCar(car);
//			}
//			
//			carList.clear();
		}

		for (Lane lane : this.track.getLanes()) {
			Car car = lane.getFirstCar();
			while (car != null) {
				car.setMoved(false);
				
				int speedOnFastLane = getPossibleMaximumSpeed(lane.getLeftLane(), car);
				int speedOnSlowLane = getPossibleMaximumSpeed(lane.getRightLane(), car);
				int speedOnCurrentLane = getPossibleMaximumSpeed(lane, car);
				
				Car previousCar = null;
				// Soll das Auto auto auf die rechtere Spur wechseln?
				if (speedOnSlowLane == speedOnCurrentLane && lane.isPassableRight() && speedOnSlowLane > 0) {
					if (lane.getRightLane().getClosestBefore(car) != null) {
						previousCar = lane.getRightLane().getClosestBefore(car);
					} else if (lane.getRightLane().getLastCar() != null) {
						previousCar = lane.getRightLane().getLastCar();
					}
					
					if (previousCar != null) {
						int previousCarNextPosition = (previousCar.getPosition() + previousCar.getSpeed() + speedDelta + securityDistance) % lane.getRightLane().getLength();
						int currentCarNextBackPosition = (car.getBackPosition() + speedOnSlowLane) % lane.getRightLane().getLength();
						if (previousCarNextPosition <= currentCarNextBackPosition) {
							car.setNext(speedOnSlowLane, true, false);
						} else {
							car.setNext(speedOnCurrentLane, false, false);
							calculateTrödel(car);
						}
					} else {
						car.setNext(speedOnSlowLane, true, false);
					}
				// Soll das auto auf die Überholspur wechseln?
				} else if (speedOnFastLane > speedOnCurrentLane && lane.isPassableLeft() && speedOnFastLane > 0) {
					if (lane.getLeftLane().getClosestBefore(car) != null) {
						previousCar = lane.getLeftLane().getClosestBefore(car);
					} else if (lane.getLeftLane().getLastCar() != null) {
						previousCar = lane.getLeftLane().getLastCar();
					}
					
					if (previousCar != null) {
						int previousCarNextPosition = (previousCar.getPosition() + previousCar.getSpeed() + speedDelta + securityDistance) % lane.getLeftLane().getLength();
						int currentCarNextBackPosition = (car.getBackPosition() + speedOnSlowLane) % lane.getLeftLane().getLength();
						if (previousCarNextPosition <= currentCarNextBackPosition) {
							car.setNext(speedOnFastLane, false, true);
						} else {
							car.setNext(speedOnCurrentLane, false, false);
							calculateTrödel(car);
						}
					} else {
						car.setNext(speedOnFastLane, false, true);
					}
					
				} else {
					car.setNext(speedOnCurrentLane, false, false);
					calculateTrödel(car);
				}

				car = lane.getNextCar(car);
			}
		}
		
		for (Lane lane : this.track.getLanes()) {
			Car car = lane.getFirstCar();
			while (car != null) {
				Lane nextLane;
				if (car.isBlinkLeft()) {
					nextLane = lane.getLeftLane();
				} else if (car.isBlinkRight()) {
					nextLane = lane.getRightLane();
				} else {
					nextLane = lane;
				}
				car.setNextLane(nextLane);
				car.setNextPosition((car.getPosition() + car.getNextSpeed()) % car.getNextLane().getLength());
				car = lane.getNextCar(car);
			}
		}
		
//		for (Lane lane : this.track.getLanes()) {
//			for (Car car : lane.getSortedCars()) {
//				if (car.isBlinkLeft() || car.isBlinkRight()) {					
//					// Alle die Blinken => nextPosition und nextBackPosition
//					
//					if (car.getNextLane().isPassableLeft()) {
//						List<Car> blinkingRightCars = car.getNextLane().getLeftLane().getCars().stream()
//								.filter(c -> (c.isBlinkRight() && c.getId() != car.getId()))
//								.collect(Collectors.toList());
//						
//						for (Car blinkingRightCar : blinkingRightCars) {
//							if (blinkingRightCar.getNextBackPosition()  <= car.getNextPosition() && blinkingRightCar.getNextPosition() >= car.getNextBackPosition() ) {
//								car.setNext(getPossibleMaximumSpeed(car.getCurrentLane(), car), false, false);
//								car.setNextLane(car.getCurrentLane());
//							}
//						}						
//					}
//
//					if (car.getNextLane().isPassableRight()) {
//						List<Car> blinkingLeftCars = car.getNextLane().getRightLane().getCars().stream()
//								.filter(c -> (c.isBlinkLeft() && c.getId() != car.getId()))
//								.collect(Collectors.toList());
//						
//						for (Car blinkingLeftCar : blinkingLeftCars) {
//							if (blinkingLeftCar.getNextBackPosition()   <= car.getNextPosition()   && blinkingLeftCar.getNextPosition()   >= car.getNextBackPosition()  ) {
//								blinkingLeftCar.setNext(getPossibleMaximumSpeed(blinkingLeftCar.getCurrentLane(), blinkingLeftCar), false, false);
//								blinkingLeftCar.setNextLane(blinkingLeftCar.getCurrentLane());
//							}
//						}
//					}
//				}
//			}
//		}
		return track;
	}

	private void moveCar(Lane lane, Car car) {

		if(!car.isMoved()){
			lane.removeCar(car);
			
			car.setSpeed(car.getNextSpeed());
			
			car.setPosition(car.getNextPosition());
			
			car.setLane(car.getNextLane());
			
			car.setBlinkLeft(false);
			car.setBlinkRight(false);
			car.setMoved(true);
			car.getCurrentLane().addCar(car);
//			carList.add(car);
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
		else {
			speed = lane.getMaxVelocity();
		}
		
		Car nextCar;
		// currentLane
		if (lane.getFastLaneIndex() == car.getCurrentLane().getFastLaneIndex()) {
			nextCar = lane.getNextCar(car);
		} else {
			nextCar = lane.getClosestAfter(car);
		}
		
		// nächstes Auto
		if (nextCar != null) {
			int availableSpace = nextCar.getBackPosition() - car.getPosition() - securityDistance; // Sicherheitsabstand
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
		
		// rechts ÜBERHOLEN CHECK
//		if (lane.isPassableLeft()){
//			Lane leftLane = lane.getLeftLane();
//			
//			if (!(leftLane.getNextCar(car) == null)) {
//				Car nextLeftCar = leftLane.getNextCar(car);
//				if ((car.getPosition() + speed) % lane.getLength() > (nextLeftCar.getPosition() + nextLeftCar.getSpeed()) % leftLane.getLength()){
//					speed = nextLeftCar.getSpeed();
//				}
//			// Spur zu Ende
//			} else {
//				Car firstLeftCar = leftLane.getFirstCar();
//				if (firstLeftCar != null) {
//					if ((car.getPosition() + speed) % lane.getLength() > (firstLeftCar.getPosition() + firstLeftCar.getSpeed()) % leftLane.getLength()){
//						speed = firstLeftCar.getSpeed();
//					}
//				}
//			}
//		}
		

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
