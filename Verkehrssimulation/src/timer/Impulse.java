package timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Timer der den Impuls weitergibt
 * @author bublm1
 */
public class Impulse implements ActionListener {
	private static final int second = 1000;
	private Timer timer;
	private IImpulsable impulsable;
	
	/**
	 * Ein Impuls der auf dem IImpulsable im angegebenen Interval den Puls weitergibt
	 * @author bublm1
	 * @param impulsable	Klasse die den Puls empfängt
	 * @param interval		Interval
	 */
	public Impulse(IImpulsable impulsable, int interval) {
		this.impulsable = impulsable;
		this.timer = new Timer(Impulse.second / interval, this);
		this.timer.start();
	}
	
	public void actionPerformed(ActionEvent ae) {
		this.impulsable.pulse();
	}
}
