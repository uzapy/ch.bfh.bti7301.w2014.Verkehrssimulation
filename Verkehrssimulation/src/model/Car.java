package model;

public class Car {

	private int id;
	private double factor;
	private int position;
	private int speed;
	private Lane lane;	

	public Car(int id,int speed, double rand, int position, Lane lane) {
		this.id = id;
		this.factor = rand;
		this.position = position;
		this.speed = speed;
		this.lane = lane;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the factor
	 */
	public double getFactor() {
		return factor;
	}

	/**
	 * @param factor the factor to set
	 */
	public void setFactor(double factor) {
		this.factor = factor;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public Lane getLane() {
		return this.lane;
	}
}