package view;

import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import util.MetricToPixel;

public class MainRunnable implements Runnable {

	@Override
	public void run() {
		int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getWidth();
		
    	MainFrame mainFrame = new MainFrame("Verkehrssimulation");
    	mainFrame.setSize(width, 40 * MetricToPixel.SCALING_FACTOR);
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	mainFrame.setVisible(true);
	}

}
