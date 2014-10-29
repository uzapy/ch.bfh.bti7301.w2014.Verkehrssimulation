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

		this.track = new Track();
		this.track.addLane(lane0);
		this.track.addLane(lane1);
		this.track.addLane(lane2);
		this.track.addLane(lane3);
		
		this.track.addCar(new Car(1, 0, trödelFactor, 0,  RandomPool.getNewCarLength(), lane0));
		this.track.addCar(new Car(2, 0, trödelFactor, 30, RandomPool.getNewCarLength(), lane0));
		this.track.addCar(new Car(3, 0, trödelFactor, 40, RandomPool.getNewCarLength(), lane0));
		this.track.addCar(new Car(4, 0, trödelFactor, 60, RandomPool.getNewCarLength(), lane0));
		this.track.addCar(new Car(5, 0, trödelFactor, 90, RandomPool.getNewCarLength(), lane0));
	
		this.track.addCar(new Car(6, 0, trödelFactor, 10, RandomPool.getNewCarLength(), lane1));
		this.track.addCar(new Car(7, 0, trödelFactor, 40, RandomPool.getNewCarLength(), lane1));
		this.track.addCar(new Car(8, 0, trödelFactor, 50, RandomPool.getNewCarLength(), lane1));
		this.track.addCar(new Car(9, 0, trödelFactor, 60, RandomPool.getNewCarLength(), lane1));
		this.track.addCar(new Car(10,0, trödelFactor, 80, RandomPool.getNewCarLength(), lane1));

		this.track.addCar(new Car(11, 0, trödelFactor, 10, RandomPool.getNewCarLength(), lane2));
		this.track.addCar(new Car(12, 0, trödelFactor, 40, RandomPool.getNewCarLength(), lane2));
		this.track.addCar(new Car(13, 0, trödelFactor, 50, RandomPool.getNewCarLength(), lane2));
		this.track.addCar(new Car(14, 0, trödelFactor, 60, RandomPool.getNewCarLength(), lane2));
		this.track.addCar(new Car(15, 0, trödelFactor, 80, RandomPool.getNewCarLength(), lane2));

		this.track.addCar(new Car(15, 0, trödelFactor, 80, RandomPool.getNewCarLength(), lane3));
	}

	public Track performStep() {
		// Pro Auto
		
			// Nachbarschaft definieren
			
			// Kann ich auf die normale Spur zurück?
				// Ja: Spur wechseln
				// Nein: Geschwindigkeit normal berechnen
		
			// Will ich überholen?
				// Nein: Geschwindigkeit normal berechnen
		
			// Kann ich überholen?
				// Nein: Geschwindigkeit normal berechnen
				// Ja: Spur wechseln
			
			// Auto bewegen
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
