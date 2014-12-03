package segment;

import java.util.ArrayList;

import model.Car;

public class MeasuringSegment implements Segment {
	
	
	private int start;
	private int end;
	private float trafficDensity;
	private int trafficFlow;
	ArrayList<Car> carsOnSegment = new ArrayList<Car>();
	
	public MeasuringSegment(int start, int end){
		this.start = start;
		this.end = end;
	}

	@Override
	public int start() {
		// TODO Auto-generated method stub
		return this.start;
	}

	@Override
	public int end() {
		// TODO Auto-generated method stub
		return this.end;
	}
	
	public void register(Car car){
		if(!(carsOnSegment.contains(car))){
			calculateTrafficDensity();
			carsOnSegment.add(car);	
		}	
	}
	
	public void deRegister(Car car){
			carsOnSegment.remove(car);	
	}

	public float getTrafficDensity() {
		return trafficDensity;
	}

	public int getTrafficFlow() {
		return trafficFlow;
	}
	
	private void calculateTrafficDensity(){
		trafficDensity = (float)(carsOnSegment.size() / (float)(this.end - this.start))*100;
		System.out.println("Number of Cars: " + carsOnSegment.size() + " Density: " + trafficDensity);
	}
	
	private void calculateTrafficFlow(){
		int combinedSpeed = 0;
		for(Car car : carsOnSegment){
			combinedSpeed += car.getSpeed();
		}
		trafficFlow = combinedSpeed / (this.end + this.start);
		
		
	}

}
