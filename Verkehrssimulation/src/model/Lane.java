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

}
