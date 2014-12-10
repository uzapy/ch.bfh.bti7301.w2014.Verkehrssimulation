package model;

import java.util.Iterator;
import java.util.List;

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
 * 
 * Repräsentiert die Fahrspur
 *
 */
public class Lane implements Iterable<Locator<Integer, Car>> {
	public static final int WIDTH = 4;	// Breite der Fahrspur in Metern
	
	private MySkipList<Integer, Car> lane;
	private int maxVelocity;			// erlaubte Höchstgeschwindigkeit in Meter pro Sekunde
	private int fastLaneIndex;
	private int length;
	private Lane leftLane;
	private Lane rightLane;
	private boolean isPassableLeft;
	private boolean isPassableRight;
	private SegmentCollection segments;
	
	public Lane(int maxVelocity, int length, int fastLaneIndex){
		this.length = length;
		this.maxVelocity = maxVelocity;
		this.lane = new MySkipList<Integer, Car>(-1, this.length+1);
		this.fastLaneIndex = fastLaneIndex;
		this.segments = new SegmentCollection(this.length);
	}
	
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
	
	public boolean isOpenToTraffic(int position){
		Segment foundSegment = this.getSegmentAt(position, OpenToTrafficSegment.class);
		if(foundSegment != null){
			return ((OpenToTrafficSegment)foundSegment).isOpenToTraffic();
		} else {
			return true;
		}
	}
	
	public Lane getLeftLane() {
		return leftLane;
	}

	public Lane getRightLane() {
		return rightLane;
	}

	public int getFastLaneIndex() {
		return fastLaneIndex;
	}

	public int getMaxVelocity(int position) {
		Segment foundSegment = this.getSegmentAt(position, VelocitySegment.class);
		if (foundSegment != null) {
			return ((VelocitySegment)foundSegment).getMaxVelocity();
		} else {
			return maxVelocity;			
		}
	}
	
	public boolean isMeasurable(int position){
		Segment foundSegment = this.getSegmentAt(position, MeasuringSegment.class);
		if (foundSegment != null){
			return true;
		} else {
			return false;
		}
	}
	
	public void includeInMeasurement(Car car){
		MeasuringSegment foundSegment = (MeasuringSegment) this.getSegmentAt(car.getPosition(), MeasuringSegment.class);
		if (foundSegment != null){
			foundSegment.register(car);
		}
	}

	public void excludeFromMeasurement(Car car){
		MeasuringSegment foundSegment = (MeasuringSegment) this.getSegmentAt(180, MeasuringSegment.class);
		if (foundSegment != null){
			foundSegment.deRegister(car);
		}
	}
	
	public int getLength() {
		return length;
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
	
	public Car getClosestBefore(Car car) {
		Locator<Integer, Car> foundCar = this.lane.find(car.getPosition());
		if (foundCar != null) {
			return foundCar.element();
		} else {
			return getElementIfPresent(this.lane.closestBefore(car.getLocator().key()));
		}
	}

	public Car getFirstCar(){
		return getElementIfPresent(this.lane.min());
	}
	
	public Car getLastCar() {
		return getElementIfPresent(this.lane.max());
	}
	
	public boolean containsKey(Car car) {
		return (this.lane.find(car.getLocator().key()) != null);
	}
	
	/**
	 * @author burkt4
	 * @param car
	 */
	public void updateCarPosition(Car car) {
		this.lane.updateKey(car.getLocator(), car.getPosition());
	}
	
	/**
	 * @author bublm1
	 * @param velocitySegment1
	 */
	public void addSegment(Segment segment) {
		this.segments.add(segment);		
	}
	
	@SuppressWarnings("rawtypes")
	private Segment getSegmentAt(int position, Class segmentClass) {
		return this.segments.get(position, segmentClass);
	}

	/**
	 * @author bublm1
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Segment> getSegments(Class segmentClass) {
		return this.segments.getSegments(segmentClass);
	}
	
	/**
	 * @author bublm1
	 * @param locator
	 * @return
	 */
	private Car getElementIfPresent(Locator<Integer, Car> locator) {
		if (locator != null) {
			return locator.element();
		} else {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Locator<Integer, Car>> iterator() {
		return this.lane.sortedLocators();
	}
}
