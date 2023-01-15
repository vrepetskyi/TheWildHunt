package main.java;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import main.java.simulation.state.Vector2D;

public class App extends Application {
	private static Simulation simulation;

	@Override
	public void start(Stage primaryStage) {
		try {
			Vector2D mapDimensions = new Vector2D(32, 18);
			SimulationBuilder simulationBuilder = new SimulationBuilder(mapDimensions);
			Species lion = new Species("Lion", 5, 3);
			simulationBuilder.addPredatorPhenotype(lion, new Vector2D(16, 16));
			App.simulation = simulationBuilder.getResult();

			Parent root = FXMLLoader.load(getClass().getResource("../resources/views/main.fxml"));
			primaryStage.setTitle("TheWildHunt");
			primaryStage.setScene(new Scene(root, 800, 600));
			primaryStage.show();

//			Parent list = FXMLLoader.load(getClass().getResource("../resources/views/list.fxml"));
//			Stage stage = new Stage();
//			stage.setTitle("Predators");
//			stage.setScene(new Scene(list, 250, 350));
//			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Simulation getSimulation() {
		return simulation;
	}
}
