package view;

import javax.swing.JFrame;

import util.MetricToPixel;

public class MainRunnable implements Runnable {

	@Override
	public void run() {
    	MainFrame mainFrame = new MainFrame("Verkehrssimulation");
    	mainFrame.setSize(150 * MetricToPixel.SCALING_FACTOR, 30 * MetricToPixel.SCALING_FACTOR);
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	mainFrame.setVisible(true);
	}

}
