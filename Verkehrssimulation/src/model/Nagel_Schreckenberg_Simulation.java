/**
 * 
 */
package model;

import util.RandomPool;

/**
 * @author burkt4
 */
public class Nagel_Schreckenberg_Simulation {

	public static int FRAMES_PER_SECOND = 30; // Simulationsgeschwindigkeit in
												// Frames pro Sekunde
	private Track track;
	private int speedDelta = 5; // Standardbeschleunigung in Meter pro Sekunde
	private double trödelFactor = 0.3;
	private int securityDistance = 1;

	/**
	 * @author bublm1
	 */
	public Nagel_Schreckenberg_Simulation() {
		Lane lane1 = new Lane(22, 100, 0);
		Lane lane2 = new Lane(28, 100, 1);
		Lane lane3 = new Lane(33, 100, 2);

		this.track = new Track();
		this.track.addLane(lane1);
		this.track.addLane(lane2);
		this.track.addLane(lane3);

		lane1.addCar(new Car(1, 0, trödelFactor, 0, RandomPool
				.getNewCarLength(), lane1));
		// lane1.addCar(new Car(2,0,trödelFactor,30,
		// RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(3, 0, trödelFactor, 40, RandomPool
				.getNewCarLength(), lane1));
		lane1.addCar(new Car(4, 0, trödelFactor, 60, RandomPool
				.getNewCarLength(), lane1));
		lane1.addCar(new Car(5, 0, trödelFactor, 90, RandomPool
				.getNewCarLength(), lane1));

		lane2.addCar(new Car(6, 0, trödelFactor, 10, RandomPool
				.getNewCarLength(), lane2));
		lane2.addCar(new Car(7, 0, trödelFactor, 40, RandomPool
				.getNewCarLength(), lane2));
		lane2.addCar(new Car(8, 0, trödelFactor, 50, RandomPool
				.getNewCarLength(), lane2));
		lane2.addCar(new Car(9, 0, trödelFactor, 60, RandomPool
				.getNewCarLength(), lane2));
		// lane2.addCar(new Car(10,0,trödelFactor,80,
		// RandomPool.getNewCarLength(), lane2));

		lane3.addCar(new Car(11, 0, trödelFactor, 10, RandomPool
				.getNewCarLength(), lane3));
		lane3.addCar(new Car(12, 0, trödelFactor, 40, RandomPool
				.getNewCarLength(), lane3));
		lane3.addCar(new Car(13, 0, trödelFactor, 50, RandomPool
				.getNewCarLength(), lane3));
		// lane3.addCar(new Car(14,0,trödelFactor,60,
		// RandomPool.getNewCarLength(), lane3));
		lane3.addCar(new Car(15, 0, trödelFactor, 80, RandomPool
				.getNewCarLength(), lane3));
	}

	public Track performStep() {
		for (Lane lane : this.track.getLanes()) {
			for (Car car : lane.getCars()) {
				moveCar(lane, car);
			}
		}
		int total = 0;
		for (Lane lane : this.track.getLanes()) {
			System.out.println("Number of Cars on Lane"
					+ lane.getFastLaneIndex() + ": " + lane.getLane().size());
			total += lane.getLane().size();
			for (Car car : lane.getCars()) {
				car.setMoved(false);
				int speedFastLane = getNewSpeed(lane.getLeftLane(), car);
				int speedSlowLane = getNewSpeed(lane.getRightLane(), car);
				int speedCurrentLane = getNewSpeed(lane, car);
				Car previousCar = null;
				if (speedSlowLane == speedCurrentLane && lane.isPassableRight()
						&& lane.getRightLane() != null) {
					if (lane.getRightLane().getPreviousCar(car) != null) {
						previousCar = lane.getRightLane().getPreviousCar(car);
					} else if (lane.getRightLane().getLane().lastEntry() != null) {
						previousCar = lane.getRightLane().getLane().lastEntry()
								.getValue();
					}
					if (previousCar != null) {
						int previousCarNextPosition = (previousCar
								.getPosition()
								+ previousCar.getSpeed()
								+ speedDelta + securityDistance)
								% lane.getRightLane().getLength();
						if (previousCarNextPosition <= car.getBackPosition()
								+ speedSlowLane) {
							car.setNextSpeed(speedSlowLane);
							car.setBlinkRight(true);
							System.out.print("Car: " + car.getId()
									+ " LoopLane: " + lane.getFastLaneIndex()
									+ " CarLane: "
									+ car.getCurrentLane().getFastLaneIndex()
									+ " Car Position: " + car.getPosition());
							System.out.print(" SpeedLeft: " + speedFastLane
									+ " Speedcurrent: " + speedCurrentLane
									+ " SpeedRight: " + speedSlowLane);
							System.out.println(" BlinkLeft: "
									+ car.isBlinkLeft() + " BlinkRight: "
									+ car.isBlinkRight());
						} else {
							car.setNextSpeed(speedCurrentLane);
							calculateTrööödel(car);
						}
					} else {
						car.setNextSpeed(speedSlowLane);
						car.setBlinkRight(true);
						System.out.print("Car: " + car.getId() + " LoopLane: "
								+ lane.getFastLaneIndex() + " CarLane: "
								+ car.getCurrentLane().getFastLaneIndex()
								+ " Car Position: " + car.getPosition());
						System.out.print(" SpeedLeft: " + speedFastLane
								+ " Speedcurrent: " + speedCurrentLane
								+ " SpeedRight: " + speedSlowLane);
						System.out.println(" BlinkLeft: " + car.isBlinkLeft()
								+ " BlinkRight: " + car.isBlinkRight());
					}
				} else if (speedFastLane > speedCurrentLane
						&& lane.isPassableLeft()
						&& (lane.getLeftLane() != null)) {
					if (lane.getLeftLane().getPreviousCar(car) != null) {
						previousCar = lane.getLeftLane().getPreviousCar(car);
					} else if (lane.getLeftLane().getLane().lastEntry() != null) {
						previousCar = lane.getLeftLane().getLane().lastEntry()
								.getValue();
					}
					if (previousCar != null) {
						int previousCarNextPosition = previousCar.getPosition()
								+ previousCar.getSpeed() + speedDelta
								+ securityDistance;
						if (previousCarNextPosition <= car.getBackPosition()
								+ speedFastLane) {
							car.setNextSpeed(speedFastLane);
							car.setBlinkLeft(true);
							System.out.print("Car: " + car.getId()
									+ " LoopLane: " + lane.getFastLaneIndex()
									+ " CarLane: "
									+ car.getCurrentLane().getFastLaneIndex()
									+ " Car Position: " + car.getPosition());
							System.out.print(" SpeedLeft: " + speedFastLane
									+ " Speedcurrent: " + speedCurrentLane
									+ " SpeedRight: " + speedSlowLane);
							System.out.println(" BlinkLeft: "
									+ car.isBlinkLeft() + " BlinkRight: "
									+ car.isBlinkRight());
						} else {
							car.setNextSpeed(speedCurrentLane);
							calculateTrööödel(car);
						}
					} else {
						car.setNextSpeed(speedFastLane);
						car.setBlinkLeft(true);
						System.out.print("Car: " + car.getId() + " LoopLane: "
								+ lane.getFastLaneIndex() + " CarLane: "
								+ car.getCurrentLane().getFastLaneIndex()
								+ " Car Position: " + car.getPosition());
						System.out.print(" SpeedLeft: " + speedFastLane
								+ " Speedcurrent: " + speedCurrentLane
								+ " SpeedRight: " + speedSlowLane);
						System.out.println(" BlinkLeft: " + car.isBlinkLeft()
								+ " BlinkRight: " + car.isBlinkRight());
					}
				} else {
					car.setNextSpeed(speedCurrentLane);
					calculateTrööödel(car);
				}
			}
		}
		for (Lane lane : this.track.getLanes()) {
			for (Car car : lane.getCars()) {
				car.setSpeed(car.getNextSpeed());
			}
		}
		System.out.println("Total Number of Cars on Track:" + total);
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
			car.setPosition((oldPosition + car.getSpeed())
					% nextLane.getLength());
			if (lane.equals(nextLane)) {
				lane.getLane().updatePosition(oldPosition, car.getPosition());
			} else {
				lane.removeCar(oldPosition, car);
				nextLane.addCar(car);
			}

			car.setLane(nextLane);
			car.setBlinkLeft(false);
			car.setBlinkRight(false);
			car.setMoved(true);
		}

	}

	private void calculateTrööödel(Car car) {
		// Trödeln
		if (RandomPool.nextDouble() <= car.getTrödelFactor()) {
			if (car.getNextSpeed() > this.speedDelta) {
				car.setNextSpeed(car.getNextSpeed() - this.speedDelta);
			} else {
				car.setNextSpeed(0);
			}
		}
	}

	private int getNewSpeed(Lane lane, Car car) {
		// Lane existiert nicht --> speed auf 0 setzen;
		if (lane == null) {
			return 0;
		}

		int speed = car.getSpeed();
		// Beschleunigen
		if (car.getSpeed() < lane.getMaxVelocity()) {
			speed = car.getSpeed() + this.speedDelta;
		}
		// nächstes Auto
		if (!(lane.getNextCar(car) == null)) {
			int availableSpace = lane.getNextCar(car).getBackPosition()
					- car.getPosition() - securityDistance; // Sicherheitsabstand
			if (availableSpace < 0) {
				speed = 0;
			} else if (availableSpace < speed) {
				speed = availableSpace;
			}
			// Spur zu Ende
		} else {
			int rest = lane.getLength() - car.getPosition();
			Car nextCar = lane.getFirstCar();
			if (nextCar != null) {
				int firstCarPosition = lane.getFirstCar().getBackPosition();
				int availableSpace = rest + firstCarPosition - securityDistance;
				if (availableSpace < 0) {
					speed = 0;
				} else if (availableSpace < speed) {
					speed = availableSpace;
				}
			}

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
