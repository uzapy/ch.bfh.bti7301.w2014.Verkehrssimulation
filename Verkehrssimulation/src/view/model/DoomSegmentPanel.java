package view.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import segment.Segment;
import util.MetricToPixel;

@SuppressWarnings("serial")
public class DoomSegmentPanel extends SegmentPanel {

	public static BufferedImage EXIT = Resources.getImage("exit");

	/**
	 * Zeichent eine Ausfahrt
	 * @author bublm1, stahr2
	 * @param segment
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public DoomSegmentPanel(Segment segment, int fastLaneOffset, int trackOffset) {
		super(segment, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int xPosition = MetricToPixel.scale(this.segment.end()) - MetricToPixel.getImageSize();
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		
		g.drawImage(EXIT, xPosition, yPosition, MetricToPixel.getImageSize(), MetricToPixel.getImageSize(), this);
	}

	/* (non-Javadoc)
	 * @see view.model.SegmentPanel#getSegmentColor()
	 */
	@Override
	protected Color getSegmentColor() {
		return new Color(85, 170, 255, 50);
	}

}
