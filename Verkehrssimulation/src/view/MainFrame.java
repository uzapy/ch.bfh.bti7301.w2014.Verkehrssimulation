package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Nagel_Schreckenberg_Simulation;
import model.Track;
import timer.IImpulsable;
import timer.Impulse;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IImpulsable, ActionListener, ChangeListener {
	
	private Nagel_Schreckenberg_Simulation simulation;
	private TrackPanel trackPanel;
	private Button buttonZoomIn = new Button("+");
	private Button buttonZoomOut = new Button("-");
	private Button buttonLeft = new Button("<");
	private Button buttonRight = new Button(">");
	private JSlider fpsSlider = new JSlider(1, 60, 30);
	private int simStep;
	
	public MainFrame(String title) {
		super(title);
		
		this.simulation = new Nagel_Schreckenberg_Simulation();
		Track track = this.simulation.getTrack();
		this.trackPanel = new TrackPanel(track);
		this.simStep = Nagel_Schreckenberg_Simulation.FRAMES_PER_SECOND;
		
		new Impulse(this, this.simStep);		

		// Layout manager
		this.setLayout(new BorderLayout());
		
		// Navigation Panel
		JPanel navigationPanel = new JPanel();
		BorderLayout navigationLayout = new BorderLayout();
		navigationPanel.setLayout(navigationLayout);
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
		
		// Parameter Panel
		JPanel parameterPanel = new JPanel();
		BoxLayout parameterLayout = new BoxLayout(parameterPanel, BoxLayout.Y_AXIS);
		parameterPanel.setLayout(parameterLayout);
		
		fpsSlider.addChangeListener(this);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( 1 ), new JLabel("1") );
		labelTable.put( new Integer( 30 ), new JLabel("Frames per Second") );
		labelTable.put( new Integer( 60 ), new JLabel("60") );
		fpsSlider.setLabelTable(labelTable);
		fpsSlider.setPaintLabels(true);
		parameterPanel.add(fpsSlider);
		
		// Button
		Button button = new Button("Click me!");
		
		// Bottom Border-Layout
		JPanel controlPanel = new JPanel();
		BorderLayout controlLayout = new BorderLayout();
		controlPanel.setLayout(controlLayout);

		controlPanel.add(navigationPanel, BorderLayout.WEST);
		controlPanel.add(parameterPanel, BorderLayout.CENTER);
		controlPanel.add(button, BorderLayout.EAST);		
		
		// Add Swing Components
		Container container = this.getContentPane();
		container.add(this.trackPanel, BorderLayout.CENTER);
		container.add(controlPanel, BorderLayout.SOUTH);
//		container.add(button, BorderLayout.SOUTH);
		// Tutorial: http://www.youtube.com/watch?v=svM0SBFqp4s
		
		try {
			PassableSegmentPanel.STRAIGHT = ImageIO.read(new File("src/resources/straight.png"));
			PassableSegmentPanel.STRAIGHT_LEFT = ImageIO.read(new File("src/resources/straight-left.png"));
			PassableSegmentPanel.STRAIGHT_RIGHT = ImageIO.read(new File("src/resources/straight-right.png"));
			VelocitySegmentPanel.MAX = ImageIO.read(new File("src/resources/max.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == this.buttonZoomIn) {
			MetricToPixel.SCALING_FACTOR++;
		} else if (ae.getSource() == this.buttonRight) {
			this.trackPanel.moveTrackRight();
		} else if (ae.getSource() == this.buttonZoomOut) {
			MetricToPixel.SCALING_FACTOR--;
		} else if (ae.getSource() == this.buttonLeft) {
			this.trackPanel.moveTrackLeft();
		}
		
		// do the Button-Magic
		this.buttonZoomIn.setEnabled(MetricToPixel.SCALING_FACTOR <= 50);
		this.buttonZoomOut.setEnabled(MetricToPixel.SCALING_FACTOR > 1);
//		this.buttonRight.setEnabled(???); TODO: Wann ist Button Right disabled?
		this.buttonLeft.setEnabled(this.trackPanel.getViewOffset() < 0);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent ce) {
		JSlider sourceSlider = (JSlider)ce.getSource();
		if (sourceSlider == this.fpsSlider && !sourceSlider.getValueIsAdjusting()) {
			Nagel_Schreckenberg_Simulation.FRAMES_PER_SECOND = (int)sourceSlider.getValue();
		}
	}

	/* (non-Javadoc)
	 * @see timer.IImpulsable#pulse()
	 */
	@Override
	public void pulse() {
		if (this.simStep >= Nagel_Schreckenberg_Simulation.FRAMES_PER_SECOND) {
			this.simulation.performStep();
			this.trackPanel.updateTrack();
			this.simStep = 0;
		} else {
			this.trackPanel.performSimStep(this.simStep);
			this.simStep++;
		}
	}
}
