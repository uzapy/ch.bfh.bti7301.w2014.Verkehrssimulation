package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.Nagel_Schreckenberg_Simulation;
import model.Track;
import timer.IImpulsable;
import timer.Impulse;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IImpulsable {
	
	private Nagel_Schreckenberg_Simulation simulation;
	private TrackPanel trackPanel;
	
	public MainFrame(String title) {
		super(title);
		
		simulation = new Nagel_Schreckenberg_Simulation();
		Track track = simulation.getTrack();
		trackPanel = new TrackPanel(track);
		
		new Impulse(this);		

		// Layout manager
		setLayout(new BorderLayout());

		// Swing components
		JButton button = new JButton("Click me!");
		
		// Add Swing Components
		Container container = getContentPane();
		container.add(trackPanel, BorderLayout.CENTER);
		container.add(button, BorderLayout.SOUTH);
		// Tutorial: http://www.youtube.com/watch?v=svM0SBFqp4s
	}

	/* (non-Javadoc)
	 * @see timer.IImpulsable#poke()
	 */
	@Override
	public void pulse() {
		Track track = simulation.performStep();
		trackPanel.setTrack(track);
	}
}
