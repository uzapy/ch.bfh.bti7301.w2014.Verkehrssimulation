package view.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import segment.PassableSegment;
import segment.Segment;
import util.MetricToPixel;

@SuppressWarnings("serial")
public class PassableSegmentPanel extends SegmentPanel {

	public static BufferedImage STRAIGHT = Resources.getImage("straight");
	public static BufferedImage STRAIGHT_LEFT = Resources.getImage("straight-left");
	public static BufferedImage STRAIGHT_RIGHT = Resources.getImage("straight-right");;
	
	/**
	 * Zeichnet ein Spurwechsel-Verbotssegment
	 * @author bublm1
	 * @param segment
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
			// 'Nur geradeaus'-Schild
			g.drawImage(STRAIGHT, xPosition, yPosition, MetricToPixel.getImageSize(), MetricToPixel.getImageSize(), this);
		} else if (isPassableLeft) {
			// 'Geradeaus und Links erlaubt'-Schild
			g.drawImage(STRAIGHT_LEFT, xPosition, yPosition, MetricToPixel.getImageSize(), MetricToPixel.getImageSize(), this);
		} else if (isPassableRight) {
			// 'Geradeaus und Rechts erlaubt'-Schild
			g.drawImage(STRAIGHT_RIGHT, xPosition, yPosition, MetricToPixel.getImageSize(), MetricToPixel.getImageSize(), this);
		}
	}

	/* (non-Javadoc)
	 * @see view.model.SegmentPanel#getSegmentColor()
	 */
	@Override
	protected Color getSegmentColor() {
		return new Color(127, 255, 127, 50);
	}
}
