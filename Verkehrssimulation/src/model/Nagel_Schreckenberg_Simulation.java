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
	}

	public void performStep() {
		for (Lane lane : this.track.getLanes()) {
			for(Locator<Integer, Car> carLocator : lane) {
				Car car = carLocator.element();
				moveCar(lane, car);				
			}
			// Neues Zufälliges Auto hinzufügen, wenn es Platz hat.
			if(lane.getFirstCar() == null || (lane.getFirstCar() != null && lane.getFirstCar().getBackPosition() > 10)){
				Car randomCar = RandomPool.getNewCar(this.track, lane);
				randomCar.getCurrentLane().addCar(randomCar);
				randomCar.setNext(randomCar.getSpeed(), false, false);
				randomCar.setMoved(true);
				this.track.addToNewCars(randomCar);					
			}
		}
		for (Lane lane : this.track.getLanes()) {
			for(Locator<Integer, Car> carLocator : lane) {
				Car car = carLocator.element();
				car.setMoved(false);
				car.setNext(calculateNextSpeed(car.getCurrentLane(), car), false, false);

				Car nextCar = car.getCurrentLane().getNextCar(car);
				if (nextCar == null) {
					nextCar = car.getCurrentLane().getFirstCar();
				}
				int possibleSpeedOnRightLane = calculateNextSpeed(lane.getRightLane(), car);		

				if (lane.isPassableLeft() && car.getSpeed() > nextCar.getSpeed() && car.getSpeed() > car.getNextSpeed()) {
					// Kann ich überholen?
					boolean canChangeToLeftLane = IsEnoughSpaceBetweenBeforeAndAfter(lane.getLeftLane(), car);
					if (canChangeToLeftLane) {
						// Überholen
						car.setNext(calculateNextSpeed(lane.getLeftLane(), car), false, true);
					} else {
						// Nein, trödeln.
						car.setNext(calculateTrödel(car), false, false);
					}
				} else if (lane.isPassableRight() && car.getSpeed() * 2 <= possibleSpeedOnRightLane) {
					// Kann ich zurück auf die slow lane?
					boolean canChangeToRightLane = IsEnoughSpaceBetweenBeforeAndAfter(lane.getRightLane(), car);
					if (canChangeToRightLane) {
						// Zurück auf die rechte Spur
						car.setNext(calculateNextSpeed(lane.getRightLane(), car), true, false);
					} else {
						// Nein, trödeln.
						car.setNext(calculateTrödel(car), false, false);
					}
				} else {
					// Nein, trödeln.
					car.setNext(calculateTrödel(car), false, false);
				}
			}
		}
		
		// TODO: Überhol-Konflikte abfangen.
		// TODO: Rechts überholen verhindern.
		// TODO: Überholen mit Geschwindigkein 0 verhindern
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
	}

	private void moveCar(Lane lane, Car car) {
		if(car.isToBeDeleted()){
			lane.removeCar(car);
			this.track.addToOldCars(car);
		}
		else{
			if(!car.isMoved()){
			boolean passedEnd = car.getPosition()> car.getNextPosition();
			
			car.setSpeed(car.getNextSpeed());
			car.setPosition(car.getNextPosition());
			car.setLane(car.getNextLane());
			
			if((lane.getFastLaneIndex() != car.getNextLane().getFastLaneIndex()) || passedEnd){
				lane.removeCar(car);
				car.getCurrentLane().addCar(car);
			}
			else{
				lane.updateCarPosition(car);
			}


			
			car.setBlinkLeft(false);
			car.setBlinkRight(false);
			car.setMoved(true);
			}	
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
				if (closestBefore == null) {
					closestBefore = lane.getLastCar();
				}
			}

			int maxNextPosition = closestAfter.getBackPosition() - securityDistance;
			int minNextPosition = closestBefore.getPosition() + closestBefore.getSpeed() * 2 + this.speedDelta + securityDistance;
			int currentCarTemporaryNextBackPosition = car.getBackPosition() + calculateNextSpeed(lane, car) - securityDistance;
			int gapLength = maxNextPosition - minNextPosition;

			if (gapLength >= car.getLength() && minNextPosition <= currentCarTemporaryNextBackPosition) {
				return true;
			} else {
				return false;
			}

		} else {
			return true;
		}
	}

	/**
	 * @author burkt4
	 * @param leftLane
	 * @param car
	 * @return
	 */
	private int calculateNextSpeed(Lane lane, Car car) {
		if (lane == null){
			return 0;
		}
		int speed = car.getSpeed();
		// Beschleunigen
		if (car.getSpeed() < lane.getMaxVelocity()) {
			if (car.getSpeed() + this.speedDelta > lane.getMaxVelocity()) {
				speed = lane.getMaxVelocity();
			} else {
				speed = car.getSpeed() + this.speedDelta;
			}
		} else {
			speed = lane.getMaxVelocity();
		}
		
		// nächstes Auto
		Car nextCar = lane.getClosestAfter(car);
		if (nextCar != null) {
			int availableSpace = nextCar.getBackPosition() - securityDistance - car.getPosition(); // Sicherheitsabstand
			speed = setSpeedAccordingToAvaliableSpace(speed, availableSpace);
		}

		return speed;
	}
	
	private int calculateTrödel(Car car) {
		// Trödeln
		if (RandomPool.nextDouble() <= car.getTrödelFactor()) {
			return (car.getNextSpeed() > this.speedDelta) ? car.getNextSpeed() - this.speedDelta : 0;
		} else {
			return car.getNextSpeed();
		}
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
