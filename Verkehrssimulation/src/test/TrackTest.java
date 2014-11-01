/**
 * 
 */
package test;

import model.Car;
import model.Lane;
import model.Track;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author bublm1
 */
public class TrackTest {

	@Test
	public void createNeiborhoodTest() {
		/*--------------------------------------------------------*/
		/*|   [   0] <<<<<<<<<<<<<<<|>>>>>>>>>>>>>>>     [   1]  |*/
		/*--------------------------------------------------------*/
		/*|          <<<<<<<<<<<[car]>>>>>>>>>>>>>>>    [ 3]     |*/
		/*--------------------------------------------------------*/
		/*|        [  4]<<<<<<<<<<<<|>>>>>>>>>>>>[   5]          |*/
		/*--------------------------------------------------------*/
		Track track = new Track();
		
		Lane lane0 = new Lane(22, 150, 0);
		Lane lane1 = new Lane(28, 150, 1);
		Lane lane2 = new Lane(33, 150, 2);

		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		Car car0 = new Car( 0, 23, 0, 20, 5, lane0);
		track.addCar(car0);
		track.addCar(new Car( 1, 11, 0, 37, 3, lane0));
		
		Car car2 = new Car( 2, 27, 0, 5, 5, lane1);
		track.addCar(car2);
		track.addCar(new Car( 3, 15, 0, 54, 3, lane1));
		
		track.addCar(new Car( 4, 20, 0, 25, 3, lane2));
		track.addCar(new Car( 5, 14, 0, 85, 4, lane2));
		
		track.createNeighborhood(car2);
		Assert.assertTrue(car2.getNeigborhood().size() > 0);
	}
}
