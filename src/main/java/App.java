package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import main.java.simulation.Simulation;
import main.java.simulation.SimulationBuilder;
import main.java.simulation.entities.animals.Species;
import main.java.simulation.state.Vector2D;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class App extends Application {
	private static Simulation simulation;

	@Override
	public void start(Stage primaryStage) {
		try {
			Vector2D mapDimensions = new Vector2D(48, 27);
			SimulationBuilder simulationBuilder = new SimulationBuilder(mapDimensions);
			Species lion = new Species("Lion", 5, 3);
			simulationBuilder.addPredatorPhenotype(lion, new Vector2D(16, 16));
			App.simulation = simulationBuilder.getResult();

			Parent root = FXMLLoader.load(getClass().getResource("../resources/views/main.fxml"));
			primaryStage.setTitle("TheWildHunt");
			primaryStage.setScene(new Scene(root, 800, 600));
			primaryStage.show();

			Parent list = FXMLLoader.load(getClass().getResource("../resources/views/list.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Predators");
			stage.setScene(new Scene(list, 250, 350));
			stage.show();
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
