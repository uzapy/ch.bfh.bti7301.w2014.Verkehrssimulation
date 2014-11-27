package model;

import skiplist.Locator;

public class Car {

	public static final int WIDTH = 2;	// Breite eines Autos in Meter
	private int id;
	private double trödelFactor;
	private int position;	 			// Position in Meter
	private int nextPosition;			// Zukünftige Position in Meter
	private int speed; 					// Gesschwindigkeit in Meter pro Sekunde
	private int nextSpeed;				// Zukünftige Geschwindigkeit in Meter pro Sekunde
	private int length;					// Länge des Autos in Meter
	private Lane lane;
	private Lane nextLane;
	private boolean blinkLeft;
	private boolean blinkRight;
	private Locator<Integer, Car> locator;
	private boolean isMoved;
	private boolean isToBeDeleted;

	public Car(int id,int speed, double trödelFactor, int position, int length, Lane lane) {
		this.id = id;
		this.trödelFactor = trödelFactor;
		this.position = position;
		this.speed = speed;
		this.length = length;
		this.lane = lane;
		this.nextLane = lane;
		this.isMoved = true;
		this.isToBeDeleted = false;
	}

	public boolean isMoved() {
		return isMoved;
	}

	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}
	
	public boolean isToBeDeleted() {
		return isToBeDeleted;
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
	
	public int getNextPosition() {
		return nextPosition;
	}
	
	public void setNextPosition(int nextPosition) {
		this.nextPosition = nextPosition;
	}
	
	public int getNextBackPosition() {
		return this.nextPosition - this.length;
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
	
	public boolean isBlinkLeft() {
		return blinkLeft;
	}

	public void setBlinkLeft(boolean blinkLeft) {
		this.blinkLeft = blinkLeft;
	}

	public boolean isBlinkRight() {
		return blinkRight;
	}

	public void setBlinkRight(boolean blinkRight) {
		this.blinkRight = blinkRight;
	}
	
	public void setLocator(Locator<Integer, Car> locator) {
		this.locator = locator;
	}
	
	public Locator<Integer, Car> getLocator() {
		return this.locator;
	}

	/**
	 * @author bublm1
	 * @param nextSpeed
	 * @param blinkRight
	 * @param blinkLeft
	 */
	public void setNext(int nextSpeed, boolean blinkRight, boolean blinkLeft) {
		this.nextSpeed = nextSpeed;
		this.blinkRight = blinkRight;
		this.blinkLeft = blinkLeft;
		
		if (blinkRight) {
			nextLane = lane.getRightLane();
		} else if (blinkLeft) {
			nextLane = lane.getLeftLane();
		} else {
			nextLane = lane;
		}
		if(position + nextSpeed > nextLane.getLength()){
			this.isToBeDeleted = true;
		}
		else{
			nextPosition = (position + nextSpeed);			
		}
	}
}