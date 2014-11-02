/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import util.IPositionAndLength;
import util.Sort;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class Neighborhood<T extends IPositionAndLength> extends ArrayList<T> {
	private Optional<T> inFront;
	private Optional<T> aboveAndInFront;
	private Optional<T> aboveAndInBack;
	private Optional<T> belowAndInFront;
	private Optional<T> belowAndInBack;
	
	/**
	 * @author bublm1
	 * @param neighbors
	 */
	public void setNeighbors(List<T> neighbors, int radius, T self) {
		this.clear();
		this.inFront = Optional.empty();
		this.aboveAndInFront = Optional.empty();
		this.aboveAndInBack = Optional.empty();
		this.belowAndInFront = Optional.empty();
		this.belowAndInBack = Optional.empty();
		this.addAll(neighbors);
		
		inFront = neighbors.stream()
				.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex())
				.filter(c -> c.getBackPosition() > self.getPosition())
				.sorted(Sort.ByPosition)
				.findFirst();
		
		aboveAndInFront = neighbors.stream()
				.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex() + 1)
				.filter(c -> c.getPosition() > self.getPosition())
				.sorted(Sort.ByPosition)
				.findFirst();
		
		aboveAndInBack = neighbors.stream()
				.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex() + 1)
				.filter(c -> c.getPosition() < self.getPosition())
				.sorted(Sort.ByPositionInverted)
				.findFirst();
		
		belowAndInFront = neighbors.stream()
				.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex() - 1)
				.filter(c -> c.getPosition() > self.getPosition())
				.findFirst();
		
		belowAndInBack = neighbors.stream()
				.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex() - 1)
				.filter(c -> c.getPosition() < self.getPosition())
				.sorted(Sort.ByPositionInverted)
				.findFirst();
		
		if (self.getPosition() + radius > 150 && !inFront.isPresent()) {
			inFront = neighbors.stream()
					.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex()).findFirst();
		}
		
		if (self.getPosition() + radius > 150 && !aboveAndInFront.isPresent()) {
			aboveAndInFront = neighbors.stream()
					.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex() + 1).findFirst();
		}
		
		if (self.getPosition() + radius > 150 && !aboveAndInBack.isPresent()) {
			belowAndInFront = neighbors.stream()
					.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex() + 1).findFirst();
		}
		
		if (self.getBackPosition() - radius < 0 && !belowAndInFront.isPresent()) {
			aboveAndInBack = neighbors.stream()
					.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex() - 1).reduce((previous, current) -> current);
		}
		
		if (self.getBackPosition() - radius < 0 && !belowAndInBack.isPresent()) {
			belowAndInFront = neighbors.stream()
					.filter(c -> c.getFastLaneIndex() == self.getFastLaneIndex() - 1).reduce((previous, current) -> current);			
		}
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public Optional<T> getInFront() {
		return this.inFront;
	}
	
	/**
	 * @author bublm1
	 * @return
	 */
	public Optional<T> getAboveInFront() {
		return this.aboveAndInFront;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public Optional<T> getAboveInBack() {
		return this.aboveAndInBack;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public Optional<T> getBelowInFront() {
		return this.belowAndInFront;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public Optional<T> getBelowInBack() {
		return this.belowAndInBack;
	}
}