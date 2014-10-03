package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public class CarPanel extends JPanel {
		
	private int x;
	
	/**
	 * @author bublm1
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLUE);
		g.fillRect(x, 20, 70, 40);
	}
	
	public void moveForward() {
		x += 10;
		this.repaint();
	}
}
