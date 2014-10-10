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
		this.lane1 = new Lane(false,false,5,15,0);
		this.lane2 = new Lane(false,false,7,15,1);
		this.lane3 = new Lane(false,false,8,15,2);
		this.track.addLane(lane2);
		this.track.addLane(lane1);
		this.track.addLane(lane3);
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
		
		lane2.addCar(new Car(6,0,troedelFactor,1));
		lane2.addCar(new Car(7,0,troedelFactor,4));
		lane2.addCar(new Car(8,0,troedelFactor,5));
		lane2.addCar(new Car(9,0,troedelFactor,6));
		lane2.addCar(new Car(10,0,troedelFactor,8));
		
		lane3.addCar(new Car(11,0,troedelFactor,1));
		lane3.addCar(new Car(12,0,troedelFactor,4));
		lane3.addCar(new Car(13,0,troedelFactor,5));
		lane3.addCar(new Car(14,0,troedelFactor,6));
		lane3.addCar(new Car(15,0,troedelFactor,8));
	}

}
