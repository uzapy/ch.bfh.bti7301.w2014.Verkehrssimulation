/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

import util.MetricToPixel;
import model.Car;
import model.Lane;
import model.Track;

/**
 * @author burkt4
 */
@SuppressWarnings("serial")
public class TrackPanel extends JPanel {
	private Track track;
	private LinkedList<LanePanel> lanePanels = new LinkedList<LanePanel>();

	/**
	 * @author burkt4
	 * @param track
	 */
	public TrackPanel(Track track) {
		super();
		this.track = track;

		for (Lane lane : this.track.getAllLanes()) {
			this.lanePanels.add(new LanePanel(lane));
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0, MetricToPixel.scale(track.getLane(0).getLength()) ,10);
		
		for (LanePanel lanePanel : this.lanePanels) {
			lanePanel.paintComponent(g);
		}

		int yPositionOtherBlackBar = this.lanePanels.size()*15+25;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, yPositionOtherBlackBar, MetricToPixel.scale(track.getLane(0).getLength()) ,10);
		
	}

	public void setTrack(Track track) {
		this.track = track;
		this.repaint();
	}

}
