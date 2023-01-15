package main.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import main.java.App;
import main.java.simulation.state.Tile;
import main.java.simulation.state.Vector2D;

public class MainController implements Initializable {
	private final Integer tileSize = 128;

	@FXML
	private ScrollPane mapScroller;

	@FXML
	private Pane mapScaler;

	@FXML
	private Pane mapContainer;
	
	@FXML
	private MenuBar menu;
	
	@FXML
	private Menu simulationMenu;
	
	@FXML
	private Label status;
	
	private void updateMapScale(Scale mapScale, Vector2D renderedMapSize) {
		updateMapScale(mapScale, renderedMapSize, mapScale.getX());
	}
	
	private void updateMapScale(Scale mapScale, Vector2D renderedMapSize, Double targetScale) {
		// the map should cover at least 75% of the space 
		Double minScale = mapScroller.getWidth() / renderedMapSize.getX() * 0.75;
		Double maxScale = 1.0;
		
		mapScale.setX(Math.min(Math.max(minScale, targetScale), maxScale));
		mapScale.setY(mapScale.getX());
		
		mapScaler.setPrefWidth(renderedMapSize.getX() * mapScale.getX());
		mapScaler.setPrefHeight(renderedMapSize.getY() * mapScale.getY());
	}
	
	private void setupScaling() {
		Scale mapScale = new Scale();
		mapScale.setPivotX(0);
		mapScale.setPivotY(0);
		mapContainer.getTransforms().add(mapScale);

		mapScaler.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		mapScaler.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		Vector2D simulationMapDimensions = App.getSimulation().getMap().getDimensions();		
		Vector2D renderedMapSize = new Vector2D(
			simulationMapDimensions.getX() * tileSize,
			simulationMapDimensions.getY() * tileSize
		);
		
		// initialise map with the smallest scale
		updateMapScale(mapScale, renderedMapSize, 0.0);
		
		mapScroller.widthProperty().addListener((obs, oldVal, newVal) -> {
			updateMapScale(mapScale, renderedMapSize);
		});
		
		mapScroller.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent e) {
				Double targetScale = mapScale.getX() + e.getDeltaY() * 0.01;
				updateMapScale(mapScale, renderedMapSize, targetScale);
				e.consume();
			}
		});
	}
	
	private void renderMap() {
		ObservableList<Node> renderedTiles = mapContainer.getChildren();

		Integer columnIndex = 0;
		for (Tile[] column : App.getSimulation().getMap().getTiles()) {
			Integer xTranslation = columnIndex * tileSize;

			Integer rowIndex = 0;
			for (Tile simulationTile : column) {
				Integer yTranslation = rowIndex * tileSize;

				Label tileToRender = new Label(columnIndex.toString() + rowIndex.toString());
				
				tileToRender.setPrefWidth(tileSize);
				tileToRender.setPrefHeight(tileSize);
				
				tileToRender.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				tileToRender.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				
				tileToRender.setStyle(null);
				
				tileToRender.setTranslateX(xTranslation);
				tileToRender.setTranslateY(yTranslation);

				renderedTiles.add(tileToRender);

				rowIndex++;
			}

			columnIndex++;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupScaling();
		
		renderMap();
//		Parent list = FXMLLoader.load(getClass().getResource("../resources/views/list.fxml"));
//		Stage stage = new Stage();
//		stage.setTitle("Predators");
//		stage.setScene(new Scene(list, 250, 350));
//		stage.show();
	}
}
