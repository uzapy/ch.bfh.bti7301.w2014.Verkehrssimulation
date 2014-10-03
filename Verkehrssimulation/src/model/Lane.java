package model;

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
	
	public Lane(boolean passableLeft, boolean passableright, int maxVelocity, int Length){
		this.passableLeft = passableLeft;
		this.passableRight = passableRight;
		this.Length = Length;
		this.maxVelocity = maxVelocity;
		this.Lane = new SimulationSkipList<Integer, Car>();
	}
	
	public Car addCar(Car car) {
		return this.Lane.put(car.getPosition(), car);
	}
	
	public Car getCarByKey(int key) {
		return this.Lane.get(key);
	}
	
	public Car getNextCar(Car car) {
		return this.getCarByKey((this.Lane.higherKey(car.getPosition())));
	}
	
	public Car getPreviousCar(Car car) {
		return this.getCarByKey(this.Lane.lowerKey(car.getPosition()));
	}
	
	public SimulationSkipList<Integer, Car> getLane(){
		return this.Lane;
	}

}
