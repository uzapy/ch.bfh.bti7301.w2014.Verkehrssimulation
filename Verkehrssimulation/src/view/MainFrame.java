package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import model.Nagel_Schreckenberg_Simulation;
import model.Track;
import timer.IImpulsable;
import timer.Impulse;
import util.ParameterPool;
import view.model.TrackPanel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IImpulsable {
	
	private Nagel_Schreckenberg_Simulation simulation;
	private TrackPanel trackPanel;
	private ControlPanel controlPanel;
	private int simStep = ParameterPool.FRAMES_PER_SECOND;
	
	public MainFrame(String title) {
		super(title);
		
		new Impulse(this, this.simStep);
		this.simulation = new Nagel_Schreckenberg_Simulation();
		Track track = this.simulation.getTrack();
		
		this.trackPanel = new TrackPanel(track);
		this.controlPanel = new ControlPanel();	
		
		// Layout manager
		this.setLayout(new BorderLayout());
		// Add Components to main Container		
		Container container = this.getContentPane();
		container.add(this.trackPanel, BorderLayout.CENTER);
		container.add(this.controlPanel, BorderLayout.SOUTH);
	}

	/* (non-Javadoc)
	 * @see timer.IImpulsable#pulse()
	 */
	@Override
	public void pulse() {
		if (this.simStep >= ParameterPool.FRAMES_PER_SECOND) {
			this.simulation.performStep();
			this.trackPanel.updateTrack();
			this.simStep = 0;
		} else {
			this.trackPanel.performSimStep(this.simStep);
			this.simStep++;
		}
	}
}
