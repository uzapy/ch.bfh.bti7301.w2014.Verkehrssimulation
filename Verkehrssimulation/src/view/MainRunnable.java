package view;

import javax.swing.JFrame;

public class MainRunnable implements Runnable {

	@Override
	public void run() {
    	MainFrame mainFrame = new MainFrame("Verkehrssimulation");
    	mainFrame.setSize(600, 500);
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	mainFrame.setVisible(true);
	}

}
