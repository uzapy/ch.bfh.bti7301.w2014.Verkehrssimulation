package main;

import java.util.ArrayList;
import java.util.Random;

import model.Car;

public class Main {
    public static void main(String[] args) {
    	ArrayList<Car> cars = new ArrayList<Car>();
    	Random random = new Random();
    	for (int i = 0; i < 100; i++) {
			cars.add(new Car(i, random.nextInt(10), 0));
		}
    	
    	cars.clear();
    }
}
