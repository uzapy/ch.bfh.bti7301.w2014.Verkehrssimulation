package main;

import javax.swing.SwingUtilities;

import view.MainRunnable;

public class Main {
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new MainRunnable());
    }
}
