package model;

import java.util.ArrayList;
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
	private boolean passableLeft, passableRight;
	private int maxVelocity;
	public boolean isPassableLeft() {
		return passableLeft;
	}

	public void setPassableLeft(boolean passableLeft) {
		this.passableLeft = passableLeft;
	}

	public boolean isPassableRight() {
		return passableRight;
	}

	public void setPassableRight(boolean passableRight) {
		this.passableRight = passableRight;
	}

	public int getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(int maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public int getLength() {
		return Length;
	}

	public void setLength(int length) {
		Length = length;
	}

	private int Length;
	
	public Lane(boolean passableLeft, boolean passableRight, int maxVelocity, int Length){
		this.passableLeft = passableLeft;
		this.passableRight = passableRight;
		this.Length = Length;
		this.maxVelocity = maxVelocity;
		this.Lane = new SimulationSkipList<Integer, Car>();
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
