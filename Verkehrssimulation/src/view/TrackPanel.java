/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Car;
import model.Lane;
import model.Track;

/**
 * @author burkt4
 */
@SuppressWarnings("serial")
public class TrackPanel  extends JPanel  {
	private Track track;
	private Color[] colorMap;

	/**
	 * @author burkt4
	 * @param track
	 */
	public TrackPanel(Track track) {
		super();
		this.track = track;
		initColorMap();
	}
	
	/**
	 * @author burkt4
	 */
	private void initColorMap() {
		colorMap = new Color[10];
		colorMap[0] = Color.BLACK;
		colorMap[1] = Color.WHITE;
		colorMap[2] = Color.RED;
		colorMap[3] = Color.GREEN;
		colorMap[4] = Color.BLUE;
		colorMap[5] = Color.CYAN;
		colorMap[6] = Color.MAGENTA;
		colorMap[8] = Color.YELLOW;
		colorMap[8] = Color.GRAY;
		colorMap[9] = Color.ORANGE;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0, 25 * track.getLane(0).getLength() ,10);
		
		for(Lane lane : track.getAllLanes()){
			for(Car car: lane.getAllCars()){
				g.fillRect(car.getPosition()*25, 25+lane.getFastLaneIndex()*15, 20, 10);
				g.setColor(colorMap[car.getId() % colorMap.length]);
			}	
		}

		g.setColor(Color.BLACK);
		g.fillRect(0,90, 25 * track.getLane(0).getLength() ,10);
		
	}
	
	public void setTrack(Track track) {
		this.track = track;
		this.repaint();
	}
	
	
}
