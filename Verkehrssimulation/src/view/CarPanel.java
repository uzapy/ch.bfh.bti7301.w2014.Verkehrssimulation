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
	private int trackOffset;
	private int numberOfLanes;
	private float xSimPosition;
	private float ySimPosition;
	private Color color;

	/**
	 * @author bublm1
	 * @param car
	 */
	public CarPanel(Car car, int numberOfLanes, int trackOffset) {
		this.car = car;
		this.trackOffset = trackOffset;
		this.numberOfLanes = numberOfLanes;
		this.xSimPosition = this.car.getPosition();
		this.color = RandomPool.getNewColor();
	}

	/**
	 * @author bublm1
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(this.color);
		
		int xPosition = MetricToPixel.scale(this.xSimPosition);
		int yPosition = MetricToPixel.scale(trackOffset) + MetricToPixel.scale(this.ySimPosition);
		int length = MetricToPixel.scale(car.getLength());
		int width = MetricToPixel.scale(Car.WIDTH);
		
		g.fillRect(xPosition, yPosition, length, width);
		
		g.setColor(Color.MAGENTA);
		g.drawString(Integer.toString(this.car.getCurrentLane().getFastLaneIndex()),
				MetricToPixel.scale(this.xSimPosition + this.car.getLength() - 1),
				MetricToPixel.scale(trackOffset) + MetricToPixel.scale(this.ySimPosition + Car.WIDTH));
		
		g.setColor(Color.CYAN);
		g.drawString(Integer.toString(this.car.getSpeed()),
				MetricToPixel.scale(this.xSimPosition),
				MetricToPixel.scale(trackOffset) + MetricToPixel.scale(this.ySimPosition + Car.WIDTH));
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

		this.ySimPosition = getLaneOffset(car.getPreviousLane().getFastLaneIndex()) * Lane.WIDTH  + (Lane.WIDTH - Car.WIDTH) / 2;
		
		if (getLaneOffset(this.car.getCurrentLane().getFastLaneIndex()) !=
			getLaneOffset(this.car.getPreviousLane().getFastLaneIndex())) {
			
			float ySimProgress = (float)Lane.WIDTH / Nagel_Schreckenberg_Simulation.FRAMES_PER_SECOND * (float)simStep;
			
			if (getLaneOffset(this.car.getCurrentLane().getFastLaneIndex()) <
				getLaneOffset(this.car.getPreviousLane().getFastLaneIndex())) {
				this.ySimPosition = this.ySimPosition - ySimProgress; // Überholt
			} 
			else {
				this.ySimPosition = this.ySimPosition + ySimProgress; // Zurück auf normale spur	
			}
		}
		
//		if (this.car.getId() == 1) {
//			System.out.println(this.car.getBackPosition() + "|" + this.car.getSpeed() + "|" + simStep + "|" + this.xSimPosition + "|" +
//					this.car.getCurrentLane().getFastLaneIndex() + "|" + this.ySimPosition);
//		}
	}
	
	public int getLaneOffset(int fastLaneIndex) {
		return (numberOfLanes - 1) - fastLaneIndex;
	}
}
