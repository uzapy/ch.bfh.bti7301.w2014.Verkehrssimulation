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

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener, ChangeListener, IMeasurementListener {
	
	private Button buttonZoomIn = new Button("+");
	private Button buttonZoomOut = new Button("-");
	private Button buttonLeft = new Button("<");
	private Button buttonRight = new Button(">");
	private JSlider fpsSlider = new JSlider(15, 60, 37);
	private Label trafficDensityLabel = new Label("Verkehrsdichte: 0");
	private Label trafficFlowLabel = new Label("Verkehrsfluss: 0");
	
	/**
	 * @author bublm1
	 */
	public ControlPanel() {
		// Control-Layout
		BorderLayout controlLayout = new BorderLayout();
		this.setLayout(controlLayout);
		
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

		this.add(navigationPanel, BorderLayout.WEST);
		this.add(parameterPanel, BorderLayout.CENTER);
		this.add(measurmentPanel, BorderLayout.EAST);
		this.add(button, BorderLayout.SOUTH);
		
		MessagePool.addMeasurementsListener(this);
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
		navigationPanel.add(new Label("NAV"), BorderLayout.CENTER);
	}
	
	/**
	 * @author bublm1
	 * @param parameterPanel
	 */
	private void createParameterPanel(JPanel parameterPanel) {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(15, new JLabel("doppelt"));
		labelTable.put(37, new JLabel("Geschwindigkeit"));
		labelTable.put(60, new JLabel("halb"));
		
		fpsSlider.setPreferredSize(new Dimension(300, 40));
		fpsSlider.addChangeListener(this);
		fpsSlider.setLabelTable(labelTable);
		fpsSlider.setPaintLabels(true);
		
		parameterPanel.add(fpsSlider);
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
		if (ae.getSource() == this.buttonZoomIn) {
			MetricToPixel.zoomIn();
		} else if (ae.getSource() == this.buttonRight) {
			ParameterPool.moveTrackRight();
		} else if (ae.getSource() == this.buttonZoomOut) {
			MetricToPixel.zoomOut();
		} else if (ae.getSource() == this.buttonLeft) {
			ParameterPool.moveTrackLeft();
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
			ParameterPool.FRAMES_PER_SECOND = (int)sourceSlider.getValue();
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
