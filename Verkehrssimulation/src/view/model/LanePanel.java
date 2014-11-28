/**
 * 
 */
package view.model;

import java.awt.Color;
import java.awt.Graphics;

import model.Lane;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class LanePanel extends AbstractPanel<Lane>  {
	
	private Lane lane = super.object;

	/**
	 * @author bublm1
	 * @param lane
	 */
	public LanePanel(Lane lane, int fastLaneOffset, int trackOffset) {
		super(lane, fastLaneOffset, trackOffset);
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
		g.drawString(Integer.toString(lane.getMaxVelocity(0)), xPosition + 10, yPosition + 20);
	}
}
