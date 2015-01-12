package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import model.Nagel_Schreckenberg_Simulation;
import timer.IImpulsable;
import timer.Impulse;
import util.ParameterPool;
import util.TrackPreset;
import view.model.TrackPanel;

/**
 * Haupt-Fenster der Sumulation
 * @author bublm1
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IImpulsable {
	
	private Nagel_Schreckenberg_Simulation simulation;				// Simulation
	private TrackPanel trackPanel;									// Visuelle Repräsentation der Autobahn
	private ControlPanel controlPanel;								// Visuelle Repräsentation der Steuertafel
	private int simStep = ParameterPool.FRAMES_PER_SECOND;			// Gezeichnete Schritte pro Sekunde
	private TrackPreset currentPreset = ParameterPool.TRACK_PRESET;	// Situations-Vorlage
	
	/**
	 * Simulations-Visualisierung starten
	 * @author bublm1
	 * @param title Fenster-Titel
	 */
	public MainFrame(String title) {
		super(title);
		
		new Impulse(this, this.simStep);
		
		createSimulation(currentPreset);

		this.controlPanel = new ControlPanel();

		this.setLayout(new BorderLayout());
		
		Container container = this.getContentPane();
		container.add(this.trackPanel, BorderLayout.CENTER);
		container.add(this.controlPanel, BorderLayout.SOUTH);
	}

	/**
	 * Simulation starten mit einer Situations-Vorlage
	 * @author bublm1
	 * @param trackPreset gewählte Situations-Vorlage
	 */
	public void createSimulation(TrackPreset trackPreset) {
		this.simulation = new Nagel_Schreckenberg_Simulation(trackPreset);
		
		// Simulation i TrackPanel anzeigen
		if (this.trackPanel == null) {
			this.trackPanel = new TrackPanel(this.simulation.getTrack());
		} else {
			this.trackPanel.setTrack(this.simulation.getTrack());			
		}
	}

	/**
	 * Zeichent die Visualisierung neu
	 */
	@Override
	public void pulse() {
		// Simulation neu starten mit der gewählten Situations-Vorlage
		if (this.currentPreset != ParameterPool.TRACK_PRESET) {
			this.currentPreset = ParameterPool.TRACK_PRESET;
			createSimulation(this.currentPreset);
		}
		
		// Falls eine Sekunde vergangen ist, den nächsten Sschtitt berechnen. Ansonsten nur einen Simulations-Schritt anzeigen
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
