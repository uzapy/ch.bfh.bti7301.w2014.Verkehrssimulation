package model;

public class Car {

	private int id;
	private int factor;
	private int position;
	
	public Car(int id, int rand, int position) {
		this.id = id;
		this.factor = rand;
		this.position = position;
	}
	
	public int getPostition(){
		return this.position;
	}

}
