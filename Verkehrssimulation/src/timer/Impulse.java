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
	private Timer timer;
	private IImpulsable impulsable;
	
	/**
	 * @author bublm1
	 */
	public Impulse(IImpulsable impulsable) {
		this.impulsable = impulsable;
		this.timer = new Timer(1000, this);
		this.timer.start();
	}
	
	public void actionPerformed(ActionEvent ae) {
		this.impulsable.poke();
	}
}
