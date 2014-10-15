/**
 * 
 */
package model;

import java.util.Collection;

import util.RandomPool;

/**
 * @author burkt4
 */
public class Nagel_Schreckenberg_Simulation {

	private Track track;
	private int simulationSpeed = 30;	// Simulationsgeschwindigkeit in Frames pro Sekunde
	private double trödelFactor = 0.2;
	
	/**
	 * @author bublm1
	 */
	public Nagel_Schreckenberg_Simulation() {
		Lane lane1 = new Lane(22,100,0);
		Lane lane2 = new Lane(28,100,1);
		Lane lane3 = new Lane(33,100,2);
		
		this.track = new Track();
		this.track.addLane(lane2);
		this.track.addLane(lane1);
		this.track.addLane(lane3);
		
		lane1.addCar(new Car(1,0,trödelFactor,0, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(2,0,trödelFactor,30, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(3,0,trödelFactor,40, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(4,0,trödelFactor,60, RandomPool.getNewCarLength(), lane1));
		lane1.addCar(new Car(5,0,trödelFactor,90, RandomPool.getNewCarLength(), lane1));
		
		lane2.addCar(new Car(6,0,trödelFactor,10, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(7,0,trödelFactor,40, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(8,0,trödelFactor,50, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(9,0,trödelFactor,60, RandomPool.getNewCarLength(), lane2));
		lane2.addCar(new Car(10,0,trödelFactor,80, RandomPool.getNewCarLength(), lane2));
		
		lane3.addCar(new Car(11,0,trödelFactor,10, RandomPool.getNewCarLength(), lane3));
		lane3.addCar(new Car(12,0,trödelFactor,40, RandomPool.getNewCarLength(), lane3));
		lane3.addCar(new Car(13,0,trödelFactor,50, RandomPool.getNewCarLength(), lane3));
		lane3.addCar(new Car(14,0,trödelFactor,60, RandomPool.getNewCarLength(), lane3));
		lane3.addCar(new Car(15,0,trödelFactor,80, RandomPool.getNewCarLength(), lane3));
	}
	
	public Track performStep(){
		for (Lane lane : this.track.getAllLanes()) {
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

				if (RandomPool.nextDouble() <= car.getTrödelFactor()) {
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

		return track;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public Track getTrack() {
		return this.track;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public int getSimulationSpeed() {
		return this.simulationSpeed;
	}

}
