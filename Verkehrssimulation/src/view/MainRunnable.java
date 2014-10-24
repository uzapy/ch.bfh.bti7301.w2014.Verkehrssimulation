package view;

import javax.swing.JFrame;

import util.MetricToPixel;

public class MainRunnable implements Runnable {

	@Override
	public void run() {
    	MainFrame mainFrame = new MainFrame("Verkehrssimulation");
    	mainFrame.setSize(100 * MetricToPixel.SCALING_FACTOR, 300);
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	mainFrame.setVisible(true);
	}

}
