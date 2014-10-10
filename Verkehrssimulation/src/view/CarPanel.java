package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Car;
import util.MetricToPixel;
import util.RandomPool;

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
		this.color = RandomPool.getNewColor();
	}

	/**
	 * @author bublm1
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(this.color);
		
		int xPosition = MetricToPixel.scale(car.getBackPosition());
		int yPosition = 20 + car.getLane().getFastLaneIndex() * 15;
		int length = MetricToPixel.scale(car.getLength());
		int width = MetricToPixel.scale(2);
		g.fillRect(xPosition, yPosition, length, width);
	}
}
