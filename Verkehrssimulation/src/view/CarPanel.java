package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Car;
import model.Lane;
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
		
		int xPosition = MetricToPixel.scale(this.stepBackPosition);
		int yPosition = MetricToPixel.scale(trackOffset) +
				car.getLane().getFastLaneIndex() * MetricToPixel.scale(Lane.WIDTH) +
				MetricToPixel.scale((Lane.WIDTH - Car.WIDTH) / 2);
		int length = MetricToPixel.scale(car.getLength());
		int width = MetricToPixel.scale(Car.WIDTH);
		
		g.fillRect(xPosition, yPosition, length, width);
	}

	/**
	 * @author bublm1
	 * @param simulationStep
	 */
	public void performSimStep(int simStep) {
		
		float simProgress = (float)this.car.getSpeed() / 30 * (float)simStep;
		this.stepBackPosition = ((float)this.car.getBackPosition() + simProgress);
	
		if ((this.stepBackPosition + this.car.getLength()) > this.car.getLane().getLength()) {
			this.stepBackPosition = this.stepBackPosition - this.car.getLane().getLength();
		}
		
		if (this.car.getId() == 1) {
//			System.out.println(this.car.getBackPosition() + "|" + this.car.getSpeed() + "|" + simStep + "|" + this.stepBackPosition);
//			System.out.println(MetricToPixel.scale(this.stepBackPosition) + "|" + this.stepBackPosition);
		}
	}
}
