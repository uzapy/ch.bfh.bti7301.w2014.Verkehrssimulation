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
	private SimulationSkipList<Integer, Car> Lane;
	private int maxVelocity;	// erlaubte Höchstgeschwindigkeit in Meter pro Sekunde
	private int fastLaneIndex;
	private int length;
	
	public Lane(int maxVelocity, int length, int fastLaneIndex){
		this.length = length;
		this.maxVelocity = maxVelocity;
		this.Lane = new SimulationSkipList<Integer, Car>();
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
		return this.Lane.put(car.getPosition(), car);
	}
	
	public boolean removeCar(Car car){
		return this.Lane.remove(car.getPosition(), car);
	}
	
	public Car getCarByPostition(int position) {
		return this.Lane.get(position);
	}
	
	public Car getNextCar(Car car) {
		Entry<Integer, Car> next = this.Lane.higherEntry(car.getPosition());
		if (!(next == null)){
			return next.getValue();
		}
		return null;
	}
	
	public Car getPreviousCar(Car car) {
		return this.getCarByPostition(this.Lane.lowerKey(car.getPosition()));
	}
	
	public Car getFirstCar(){
		return this.Lane.firstEntry().getValue();
	}
	
	public SimulationSkipList<Integer, Car> getLane(){
		return this.Lane;
	}
	
	public Collection<Car> getAllCars(){
		
		return this.Lane.values();
	}
	
}
