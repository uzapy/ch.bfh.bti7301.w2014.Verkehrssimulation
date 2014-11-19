/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author bublm1
 */
public class SegmentCollection {
	
	private List<Segment> segments = new ArrayList<Segment>();

	/**
	 * @author bublm1
	 * @param position
	 * @param segmentType
	 * @return
	 */
	public Optional<Segment> get(int position, @SuppressWarnings("rawtypes") Class segmentClass) {
		return this.segments.stream().filter(s -> s.getClass() == segmentClass && s.start() <= position && s.end() >= position).findFirst();
	}

	/**
	 * @author bublm1
	 * @param segment
	 */
	public void add(Segment segment) {
		this.segments.add(segment);
	}

}
