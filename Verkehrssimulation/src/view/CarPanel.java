package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Car;
import model.Lane;
import model.Nagel_Schreckenberg_Simulation;
import util.MetricToPixel;
import util.RandomPool;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class CarPanel extends JPanel {

	private Car car;
	private float xSimPosition;
	private float ySimPosition;
	private Color color;

	/**
	 * @author bublm1
	 * @param car
	 */
	public CarPanel(Car car) {
		this.car = car;
		this.xSimPosition = this.car.getPosition();
		this.color = RandomPool.getNewColor();
	}

	/**
	 * @author bublm1
	 */
	public void paintComponent(Graphics g, int trackOffset) {
		super.paintComponent(g);

		g.setColor(this.color);
		
		int xPosition = MetricToPixel.scale(this.xSimPosition);
		int yPosition = MetricToPixel.scale(trackOffset) + MetricToPixel.scale(this.ySimPosition);
		int length = MetricToPixel.scale(car.getLength());
		int width = MetricToPixel.scale(Car.WIDTH);
		
		g.fillRect(xPosition, yPosition, length, width);
	}

	/**
	 * @author bublm1
	 * @param simulationStep
	 */
	public void performSimStep(int simStep) {
		
		float xSimProgress = (float)this.car.getSpeed() / Nagel_Schreckenberg_Simulation.FRAMES_PER_SECOND * (float)simStep;
		this.xSimPosition = ((float)this.car.getBackPosition() + xSimProgress);
	
		if (this.xSimPosition > this.car.getCurrentLane().getLength()) {
			this.xSimPosition = this.xSimPosition - this.car.getCurrentLane().getLength();
		}

		this.ySimPosition = car.getPreviousLane().getFastLaneIndex() * Lane.WIDTH  + (Lane.WIDTH - Car.WIDTH) / 2;
		
		if (this.car.getCurrentLane().getFastLaneIndex() != this.car.getPreviousLane().getFastLaneIndex()) {
			float ySimProgress = (float)Lane.WIDTH / Nagel_Schreckenberg_Simulation.FRAMES_PER_SECOND * (float)simStep;
			
			if (this.car.getCurrentLane().getFastLaneIndex() < this.car.getPreviousLane().getFastLaneIndex()) {
				this.ySimPosition = this.ySimPosition - ySimProgress; // Überholt				
			} 
			else {
				this.ySimPosition = this.ySimPosition + ySimProgress; // Zurück auf normale spur	
			}
		}
		
//		if (this.car.getId() == 1) {
//			System.out.println(this.car.getBackPosition() + "|" + this.car.getSpeed() + "|" + simStep + "|" + this.stepBackPosition);
//			System.out.println(MetricToPixel.scale(this.stepBackPosition) + "|" + this.stepBackPosition);
//		}
	}
}
