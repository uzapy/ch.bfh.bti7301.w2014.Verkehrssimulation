package model;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
	private Lane leftLane;
	private Lane rightLane;
	private boolean isPassableLeft;
	private boolean isPassableRight;
	private Comparator<Car> byPosition = (car1, car2) -> -Integer.compare(car1.getPosition(), car2.getPosition());
	
	public Lane(int maxVelocity, int length, int fastLaneIndex){
		this.length = length;
		this.maxVelocity = maxVelocity;
		this.lane = new SimulationSkipList();
		this.fastLaneIndex = fastLaneIndex;
	}
	
	public void setAdjacentLanes(Lane leftLane, Lane rightLane) {
		this.isPassableLeft = (leftLane != null);
		if (isPassableLeft) {
			this.leftLane = leftLane;
		}

		this.isPassableRight = (rightLane != null);
		if (isPassableRight) {
			this.rightLane = rightLane;
		}
	}
	
	public boolean isPassableLeft() {
		return isPassableLeft;
	}

	public boolean isPassableRight() {
		return isPassableRight;
	}
	
	public Lane getLeftLane() {
		return leftLane;
	}

	public Lane getRightLane() {
		return rightLane;
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
	
	public List<Car> getSortedCars(){
		return this.lane.values().stream().sorted(byPosition).collect(Collectors.toList());
	}
}
