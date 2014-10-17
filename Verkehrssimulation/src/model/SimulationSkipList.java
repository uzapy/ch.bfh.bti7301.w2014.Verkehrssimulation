package model;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author stahr2
 *
 * @param <K key, V Value>
 * Implementet with help of article http://www.csee.umbc.edu/courses/undergraduate/341/fall01/Lectures/SkipLists/skip_lists/skip_lists.html
 */

@SuppressWarnings("serial")
public class SimulationSkipList extends ConcurrentSkipListMap<Integer, Car> {

	//Hier eigene Methoden implementieren. z.B update Methode
	public void updatePosition(Integer oldPosition, Integer newPosition){
		if(oldPosition != newPosition){
			Car car = this.get(oldPosition);
			this.remove(oldPosition);
			this.put(newPosition, car);
		}
	}
}
