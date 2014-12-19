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
public class SpawnSegmentPanel extends SegmentPanel {

	public static BufferedImage ON_RAMP = Resources.getImage("on-ramp");

	/**
	 * @author bublm1
	 * @param segment
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public SpawnSegmentPanel(Segment segment, int fastLaneOffset, int trackOffset) {
		super(segment, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int xPosition = MetricToPixel.scale(this.segment.start());
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		
		g.drawImage(ON_RAMP, xPosition, yPosition, MetricToPixel.getImageSize(), MetricToPixel.getImageSize(), this);
	}

	/* (non-Javadoc)
	 * @see view.model.SegmentPanel#getSegmentColor()
	 */
	@Override
	protected Color getSegmentColor() {
		return new Color(85, 170, 255, 50);
	}

}
