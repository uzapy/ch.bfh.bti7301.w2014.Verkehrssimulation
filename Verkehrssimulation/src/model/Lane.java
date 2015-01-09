package model;

import java.util.Iterator;
import java.util.List;

import segment.DoomSegment;
import segment.MeasuringSegment;
import segment.OpenToTrafficSegment;
import segment.PassableSegment;
import segment.Segment;
import segment.SegmentCollection;
import segment.VelocitySegment;
import skiplist.Locator;
import skiplist.MySkipList;

/**
 * @author stahr2
 * Repräsentiert die Fahrspur
 */
public class Lane implements Iterable<Locator<Integer, Car>> {
	public static final int WIDTH = 4;		// Breite der Fahrspur in Metern
	
	private MySkipList<Integer, Car> lane;	// Skip-List für die Autos
	private int maxVelocity;				// Erlaubte Höchstgeschwindigkeit in Meter pro Sekunde
	private int fastLaneIndex;				// Überhol-Spur-Index: Rechte Spur = 0, Erste Überholspur = 1, usw.
	private int length;						// Lände der Spur in Meter
	private Lane leftLane;					// Links angrenzende Spur
	private Lane rightLane;					// Rechts angrenzende Spur
	private boolean isPassableLeft;			// Kann auf die links angrenzende Spur gewechselt werden?
	private boolean isPassableRight;		// Kann auf die rechts angrenzende Spur gewechselt werden?
	private SegmentCollection segments;		// Kollektion von Segmenten mit speziellen Regeln
	
	/**
	 * Eine Spur auf der Autobahn
	 * @author bublm1
	 * @param maxVelocity	Erlaubte Höchstgeschwindigkeit in Meter pro Sekunde
	 * @param length		Lände der Spur in Meter
	 * @param fastLaneIndex	Überhol-Spur-Index: Rechte Spur = 0, Erste Überholspur = 1, usw.
	 */
	public Lane(int maxVelocity, int length, int fastLaneIndex){
		this.length = length;
		this.maxVelocity = maxVelocity;
		this.fastLaneIndex = fastLaneIndex;
		
		this.lane = new MySkipList<Integer, Car>(-1, this.length+1);
		this.segments = new SegmentCollection(this.length);
	}
	
	/**
	 * Adjazente Spuren definieren
	 * @author bublm1
	 * @param leftLane	Links angrenzende Spur
	 * @param rightLane	Rechts angrenzende Spur
	 */
	public void setAdjacentLanes(Lane leftLane, Lane rightLane) {
		this.isPassableLeft = (leftLane != null);
		if (isPassableLeft) {
			this.leftLane = leftLane;
		}

		this.isPassableRight = (rightLane != null);
		if (isPassableRight) {
			this.rightLane = rightLane;
		}
	}

	public int getMaxVelocity(int position) {
		Segment foundSegment = this.getSegmentAt(position, VelocitySegment.class);
		if (foundSegment != null) {
			return ((VelocitySegment)foundSegment).getMaxVelocity();
		} else {
			return maxVelocity;			
		}
	}

	public int getFastLaneIndex() {
		return fastLaneIndex;
	}
	
	public int getLength() {
		return length;
	}
	
	public Lane getLeftLane() {
		return leftLane;
	}

	public Lane getRightLane() {
		return rightLane;
	}
	
	public boolean isPassableLeft(int position) {
		Segment foundSegment = this.getSegmentAt(position, PassableSegment.class);
		if (foundSegment != null) {
			return ((PassableSegment)foundSegment).isPassableLeft();
		} else {
			return isPassableLeft;			
		}
	}

	public boolean isPassableRight(int position) {
		Segment foundSegment = this.getSegmentAt(position, PassableSegment.class);
		if (foundSegment != null) {
			return ((PassableSegment)foundSegment).isPassableRight();
		} else {
			return isPassableRight;			
		}
	}
	
	public void addSegment(Segment segment) {
		this.segments.add(segment);		
	}
	
	/**
	 * Streckensegement einer bestimmten Klasse an einem Strecken-Position abfragen
	 * @author burkt4
	 * @param position		Punkt auf der Strecke
	 * @param segmentClass	Segment-Typ
	 * @return				Das Segment wenn eines an dieser Position existiert; ansonsten null
	 */
	@SuppressWarnings("rawtypes")
	private Segment getSegmentAt(int position, Class segmentClass) {
		return this.segments.get(position, segmentClass);
	}

