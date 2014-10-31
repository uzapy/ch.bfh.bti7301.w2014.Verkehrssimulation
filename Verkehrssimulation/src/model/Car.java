package model;

import java.util.ArrayList;
import java.util.List;

public class Car {
	public static final int WIDTH = 2;	// Breite eines Autos in Meter
	private int id;
	private double trödelFactor;
	private int length;					// Länge des Autos in Meter
	private int position;				// Position in Meter
	private int currentSpeed;			// Gesschwindigkeit in Meter pro Sekunde
	private int nextSpeed;				// Zukünftige Geschwindigkeit in Meter pro Sekunde
	private Lane currentLane;
	private Lane nextLane;
	private boolean isBlinkingLeft;
	private boolean isBlinkingRight;
	private List<Car> neighborhood = new ArrayList<Car>();

	public Car(int id, int speed, double trödelFactor, int position, int length, Lane lane) {
		this.id = id;
		this.trödelFactor = trödelFactor;
		this.position = position;
		this.currentSpeed = speed;
		this.currentLane = lane;
		this.nextLane = lane;
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
		return currentSpeed;
	}

	public void setSpeed(int speed) {
		this.currentSpeed = speed;
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
	
	public void setCurrentLane(Lane lane) {
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

	/**
	 * @author bublm1
	 * @param collect
	 */
	public void setNeigborhood(List<Car> neighboringCars) {
		this.neighborhood = neighboringCars;
	}
	
	public List<Car> getNeigborhood() {
		return this.neighborhood;
	}
}
