/**
 * 
 */
package fx;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import timer.IImpulsable;
import timer.Impulse;

/**
 * @author bublm1
 */
public class MainBox {
	
	private Rectangle acceleratingCar = new Rectangle();
	private Rectangle deceleratingCar = new Rectangle();
	
	private TranslateTransition acceleration;
	private TranslateTransition deceleration;
	
	double startSpeed1 = 100;
	double startSpeed2 = 50;

	/**
	 * @author bublm1
	 * @param vBox
	 */
	public MainBox(VBox vBox) {
//		acceleratingCar.setX(20);
//		acceleratingCar.setY(30);
		acceleratingCar.setWidth(40);
		acceleratingCar.setHeight(20);
		acceleratingCar.setFill(Color.BLUE);
		
		acceleration = new TranslateTransition(Duration.millis(950), acceleratingCar);
		acceleration.setByX(startSpeed1);
		acceleration.setCycleCount(1);
		acceleration.setAutoReverse(false);
		acceleration.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				handleAcceleration();
			}
		});
		
		deceleratingCar.setWidth(40);
		deceleratingCar.setHeight(20);
		deceleratingCar.setFill(Color.GREEN);
		
		deceleration = new TranslateTransition(Duration.millis(950), deceleratingCar);
		deceleration.setByX(startSpeed2);
		deceleration.setCycleCount(1);
		deceleration.setAutoReverse(false);
		deceleration.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				hanldeDeceleration();
			}
		});
		
		acceleration.play();
		deceleration.play();
	}

	private void handleAcceleration() {
		startSpeed1 = startSpeed1 * 0.9;
		acceleration.setByX(startSpeed1);
		acceleration.play();
	}

	private void hanldeDeceleration() {
		startSpeed2 = startSpeed2 * 1.1;
		deceleration.setByX(startSpeed2);
		deceleration.play();
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public ArrayList<Node> getAllNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(acceleratingCar);
		nodes.add(deceleratingCar);
		return nodes;
	}
}
