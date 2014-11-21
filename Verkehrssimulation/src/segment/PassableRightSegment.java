package segment;

public class PassableRightSegment implements Segment {
	
	int start;
	int end;
	boolean passable;
	
	public PassableRightSegment(int start, int end, boolean passable){
		this.start = start;
		this.end = end;
		this.passable = passable;
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
	
	public boolean isPassable(){
		return this.passable;
	}

}
