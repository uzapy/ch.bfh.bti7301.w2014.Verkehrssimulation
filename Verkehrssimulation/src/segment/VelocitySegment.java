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
	
	@Override
	public int start() {
		return this.start;
	}

	@Override
	public int end() {
		return this.end;
	}

	public int getMaxVelocity() {
		return maxVelocity;
	}
}
