package model;

public class Car {

	private int id;
	private double trödelFactor;
	private int position;
	private int speed;
	private Lane lane;	

	public Car(int id,int speed, double trödelFactor, int position, Lane lane) {
		this.id = id;
		this.trödelFactor = trödelFactor;
		this.position = position;
		this.speed = speed;
		this.lane = lane;
	}

	public int getId() {
		return id;
	}

	public double getTrödelFactor() {
		return trödelFactor;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Lane getLane() {
		return this.lane;
	}
}