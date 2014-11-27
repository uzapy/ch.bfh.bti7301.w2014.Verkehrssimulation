/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import segment.Segment;
import segment.VelocitySegment;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class VelocitySegmentPanel extends SegmentPanel {
	
	public static BufferedImage MAX;
	
	/**
	 * @author bublm1
	 * @param segment
	 * @param numberOfLanes
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public VelocitySegmentPanel(Segment segment, int numberOfLanes, int fastLaneOffset, int trackOffset) {
		super(segment, numberOfLanes, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int maxVelocity = ((VelocitySegment) this.segment).getMaxVelocity();
		int xPosition = MetricToPixel.scale(this.segment.start());
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		
		g.drawImage(MAX, xPosition + 7, yPosition + 3, 24, 24, this);
		
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(maxVelocity), xPosition+11, yPosition+20);
	}

}
