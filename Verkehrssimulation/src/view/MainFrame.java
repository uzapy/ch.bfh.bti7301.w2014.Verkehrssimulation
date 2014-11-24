package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Nagel_Schreckenberg_Simulation;
import model.Track;
import timer.IImpulsable;
import timer.Impulse;
import util.MetricToPixel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IImpulsable, ActionListener {
	
	private Nagel_Schreckenberg_Simulation simulation;
	private TrackPanel trackPanel;
	private Button buttonZoomIn = new Button("+");
	private Button buttonZoomOut = new Button("-");
	private Button buttonLeft = new Button("<");
	private Button buttonRight = new Button(">");
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
		
		// Swing components
		JPanel navigationPanel = new JPanel();
		BorderLayout navigationLayout = new BorderLayout();
		navigationPanel.setLayout(navigationLayout);
		buttonZoomIn.addActionListener(this);
		buttonRight.addActionListener(this);
		buttonRight.setPreferredSize(new Dimension(30, 100));
		buttonZoomOut.addActionListener(this);
		buttonLeft.addActionListener(this);
		buttonLeft.setPreferredSize(new Dimension(30, 100));
		buttonLeft.setEnabled(false);
		navigationPanel.add(buttonZoomIn, BorderLayout.NORTH);
		navigationPanel.add(buttonRight, BorderLayout.EAST);
		navigationPanel.add(buttonZoomOut, BorderLayout.SOUTH);
		navigationPanel.add(buttonLeft, BorderLayout.WEST);
		navigationPanel.add(new Label("NAV"), BorderLayout.CENTER);
		
		Button button = new Button("Click me!");
		
		// Add Swing Components
		Container container = this.getContentPane();
		container.add(navigationPanel, BorderLayout.EAST);
		container.add(this.trackPanel, BorderLayout.CENTER);
		container.add(button, BorderLayout.SOUTH);
		// Tutorial: http://www.youtube.com/watch?v=svM0SBFqp4s
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
//		this.buttonRight.setEnabled(???);
		this.buttonLeft.setEnabled(this.trackPanel.getViewOffset() < 0);
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
