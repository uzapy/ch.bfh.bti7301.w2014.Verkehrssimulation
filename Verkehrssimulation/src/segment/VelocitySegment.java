/**
 * 
 */
package segment;

/**
 * @author bublm1
 */
public class VelocitySegment implements Segment {
	
	private int start;
	private int end;
	private int maxVelocity;

	/**
	 * @author bublm1
	 * @param i
	 * @param j
	 * @param maxvelocity2
	 * @param k
	 */
	public VelocitySegment(int start, int end, int maxVelocity) {
		this.start = start;
		this.end = end;
		this.maxVelocity = maxVelocity;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public int getMaxVelocity() {
		return maxVelocity;
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

}
