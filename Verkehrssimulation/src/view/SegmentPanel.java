/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Lane;
import segment.Segment;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class SegmentPanel extends JPanel {

	private Segment segment;
	private int trackOffset;
	private int fastLaneOffset; 
	
	/**
	 * @author bublm1
	 * @param element
	 * @param size
	 * @param fastLaneOffset
	 */
	public SegmentPanel(Segment segment, int numberOfLanes, int fastLaneOffset, int trackOffset) {
		this.segment = segment;
		this.fastLaneOffset = fastLaneOffset;
		this.trackOffset = trackOffset;
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(new Color(255, 127, 127, 66));
		
		int xPosition = MetricToPixel.scale(this.segment.start());
		int yPosition = MetricToPixel.scale(this.trackOffset + Lane.WIDTH * this.fastLaneOffset);
		int length = MetricToPixel.scale(this.segment.end() - this.segment.start());
		int width = MetricToPixel.scale(Lane.WIDTH);
		
		g.fillRect(xPosition, yPosition, length, width);
	}

}
