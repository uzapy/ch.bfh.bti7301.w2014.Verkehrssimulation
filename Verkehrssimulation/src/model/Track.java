package model;

/**
 * @author stahr2
 * 
 * Repräsentiert die Strasse
 *
 */
public class Track {
	private SkipList<Car> Track;
	
	public Track(){
		this.Track = new SkipList<Car>();
	}
	
	public boolean addCar(Car car)
	{
		return this.Track.addNode(new SkipListNode(car.getPostition(),car));
	}

}
