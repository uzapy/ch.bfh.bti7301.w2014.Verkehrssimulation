package model;

import java.util.List;

import util.IPositionAndLength;

public class Car implements IPositionAndLength {
	public static final int WIDTH = 2;	// Breite eines Autos in Meter
	private int id;
	private double trödelFactor;
	private int length;					// Länge des Autos in Meter
	private int position;				// Position in Meter
	private int nextPosition;			// Zukünftige Position in Meter
	private int speed;					// Gesschwindigkeit in Meter pro Sekunde
	private int nextSpeed;				// Zukünftige Geschwindigkeit in Meter pro Sekunde
	private Lane lane;
	private Lane nextLane;
	private boolean isBlinkingLeft;
	private boolean isBlinkingRight;
	private Neighborhood<Car> neighborhood = new Neighborhood<Car>();

	public Car(int id, int speed, double trödelFactor, int position, int length, Lane lane) {
		this.id = id;
		this.trödelFactor = trödelFactor;
		this.length = length;
		this.position = position;
		this.nextPosition = position + speed;
		this.speed = speed;
		this.nextSpeed = speed;
		this.lane = lane;
		this.nextLane = lane;
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
	
	public int getNextPosition() {
		return this.nextPosition;
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

	public int getNextSpeed() {
		return nextSpeed;
	}

	public void setNextSpeed(int nextSpeed) {
		this.nextSpeed = nextSpeed;
	}

	public Lane getLane() {
		return this.lane;
	}
	
	public void setLane(Lane lane) {
		this.lane = lane;
	}

	public Lane getNextLane() {
		return this.nextLane;
	}

	public void setNextLane(Lane lane) {
		this.nextLane = lane;
	}

	public int getLength() {
		return this.length;
	}

	public boolean isBlinkingLeft() {
		return isBlinkingLeft;
	}

	public void setBlinkingLeft(boolean isBlinkingLeft) {
		this.isBlinkingLeft = isBlinkingLeft;
	}

	public boolean isBlinkingRight() {
		return isBlinkingRight;
	}

	public void setBlinkingRight(boolean blinkingRight) {
		this.isBlinkingRight = blinkingRight;
	}

	public void setNeigborhood(List<Car> neighboringCars, int radius) {
		this.neighborhood.setNeighbors(neighboringCars, radius, this);
	}
	
	public Neighborhood<Car> getNeigborhood() {
		return this.neighborhood;
	}

	@Override
	public int getFastLaneIndex() {
		return this.lane.getFastLaneIndex();
	}

	public void setNext(int nextSpeed, Lane nextLane, boolean isBlinkingRight, boolean isBlinkingLeft) {
		this.nextPosition = (this.position + this.speed) % this.nextLane.getLength();
		
		this.nextSpeed = nextSpeed;
		this.nextLane = nextLane;
		this.isBlinkingRight = isBlinkingRight;
		this.isBlinkingLeft = isBlinkingLeft;
		
	}
}
