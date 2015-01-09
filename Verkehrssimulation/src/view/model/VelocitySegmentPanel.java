package view.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import segment.Segment;
import segment.VelocitySegment;
import util.MetricToPixel;

@SuppressWarnings("serial")
public class VelocitySegmentPanel extends SegmentPanel {
	
	public static BufferedImage MAX = Resources.getImage("max");
	
	/**
	 * Zeichnet ein Segment mit einer Geschwindigkeitsbegrenzung
	 * @author bublm1
	 * @param segment
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
		
		// Zeichnet ein Verkehrsschild mit der Höchstgeschwindigkeit
		g.drawImage(MAX, xPosition, yPosition, MetricToPixel.getImageSize(), MetricToPixel.getImageSize(), this);
		
		int xStringPosition = xPosition + MetricToPixel.SCALING_FACTOR;
		int yStringPosition = yPosition + (int)((float)2.75 * (float)MetricToPixel.SCALING_FACTOR);
		
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(maxVelocity), xStringPosition, yStringPosition);
	}

	/* (non-Javadoc)
	 * @see view.model.SegmentPanel#getSegmentColor()
	 */
	@Override
	protected Color getSegmentColor() {
		return new Color(127, 127, 255, 50);
	}

}
