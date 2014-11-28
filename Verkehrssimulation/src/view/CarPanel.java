package view;

import java.awt.Color;
import java.awt.Graphics;

import model.Car;
import model.Lane;
import util.MetricToPixel;
import util.ParameterPool;
import util.RandomPool;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class CarPanel extends AbstractPanel<Car> {

	private Car car = super.object;
	private int numberOfLanes;		// TODO: weg damit!
	private float xSimPosition;
	private float ySimPosition;
	private Color color;

	/**
	 * @author bublm1
	 * @param car
	 */
	public CarPanel(Car car, int fastLaneOffset, int trackOffset, int numberOfLanes) {
		super(car, fastLaneOffset, trackOffset);
		this.numberOfLanes = numberOfLanes;

		this.xSimPosition = this.car.getBackPosition();
		this.ySimPosition = getLaneOffset(this.car.getLane().getFastLaneIndex()) * Lane.WIDTH  + (Lane.WIDTH - Car.WIDTH) / 2;
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
		
//		g.setColor(Color.MAGENTA);
//		g.drawString(Integer.toString(this.car.getId()),
//				MetricToPixel.scale(this.xSimPosition + this.car.getLength()),
//				MetricToPixel.scale(trackOffset) + MetricToPixel.scale(this.ySimPosition + Car.WIDTH));
		
		g.setColor(Color.CYAN);
		g.drawString(Integer.toString(this.car.getNextSpeed()),
				MetricToPixel.scale(this.xSimPosition),
				MetricToPixel.scale(trackOffset) + MetricToPixel.scale(this.ySimPosition + Car.WIDTH));
	}

	/**
	 * @author bublm1
	 * @param simulationStep
	 */
	public void performSimStep(int simStep) {
		
		float xSimProgress = (float)this.car.getNextSpeed() / ParameterPool.FRAMES_PER_SECOND * (float)simStep;
		this.xSimPosition = ((float)this.car.getBackPosition() + xSimProgress);

		this.ySimPosition = getLaneOffset(car.getLane().getFastLaneIndex()) * Lane.WIDTH  + (Lane.WIDTH - Car.WIDTH) / 2;
		
		if (getLaneOffset(this.car.getNextLane().getFastLaneIndex()) !=
			getLaneOffset(this.car.getLane().getFastLaneIndex())) {
			
			float ySimProgress = (float)Lane.WIDTH / ParameterPool.FRAMES_PER_SECOND * (float)simStep;
			
			if (getLaneOffset(this.car.getNextLane().getFastLaneIndex()) <
				getLaneOffset(this.car.getLane().getFastLaneIndex())) {
				this.ySimPosition = this.ySimPosition - ySimProgress; // Überholt
			} else {
				this.ySimPosition = this.ySimPosition + ySimProgress; // Zurück auf normale spur	
			}
		}
	}
	
	public int getLaneOffset(int fastLaneIndex) {
		return numberOfLanes - fastLaneIndex;
	}

	public int getId() {
		return this.car.getId();
	}
}
