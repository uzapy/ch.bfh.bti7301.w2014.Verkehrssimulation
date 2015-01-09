package model;

import java.util.ArrayList;

/**
 * Repräsentiert die Autobahn mit mehreren Spuren
 * @author stahr2
 */
public class Track {
	
	private ArrayList<Car> oldCars;	// Autos die im nächsten Schritt gelöscht werden
	private ArrayList<Car> newCars; // Autos die im nächsten Schritt in eine Spur fahren
	private ArrayList<Lane> lanes;	// Kollektion aller Autobahn-Spuren

	/**
	 * Autobahn mit mehreren Spuren
	 * @author bublm1
	 */
	public Track(){
		this.lanes = new ArrayList<Lane>();	
		this.newCars = new ArrayList<Car>();
		this.oldCars = new ArrayList<Car>();
	}
	
	public void addLane(Lane lane){
		this.lanes.add(lane);
	}
	
	public Lane getLane(int Lane){
		return this.lanes.get(Lane);
	}
	
	public ArrayList<Lane> getLanes() {
		return this.lanes;
	}
	
	public void addToOldCars(Car oldCar){
		this.oldCars.add(oldCar);
	}
	
	public ArrayList<Car> getOldCars() {
		return oldCars;
	}
	
	public void clearOldCars(){
		this.oldCars.clear();
	}
	
	public void addToNewCars(Car newCar){
		this.newCars.add(newCar);		
	}
	
	public ArrayList<Car> getNewCars() {
		return newCars;
	}
	
	public void clearNewCars(){
		this.newCars.clear();
	}
}
