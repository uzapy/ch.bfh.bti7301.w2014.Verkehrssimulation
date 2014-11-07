package model;

import skiplist.Locator;

public class Car {

	public static final int WIDTH = 2;	// Breite eines Autos in Meter
	private int id;
	private double trödelFactor;
	private int position; 				// Position in Meter
	private int speed; 					// Gesschwindigkeit in Meter pro Sekunde
	private int nextSpeed;				// Zukünftige Geschwindigkeit in Meter pro Sekunde
	private int length;					// Länge des Autos in Meter
	private Lane currentLane;
	private Lane nextLane;
	private boolean blinkLeft;
	private boolean blinkRight;
	private boolean moved;
	private int nextPosition;
	private Locator<Integer, Car> locator;

	public Car(int id,int speed, double trödelFactor, int position, int length, Lane lane) {
		this.id = id;
		this.trödelFactor = trödelFactor;
		this.position = position;
		this.speed = speed;
		this.currentLane = lane;
		this.nextLane = lane;
		this.length = length;
		this.moved = true;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
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
	
	public Lane getCurrentLane() {
		return this.currentLane;
	}
	
	public void setLane(Lane lane) {
		this.currentLane = lane;
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
	
	/**
	 * @author bublm1
	 * @return
	 */
	public Locator<Integer, Car> getLocator() {
		return this.locator;
	}

	/**
	 * @author bublm1
	 * @param speedOnSlowLane
	 * @param b
	 * @param c
	 * @param d
	 */
	public void setNext(int nextSpeed, boolean blinkRight, boolean blinkLeft) {
		this.nextSpeed = nextSpeed;
		this.blinkRight = blinkRight;
		this.blinkLeft = blinkLeft;
		
		if (blinkRight) {
			nextLane = currentLane.getRightLane();
		} else if (blinkLeft) {
			nextLane = currentLane.getLeftLane();
		} else {
			nextLane = currentLane;
		}
		
		nextPosition = (position + nextSpeed) % nextLane.getLength();
	}
}