package segment;

public class SpawnSegment implements Segment {
	
	private int start;
	private int end;
	
	public SpawnSegment(int start, int end){
		this.start = start;
		this.end = end;
	}

	@Override
	public int start() {
		// TODO Auto-generated method stub
		return this.start;
	}

	@Override
	public int end() {
		// TODO Auto-generated method stub
		return this.end;
	}

}
