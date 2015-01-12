package model;

import java.util.List;

import segment.Segment;
import segment.SpawnSegment;
import skiplist.Locator;
import util.PresetPool;
import util.RandomPool;
import util.TrackPreset;

/**
 * Die Simulation
 * @author burkt4
 */
public class Nagel_Schreckenberg_Simulation {
	// TODO: umbenennen nach Simulation.java?
	
	private Track track;				// Die Autobahn mit mehreren Spuren
	private int speedDelta = 3;			// Standardbeschleunigung in Meter pro Sekunde
	private int securityDistance = 1;	// Sicherheitsabstand

	/**
	 * Die Simulation initialisiert mit einem Autobahn-Vorlage
	 * @author bublm1
	 * @param trackPreset	Situations-Vorlage
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

	/**
	 * Bewegt die Autos um einen Schritt und berechnet den zukünftigen Zustand aller Autos (Position, Spur und Geschwindigkeit)
	 * @author bublm1, burkt4, stahr2
	 */
	public void performStep() {
		for (Lane lane : this.track.getLanes()) {
			for (Locator<Integer, Car> carLocator : lane) {
				Car car = carLocator.element();

				// Autos auf Mess-Segment in die Messung hinzufügen und Autos ausserhalb des Mess-Segments von der Messung ausschliessen
				if (lane.isMeasurable(car.getPosition())) {
					lane.includeInMeasurement(car);
				} else {
					lane.excludeFromMeasurement(car);
				}

				// Auto bewegen
				moveCar(lane, car);
			}

			// Zufällig neues zufälliges Auto hinzufügen, wenn es Platz hat.
			if (RandomPool.isSpawning()) {
				boolean isFirstCarFarEnough = lane.getFirstCar() != null && lane.getFirstCar().getBackPosition() > 20;
				boolean isLaneOpenToTraffic = lane.isOpenToTraffic(0);
				if ((lane.getFirstCar() == null || isFirstCarFarEnough) && isLaneOpenToTraffic) {
					Car randomCar = RandomPool.getNewCar(lane);
					randomCar.getLane().addCar(randomCar);
					randomCar.setNext(randomCar.getSpeed(), false, false);
					randomCar.setMoved(true);
					this.track.addToNewCars(randomCar);
				}
				
				// Neue zufällige Autos auch auf SpawnSegments hunzufügen, wenn es Platz hat
				List<Segment> spawnSegments = lane.getSegments(SpawnSegment.class);
				if (!spawnSegments.isEmpty()) {
					for (Segment segment : spawnSegments) {
						
						Car randomCar = RandomPool.getNewCar(lane);
						randomCar.setPosition(segment.start() + randomCar.getLength());
						Car afterRandomCar = lane.getClosestAfter(randomCar.getPosition());
						Car beforeRandomCar = lane.getClosestBefore(randomCar.getPosition());

						if (afterRandomCar != null && beforeRandomCar != null) {
							if (((afterRandomCar.getBackPosition() - randomCar.getPosition()) > 10)
									&& ((randomCar.getBackPosition() - beforeRandomCar.getBackPosition()) > 10)) {
								randomCar.getLane().addCar(randomCar);
								randomCar.setNext(randomCar.getSpeed(), false, false);
								randomCar.setMoved(true);
								this.track.addToNewCars(randomCar);
							}
						} else {
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
				// Potentiellen nächsten Zustand berechnen
				car.setNext(calculateNextSpeed(car.getLane(), car), false, false);

				Car nextCar = car.getLane().getNextCar(car);
				if (nextCar == null) { // TODO: weg mit diesen If
					nextCar = car.getLane().getFirstCar();
				}

				// Will ich überholen?
				boolean isPassableLeft = lane.isPassableLeft(car.getPosition());
				boolean isFasterThanNextCar = car.getSpeed() > nextCar.getSpeed();
				boolean isNextCarClose = nextCar.getBackPosition() - car.getPosition() < lane.getMaxVelocity(car.getPosition());
				boolean hasChangedLanesBefore = car.hasChangedLanesBefore();
				boolean hasToChangeLane = !lane.isOpenToTraffic(car.getPosition());
				boolean isGoingToChangeLane = false;
				
				if ((isPassableLeft) && ((isFasterThanNextCar && isNextCarClose && !hasChangedLanesBefore) || hasToChangeLane)) {
					// Kann ich überholen?
					boolean canChangeToLeftLane = isEnoughSpaceBetweenBeforeAndAfter(lane.getLeftLane(), car);
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
					boolean canChangeToRightLane = isEnoughSpaceBetweenBeforeAndAfter(lane.getRightLane(), car);
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

		// Überholkonflikte auflösen
		clearConflicts();

	}

	/**
	 * Überholkonflikte auflösen. Z.B.: Ein auto auf der linken Spur und ein Auto auf der rechten Spur wollen beid auf die mittlere Spur wechseln.
	 * Dieser Konflikt wird so gelöst, dass nur ein Auto die Spur wechseln darf. Das andere Auto muss auf seiner aktuellen Spur weiterfahren.
	 * @author burkt4, stahr2
	 */
	private void clearConflicts() {
		// Von der obersten zur untersten Lane
		for (Lane lane : this.track.getLanes()) {
			// Vom Vordersten Auto zum hintersten
			Car car = lane.getLastCar();
			while (car != null) {

				if (car.isBlinkingLeft() || car.isBlinkingRight()) {
					Lane leftlane = car.getNextLane().getLeftLane();
					Lane rightLane = car.getNextLane().getRightLane();

					if (leftlane != null) {

						Car blinkingRightCar = leftlane.getLastCar();
						// durch alle Autos auf der LeftLane iterieren
						while (blinkingRightCar != null) {
							// Nur nach rechts blinkenede Autos berücksichtigen
							if (blinkingRightCar.isBlinkingRight() && blinkingRightCar.getId() != car.getId()) {
								// Falls die zwei Autos nach einem Spurwechsel kollidieren würden
								if (blinkingRightCar.getNextBackPosition() <= car.getNextPosition()
										&& blinkingRightCar.getNextPosition() >= car.getNextBackPosition()) {
									int blinkingRightcarspeed = calculateNextSpeed(blinkingRightCar.getLane(), blinkingRightCar);
									// Das Auto auf der linken Spur vom Überholen hindern
									blinkingRightCar.setNext(blinkingRightcarspeed, false, false);
								}
							}
							blinkingRightCar = leftlane.getPreviousCar(blinkingRightCar);
						}
					}

					if (rightLane != null) {

						Car blinkingLeftCar = rightLane.getLastCar();
						// durch alle Autos auf der RightLane iterieren
						while (blinkingLeftCar != null) {
							// Nur nach links blinkenede Autos berücksichtigen
							if (blinkingLeftCar.isBlinkingLeft() && blinkingLeftCar.getId() != car.getId()) {
								// Falls die zwei Autos nach einem Spurwechsel kollidieren würden
								if (blinkingLeftCar.getNextBackPosition() <= car.getNextPosition()
										&& blinkingLeftCar.getNextPosition() >= car.getNextBackPosition()) {
									int blinkingLeftcarspeed = calculateNextSpeed(blinkingLeftCar.getLane(), blinkingLeftCar);
									// Das Auto auf der rechten Spur vom Überholen hindern
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

	/**
	 * Bewegt das auto auf der Spur um einen Schritt
	 * @author bublm1, burkt4, stahr2
	 * @param lane
	 * @param car	
	 */
	private void moveCar(Lane lane, Car car) {
		// Das Auto löschen, wenn es die Strecke verlassen hat
		if (car.isToBeDeleted()) {
			lane.removeCar(car);
			this.track.addToOldCars(car);
		} else {
			if (!car.isMoved()) {
				car.saveState();

				car.setSpeed(car.getNextSpeed());
				car.setPosition(car.getNextPosition());
				car.setLane(car.getNextLane());

				// Falls die Spur gewechselt wurde, das Auto entfernt und auf der neuen Spur hinzugefügt. Ansinsten Soft-Update
				if ((lane.getFastLaneIndex() != car.getNextLane().getFastLaneIndex())) {
					lane.removeCar(car);
					car.getLane().addCar(car);
				} else {
					lane.updateCarPosition(car);
				}

				car.setBlinkingLeft(false);
				car.setBlinkingRight(false);
				car.setMoved(true);
			}
		}
	}

	/**
	 * Prüfen ob nach einem Spurwchsel genügend Platz auf der neuen Spur ist
	 * @author bublm1, burkt4, stahr2
	 * @param lane
	 * @param car
	 * @return true wenn es genügent platz hat
	 */
	private boolean isEnoughSpaceBetweenBeforeAndAfter(Lane lane, Car car) {
		boolean hasCars = lane.iterator().hasNext();

		// Ist die Spur befahrbar?
		if (!(lane.isOpenToTraffic(car.getPosition()))) {
			return false;
		}

		if (hasCars) {
			// Das Auto das sich unmittelbar vor dem aktuellen befindet.
			Car closestAfter = lane.getClosestAfter(car);
			// Das Auto das sich unmittelbar hinter dem aktuellen befindet.
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
			
			// Potentielle Position des hinteren Autos berechnen
			if (closestBefore != null) {
				minNextPosition = closestBefore.getPosition() + closestBefore.getSpeed() + this.speedDelta + securityDistance;
				// Wenn auf nicht einer Einfahrspur wird mehr Rücksicht genommen auf andere Verkersteilnehmer
				if (car.getLane().isOpenToTraffic(car.getPosition())) {
					minNextPosition += closestBefore.getSpeed();
				}
			} else {
				minNextPosition = car.getPosition();
			}

			// Potentielle (Heck-)Position des vorderen Autos berechnen
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
	 * Berechnet die Geschwindigkeit des Autos im nächsten Schritt auf der angegebenen Spur
	 * @author bublm1, burkt4, stahr2
	 * @param lane
	 * @param car
	 * @return Nächste Geschwindigkeit des Autos in m/s auf der angegebenen Spur
	 */
	private int calculateNextSpeed(Lane lane, Car car) {
		if (lane == null) {
			return 0;
		}
		
		int speed = car.getSpeed();
		
		// Beschleunigen (bis zur Höchstgeschwindigkeit)
		if (car.getSpeed() < lane.getMaxVelocity(car.getPosition())) {
			if (car.getSpeed() + this.speedDelta > lane.getMaxVelocity(car.getPosition())) {
				speed = lane.getMaxVelocity(car.getPosition());
			} else {
				speed = car.getSpeed() + this.speedDelta;
			}
		} else {
			speed = lane.getMaxVelocity(car.getPosition());
		}

		// Geschwindigkeit den Platzverhältnissen anpassen
		Car nextCar = lane.getClosestAfter(car);
		if (nextCar != null) {
			int availableSpace = nextCar.getBackPosition() - securityDistance - car.getPosition(); // Sicherheitsabstand
			
			if (availableSpace < 0) {
				speed = 0;
			} else if (availableSpace < speed) {
				speed = availableSpace;
			}
		}

		// Das Auto soll verkehrsteilnehmer nicht rechts überholen.
		if (lane.getLeftLane() != null) {
			Car leftCar = lane.getLeftLane().getClosestAfter(car);
			// Es darf rechts überholt werden, wenn die Geschwindigkeit von car und leftCar kleiner gleich speedDelta ist.
			boolean areBothCarsFast = car.getSpeed() >= speedDelta && leftCar != null && leftCar.getSpeed() >= speedDelta;
			if (leftCar != null && areBothCarsFast) {
				// Geschwindigkeit verkleinern, dass das linke Auto das rechte nicht überholt
				int nextPositionDifference = (leftCar.getPosition() + leftCar.getSpeed()) - (car.getPosition() + speed);
				if (nextPositionDifference < 0) {
					speed += nextPositionDifference;
				}
			}
		}

		// Geschwindigkeit verkleinern, wenn das Auto sich auf einer Spur befindet, dass zukünftig nicht mehr befahrbar ist (Fahrverbot)
		if (!lane.isOpenToTraffic(car.getPosition()) && !lane.isDoomSegment(car.getPosition())) {
			if ((car.getPosition() - lane.beginningOfIsOpenToTrafficSegment(car.getPosition())) > 50) {
				speed = 0;
			} else {
				speed = (int) (speed * 0.7);
			}
		}

		return speed;
	}

	/**
	 * Die Geschwindigkeit eines Autos verringern ohne Grund, mit der Wahrschenlchekeit p
	 * @author bublm1, burkt4, stahr2
	 * @param car
	 * @return
	 */
	private int calculateTrödel(Car car) {
		if (RandomPool.nextDouble() <= car.getTrödelFactor()) {
			return (car.getNextSpeed() > this.speedDelta) ? car.getNextSpeed() - this.speedDelta : 0;
		} else {
			return car.getNextSpeed();
		}
	}

	public Track getTrack() {
		return this.track;
	}
}
