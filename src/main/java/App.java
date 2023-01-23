package main.java;

import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.simulation.Simulation;
import main.java.simulation.SimulationBuilder;
import main.java.simulation.entities.animals.Species;
import main.java.simulation.entities.locations.Decoration;
import main.java.simulation.state.Vector2D;

public class App extends Application {
	private static Simulation simulation;
	
	private static Double secondsElapsed = 0.0;
	
	public static URL getResourceURL(String subpath) {
		return App.class.getResource("../resources/" + subpath);
	}
	
	public static String getResourcePath(String subpath) {
		return getResourceURL(subpath).toExternalForm();
	}
	
	public static Simulation getSimulation() {
		return simulation;
	}
	
	public static Double getSecondsElapsed() {
		return secondsElapsed;
	}
	
	private void setupSimulation() {		
		Vector2D mapDimensions = new Vector2D(32, 18);
		SimulationBuilder simulationBuilder = new SimulationBuilder(mapDimensions);
		Species lion = new Species("Lion", 5, 3);
		simulationBuilder.addPredatorPhenotype(lion, new Vector2D(16, 16));
		simulationBuilder.addLocation(new Decoration(simulation, new Vector2D(4, 9)));
		App.simulation = simulationBuilder.getResult();
	}
	
	private void setupVisualization(Stage stage) {		
		try {
			Image icon = new Image(getResourcePath("sprites/lion.png"), 256, 256, true, false);
			stage.getIcons().add(icon);
			stage.setTitle("The Wild Hunt: Savana Edition");
			
			Parent root = FXMLLoader.load(getResourceURL("views/main.fxml"));
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getResourcePath("styles/main.css"));
			
			stage.setScene(scene);
			stage.setMinWidth(360);
			stage.setMinHeight(300);
			
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) {
		setupSimulation();
		setupVisualization(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
