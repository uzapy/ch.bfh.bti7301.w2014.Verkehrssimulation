/**
 * 
 */
package model;

import java.util.Random;

/**
 * @author burkt4
 */
public class Nagel_SchreckenbergSimulation {

	private Car[] track = new Car[100];
	private double troedelFactor = 0.2;
	private int maxSpeed = 5;
	
	public Car[] initializeSimulation(){
		initTrack();
		return track;
	}
	
	public Car[] preformStep(){
		calculateSpeeds();
		moveCars();
		return track;
	}

public void start() {
		
		initTrack();
		int n = 0;
		try {	
			printCars();
			Thread.sleep(1000);

			while(n<=100){
				
				calculateSpeeds();
				
				moveCars();
				
				printCars();
				Thread.sleep(1000);
				n = n +1;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @author burkt4
	 */
	private void printCars() {
		
		for(int i = 0; i < track.length; i++){
			
			if(track[i] != null){
				
				System.out.print("(" + track[i].getSpeed() +  ")");
				//System.out.print("(C)");
			}
			else{
				System.out.print("( )");
			}

		}
		
		System.out.println();
		
	}

	/**
	 * @author burkt4
	 */
	private  void moveCars() {
		Car[] newtrack = new Car[track.length];
		for(int i = 0; i < track.length; i++){
			
			if(track[i] != null){
				
				int newPosition = (i + track[i].getSpeed()) % track.length;
				newtrack[newPosition] = track[i];
				newtrack[newPosition].setPosition(newPosition);
			}

		}
		
		track = newtrack;
	}

	/**
	 * @author burkt4
	 */
	private  void calculateSpeeds() {
		//Step 1: increase Speed by one if possible
		for(int i = 0; i < track.length; i++){
		
			if(track[i] != null){
				if(track[i].getSpeed() < maxSpeed){
					
					track[i].setSpeed(track[i].getSpeed() + 1); 
					
				}
			}

		}
		
		//Step 2: check distance to next car and slow down if necessary 
		for(int i = 0; i < track.length; i++){
			if(track[i] != null){
				
				int currentSpeed = track[i].getSpeed();
				int tragetSpeed = 0;
				for(int j = 0; j <= currentSpeed; j++){
					tragetSpeed = j;
					int checkPosition = (i + j + 1) % track.length;
					
					if( track[checkPosition] != null){
						
						break;
					}
					
				}
				track[i].setSpeed(tragetSpeed);
					
				
			}
			
		}
		//Step 3: trödeln
		for(int i = 0; i < track.length; i++){
			if(track[i] != null){
				
				Random rn = new Random();
				double res = rn.nextDouble();
				if(res < track[i].getFactor())	{
					if(track[i].getSpeed() > 0){
						track[i].setSpeed(track[i].getSpeed() - 1);	
					} 
					
				}
				
			}
			
		}
		
	}

	/**
	 * @author burkt4
	 */
	private  void initTrack() {
		track = new Car[15];
		track[0] = new Car(1,0,troedelFactor,0);
		track[3] = new Car(2,0,troedelFactor,3);
		track[4] = new Car(3,0,troedelFactor,4);
		track[6] = new Car(4,0,troedelFactor,6);
		track[9] = new Car(5,0,troedelFactor,9);
	}

}
