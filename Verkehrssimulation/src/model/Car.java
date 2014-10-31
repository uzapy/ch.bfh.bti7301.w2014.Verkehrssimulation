package model;

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

	/**
	 * @return the moved
	 */
	public boolean isMoved() {
		return moved;
	}

	/**
	 * @param moved the moved to set
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public Car(int id,int speed, double trödelFactor, int position, int length, Lane lane) {
		this.id = id;
		this.trödelFactor = trödelFactor;
		this.position = position;
		this.speed = speed;
		this.currentLane = lane;
		this.nextLane = lane;
		this.length = length;
		this.moved = false;
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
	
	public int getNextPosition() {
		return nextPosition;
	}

	public void setNextPosition(int nextPosition) {
		this.nextPosition = nextPosition;
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
}