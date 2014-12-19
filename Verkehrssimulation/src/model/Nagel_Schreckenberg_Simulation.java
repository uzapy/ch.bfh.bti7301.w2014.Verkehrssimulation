/**
 * 
 */
package model;

import java.util.List;

import segment.Segment;
import segment.SpawnSegment;
import skiplist.Locator;
import util.PresetPool;
import util.RandomPool;
import util.TrackPreset;

/**
 * @author burkt4
 */
public class Nagel_Schreckenberg_Simulation {

	private Track track;
	private int speedDelta = 3;			// Standardbeschleunigung in Meter pro Sekunde
	private int securityDistance = 1;	// Sicherheitsabstand

	/**
	 * @author bublm1
	 * @param trackPreset 
	 */
	public Nagel_Schreckenberg_Simulation(TrackPreset trackPreset) {
		switch (trackPreset) {
		case RoadWorks:
			this.track = PresetPool.getRoadWorks();
			break;
		case Bottleneck:
			this.track = PresetPool.getBottleneck();
			break;
		case SpeedLimit:
			this.track = PresetPool.getSpeedLimit();
			break;
		case BanOnPassing:
			this.track = PresetPool.getBanOnPassing();
			break;
		case Experimental:
			this.track = PresetPool.getExperimental();
			break;
		case OnAndExitRamp:
			this.track = PresetPool.getOnAndExitRamp();
			break;
		case Default:
		default:
			this.track = PresetPool.getDefault();			
			break;
		}
	}

	public void performStep() {
		for (Lane lane : this.track.getLanes()) {
			for (Locator<Integer, Car> carLocator : lane) {
				Car car = carLocator.element();

				if (lane.isMeasurable(car.getPosition())) {
					lane.includeInMeasurement(car);
				} else {
					lane.excludeFromMeasurement(car);
				}

				moveCar(lane, car);
			}

			// Zufällig neues Zufälliges Auto hinzufügen, wenn es Platz hat.
			if (RandomPool.isSpawning()) {
				if (lane.getFirstCar() == null || (lane.getFirstCar() != null && lane.getFirstCar().getBackPosition() > 20)) {
					Car randomCar = RandomPool.getNewCar(lane);
					randomCar.getLane().addCar(randomCar);
					randomCar.setNext(randomCar.getSpeed(), false, false);
					randomCar.setMoved(true);
					this.track.addToNewCars(randomCar);
				}
				List<Segment> spawnSegments = lane.getSegments(SpawnSegment.class);
				if(!spawnSegments.isEmpty()){
					for(Segment segment : spawnSegments){
						Car randomCar = RandomPool.getNewCar(lane);
						randomCar.setPosition(segment.start());
						Car afterRandomCar = lane.getClosestAfter(randomCar.getPosition());
						Car beforeRandomCar = lane.getClosestBefore(randomCar.getPosition());
											
						if(afterRandomCar != null && beforeRandomCar != null){
							if(((afterRandomCar.getBackPosition() - randomCar.getPosition()) > 10) && ((randomCar.getBackPosition() - beforeRandomCar.getBackPosition()) > 10)){
								randomCar.getLane().addCar(randomCar);
								randomCar.setNext(randomCar.getSpeed(), false, false);
								randomCar.setMoved(true);
								this.track.addToNewCars(randomCar);	
							}
						}else {
							randomCar.getLane().addCar(randomCar);
							randomCar.setNext(randomCar.getSpeed(), false, false);
							randomCar.setMoved(true);
							this.track.addToNewCars(randomCar);						
						}
					}
				}
			}
		}

		for (Lane lane : this.track.getLanes()) {
			for (Locator<Integer, Car> carLocator : lane) {
				Car car = carLocator.element();
				car.setMoved(false);
				car.setNext(calculateNextSpeed(car.getLane(), car), false, false);

				Car nextCar = car.getLane().getNextCar(car);
				if (nextCar == null) {
					nextCar = car.getLane().getFirstCar();
				}

				// Will ich überholen?
				boolean isPassableLeft = lane.isPassableLeft(car.getPosition());
				boolean isFasterThanNextCar = car.getSpeed() > nextCar.getSpeed();
				boolean isNextCarClose = nextCar.getBackPosition() - car.getPosition() < lane.getMaxVelocity(car.getPosition());
				boolean hasChangedLanesBefore = car.hasChangedLanesBefore();
				boolean hasToChangeLane = !lane.isOpenToTraffic(car.getPosition());
				boolean isGoingToChangeLane = false;
				
				// boolean isNextSpeedSmaller = car.getSpeed() > car.getNextSpeed();
				if ((isPassableLeft) && ((isFasterThanNextCar && isNextCarClose && !hasChangedLanesBefore) || hasToChangeLane)) {
					// Kann ich überholen?
					boolean canChangeToLeftLane = IsEnoughSpaceBetweenBeforeAndAfter(lane.getLeftLane(), car);
					int speedOnLeftLane = calculateNextSpeed(lane.getLeftLane(), car);
					if (canChangeToLeftLane && speedOnLeftLane > 0) {
						// Überholen
						car.setNext(speedOnLeftLane, false, true);
						isGoingToChangeLane = true;
					}
				}
				
				boolean isPassableRight = lane.isPassableRight(car.getPosition());
				boolean canKeepCurentSpeed = car.getSpeed() <= calculateNextSpeed(lane.getRightLane(), car);
				
				if ((isPassableRight && ((canKeepCurentSpeed && !hasChangedLanesBefore) || hasToChangeLane)) && !isGoingToChangeLane) {
					// Kann ich zurück auf die slow lane?
					boolean canChangeToRightLane = IsEnoughSpaceBetweenBeforeAndAfter(lane.getRightLane(), car);
					int speedOnRightLane = calculateNextSpeed(lane.getRightLane(), car);
					if (canChangeToRightLane && speedOnRightLane > 0) {
						// Zurück auf die rechte Spur
						car.setNext(speedOnRightLane, true, false);
						isGoingToChangeLane = true;
					}
				} 
				
				if (!isGoingToChangeLane) {
					// Nein, trödeln.
					car.setNext(calculateTrödel(car), false, false);
				}
			}
		}

		clearConflicts();

	}

