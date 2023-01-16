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
import javafx.scene.image.ImageView;
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
	private MenuBar menu;
	
	@FXML
	private Menu simulationMenu;
	
	@FXML
	private Label status;
	
	private void updateMapScale(Scale mapScale, Vector2D renderedMapSize, Double targetScale) {
		// the map should cover at least 75% of the space 
		Double minScale = mapScroller.getWidth() / renderedMapSize.getX() * 0.75;
		// limit scaling by map's resolution
		Double maxScale = 1.0;
		
		mapScale.setX(Math.min(Math.max(minScale, targetScale), maxScale));
		mapScale.setY(mapScale.getX());
		
		Double oldWidth = mapScaler.getPrefWidth();
		Double oldHeight = mapScaler.getPrefHeight();
		
		// adjust mapScaler size to scaled map size
		mapScaler.setPrefWidth(renderedMapSize.getX() * mapScale.getX());
		mapScaler.setPrefHeight(renderedMapSize.getY() * mapScale.getY());
		
		// scroll map in such a way so it scales to the centre of view
		
		// horizontally
		Double deltaWidth = mapScaler.getPrefWidth() - oldWidth;
		Double deltaHvalue = -deltaWidth / (mapScaler.getWidth() - mapScroller.getWidth());
		
		Double viewLeftMapX = (mapScaler.getWidth() - mapScroller.getWidth()) * mapScroller.getHvalue();
		Double viewCenterMapX = viewLeftMapX + mapScroller.getWidth() / 2;
		Double viewCenterMapXFraction = viewCenterMapX / mapScaler.getWidth();
		
		if (!deltaHvalue.isNaN() && !viewCenterMapXFraction.isNaN()) {
			mapScroller.setHvalue(Math.min(Math.max(0, mapScroller.getHvalue() - deltaHvalue * viewCenterMapXFraction), 1));
		}
		
		// vertically
		Double deltaHeight = mapScaler.getPrefHeight() - oldHeight;
		Double deltaVvalue = -deltaHeight / (mapScaler.getHeight() - mapScroller.getHeight());
		
		Double viewTopMapY = (mapScaler.getHeight() - mapScroller.getHeight()) * mapScroller.getVvalue();
		Double viewCenterMapY = viewTopMapY + mapScroller.getHeight() / 2;
		Double viewCenterMapYFraction = viewCenterMapY / mapScaler.getHeight();
		
		if (!deltaVvalue.isNaN() && !viewCenterMapYFraction.isNaN()) {
			mapScroller.setVvalue(Math.min(Math.max(0, mapScroller.getVvalue() - deltaVvalue * viewCenterMapYFraction), 1));
		}
	}
	
	private void updateMapScale(Scale mapScale, Vector2D renderedMapSize) {
		updateMapScale(mapScale, renderedMapSize, mapScale.getX());
	}
	
	private void setupScaling() {
		Scale mapScale = new Scale();
		mapScale.setPivotX(0);
		mapScale.setPivotY(0);
		mapScaler.getTransforms().add(mapScale);

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
				if (e.getDeltaY() != 0) {					
					Double targetScale = mapScale.getX() + e.getDeltaY() * 0.01;
					updateMapScale(mapScale, renderedMapSize, targetScale);
				}
				e.consume();
			}
		});
	}
	
	private void renderMap() {
		// TODO: multiple layers
		
		ObservableList<Node> renderedTiles = mapScaler.getChildren();

		Integer columnIndex = 0;
		for (Tile[] column : App.getSimulation().getMap().getTiles()) {
			Integer xTranslation = columnIndex * tileSize;

			Integer rowIndex = 0;
			for (Tile simulationTile : column) {
				Integer yTranslation = rowIndex * tileSize;

				ImageView image = new ImageView(getClass().getResource("../../resources/images/tile-background.png").toExternalForm());
				
				Pane tileToRender = new Pane(image);
				
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
