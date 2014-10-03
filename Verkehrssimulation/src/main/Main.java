package main;

import javax.swing.SwingUtilities;

import model.Nagel_SchreckenbergSimulation;
import view.MainRunnable;

public class Main {
    public static void main(String[] args) {
    	//SwingUtilities.invokeLater(new MainRunnable());
		new Nagel_SchreckenbergSimulation().start();
    }
}
