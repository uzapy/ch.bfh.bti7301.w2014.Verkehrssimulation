package model;

import java.util.ArrayList;

/**
 * @author stahr2
 * 
 * Repräsentiert die Strecke
 *
 */

public class Track {
	private ArrayList<Lane> Track;
	
	public Track(ArrayList<Lane> Lanes){
		this.setTrack(Lanes);
		for (Lane lane : Track) {
			lane = new Lane(true, true,120, 10000);
		}
	}

	public ArrayList<Lane> getTrack() {
		return Track;
	}

	public void setTrack(ArrayList<Lane> lanes) {
		Track = lanes;
	}
	
	public Lane getLane(int Lane){
		return this.Track.get(Lane);
	}
	
	public void update(){
		for (Lane lane : Track) {
			Car car = lane.getLane().firstEntry().getValue();
			Lane nextLane = Track.iterator().next();
			
			while(!car.equals(null))
			{
				if(car.getSpeed() < lane.getMaxVelocity()){
					car.setSpeed(car.getSpeed() + 1);
				}
				if((lane.getNextCar(car).getPosition() - car.getPosition()) < car.getSpeed()){
					car.setSpeed(lane.getNextCar(car).getPosition() - car.getPosition());
				}
				
				car = lane.getNextCar(car);
			}
		}
	}

}
