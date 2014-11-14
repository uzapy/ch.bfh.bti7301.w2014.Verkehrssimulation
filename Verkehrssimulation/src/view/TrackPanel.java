/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Optional;

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
	
	private int trackOffset = 2;

	/**
	 * @author burkt4
	 * @param track
	 */
	public TrackPanel(Track track) {
		super();
		this.track = track;

		for (Lane lane : this.track.getLanes()) {
			int fastLaneOffset = (this.track.getLanes().size() - 1) - lane.getFastLaneIndex();
			
			this.lanePanels.add(new LanePanel(lane, fastLaneOffset, trackOffset));
			
			Car car = lane.getFirstCar();
			while(car != null) {
				this.carPanels.add(new CarPanel(car, this.track.getLanes().size(), trackOffset));
				car = lane.getNextCar(car);
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MetricToPixel.scale(track.getLane(0).getLength()), MetricToPixel.scale(trackOffset));
		
		for (LanePanel lanePanel : this.lanePanels) {
			lanePanel.paintComponent(g);
		}
		
		for (CarPanel carPanel : carPanels) {
			carPanel.paintComponent(g);
		}

		int xPosition = 0;
		int yPosition = this.lanePanels.size() * MetricToPixel.scale(Lane.WIDTH) + MetricToPixel.scale(trackOffset);
		int length = MetricToPixel.scale(track.getLane(0).getLength());
		int width = MetricToPixel.scale(trackOffset);
		
		g.setColor(Color.BLACK);
		g.fillRect(xPosition, yPosition, length, width);
	}

	public void setTrack() {
		for (Car oldCar : this.track.getOldCars()) {
			Optional<CarPanel> foundCar = this.carPanels.stream().filter(cp -> cp.getId() == oldCar.getId()).findFirst();
			if (foundCar.isPresent()) {
				this.carPanels.remove(foundCar.get());
			}

		}
		for (Car newCar : this.track.getNewCars()) {
			this.carPanels.add(new CarPanel(newCar, this.track.getLanes().size(), trackOffset));
		}
		this.track.clearNewCars();
		this.track.clearOldCars();
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
