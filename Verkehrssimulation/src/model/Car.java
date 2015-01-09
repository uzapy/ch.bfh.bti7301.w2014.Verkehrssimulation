package model;

import java.util.LinkedList;
import java.util.List;

import segment.DoomSegment;
import segment.Segment;
import skiplist.Locator;

/**
 * Repräsentiert ein Auto
 * @author bublm1
 */
public class Car {

	public static final int WIDTH = 2;	// Breite eines Autos in Meter
	private int id;						// Identifikationsnummer
	private double trödelFactor;		// Trödel-Faktor zwischen 0.2 und 0.5
	private int position;	 			// Position in Meter
	private int nextPosition;			// Zukünftige Position in Meter
	private int speed; 					// Gesschwindigkeit in Meter pro Sekunde
	private int nextSpeed;				// Zukünftige Geschwindigkeit in Meter pro Sekunde
	private int length;					// Länge des Autos in Meter
	private Lane lane;					// Aktuell befahrene Spur
	private Lane nextLane;				// Zukünftig befahrene Spur
	private boolean isBlinkingLeft;		// Linker Blinker
	private boolean isBlinkingRight;	// Rechter Blinker
	private boolean isMoved;			// Wurde das Auto im aktuellen Schritt bewegt?
	private boolean isToBeDeleted;		// Markiert zum löschen (Ausfahrt)
	private Locator<Integer, Car> locator; // Locator des Autos in der Skip-List
	private LinkedList<Car> memory = new LinkedList<Car>(); // Kollektion von bisherigen Zuständen des Autos

	/**
	 * @author bublm1
	 * @param id			Identifikationsnummer
	 * @param speed			Gesschwindigkeit in Meter pro Sekunde
	 * @param trödelFactor	Trödel-Faktor zwischen 0.2 und 0.5
	 * @param position		Position in Meter
	 * @param length		Länge des Autos in Meter
	 * @param lane			Aktuell befahrene Spur
	 */
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
		return nextPosition;
	}
	
	public void setNextPosition(int nextPosition) {
		this.nextPosition = nextPosition;
	}
	
	public int getBackPosition() {
		return this.position - this.length;
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

	public int getLength() {
		return this.length;
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
	
	public boolean isBlinkingLeft() {
		return isBlinkingLeft;
	}

	public void setBlinkingLeft(boolean blinkingLeft) {
		this.isBlinkingLeft = blinkingLeft;
	}

	public boolean isBlinkingRight() {
		return isBlinkingRight;
	}

	public void setBlinkingRight(boolean blinkingRight) {
		this.isBlinkingRight = blinkingRight;
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
	
	public Locator<Integer, Car> getLocator() {
		return this.locator;
	}
	
	public void setLocator(Locator<Integer, Car> locator) {
		this.locator = locator;
	}

	/**
	 * Definiert den nächsten Schritt des Autos
	 * @author stahr2
	 * @param nextSpeed			Zukünftige Geschwindigkeit in Meter pro Sekunde
	 * @param isBlinkingRight	Rechter Blinker
	 * @param isBlinkingLeft	Linker Blinker
	 */
	public void setNext(int nextSpeed, boolean isBlinkingRight, boolean isBlinkingLeft) {
		this.nextSpeed = nextSpeed;
		this.isBlinkingRight = isBlinkingRight;
		this.isBlinkingLeft = isBlinkingLeft;

		// Zukünftig befahrene Spur setzen abhängig des gesetzten Blinkers
		if (isBlinkingRight) {
			nextLane = lane.getRightLane();
		} else if (isBlinkingLeft) {
			nextLane = lane.getLeftLane();
		} else {
			nextLane = lane;
		}
		
		// TODO: Nach Nagel_Schreckenberg verschieben
		// Falls sich das Auto auf eine Ausfahrtsstrecke (DoomSegment) auffährt, soll es im nächsten Schritt gelöscht werden.
		List<Segment> doomSegments = lane.getSegments(DoomSegment.class);
		if (!doomSegments.isEmpty()) {
			for (Segment segment : doomSegments) {
				boolean isBackPositionNearSegmentEnd = getBackPosition() >= segment.end() - lane.getMaxVelocity(position);
				boolean isPositionSmallerThanSegmentEnd = position <= segment.end();
				if (isBackPositionNearSegmentEnd && isPositionSmallerThanSegmentEnd) {
					this.isToBeDeleted = true;
				}
			}
		}
		
		// Auto im nächsten Schritt löschen, wenn es am Ende der Strecke ist. Ansonsten nächste Position berechnen.
		if (position + nextSpeed > nextLane.getLength()) {
			this.isToBeDeleted = true;
		} else {
			nextPosition = (position + nextSpeed);
		}
	}

	/**
	 * Bisherigen Zustand des Autos speichern
	 * @author bublm1
	 */
	public void saveState() {
		Car previousState = new Car(this.id, this.speed, this.trödelFactor, this.position, this.length, this.lane);
		previousState.setBlinkingLeft(this.isBlinkingLeft);
		previousState.setBlinkingRight(this.isBlinkingRight);
		
		// Zustand speichern
		this.memory.add(previousState);
		// Ältesten Zustand löschen
		if (this.memory.size() > 5) {
			this.memory.removeFirst();
		}
	}

	/**
	 * Hat das Auto im letzten Schritt die Spur gewechselt
	 * @author bublm1
	 * @return true wenn das Auto im letzten Schritt die Spur gewechselt hat
	 */
	public boolean hasChangedLanesBefore() {
		return this.memory.size() > 0 && (this.memory.getLast().isBlinkingLeft() || this.memory.getLast().isBlinkingRight());
	}
}