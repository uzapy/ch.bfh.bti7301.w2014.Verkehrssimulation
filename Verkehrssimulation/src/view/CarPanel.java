package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Car;
import util.ColorPool;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class CarPanel extends JPanel {

	private Car car;
	private Color color;

	/**
	 * @author bublm1
	 * @param car
	 */
	public CarPanel(Car car) {
		this.car = car;
		this.color = ColorPool.getNewColor();
	}

	/**
	 * @author bublm1
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(this.color);
		g.fillRect(car.getPosition() * 25, 25 + car.getLane().getFastLaneIndex() * 15, 20, 10);
	}
}
