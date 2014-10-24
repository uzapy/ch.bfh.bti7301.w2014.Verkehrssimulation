/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

import model.Car;
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
	private LinkedList<CarPanel> carPanels = new LinkedList<CarPanel>();

	/**
	 * @author burkt4
	 * @param track
	 */
	public TrackPanel(Track track) {
		super();
		this.track = track;

		for (Lane lane : this.track.getLanes()) {
			this.lanePanels.add(new LanePanel(lane));
			
			for (Car car : lane.getCars()) {
				this.carPanels.add(new CarPanel(car));
			}
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
		
		for (CarPanel carPanel : carPanels) {
			carPanel.paintComponent(g, trackOffset);
		}		

		int xPosition = 0;
		int yPosition = this.lanePanels.size() * MetricToPixel.scale(Lane.WIDTH) + MetricToPixel.scale(trackOffset);
		int length = MetricToPixel.scale(track.getLane(0).getLength());
		int width = MetricToPixel.scale(trackOffset);
		
		g.setColor(Color.BLACK);
		g.fillRect(xPosition, yPosition, length, width);
	}

	public void setTrack(Track track) {
		this.track = track;
		this.repaint();
	}

	/**
	 * @author bublm1
	 */
	public void performSimStep(int simStep) {
		for (CarPanel carPanel : this.carPanels) {
			carPanel.performSimStep(simStep);
		}
		this.repaint();
	}

}
