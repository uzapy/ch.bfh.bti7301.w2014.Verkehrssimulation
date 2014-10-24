/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Lane;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class LanePanel extends JPanel  {
	
	private Lane lane;
	private int fastLaneOffset;
	private int trackOffset;

	/**
	 * @author bublm1
	 * @param lane
	 */
	public LanePanel(Lane lane, int fastLaneOffset, int trackOffset) {
		this.lane = lane;
		this.fastLaneOffset = fastLaneOffset;
		this.trackOffset = trackOffset;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		float invertedFastlaneIndex = 1.0F / (lane.getFastLaneIndex() + 1);
		float grayComponent = invertedFastlaneIndex;
		
		g.setColor(new Color(grayComponent, grayComponent, grayComponent));
		
		int xPosition = 0;
		int yPosition = MetricToPixel.scale(trackOffset + Lane.WIDTH * fastLaneOffset);
		int length = MetricToPixel.scale(lane.getLength());
		int width = MetricToPixel.scale(Lane.WIDTH);
		
		g.fillRect(xPosition, yPosition, length, width);
		// TODO: add gschtrichleti linie
		
		g.setColor(Color.MAGENTA);
		g.drawString(Integer.toString(lane.getFastLaneIndex()), xPosition+10, yPosition+20);
	}
}
