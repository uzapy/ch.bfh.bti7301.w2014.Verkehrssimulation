/**
 * 
 */
package view.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import segment.Segment;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class OpenToTrafficSegmentPanel extends SegmentPanel {
	
	public static BufferedImage MAX = Resources.getImage("max");
//	public static BufferedImage LEFT = Resources.getImage("left");
//	public static BufferedImage RIGHT = Resources.getImage("right");

	/**
	 * @author bublm1
	 * @param segment
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public OpenToTrafficSegmentPanel(Segment segment, int fastLaneOffset, int trackOffset) {
		super(segment, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int xPosition = MetricToPixel.scale(this.segment.start() + 50);
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		
		g.drawImage(MAX, xPosition, yPosition, MetricToPixel.getImageSize(), MetricToPixel.getImageSize(), this);
	}

	/* (non-Javadoc)
	 * @see view.model.SegmentPanel#getSegmentColor()
	 */
	@Override
	protected Color getSegmentColor() {
		return new Color(255, 255, 127, 50);
	}

}
