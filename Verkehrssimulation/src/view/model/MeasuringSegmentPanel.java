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
public class MeasuringSegmentPanel extends SegmentPanel {

	public static BufferedImage MEASUREMENT = Resources.getImage("measurement");
	
	/**
	 * @author bublm1
	 * @param segment
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public MeasuringSegmentPanel(Segment segment, int fastLaneOffset, int trackOffset) {
		super(segment, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int xPosition = MetricToPixel.scale(this.segment.start());
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		
		g.drawImage(MEASUREMENT, xPosition, yPosition, 4 * MetricToPixel.SCALING_FACTOR, 4 * MetricToPixel.SCALING_FACTOR, this);
	}

	/* (non-Javadoc)
	 * @see view.model.SegmentPanel#getSegmentColor()
	 */
	@Override
	protected Color getSegmentColor() {
		return new Color(255, 127, 127, 50);
	}
}
