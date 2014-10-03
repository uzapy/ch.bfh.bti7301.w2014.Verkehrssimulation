package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import timer.IImpulsable;
import timer.Impulse;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IImpulsable {
	
	private CarPanel car1 = new CarPanel();
	private Impulse impulse;
	
	public MainFrame(String title) {
		super(title);
		
		this.impulse = new Impulse(this);		

		// Layout manager
		setLayout(new BorderLayout());

		// Swing components
		JButton button = new JButton("Click me!");
		
		// Add Swing Components
		Container container = getContentPane();
		container.add(car1, BorderLayout.CENTER);
		container.add(button, BorderLayout.SOUTH);
		// Tutorial: http://www.youtube.com/watch?v=svM0SBFqp4s
	}

	/* (non-Javadoc)
	 * @see timer.IImpulsable#poke()
	 */
	@Override
	public void poke() {
		car1.moveForward();
	}
}
