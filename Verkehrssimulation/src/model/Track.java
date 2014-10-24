package model;

import java.util.ArrayList;

/**
 * @author stahr2
 * 
 * Repräsentiert die Strecke
 *
 */
public class Track {
	private ArrayList<Lane> Lanes;
		
	public Track(){
		this.Lanes = new ArrayList<Lane>();		
	}
	
	public void addLane(Lane lane){
		this.Lanes.add(lane);
	}
	
	public Lane getLane(int Lane){
		return this.Lanes.get(Lane);
	}
	
	public ArrayList<Lane> getLanes() {
		return this.Lanes;
	}
}
