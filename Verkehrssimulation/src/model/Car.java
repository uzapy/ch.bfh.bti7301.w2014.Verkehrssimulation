package model;

public class Car {

	private int id;
	private double trödelFactor;
	private int position; 			// Position in Meter
	private int speed; 				// Gesschwindigkeit in Meter pro Sekunde
	private int length;				// Länge des Autos in Meter
	private Lane lane;

	public Car(int id,int speed, double trödelFactor, int position, int length, Lane lane) {
		this.id = id;
		this.trödelFactor = trödelFactor;
		this.position = position;
		this.speed = speed;
		this.lane = lane;
		this.length = length;
	}

	public Car(int id, int speed, double trödelFactor, int position,int length, int laneID) {
		// TODO Auto-generated constructor stub
		// TODO: Add constructor with Lane ID --> Place car on the Lane with the given ID
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
	
	public int getBackPosition() {
		return this.position - this.length;
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

	public int getLength() {
		return this.length;
	}
}