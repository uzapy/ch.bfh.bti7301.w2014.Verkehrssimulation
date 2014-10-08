package model;

import java.util.ArrayList;
import java.util.Random;

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
			lane = new Lane(true, true, 120, 10000);
		}
	}
	
	public Track(){
		this.Track = new ArrayList<Lane>();		
	}

	public ArrayList<Lane> getTrack() {
		return Track;
	}
	
	public void addLane(Lane lane){
		this.Track.add(lane);
	}

	public void setTrack(ArrayList<Lane> lanes) {
		Track = lanes;
	}
	
	public Lane getLane(int Lane){
		return this.Track.get(Lane);
	}
	
	public Track update(){
		Lane lane = this.Track.get(0);
		Car car = lane.getLane().firstEntry().getValue();

		while(!(car == null))
		{
			Random rn = new Random();
			double res = rn.nextDouble();
			if(car.getSpeed() < lane.getMaxVelocity()){
				car.setSpeed(car.getSpeed() + 1);
			}
			if(!(lane.getNextCar(car) == null)){
				if((lane.getNextCar(car).getPosition() - car.getPosition()) < car.getSpeed()){
					car.setSpeed(lane.getNextCar(car).getPosition() - car.getPosition());
				}	
			}
			else{
				int rest = lane.getLength() - car.getPosition();
				int firstCarPosition = lane.getFirstCar().getPosition();
				if(rest + firstCarPosition < car.getSpeed()){
					car.setSpeed(rest + firstCarPosition);
				}		
			}
			if(res <= car.getFactor() && car.getSpeed() > 0){
				car.setSpeed(car.getSpeed()-1);
			}
			if((car.getPosition() + car.getSpeed()) > lane.getLength()){
				lane.removeCar(car);
				car.setPosition((car.getPosition() + car.getSpeed()) % lane.getLength());
				lane.addCar(car);
			}
			else{
				car.setPosition(car.getPosition() + car.getSpeed());		
			}
			car = lane.getNextCar(car);
		}
		return this;
	}

}
