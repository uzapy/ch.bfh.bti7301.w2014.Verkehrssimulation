/**
 * 
 */
package view;

import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

import model.Car;
import model.Lane;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class LanePanel extends JPanel  {
	
	private Lane lane;
	private LinkedList<CarPanel> carPanels = new LinkedList<CarPanel>();

	/**
	 * @author bublm1
	 * @param lane
	 */
	public LanePanel(Lane lane) {
		this.lane = lane;
		
		for (Car car : lane.getAllCars()) {
			this.carPanels.add(new CarPanel(car));
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (CarPanel carPanel : carPanels) {
			carPanel.paintComponent(g);
		}
		// TODO: add gschtrichleti linie
	}

}