	private void clearConflicts() {
		for (Lane lane : this.track.getLanes()) {

			Car car = lane.getLastCar();
			while (car != null) {

				if (car.isBlinkLeft() || car.isBlinkRight()) {
					Lane leftlane = car.getNextLane().getLeftLane();
					Lane rightLane = car.getNextLane().getRightLane();

					if (leftlane != null) {

						Car blinkingRightCar = leftlane.getLastCar();

						while (blinkingRightCar != null) {

							if (blinkingRightCar.isBlinkRight() && blinkingRightCar.getId() != car.getId()) {

								if (blinkingRightCar.getNextBackPosition() <= car.getNextPosition()
										&& blinkingRightCar.getNextPosition() >= car.getNextBackPosition()) {
									int blinkingRightcarspeed = calculateNextSpeed(blinkingRightCar.getLane(), blinkingRightCar);
									blinkingRightCar.setNext(blinkingRightcarspeed, false, false);
								}
							}
							blinkingRightCar = leftlane.getPreviousCar(blinkingRightCar);
						}
					}

					if (rightLane != null) {

						Car blinkingLeftCar = rightLane.getLastCar();

						while (blinkingLeftCar != null) {

							if (blinkingLeftCar.isBlinkLeft() && blinkingLeftCar.getId() != car.getId()) {

								if (blinkingLeftCar.getNextBackPosition() <= car.getNextPosition()
										&& blinkingLeftCar.getNextPosition() >= car.getNextBackPosition()) {
									int blinkingLeftcarspeed = calculateNextSpeed(blinkingLeftCar.getLane(), blinkingLeftCar);
									blinkingLeftCar.setNext(blinkingLeftcarspeed, false, false);
								}
							}
							blinkingLeftCar = rightLane.getPreviousCar(blinkingLeftCar);
						}
					}
				}

				car = lane.getPreviousCar(car);
			}
		}
	}

