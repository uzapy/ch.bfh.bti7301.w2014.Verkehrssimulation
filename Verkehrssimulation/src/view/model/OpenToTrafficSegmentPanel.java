package view.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import segment.Segment;
import util.MetricToPixel;

@SuppressWarnings("serial")
public class OpenToTrafficSegmentPanel extends SegmentPanel {
	
	public static BufferedImage MAX = Resources.getImage("max");

	/**
	 * Zeichent ein Fahrverbots-Segment
	 * @author burkt4
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
		
		// Zeichnet ein Fahrverbotsschild
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
