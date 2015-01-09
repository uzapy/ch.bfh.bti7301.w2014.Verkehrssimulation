package segment;

/**
 * Repräsentiert eine Einfahrt
 * @author stahr2
 */
public class SpawnSegment implements Segment {
	
	private int start;
	private int end;
	
	public SpawnSegment(int start, int end){
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
