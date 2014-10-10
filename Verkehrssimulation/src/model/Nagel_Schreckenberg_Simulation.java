/**
 * 
 */
package model;

/**
 * @author burkt4
 */
public class Nagel_Schreckenberg_Simulation {

	private Track track;
	private Lane lane1, lane2, lane3;
	private double trödelFactor = 0.2;
	
	/**
	 * @author bublm1
	 */
	public Nagel_Schreckenberg_Simulation() {
		this.lane1 = new Lane(5,15,0);
		this.lane2 = new Lane(7,15,1);
		this.lane3 = new Lane(8,15,2);
		
		this.track = new Track();
		this.track.addLane(lane2);
		this.track.addLane(lane1);
		this.track.addLane(lane3);
		
		lane1.addCar(new Car(1,0,trödelFactor,0, lane1));
		lane1.addCar(new Car(2,0,trödelFactor,3, lane1));
		lane1.addCar(new Car(3,0,trödelFactor,4, lane1));
		lane1.addCar(new Car(4,0,trödelFactor,6, lane1));
		lane1.addCar(new Car(5,0,trödelFactor,9, lane1));
		
		lane2.addCar(new Car(6,0,trödelFactor,1, lane2));
		lane2.addCar(new Car(7,0,trödelFactor,4, lane2));
		lane2.addCar(new Car(8,0,trödelFactor,5, lane2));
		lane2.addCar(new Car(9,0,trödelFactor,6, lane2));
		lane2.addCar(new Car(10,0,trödelFactor,8, lane2));
		
		lane3.addCar(new Car(11,0,trödelFactor,1, lane3));
		lane3.addCar(new Car(12,0,trödelFactor,4, lane3));
		lane3.addCar(new Car(13,0,trödelFactor,5, lane3));
		lane3.addCar(new Car(14,0,trödelFactor,6, lane3));
		lane3.addCar(new Car(15,0,trödelFactor,8, lane3));
	}
	
	public Track performStep(){
		this.track.update();
		return track;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public Track getTrack() {
		return this.track;
	}

}
