package model;

/**
 * @author stahr2
 * 
 * Repräsentiert die Strecke
 *
 */

public class Track {
	private Lane[] Track;
	
	public Track(int Lanes){
		this.setTrack(new Lane[Lanes]);
		for (int i = 0; i < Track.length; i++) {
			Track[i] = new Lane(true, true,120, 10000);
		}
	}

	public Lane[] getTrack() {
		return Track;
	}

	public void setTrack(Lane[] track) {
		Track = track;
	}
	
	public Lane getLane(int Lane){
		return this.Track[Lane];
	}

}
