package model;

import java.util.ArrayList;
import java.util.Collection;
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
				if(((lane.getNextCar(car).getPosition() - car.getPosition()) - 1) < car.getSpeed()){
					car.setSpeed(lane.getNextCar(car).getPosition() - car.getPosition() - 1);
				}	
			}
			else{
				int rest = lane.getLength() - car.getPosition();
				int firstCarPosition = lane.getFirstCar().getPosition();
				if(rest + firstCarPosition -1 < car.getSpeed()){
					car.setSpeed(rest + firstCarPosition -1);
				}		
			}
			if(res <= car.getFactor() && car.getSpeed() > 0){
				car.setSpeed(car.getSpeed()-1);
			}

			car = lane.getNextCar(car);
		}
		car = lane.getLane().firstEntry().getValue();


		Collection<Car> cars = lane.getAllCars();
		for (Car  currentCar : cars){
			//if((currentCar.getPosition() + currentCar.getSpeed()) > lane.getLength()){
				//lane.removeCar(currentCar);
				//Car oldCar = currentCar;
				int oldPosition = currentCar.getPosition();
				currentCar.setPosition((currentCar.getPosition() + currentCar.getSpeed()) % lane.getLength());
				lane.getLane().updatePosition(oldPosition, currentCar.getPosition());
				//lane.addCar(currentCar);
			//}
			//else{
			//	currentCar.setPosition(currentCar.getPosition() + currentCar.getSpeed());
			//}
			
		}
		
		return this;
		
		
		
	}

}
