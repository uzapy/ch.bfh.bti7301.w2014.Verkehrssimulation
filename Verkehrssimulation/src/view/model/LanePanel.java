/**
 * 
 */
package view.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class LanePanel extends AbstractPanel<Lane>  {
	
	public static BufferedImage MAX = Resources.getImage("max");
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
		g.drawImage(MAX, xPosition, yPosition, 4 * MetricToPixel.SCALING_FACTOR, 4 * MetricToPixel.SCALING_FACTOR, this);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 2 * MetricToPixel.SCALING_FACTOR)); 
		
		int xStringPosition = xPosition + MetricToPixel.SCALING_FACTOR;
		int yStringPosition = yPosition + (int)((float)2.75 * (float)MetricToPixel.SCALING_FACTOR);
		
		g.drawString(Integer.toString(lane.getMaxVelocity(0)), xStringPosition, yStringPosition);
	}
}
