package segment;

import java.util.ArrayList;

import util.MessagePool;
import model.Car;

public class MeasuringSegment implements Segment {
	
	
	private int start;
	private int end;
	private float trafficDensity;
	private float trafficFlow;
	ArrayList<Car> carsOnSegment = new ArrayList<Car>();
	
	public MeasuringSegment(int start, int end){
		this.start = start;
		this.end = end;
	}

	@Override
	public int start() {
		return this.start;
	}

	@Override
	public int end() {
		return this.end;
	}
	
	public void register(Car car) {
		if (!(carsOnSegment.contains(car))) {
			carsOnSegment.add(car);
			calculateTrafficDensity();
			calculateTrafficFlow();
			MessagePool.sendTrafficMeasurments(trafficDensity, trafficFlow);
		}
	}

	public void deRegister(Car car) {
		carsOnSegment.remove(car);
		calculateTrafficDensity();
		calculateTrafficFlow();
		MessagePool.sendTrafficMeasurments(trafficDensity, trafficFlow);
	}
	
	private void calculateTrafficDensity() {
		trafficDensity = (float) (carsOnSegment.size() / (float) (this.end - this.start)) * 100;
	}
	
	private void calculateTrafficFlow() {
		trafficFlow = (float) carsOnSegment.stream().mapToInt(c -> c.getSpeed()).sum() / (float) (this.end + this.start);
	}

}
