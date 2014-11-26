/**
 * 
 */
package view;

import java.awt.Graphics;

import segment.Segment;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class VelocitySegmentPanel extends SegmentPanel {

	/**
	 * @author bublm1
	 * @param segment
	 * @param numberOfLanes
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public VelocitySegmentPanel(Segment segment, int numberOfLanes, int fastLaneOffset, int trackOffset) {
		super(segment, numberOfLanes, fastLaneOffset, trackOffset);
		// TODO Auto-generated constructor stub
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
	}

}
