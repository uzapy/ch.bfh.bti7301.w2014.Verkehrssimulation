/**
 * 
 */
package segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import skiplist.Locator;
import skiplist.MySkipList;

/**
 * @author bublm1
 */
public class SegmentCollection {
	
	private List<Segment> segments = new ArrayList<Segment>();
	@SuppressWarnings("rawtypes")
	private HashMap<Class, MySkipList<Integer, Segment>> segmentsPool = new HashMap<Class, MySkipList<Integer, Segment>>();
	private int length;
	
	public SegmentCollection(int length){
		this.length = length;
	}
	
	/**
	 * @author bublm1,burkt4, stahr2
	 * @param position
	 * @param segmentType
	 * @return
	 */
	public Segment get(int position, @SuppressWarnings("rawtypes") Class segmentClass) {
		
		Segment foundSegment = null;

		if (segmentsPool.containsKey(segmentClass)) {
			MySkipList<Integer, Segment> segmentList = segmentsPool.get(segmentClass);
			Locator<Integer, Segment> result = segmentList.find(position);
			if (result != null) {
				foundSegment = result.element();
			} else {
				result = segmentList.closestBefore(position);
				if (result != null) {
					int end = result.element().end();
					if (position <= end) {
						foundSegment = result.element();
					}
				}
			}
		}
		return foundSegment;
		//return this.segments.stream().filter(s -> s.getClass() == segmentClass && s.start() <= position && s.end() >= position).findFirst();
	}

	/**
	 * @author bublm1,burkt4
	 * @param segment
	 */
	public void add(Segment segment) {
		this.segments.add(segment);

		if (!segmentsPool.containsKey(segment.getClass())) {
			segmentsPool.put(segment.getClass(), new MySkipList<Integer, Segment>(-1, this.length + 1));
		}
		MySkipList<Integer, Segment> segmentList = segmentsPool.get(segment.getClass());
		// TODO check for overlap
		Locator<Integer, Segment> result = segmentList.closestBefore(segment.end());
		if (result != null && result.element().end() >= segment.start()) {
			throw new RuntimeException("segments overlap!");
		} else {
			result = segmentList.find(segment.end());
			if (result != null) {
				throw new RuntimeException("segments overlap!");
			}
		}
		segmentList.insert(segment.start(), segment);
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public List<Segment> getSegments() {
		return this.segments;
	}
}
