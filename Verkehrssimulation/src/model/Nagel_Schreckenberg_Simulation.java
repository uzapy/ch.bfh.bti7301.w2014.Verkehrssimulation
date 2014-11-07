/**
 * 
 */
package model;

import java.util.ArrayList;

import skiplist.Locator;
import util.RandomPool;

/**
 * @author burkt4
 */
public class Nagel_Schreckenberg_Simulation {

	public static int FRAMES_PER_SECOND = 30;	// Simulationsgeschwindigkeit in Frames pro Sekunde
	private Track track;
	private int speedDelta = 3; 				// Standardbeschleunigung in Meter pro Sekunde
	private double trödelFactor = 0.2;
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
			for(Locator<Integer, Car> carLocator : lane) {
				Car car = carLocator.element();
				moveCar(lane, car);
			}
		}

		for (Lane lane : this.track.getLanes()) {
			for(Locator<Integer, Car> carLocator : lane) {
				Car car = carLocator.element();
				car.setMoved(false);
				car.setNext(getMaximumSpeedOnCurrentLane(car), false, false);
				
				Car nextCar = car.getCurrentLane().getNextCar(car);
				if (nextCar == null) {
					nextCar = car.getCurrentLane().getFirstCar();
				}
				
				if (lane.isPassableLeft() && car.getSpeed() > nextCar.getSpeed() && car.getSpeed() > car.getNextSpeed()) {
					// Kann ich überholen?
					boolean canChangeToLeftLane = IsEnoughSpaceBetweenBeforeAndAfter(lane.getLeftLane(), car);
				} else {
					// Nein, trödeln.
					car.setNext(calculateTrödel(car), false, false);
				}
			}
		}
		
//		for (Lane lane : this.track.getLanes()) {
//			for(Locator<Integer, Car> carLocator : lane) {
//				Car car = carLocator.element();
//				Lane nextLane;
//				if (car.isBlinkLeft()) {
//					nextLane = lane.getLeftLane();
//				} else if (car.isBlinkRight()) {
//					nextLane = lane.getRightLane();
//				} else {
//					nextLane = lane;
//				}
//				car.setNextLane(nextLane);
//				car.setNextPosition((car.getPosition() + car.getNextSpeed()) % car.getNextLane().getLength());
//			}
//		}
		
//		for (Lane lane : this.track.getLanes()) {
//			for (Locator<Integer, Car> carLocator : lane) {
//				Car car = carLocator.element();
//				
//				if (car.isBlinkLeft() || car.isBlinkRight()) {					
//					// Alle die Blinken => nextPosition und nextBackPosition
//					
//					// Alle die blinken auf der übernächsten Spur
//					if (car.getNextLane().isPassableLeft()) {
//						List<Car> blinkingRightCars = new ArrayList<Car>();
//						for (Locator<Integer, Car> blinkingRightCarLocator : car.getNextLane().getLeftLane()) {
//							if (blinkingRightCarLocator.element().isBlinkRight()) {
//								blinkingRightCars.add(blinkingRightCarLocator.element());
//							}
//						}
//
//						for (Car blinkingRightCar : blinkingRightCars) {
//							if (blinkingRightCar.getNextBackPosition() <= car.getNextPosition()
//									&& blinkingRightCar.getNextPosition() >= car.getNextBackPosition()) {
//								car.setNext(getPossibleMaximumSpeed(car.getCurrentLane(), car), false, false);
//								car.setNextLane(car.getCurrentLane());
//							}
//						}
//					}
//
//					if (car.getNextLane().isPassableRight()) {
//						List<Car> blinkingLeftCars = new ArrayList<Car>();
//						for (Locator<Integer, Car> blinkingLeftCar : car.getNextLane().getRightLane()) {
//							if (blinkingLeftCar.element().isBlinkLeft()) {
//								blinkingLeftCars.add(blinkingLeftCar.element());
//							}
//						}
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
			car.getCurrentLane().addCar(car);
			
			car.setBlinkLeft(false);
			car.setBlinkRight(false);
			car.setMoved(true);
		}
	}
	
	/**
	 * @author bublm1
	 * @param leftLane
	 * @param car
	 * @return
	 */
	private boolean IsEnoughSpaceBetweenBeforeAndAfter(Lane lane, Car car) {
		boolean hasCars = lane.iterator().hasNext();
		if (hasCars) {
			
			Car closestAfter = lane.getClosestAfter(car);
			if (closestAfter == null) {
				closestAfter = lane.getFirstCar();
			}
			
			Car closestBefore = lane.getPreviousCar(closestAfter);
			if (closestBefore == null) {
				closestBefore = lane.getClosestBefore(car);
			}
			
			boolean isCloesestBeforeFaster = closestBefore.getSpeed() > car.getSpeed();
			
		}
		
		return false;
	}

	private int calculateTrödel(Car car) {
		// Trödeln
		if (RandomPool.nextDouble() <= car.getTrödelFactor()) {
			return (car.getNextSpeed() > this.speedDelta) ? car.getNextSpeed() - this.speedDelta : 0;
		} else {
			return car.getNextSpeed();
		}
	}

	private int getMaximumSpeedOnCurrentLane(Car car) {
		int speed = car.getSpeed();
		// Beschleunigen
		if (car.getSpeed() < car.getCurrentLane().getMaxVelocity()) {
			if(car.getSpeed() + this.speedDelta > car.getCurrentLane().getMaxVelocity()) {
				speed = car.getCurrentLane().getMaxVelocity();
			} else {
				speed = car.getSpeed() + this.speedDelta;				
			}
		} else {
			speed = car.getCurrentLane().getMaxVelocity();
		}
		
		Car nextCar = car.getCurrentLane().getNextCar(car);
		
		// nächstes Auto
		if (nextCar != null) {
			int availableSpace = nextCar.getBackPosition() - securityDistance - car.getPosition(); // Sicherheitsabstand
			speed = setSpeedAccordingToAvaliableSpace(speed, availableSpace);
		// Spur zu Ende
		} else {
			int rest = car.getCurrentLane().getLength() - car.getPosition();
			Car firstCar = car.getCurrentLane().getFirstCar();
			if (firstCar != null) {
				int availableSpace = firstCar.getBackPosition() - securityDistance + rest;
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
