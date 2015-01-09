package view.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Lane;
import resources.Resources;
import util.MetricToPixel;

@SuppressWarnings("serial")
public class LanePanel extends AbstractPanel<Lane>  {
	
	public static BufferedImage MAX = Resources.getImage("max");
	private Lane lane = super.object;

	/**
	 * Zeichent eine Fahrspur
	 * @author bublm1
	 * @param lane
	 */
	public LanePanel(Lane lane, int fastLaneOffset, int trackOffset) {
		super(lane, fastLaneOffset, trackOffset);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Farbe der Spur berechenen
		float invertedFastlaneIndex = 1.0F / (lane.getFastLaneIndex() + 1);
		float grayComponent = invertedFastlaneIndex;
		
		g.setColor(new Color(grayComponent, grayComponent, grayComponent));
		
		int xPosition = 0;
		int yPosition = MetricToPixel.scale(trackOffset + Lane.WIDTH * fastLaneOffset);
		int length = MetricToPixel.scale(lane.getLength());
		int width = MetricToPixel.scale(Lane.WIDTH);
		
		// Spur als langes Rechteck
		g.fillRect(xPosition, yPosition, length, width);
		
		// TODO: add gschtrichleti linie
		
		// Zeichnet ein Verkehrsschild mit der Höchstgeschwindigkeit
		g.drawImage(MAX, xPosition, yPosition, MetricToPixel.getImageSize(), MetricToPixel.getImageSize(), this);
		
		g.setColor(Color.BLACK);
		
		int xStringPosition = xPosition + MetricToPixel.SCALING_FACTOR;
		int yStringPosition = yPosition + (int)((float)2.75 * (float)MetricToPixel.SCALING_FACTOR);
		
		g.drawString(Integer.toString(lane.getMaxVelocity(0)), xStringPosition, yStringPosition);
	}
}
