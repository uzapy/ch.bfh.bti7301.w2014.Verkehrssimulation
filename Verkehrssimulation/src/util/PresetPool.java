/**
 * 
 */
package util;

import model.Lane;
import model.Track;
import segment.MeasuringSegment;
import segment.OpenToTrafficSegment;
import segment.Segment;
import segment.VelocitySegment;

/**
 * @author bublm1
 */
public class PresetPool {

	/**
	 * @author bublm1
	 * @return
	 */
	public static Track getDefault() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public static Track getBottleneck() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);

		Segment velocitySegment0 = new VelocitySegment(100, 200, 22);
		Segment velocitySegment1 = new VelocitySegment(100, 200, 22);
		Segment velocitySegment2 = new VelocitySegment(100, 200, 22);

		Segment velocitySegment3 = new VelocitySegment(200, 500, 17);
		Segment velocitySegment4 = new VelocitySegment(200, 500, 17);
		Segment velocitySegment5 = new VelocitySegment(200, 500, 17);

//		Segment notPassableSegment0 = new PassableSegment(20, 120, true, false);
//		Segment notPassableSegment1 = new PassableSegment(20, 120, false, true);
//		Segment notPassableSegment2 = new PassableSegment(20, 120, false, false);

		Segment measureSegment0 = new MeasuringSegment(50, 400);

		Segment notOpenToTrafficSegment0 = new OpenToTrafficSegment(300, 500, false);
		Segment notOpenToTrafficSegment1 = new OpenToTrafficSegment(200, 500, false);

		lane0.addSegment(velocitySegment0);
		lane1.addSegment(velocitySegment1);
		lane2.addSegment(velocitySegment2);

		lane0.addSegment(velocitySegment3);
		lane1.addSegment(velocitySegment4);
		lane2.addSegment(velocitySegment5);

//		lane0.addSegment(notPassableSegment0);
//		lane1.addSegment(notPassableSegment1);
//		lane2.addSegment(notPassableSegment2);

		lane0.addSegment(measureSegment0);
		lane1.addSegment(measureSegment0);
		lane2.addSegment(measureSegment0);

		lane1.addSegment(notOpenToTrafficSegment0);
		lane2.addSegment(notOpenToTrafficSegment1);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

}
