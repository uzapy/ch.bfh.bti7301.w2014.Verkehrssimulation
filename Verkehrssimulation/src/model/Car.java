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
	private Lane previousLane;
	private boolean isBlinkingLeft;
	private boolean isBlinkingRight;
	private boolean moved = false;

	public Car(int id,int speed, double trödelFactor, int position, int length, Lane lane) {
		this.id = id;
		this.trödelFactor = trödelFactor;
		this.position = position;
		this.speed = speed;
		this.currentLane = lane;
		this.previousLane = lane;
		this.length = length;
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
	
	public Lane getPreviousLane() {
		return this.previousLane;
	}
	
	public void setLane(Lane lane) {
		this.previousLane = this.currentLane;
		this.currentLane = lane;
	}

	public int getLength() {
		return this.length;
	}
	
	public boolean isBlinkingLeft() {
		return isBlinkingLeft;
	}

	public void setIsBlinkingLeft(boolean isBlinkingLeft) {
		this.isBlinkingLeft = isBlinkingLeft;
	}

	public boolean isBlinkingRight() {
		return isBlinkingRight;
	}

	public void setIsBlinkingRight(boolean isBlinkingRight) {
		this.isBlinkingRight = isBlinkingRight;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}