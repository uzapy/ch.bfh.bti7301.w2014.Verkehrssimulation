/**
 * 
 */
package segment;

/**
 * Repräsentiert ein Segment um die Maximalgeschwindigkeit zu beschränken
 * @author stahr2
 */

public class VelocitySegment implements Segment {
	
	private int start;
	private int end;
	private int maxVelocity;

	/**
	 * @author bublm1
	 * @param start			Anfang des Segmentes
	 * @param end			Ende des Segmentes	
	 * @param maxvelocity	Maximalgeschwindigkeit auf diesem Segment
	 */
	public VelocitySegment(int start, int end, int maxVelocity) {
		this.start = start;
		this.end = end;
		this.maxVelocity = maxVelocity;
	}
	
	/* (non-Javadoc)
	 * @see model.Segment#start()
	 */
	@Override
	public int start() {
		return this.start;
	}
	
	/* (non-Javadoc)
	 * @see model.Segment#end()
	 */
	@Override
	public int end() {
		return this.end;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public int getMaxVelocity() {
		return maxVelocity;
	}
}
