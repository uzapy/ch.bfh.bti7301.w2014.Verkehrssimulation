package model;

/**
 * @author stahr2
 * 
 * Repräsentiert die Fahrspur
 *
 */
public class Track {
	private SkipList<Car> Track;
	
	public Track(){
		this.Track = new SkipList<Car>();
	}

}
