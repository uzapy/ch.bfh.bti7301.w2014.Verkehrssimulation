package model;

import java.util.ArrayList;

/**
 * @author stahr2
 * 
 * Repräsentiert die Strecke
 *
 */
public class Track {
	
	private ArrayList<Car> oldCars;
	private ArrayList<Car> newCars;
	private ArrayList<Lane> Lanes;
		
	public Track(){
		this.Lanes = new ArrayList<Lane>();	
		this.newCars = new ArrayList<Car>();
		this.oldCars = new ArrayList<Car>();
	}
	
	public void addLane(Lane lane){
		this.Lanes.add(lane);
	}
	
	public Lane getLane(int Lane){
		return this.Lanes.get(Lane);
	}
	
	public ArrayList<Lane> getLanes() {
		return this.Lanes;
	}
	
	public ArrayList<Car> getOldCars() {
		return oldCars;
	}
	
	public void addToOldCars(Car oldCar){
		this.oldCars.add(oldCar);
	}
	
	public ArrayList<Car> getNewCars() {
		return newCars;
	}
	
	public void addToNewCars(Car newCar){
		this.newCars.add(newCar);		
	}
	
	public void clearOldCars(){
		this.oldCars.clear();
	}
	
	public void clearNewCars(){
		this.newCars.clear();
	}
}
