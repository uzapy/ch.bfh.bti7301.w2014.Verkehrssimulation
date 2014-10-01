package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	public MainFrame(String title) {
		super(title);

		// Layout manager
		setLayout(new BorderLayout());

		// Swing components
		JTextArea textArea = new JTextArea();
		JButton button = new JButton("Click me!");

		// Add Swing Components
		Container container = getContentPane();
		container.add(textArea, BorderLayout.CENTER);
		container.add(button, BorderLayout.SOUTH);
		
		// Tutorial: http://www.youtube.com/watch?v=svM0SBFqp4s
	}
}
