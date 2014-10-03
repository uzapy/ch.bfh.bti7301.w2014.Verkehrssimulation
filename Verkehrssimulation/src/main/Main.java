package main;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.SwingUtilities;

import fx.MainBox;
import view.MainRunnable;

public class Main extends Application {
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new MainRunnable());
		//new Nagel_SchreckenbergSimulation().start();
    	//launch(args);
    }

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {		
		VBox vBox = new VBox();
		MainBox mainBox = new MainBox(vBox);
		vBox.getChildren().addAll(mainBox.getAllNodes());
		
		Scene scene = new Scene(vBox);
		stage.setScene(scene);
		stage.setTitle("Verkehrssimulation");
		stage.centerOnScreen();
		stage.setWidth(1000);
		stage.setHeight(300);
		stage.show();
	}
}
