/**
 * 
 */
package fx;

import java.util.ArrayList;

import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * @author bublm1
 */
public class MainBox {
	
	private Rectangle acceleratingCar = new Rectangle();
	private Rectangle deceleratingCar = new Rectangle();
	private Rectangle chainedCar = new Rectangle();
	
	private TranslateTransition acceleration;
	private TranslateTransition deceleration;
	private TranslateTransition chainedTransition1;
	private TranslateTransition chainedTransition2;
	private SequentialTransition sequentialTransition;
	
	double startSpeed1 = 100;
	double startSpeed2 = 50;
	double step = 150;

	/**
	 * @author bublm1
	 * @param pane
	 */
	public MainBox(Pane pane) {
		acceleratingCar.setWidth(40);
		acceleratingCar.setHeight(20);
		acceleratingCar.setFill(Color.BLUE);
		
		acceleration = new TranslateTransition(Duration.millis(1000), acceleratingCar);
		acceleration.setByX(startSpeed1);
		acceleration.setCycleCount(1);
		acceleration.setAutoReverse(false);
		acceleration.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				handleAcceleration();
			}
		});
		
		deceleratingCar.setY(30);
		deceleratingCar.setWidth(40);
		deceleratingCar.setHeight(20);
		deceleratingCar.setFill(Color.GREEN);
		
		deceleration = new TranslateTransition(Duration.millis(1000), deceleratingCar);
		deceleration.setByX(startSpeed2);
		deceleration.setCycleCount(1);
		deceleration.setAutoReverse(false);
		deceleration.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				hanldeDeceleration();
			}
		});
		
		chainedCar.setY(60);
		chainedCar.setWidth(40);
		chainedCar.setHeight(20);
		chainedCar.setFill(Color.RED);
		
		chainedTransition1 = new TranslateTransition(Duration.millis(1000), chainedCar);
		chainedTransition1.setByX(step);
		chainedTransition1.setAutoReverse(false);
		
		chainedTransition2 = new TranslateTransition(Duration.millis(1000), chainedCar);
		chainedTransition2.setByX(step);
		chainedTransition2.setAutoReverse(false);
		
		sequentialTransition = new SequentialTransition();
		sequentialTransition.getChildren().add(chainedTransition1);
		sequentialTransition.getChildren().add(chainedTransition2);
		sequentialTransition.setCycleCount(Timeline.INDEFINITE);
		
//		acceleration.play();
//		deceleration.play();
		sequentialTransition.play();
	}

	private void handleAcceleration() {
		startSpeed1 = startSpeed1 * 0.9;
		acceleration.setByX(startSpeed1);
		if (acceleratingCar.getTranslateX() < 1000) {
			acceleration.play();			
		}
	}

	private void hanldeDeceleration() {
		startSpeed2 = startSpeed2 * 1.1;
		deceleration.setByX(startSpeed2);
		if (deceleratingCar.getTranslateX() < 1000) {
			deceleration.play();			
		}
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public ArrayList<Node> getAllNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(acceleratingCar);
		nodes.add(deceleratingCar);
		nodes.add(chainedCar);
		return nodes;
	}
}
