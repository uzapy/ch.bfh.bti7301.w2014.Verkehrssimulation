package model;

import skiplist.Locator;
import skiplist.MySkipList;

/**
 * @author stahr2
 * 
 * Repräsentiert die Fahrspur
 *
 */
public class Lane {
	public static final int WIDTH = 4;	// Breite der Fahrspur in Metern
	
	private MySkipList<Integer, Car> lane;
	private int maxVelocity;			// erlaubte Höchstgeschwindigkeit in Meter pro Sekunde
	private int fastLaneIndex;
	private int length;
	private Lane leftLane;
	private Lane rightLane;
	private boolean isPassableLeft;
	private boolean isPassableRight;
	
	public Lane(int maxVelocity, int length, int fastLaneIndex){
		this.length = length;
		this.maxVelocity = maxVelocity;
		this.lane = new MySkipList<Integer, Car>(-1, this.length);
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

	public void addCar(Car car) {
		car.setLocator(this.lane.insert(car.getPosition(), car));
	}
	
	public void removeCar(Car car){
		this.lane.remove(car.getLocator());
	}
	
	public Car getNextCar(Car car) {
		Locator<Integer, Car> found = this.lane.next(car.getLocator());
		if (found != null) {
			return found.element();
		} else {
			return null;
		}
	}
	
	public Car getPreviousCar(Car car) {
		Locator<Integer, Car> found = this.lane.previous(car.getLocator());
		if (found != null) {
			return found.element();
		} else {
			return null;
		}
	}
	
	public Car getClosestAfter(Car car) {
		Locator<Integer, Car> found = this.lane.closestAfter(car.getLocator().key());
		if (found != null) {
			return found.element();
		} else {
			return null;
		}
	}
	
	public Car getClosestBefore(Car car) {
		Locator<Integer, Car> found = this.lane.closestBefore(car.getLocator().key());
		if (found != null) {
			return found.element();
		} else {
			return null;
		}
	}

	public Car getFirstCar(){
		Locator<Integer, Car> found = this.lane.min();
		if (found != null) {
			return found.element();
		} else {
			return null;
		}
	}
	
	public Car getLastCar() {
		Locator<Integer, Car> found = this.lane.max();
		if (found != null) {
			return found.element();
		} else {
			return null;
		}
	}
	
	public boolean containsKey(Car car) {
		return (this.lane.find(car.getLocator().key()) != null);
	}
}
