/**
 * 
 */
package model;

import java.util.Random;

/**
 * @author burkt4
 */
public class Nagel_SchreckenbergSimulation_SkipList {

	private Track track;
	private Lane lane1, lane2, lane3;
	private double troedelFactor = 0.2;
	
	public Track initializeSimulation(){
		this.track = new Track();
		this.lane1 = new Lane(false,false,5,15);
		this.track.addLane(lane1);
		initTrack();
		return track;
	}
	
	public Track performStep(){
		this.track.update();
		return track;
	}
	/**
	 * @author burkt4
	 */
	private  void initTrack() {
		lane1.addCar(new Car(1,0,troedelFactor,0));
		lane1.addCar(new Car(2,0,troedelFactor,3));
		lane1.addCar(new Car(3,0,troedelFactor,4));
		lane1.addCar(new Car(4,0,troedelFactor,6));
		lane1.addCar(new Car(5,0,troedelFactor,9));

	}

}
