/**
 * 
 */
package timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * @author bublm1
 */
public class Impulse implements ActionListener {
	private static final int second = 1000;
	private Timer timer;
	private IImpulsable impulsable;
	
	/**
	 * @author bublm1
	 */
	public Impulse(IImpulsable impulsable) {
		this.impulsable = impulsable;
		this.timer = new Timer(Impulse.second / impulsable.getInterval(), this);
		this.timer.start();
	}
	
	public void actionPerformed(ActionEvent ae) {
		this.impulsable.pulse();
	}
}