	private void moveCar(Lane lane, Car car) {
		if (car.isToBeDeleted()) {
			lane.removeCar(car);
			this.track.addToOldCars(car);
		} else {
			if (!car.isMoved()) {
				car.saveState();

				car.setSpeed(car.getNextSpeed());
				car.setPosition(car.getNextPosition());
				car.setLane(car.getNextLane());

				if ((lane.getFastLaneIndex() != car.getNextLane().getFastLaneIndex())) {
					lane.removeCar(car);
					car.getLane().addCar(car);
				} else {
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

		if (!(lane.isOpenToTraffic(car.getPosition()))) {
			return false;
		}

		if (hasCars) {
			Car closestAfter = lane.getClosestAfter(car);
			
			Car closestBefore;
			int maxNextPosition;
			int minNextPosition;

			if (closestAfter == null) {
				closestBefore = lane.getClosestBefore(car);
			} else {
				closestBefore = lane.getPreviousCar(closestAfter);
			}

			if (closestAfter != null) {
				maxNextPosition = closestAfter.getBackPosition() - securityDistance;
			} else {
				maxNextPosition = lane.getLength();
			}
			if (closestBefore != null) {
				minNextPosition = closestBefore.getPosition() + closestBefore.getSpeed() + this.speedDelta + securityDistance;
				//Wenn auf nicht einer Einfahrspur wird mehr Rücksicht genommen auf andere Verkersteilnehmer 
				if(!car.getLane().isOpenToTraffic(car.getPosition())){
					minNextPosition += closestBefore.getSpeed();
				} 
			} else {
				minNextPosition = car.getPosition();
			}

			int currentCarTemporaryNextBackPosition = car.getBackPosition() + calculateNextSpeed(lane, car);
			int gapLength = maxNextPosition - minNextPosition;

			if (gapLength > car.getLength() && minNextPosition < currentCarTemporaryNextBackPosition) {
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
		if (lane == null) {
			return 0;
		}
		int speed = car.getSpeed();
		// Beschleunigen
		if (car.getSpeed() < lane.getMaxVelocity(car.getPosition())) {
			if (car.getSpeed() + this.speedDelta > lane.getMaxVelocity(car.getPosition())) {
				speed = lane.getMaxVelocity(car.getPosition());
			} else {
				speed = car.getSpeed() + this.speedDelta;
			}
		} else {
			speed = lane.getMaxVelocity(car.getPosition());
		}

		// nächstes Auto
		Car nextCar = lane.getClosestAfter(car);
		if (nextCar != null) {
			int availableSpace = nextCar.getBackPosition() - securityDistance - car.getPosition(); // Sicherheitsabstand
			speed = setSpeedAccordingToAvaliableSpace(speed, availableSpace);
		}

		// Rechts überholen
		if (lane.getLeftLane() != null) {
			Car leftCar = lane.getLeftLane().getClosestAfter(car);
			// Es darf rechts überholt werden, wenn die Geschwindigkeit von car und leftCar kleiner gleich speedDelta ist.
			if (leftCar != null && !(car.getSpeed() <= speedDelta && leftCar.getSpeed() <= speedDelta) && lane.isOpenToTraffic(car.getPosition())) {
				int speedDelta = (leftCar.getPosition() + leftCar.getSpeed()) - (car.getPosition() + speed);
				if (speedDelta < 0) {
					speed += speedDelta;
				}
			}
		}

		if (!lane.isOpenToTraffic(car.getPosition())) {
			if((car.getPosition() - lane.beginningOfIsOpenToTrafficSegment(car.getPosition())) > 50){
				speed = 0;
			}else{
				speed = (int) (speed * 0.7);				
			}
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
