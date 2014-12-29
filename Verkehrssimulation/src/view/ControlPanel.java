/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.IMeasurementListener;
import util.MessagePool;
import util.MetricToPixel;
import util.ParameterPool;
import util.TrackPreset;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener, ChangeListener, IMeasurementListener {
	
	private Button buttonDefault = new Button("Standard");
	private Button buttonRoadWorks = new Button("Baustelle");
	private Button buttonBottleneck = new Button("Spurverengung");
	private Button buttonSpeedLimit = new Button("Geschwindigkeitsbegrenzung");
	private Button buttonBanOnPassing = new Button("Überhoverbot");
	private Button buttonOnAndExitRamp = new Button("Ein- und Ausfahrt");
	private Button buttonExperimental = new Button("Experimentell");
	
	private Button buttonZoomIn = new Button("+");
	private Button buttonZoomOut = new Button("-");
	private Button buttonLeft = new Button("<");
	private Button buttonRight = new Button(">");
	
	private JSlider fpsSlider = new JSlider(15, 60, 37);
	private JSlider spawnSlider = new JSlider(0, 100, 100);
	
	private Label trafficDensityLabel = new Label("Verkehrsdichte: 0.0");
	private Label trafficFlowLabel = new Label("Verkehrsfluss: 0.0");
	
	/**
	 * @author bublm1
	 */
	public ControlPanel() {
		// Control-Layout
		BorderLayout controlLayout = new BorderLayout();
		this.setLayout(controlLayout);
		
		// Preset Panel
		JPanel presetPanel = new JPanel();
		BoxLayout presetLayout = new BoxLayout(presetPanel, BoxLayout.X_AXIS);
		presetPanel.setLayout(presetLayout);
		createPresetPanel(presetPanel);
		
		// Navigation Panel
		JPanel navigationPanel = new JPanel();
		BorderLayout navigationLayout = new BorderLayout();
		navigationPanel.setLayout(navigationLayout);
		createNavigationPanel(navigationPanel);
		
		// Parameter Panel
		JPanel parameterPanel = new JPanel();
		BoxLayout parameterLayout = new BoxLayout(parameterPanel, BoxLayout.X_AXIS);
		parameterPanel.setLayout(parameterLayout);
		createParameterPanel(parameterPanel);
		
		// Measurment Panel
		JPanel measurmentPanel = new JPanel();
		BoxLayout measurmentLayout = new BoxLayout(measurmentPanel, BoxLayout.Y_AXIS);
		measurmentPanel.setLayout(measurmentLayout);
		createMeasurmentPanel(measurmentPanel);
		
		// Button
		Button button = new Button("Click me!");

		this.add(presetPanel, BorderLayout.NORTH);
		this.add(navigationPanel, BorderLayout.WEST);
		this.add(parameterPanel, BorderLayout.CENTER);
		this.add(measurmentPanel, BorderLayout.EAST);
		this.add(button, BorderLayout.SOUTH);
		
		MessagePool.addMeasurementListener(this);
	}

	/**
	 * @author bublm1
	 * @param presetPanel
	 */
	private void createPresetPanel(JPanel presetPanel) {
		buttonDefault.addActionListener(this);
		buttonRoadWorks.addActionListener(this);
		buttonBottleneck.addActionListener(this);
		buttonSpeedLimit.addActionListener(this);
		buttonBanOnPassing.addActionListener(this);
		buttonOnAndExitRamp.addActionListener(this);
		buttonExperimental.addActionListener(this);
		
		presetPanel.add(buttonDefault);
		presetPanel.add(buttonRoadWorks);
		presetPanel.add(buttonBottleneck);
		presetPanel.add(buttonSpeedLimit);
		presetPanel.add(buttonBanOnPassing);
		presetPanel.add(buttonOnAndExitRamp);
		presetPanel.add(buttonExperimental);
	}

	/**
	 * @author bublm1
	 * @param navigationPanel
	 */
	private void createNavigationPanel(JPanel navigationPanel) {
		buttonZoomIn.addActionListener(this);
		buttonRight.addActionListener(this);
		buttonRight.setPreferredSize(new Dimension(30, 70));
		buttonZoomOut.addActionListener(this);
		buttonLeft.addActionListener(this);
		buttonLeft.setPreferredSize(new Dimension(30, 70));
		buttonLeft.setEnabled(false);
		
		navigationPanel.add(buttonZoomIn, BorderLayout.NORTH);
		navigationPanel.add(buttonRight, BorderLayout.EAST);
		navigationPanel.add(buttonZoomOut, BorderLayout.SOUTH);
		navigationPanel.add(buttonLeft, BorderLayout.WEST);
		navigationPanel.add(new JLabel("NAV"), BorderLayout.CENTER);
	}
	
	/**
	 * @author bublm1
	 * @param parameterPanel
	 */
	private void createParameterPanel(JPanel parameterPanel) {
		Hashtable<Integer, JLabel> fpsLabelTable = new Hashtable<Integer, JLabel>();
		fpsLabelTable.put(15, new JLabel("halb"));
		fpsLabelTable.put(37, new JLabel("Geschwindigkeit"));
		fpsLabelTable.put(60, new JLabel("doppelt"));
		
		fpsSlider.setPreferredSize(new Dimension(300, 40));
		fpsSlider.addChangeListener(this);
		fpsSlider.setLabelTable(fpsLabelTable);
		fpsSlider.setPaintLabels(true);
		
		parameterPanel.add(fpsSlider);
		
		Hashtable<Integer, JLabel> spawnLabelTable = new Hashtable<Integer, JLabel>();
		spawnLabelTable.put(0, new JLabel("0.0"));
		spawnLabelTable.put(50, new JLabel("Auto-Rate"));
		spawnLabelTable.put(100, new JLabel("1.0"));
		
		spawnSlider.setPreferredSize(new Dimension(290, 40));
		spawnSlider.addChangeListener(this);
		spawnSlider.setLabelTable(spawnLabelTable);
		spawnSlider.setPaintLabels(true);
		spawnSlider.setValue((int)ParameterPool.SPAWN_RATE * 100);
		
		parameterPanel.add(spawnSlider);
	}
	
	/**
	 * @author bublm1
	 * @param measurmentPanel
	 */
	private void createMeasurmentPanel(JPanel measurmentPanel) {
		trafficDensityLabel.setPreferredSize(new Dimension(200, 30));
		trafficFlowLabel.setPreferredSize(new Dimension(200, 30));
		measurmentPanel.add(trafficDensityLabel);
		measurmentPanel.add(trafficFlowLabel);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source == this.buttonZoomIn) {
			MetricToPixel.zoomIn();
		} else if (source == this.buttonRight) {
			ParameterPool.moveTrackRight();
		} else if (source == this.buttonZoomOut) {
			MetricToPixel.zoomOut();
		} else if (source == this.buttonLeft) {
			ParameterPool.moveTrackLeft();
		} else if (source == this.buttonDefault) {
			ParameterPool.TRACK_PRESET = TrackPreset.Default;
		} else if (source == this.buttonRoadWorks) {
			ParameterPool.TRACK_PRESET = TrackPreset.RoadWorks;
		} else if (source == this.buttonBottleneck) {
			ParameterPool.TRACK_PRESET = TrackPreset.Bottleneck;
		} else if (source == this.buttonSpeedLimit) {
			ParameterPool.TRACK_PRESET = TrackPreset.SpeedLimit;
		} else if (source == this.buttonBanOnPassing) {
			ParameterPool.TRACK_PRESET = TrackPreset.BanOnPassing;
		} else if (source == this.buttonOnAndExitRamp) {
			ParameterPool.TRACK_PRESET = TrackPreset.OnAndExitRamp;
		} else if (source == this.buttonExperimental) {
			ParameterPool.TRACK_PRESET = TrackPreset.Experimental;
		}
		
		// do the Button-Magic
		this.buttonZoomIn.setEnabled(MetricToPixel.SCALING_FACTOR <= 50);
		this.buttonZoomOut.setEnabled(MetricToPixel.SCALING_FACTOR > 1);
//		this.buttonRight.setEnabled(???); TODO: Wann ist Button Right disabled?
		this.buttonLeft.setEnabled(ParameterPool.VIEW_OFFSET < 0);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent ce) {
		JSlider sourceSlider = (JSlider)ce.getSource();
		if (sourceSlider == this.fpsSlider && !sourceSlider.getValueIsAdjusting()) {
			ParameterPool.FRAMES_PER_SECOND = 75 - (int)sourceSlider.getValue();
		} else if (sourceSlider == this.spawnSlider && ! sourceSlider.getValueIsAdjusting()) {
			ParameterPool.SPAWN_RATE = (double)sourceSlider.getValue() / 100;
		}
	}

	/* (non-Javadoc)
	 * @see util.IMeasurementListener#updateMeasurements(float, float)
	 */
	@Override
	public void updateMeasurements(float trafficDensity, float trafficFlow) {
		trafficDensityLabel.setText("Verkehrsdichte: " + trafficDensity);
		trafficFlowLabel.setText("Verkehrsfluss: " + trafficFlow);
	}
}
