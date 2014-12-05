/**
 * 
 */
package view.model;

import java.awt.Color;
import java.awt.Graphics;

import model.Lane;
import segment.Segment;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public abstract class SegmentPanel extends AbstractPanel<Segment> {

	protected Segment segment = super.object;
	
	/**
	 * @author bublm1
	 * @param element
	 * @param size
	 * @param fastLaneOffset
	 */
	public SegmentPanel(Segment segment, int fastLaneOffset, int trackOffset) {
		super(segment, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(getSegmentColor());
		
		int xPosition = MetricToPixel.scale(this.segment.start());
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		int length = MetricToPixel.scale(this.segment.end() - this.segment.start());
		int width = MetricToPixel.scale(Lane.WIDTH);
		
		g.fillRect(xPosition, yPosition, length, width);
	}

	protected abstract Color getSegmentColor();
}
