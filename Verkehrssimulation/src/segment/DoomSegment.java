package segment;

/**
 * Repräsentiert eine Ausfahrt
 * @author stahr2
 */

public class DoomSegment implements Segment {
	
	private int start;
	private int end;
	
	public DoomSegment(int start, int end){
		this.start = start;
		this.end = end;
	}

	@Override
	public int start() {
		return this.start;
	}

	@Override
	public int end() {
		return this.end;
	}

}
