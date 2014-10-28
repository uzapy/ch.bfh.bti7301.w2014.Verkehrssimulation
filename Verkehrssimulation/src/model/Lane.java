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
	
	public Lane(int maxVelocity, int length, int fastLaneIndex){
		this.length = length;
		this.maxVelocity = maxVelocity;
		this.lane = new SimulationSkipList();
		this.fastLaneIndex = fastLaneIndex;
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
	
	public boolean removeCar(int oldPosition, Car car){
		return this.lane.remove(oldPosition, car);
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
	
	public Car getPreviousCarOrSelf(Car car) {
		if(lane.containsKey(car.getPosition())){
			return this.getCarByPostition(car.getPosition());
		}
		Entry<Integer, Car> next = lane.lowerEntry(car.getPosition());
		if (!(next == null)){
			return next.getValue();
		}
		return null;
	}
	
	public Car getFirstCar(){
		Entry<Integer, Car> first = this.lane.firstEntry();
		if (!(first == null)){
			return first.getValue();
		}
		return null;
	}
	
	public SimulationSkipList getLane(){
		return this.lane;
	}
	
	public Collection<Car> getCars(){
		return this.lane.values();
	}
}
