package segment;

/**
 * Interface für ein Segment - dieses enthält in jedem Fall eine Anfangs- und Endposition
 * @author bublm1
 */
public interface Segment {

	/**
	 * Start-Position des Segments
	 * @author bublm1
	 * @return Start-Position
	 */
	public abstract int start();

	/**
	 * End-Position des Seghments
	 * @author bublm1
	 * @return End-Position
	 */
	public abstract int end();

}