package segment;

public class PassableSegment implements Segment {
	
	private int start;
	private int end;
	private boolean passableLeft;
	private boolean passableRight;
	
	public PassableSegment(int start, int end, boolean passableLeft, boolean passableRight){
		this.start = start;
		this.end = end;
		this.passableLeft = passableLeft;
		this.passableRight = passableRight;
	}

	@Override
	public int start() {
		return this.start;
	}

	@Override
	public int end() {
		return this.end;
	}
	
	public boolean isPassableLeft(){
		return this.passableLeft;
	}
	
	public boolean isPassableRight(){
		return this.passableRight;
	}
}
