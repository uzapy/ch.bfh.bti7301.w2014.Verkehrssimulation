package model;

import java.util.Collection;
import java.util.Map.Entry;

/**
 * @author stahr2
 * 
 * Repräsentiert die Fahrspur
 *
 */
public class Lane {
	public static final int WIDTH = 4;	// Breite der Fahrspur in Metern
	
	private SimulationSkipList lane;
	private int maxVelocity;			// erlaubte Höchstgeschwindigkeit in Meter pro Sekunde
	private int fastLaneIndex;
	private int length;
	private Lane upperLane;
	private Lane lowerLane;
	private Track track;
	
	public Lane(int maxVelocity, int length, int fastLaneIndex){
		this.length = length;
		this.maxVelocity = maxVelocity;
		this.lane = new SimulationSkipList();
		this.fastLaneIndex = fastLaneIndex;
	}
	
	public Lane getUpperLane() {
		return upperLane;
	}

	public void setUpperLane() {
		if(this.fastLaneIndex != 0){
			this.upperLane = track.getLane(this.fastLaneIndex -1);
		}
	}

	public Lane getLowerLane() {
		return lowerLane;
	}

	public void setLowerLane() {
		if(this.fastLaneIndex < (track.getLanes().size() - 1)){
			this.upperLane = track.getLane(this.fastLaneIndex + 1);
		}
	}

	public void setTrack(Track track) {
		this.track = track;
		this.setLowerLane();
		this.setUpperLane();
	}

	public int getFastLaneIndex() {
		return fastLaneIndex;
	}

	public int getMaxVelocity() {
		return maxVelocity;
	}

	public int getLength() {
		return length;
	}

	public Car addCar(Car car) {
		return this.lane.put(car.getPosition(), car);
	}
	
	public boolean removeCar(Car car){
		return this.lane.remove(car.getPosition(), car);
	}
	
	public Car getCarByPostition(int position) {
		return this.lane.get(position);
	}
	
	public Car getNextCar(Car car) {
		Entry<Integer, Car> next = this.lane.higherEntry(car.getPosition());
		if (!(next == null)){
			return next.getValue();
		}
		return null;
	}
	
	public Car getPreviousCar(Car car) {
		return this.getCarByPostition(this.lane.lowerKey(car.getPosition()));
	}
	
	public Car getFirstCar(){
		return this.lane.firstEntry().getValue();
	}
	
	public SimulationSkipList getLane(){
		return this.lane;
	}
	
	public Collection<Car> getCars(){
		return this.lane.values();
	}
}
