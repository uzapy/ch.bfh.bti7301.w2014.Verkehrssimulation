package segment;

/**
 * Segment, um eine Strasse nicht für ein Überholmanöver zuzulassen
 * @author stahr2, burkt4, bublm1
 */

public class PassableSegment implements Segment {
	
	private int start;
	private int end;
	private boolean passableLeft;
	private boolean passableRight;
	
	/**
	 * Ein Segment zur Beschränkung von Überholmanövern
	 * @author bublm1
	 * @param start			Start des Segmentes
	 * @param end			Ende des Segmentes 
	 * @param passableLeft	Spurbeschränkung nach links: false = darf nicht nach links verlassen werden, true = darf nach links verlassen werden 
	 * @param passableRight	Spurbeschränkung nach rechts: false = darf nicht nach rechts verlassen werden, true = darf nach rechts verlassen werden
	 */
	
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
