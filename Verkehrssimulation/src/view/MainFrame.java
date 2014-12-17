package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import model.Nagel_Schreckenberg_Simulation;
import model.Track;
import timer.IImpulsable;
import timer.Impulse;
import util.ParameterPool;
import util.TrackPreset;
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
	private TrackPreset currentPreset = ParameterPool.TRACK_PRESET;
	
	public MainFrame(String title) {
		super(title);
		
		new Impulse(this, this.simStep);
		createSimulation(currentPreset);

		this.controlPanel = new ControlPanel();
		// Layout manager
		this.setLayout(new BorderLayout());
		// Add Components to main Container		
		Container container = this.getContentPane();
		container.add(this.trackPanel, BorderLayout.CENTER);
		container.add(this.controlPanel, BorderLayout.SOUTH);
	}

	/**
	 * @author bublm1
	 */
	public void createSimulation(TrackPreset trackPreset) {
		this.simulation = new Nagel_Schreckenberg_Simulation(trackPreset);
		if (this.trackPanel == null) {
			this.trackPanel = new TrackPanel(this.simulation.getTrack());
		} else {
			this.trackPanel.setTrack(this.simulation.getTrack());			
		}
	}

	/* (non-Javadoc)
	 * @see timer.IImpulsable#pulse()
	 */
	@Override
	public void pulse() {
		if (this.currentPreset != ParameterPool.TRACK_PRESET) {
			this.currentPreset = ParameterPool.TRACK_PRESET;
			createSimulation(this.currentPreset);
		}
		
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
