/**
 * 
 */
package model;

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

	/**
	 * @author bublm1
	 */
	public Nagel_Schreckenberg_Simulation() {
		Lane lane0 = new Lane(22, 150, 0);
		Lane lane1 = new Lane(28, 150, 1);
		Lane lane2 = new Lane(33, 150, 2);
//		Lane lane3 = new Lane(39, 150, 3);

		this.track = new Track();
		this.track.addLane(lane0);
		this.track.addLane(lane1);
		this.track.addLane(lane2);
//		this.track.addLane(lane3);
		
		this.track.addCar(new Car( 1,  1, trödelFactor,  9, RandomPool.getNewCarLength(), lane0));
		this.track.addCar(new Car( 2,  3, trödelFactor, 37, RandomPool.getNewCarLength(), lane0));
		this.track.addCar(new Car( 3,  5, trödelFactor, 68, RandomPool.getNewCarLength(), lane0));
//		this.track.addCar(new Car( 4,  7, trödelFactor, 95, RandomPool.getNewCarLength(), lane0));
//		this.track.addCar(new Car( 5,  9, trödelFactor, 45, RandomPool.getNewCarLength(), lane0));
		
		this.track.addCar(new Car( 6, 11, trödelFactor, 43, RandomPool.getNewCarLength(), lane1));
		this.track.addCar(new Car( 7, 13, trödelFactor, 54, RandomPool.getNewCarLength(), lane1));
		this.track.addCar(new Car( 8, 15, trödelFactor, 62, RandomPool.getNewCarLength(), lane1));
//		this.track.addCar(new Car( 9,  0, trödelFactor, 14, RandomPool.getNewCarLength(), lane1));
//		this.track.addCar(new Car(10,  2, trödelFactor, 97, RandomPool.getNewCarLength(), lane1));
		
		this.track.addCar(new Car(11,  4, trödelFactor, 25, RandomPool.getNewCarLength(), lane2));
		this.track.addCar(new Car(12,  6, trödelFactor, 85, RandomPool.getNewCarLength(), lane2));
		this.track.addCar(new Car(13,  8, trödelFactor, 52, RandomPool.getNewCarLength(), lane2));
		this.track.addCar(new Car(14, 10, trödelFactor,  4, RandomPool.getNewCarLength(), lane2));
//		this.track.addCar(new Car(15, 12, trödelFactor, 40, RandomPool.getNewCarLength(), lane2));
//		this.track.addCar(new Car(16, 14, trödelFactor, 71, RandomPool.getNewCarLength(), lane2));
	}

	public Track performStep() {
		// Pro Auto
		for (Car car : this.track.getAllCars()) {
			
			this.track.createNeighborhood(car);

			car.setPosition((car.getPosition() + car.getSpeed()) % car.getNextLane().getLength());
			
				// Kann ich auf die normale Spur zurück?
				// Ja: Spur wechseln
				// Nein: Geschwindigkeit normal berechnen
				
				// Will ich überholen?
				// Nein: Geschwindigkeit normal berechnen
				
				// Kann ich überholen?
				// Nein: Geschwindigkeit normal berechnen
				// Ja: Spur wechseln
				
				// Auto bewegen
			if (car.getId() == 6) {
				car.getNeigborhood().stream().forEach((Car c) -> System.out.print(" " + c.getId()));
				System.out.println();
			}
		}
		return this.track;
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
