package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;

import view.MainRunnable;
import fx.MainBox;

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
		Pane pane = new Pane();
		MainBox mainBox = new MainBox(pane);
		pane.getChildren().addAll(mainBox.getAllNodes());
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("Verkehrssimulation");
		stage.centerOnScreen();
		stage.setWidth(1000);
		stage.setHeight(300);
		stage.show();
	}
}
