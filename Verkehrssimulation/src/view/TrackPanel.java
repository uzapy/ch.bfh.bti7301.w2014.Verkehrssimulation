/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

import model.Lane;
import model.Track;
import util.MetricToPixel;

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
		
		int trackOffset = 2;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MetricToPixel.scale(track.getLane(0).getLength()), MetricToPixel.scale(trackOffset));

		for (LanePanel lanePanel : this.lanePanels) {
			lanePanel.paintComponent(g, trackOffset);
		}

		int yPositionOtherBlackBar = this.lanePanels.size() * MetricToPixel.scale(Lane.WIDTH) + MetricToPixel.scale(trackOffset);

		g.setColor(Color.BLACK);
		g.fillRect(0, yPositionOtherBlackBar, MetricToPixel.scale(track.getLane(0).getLength()), MetricToPixel.scale(trackOffset));
	}

	public void setTrack(Track track) {
		this.track = track;
		this.repaint();
	}

	/**
	 * @author bublm1
	 */
	public void performSimStep(int delta) {
		for (LanePanel lanePanel : this.lanePanels) {
			lanePanel.performSimStep(delta);
		}
		this.repaint();
	}

}
