package model;

import java.util.ArrayList;

/**
 * @author stahr2
 * 
 * Repräsentiert die Strecke
 *
 */
public class Track {
	private ArrayList<Lane> lanes;
		
	public Track(){
		this.lanes = new ArrayList<Lane>();		
	}
	
	public void addLane(Lane lane){
		this.lanes.add(lane);
	}
	
	public Lane getLane(int laneIndex){
		if (laneIndex >= 0 && laneIndex <= (this.lanes.size() - 1)) {
			return this.lanes.get(laneIndex);			
		} else {
			return null;
		}
	}
	
	public ArrayList<Lane> getLanes() {
		return this.lanes;
	}
}
