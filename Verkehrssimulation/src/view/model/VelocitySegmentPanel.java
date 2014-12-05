/**
 * 
 */
package view.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import segment.Segment;
import segment.VelocitySegment;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class VelocitySegmentPanel extends SegmentPanel {
	
	public static BufferedImage MAX = Resources.getImage("max");
	
	/**
	 * @author bublm1
	 * @param segment
	 * @param numberOfLanes
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public VelocitySegmentPanel(Segment segment, int fastLaneOffset, int trackOffset) {
		super(segment, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int maxVelocity = ((VelocitySegment) this.segment).getMaxVelocity();
		int xPosition = MetricToPixel.scale(this.segment.start());
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		
		g.drawImage(MAX, xPosition, yPosition, 4 * MetricToPixel.SCALING_FACTOR, 4 * MetricToPixel.SCALING_FACTOR, this);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 2 * MetricToPixel.SCALING_FACTOR)); 
		g.drawString(Integer.toString(maxVelocity), 
				xPosition + MetricToPixel.SCALING_FACTOR,
				yPosition + (int)((float)2.75 * (float)MetricToPixel.SCALING_FACTOR));
	}

}
