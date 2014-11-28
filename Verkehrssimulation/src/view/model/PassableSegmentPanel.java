/**
 * 
 */
package view.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import segment.PassableSegment;
import segment.Segment;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class PassableSegmentPanel extends SegmentPanel {

	public static BufferedImage STRAIGHT = Resources.getImage("straight");
	public static BufferedImage STRAIGHT_LEFT = Resources.getImage("straight-left");
	public static BufferedImage STRAIGHT_RIGHT = Resources.getImage("straight-right");;
	
	/**
	 * @author bublm1
	 * @param segment
	 * @param numberOfLanes
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public PassableSegmentPanel(Segment segment, int fastLaneOffset, int trackOffset) {
		super(segment, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		boolean isPassableLeft = ((PassableSegment) this.segment).isPassableLeft();
		boolean isPassableRight = ((PassableSegment) this.segment).isPassableRight();
		
		int xPosition = MetricToPixel.scale(this.segment.start());
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		
		if (!isPassableLeft && !isPassableRight) {
			g.drawImage(STRAIGHT, xPosition, yPosition + 5, 20, 20, this);
		} else if (isPassableLeft) {
			g.drawImage(STRAIGHT_LEFT, xPosition, yPosition + 5, 20, 20, this);
		} else if (isPassableRight) {
			g.drawImage(STRAIGHT_RIGHT, xPosition, yPosition + 5, 20, 20, this);
		}
	}

}
