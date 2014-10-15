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
	private float stepBackPosition;
	private Color color;

	/**
	 * @author bublm1
	 * @param car
	 */
	public CarPanel(Car car) {
		this.car = car;
		this.stepBackPosition = this.car.getPosition();
		this.color = RandomPool.getNewColor();
	}

	/**
	 * @author bublm1
	 */
	public void paintComponent(Graphics g, int trackOffset) {
		super.paintComponent(g);

		g.setColor(this.color);
		
//		if (this.stepBackPosition >= this.car.getBackPosition()) {
			this.stepBackPosition = this.car.getBackPosition();
//		}
		
		int xPosition = MetricToPixel.scale(this.stepBackPosition);
		int yPosition = MetricToPixel.scale(trackOffset) +
				car.getLane().getFastLaneIndex() * MetricToPixel.scale(car.getLane().getWidth()) +
				MetricToPixel.scale((car.getLane().getWidth() - car.getWidth()) / 2);
		int length = MetricToPixel.scale(car.getLength());
		int width = MetricToPixel.scale(car.getWidth());
		g.fillRect(xPosition, yPosition, length, width);
	}

	/**
	 * @author bublm1
	 * @param simulationStep
	 */
	public void performSimStep(int delta) {
//		if (this.car.getId() == 7) {
//			System.out.println(this.stepBackPosition + " /// " + (this.car.getBackPosition() + delta * car.getSpeed()));
//		}
//		this.stepBackPosition = this.stepBackPosition + (int)Math.ceil(((float)car.getSpeed() / 30));
//		this.stepBackPosition = this.car.getBackPosition() + delta * car.getSpeed();
	}
}