	/**
	 * Alle Segmente einer bestimmten Klasse auf der Strecke abfragen
	 * @author burkt4
	 * @param segmentClass	Segment-Typ
	 * @return				Alle Segmente auf der Strecke; ansonsten leere Kollektion
	 */
	@SuppressWarnings("rawtypes")
	public List<Segment> getSegments(Class segmentClass) {
		return this.segments.getSegments(segmentClass);
	}
	
	/**
	 * Ist sie Strecke an dieser Position befahrbar
	 * @author stahr2
	 * @param position	Punkt auf der Strecke
	 * @return			true wenn die Strecke an der Position befahrbar ist
	 */
	public boolean isOpenToTraffic(int position){
		Segment foundSegment = this.getSegmentAt(position, OpenToTrafficSegment.class);
		if(foundSegment != null){
			return ((OpenToTrafficSegment)foundSegment).isOpenToTraffic();
		} else {
			return true;
		}
	}
	
	/**
	 * Start-Position eines Fahrververbotes
	 * @author stahr2
	 * @param position	Punkt auf der Strecke
	 * @return			Start-Position eins Fahrverbots-Segments
	 */
	public int beginningOfIsOpenToTrafficSegment(int position){
		Segment foundSegment = this.getSegmentAt(position, OpenToTrafficSegment.class);
		if(foundSegment != null){
			return ((OpenToTrafficSegment)foundSegment).start();
		} else {
			return 0;
		}
	}
	
	/**
	 * Ist an der Position auf der Strecke ein Messsegment
	 * @author stahr2
	 * @param position	Punkt auf der Strecke
	 * @return			true wenn an dieser Position ein Messegmet existiert
	 */
	public boolean isMeasurable(int position){
		Segment foundSegment = this.getSegmentAt(position, MeasuringSegment.class);
		if (foundSegment != null){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Auto zur Messung hinzufügen
	 * @author stahr2
	 * @param car
	 */
	public void includeInMeasurement(Car car){
		MeasuringSegment foundSegment = (MeasuringSegment) this.getSegmentAt(car.getPosition(), MeasuringSegment.class);
		if (foundSegment != null){
			foundSegment.register(car);
		}
	}

	/**
	 * Auto aus der Messung ausschliessen
	 * @author stahr2
	 * @param car
	 */
	public void excludeFromMeasurement(Car car){
		MeasuringSegment foundSegment = (MeasuringSegment) this.getSegmentAt(180, MeasuringSegment.class);
		if (foundSegment != null){
			foundSegment.deRegister(car);
		}
	}

	public boolean isDoomSegment(int position) {
		return getSegmentAt(position, DoomSegment.class) != null;
	}

	public void addCar(Car car) {
		car.setLocator(this.lane.insert(car.getPosition(), car));
	}
	
	public void removeCar(Car car){
		this.lane.remove(car.getLocator());
	}
	
	public Car getNextCar(Car car) {
		return getElementIfPresent(this.lane.next(car.getLocator()));
	}
	
	public Car getPreviousCar(Car car) {
		return getElementIfPresent(this.lane.previous(car.getLocator()));
	}
	
	public Car getClosestAfter(Car car) {
		return getElementIfPresent(this.lane.closestAfter(car.getLocator().key()));
	}
	
	public Car getClosestAfter(int position) {
		return getElementIfPresent(this.lane.closestAfter(position));
	}
	
	public Car getClosestBefore(Car car) {
		Locator<Integer, Car> foundCar = this.lane.find(car.getPosition());
		if (foundCar != null) {
			return foundCar.element();
		} else {
			return getElementIfPresent(this.lane.closestBefore(car.getLocator().key()));
		}
	}
	
	public Car getClosestBefore(int position) {
		Locator<Integer, Car> foundCar = this.lane.find(position);
		if (foundCar != null) {
			return foundCar.element();
		} else {
			return getElementIfPresent(this.lane.closestBefore(position));
		}
	}

	public Car getFirstCar(){
		return getElementIfPresent(this.lane.min());
	}
	
	public Car getLastCar() {
		return getElementIfPresent(this.lane.max());
	}
	
	public void updateCarPosition(Car car) {
		// Soft-Update der Position des Autos auf der Strecke
		this.lane.updateKey(car.getLocator(), car.getPosition());
	}
	
	@Override
	public Iterator<Locator<Integer, Car>> iterator() {
		return this.lane.sortedLocators();
	}

	private Car getElementIfPresent(Locator<Integer, Car> locator) {
		if (locator != null) {
			return locator.element();
		} else {
			return null;
		}
	}
}
