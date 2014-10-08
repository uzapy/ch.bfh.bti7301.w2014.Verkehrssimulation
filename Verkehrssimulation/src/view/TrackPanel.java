/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Car;
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
		colorMap = new Color[5];
		colorMap[0] = Color.BLACK;
		colorMap[1] = Color.WHITE;
		colorMap[2] = Color.RED;
		colorMap[3] = Color.GREEN;
		colorMap[4] = Color.BLUE;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0, 25 * track.getLane(0).getLength() ,10);
		Car currentCar = track.getLane(0).getFirstCar();

		while(track.getLane(0).getNextCar(currentCar) != null){
			g.setColor(colorMap[currentCar.getId() % colorMap.length]);
			g.fillRect(currentCar.getPosition()*25, 20, 20, 10);
			currentCar = track.getLane(0).getNextCar(currentCar);			
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(0,40, 25 * track.getLane(0).getLength() ,10);
		
	}
	
	public void setTrack(Track track) {
		this.track = track;
		this.repaint();
	}
	
	
}
