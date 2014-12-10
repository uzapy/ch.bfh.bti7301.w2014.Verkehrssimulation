/**
 * 
 */
package test;

import org.junit.Test;

import util.RandomPool;

/**
 * @author bublm1
 */
public class PoissonDistributionTest {

	@Test
	public void getNewPoissonTest() {
		int[] distribution = new int[11];
		double poisson = 0;
		int index = 0;
		for (int i = 0; i < 1000; i++) {
			poisson = RandomPool.getNextGaussian();

			index = (int) Math.round(poisson * 10);

			if (index > 0 && index < 10) {
				distribution[index]++;
			}
		}
		
		System.out.println();
		for (int j = 0; j < distribution.length; j++) {
			System.out.println(j + "|" + distribution[j]);
		}
	}

}
