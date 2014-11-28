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

import util.MetricToPixel;
import util.ParameterPool;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener, ChangeListener {
	
	private Button buttonZoomIn = new Button("+");
	private Button buttonZoomOut = new Button("-");
	private Button buttonLeft = new Button("<");
	private Button buttonRight = new Button(">");
	private JSlider fpsSlider = new JSlider(1, 60, 30);
	
	/**
	 * @author bublm1
	 */
	public ControlPanel() {
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
		BorderLayout controlLayout = new BorderLayout();
		this.setLayout(controlLayout);

		this.add(navigationPanel, BorderLayout.WEST);
		this.add(parameterPanel, BorderLayout.CENTER);
		this.add(button, BorderLayout.SOUTH);	
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
}
