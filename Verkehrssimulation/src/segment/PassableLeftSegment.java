package segment;

public class PassableLeftSegment implements Segment {
	
	int start;
	int end;
	boolean passable;

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
	
	public boolean isPassable(){
		return this.passable;
	}

}
