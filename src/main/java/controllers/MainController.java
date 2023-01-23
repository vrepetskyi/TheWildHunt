package main.java.controllers;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import main.java.App;
import main.java.Config;
import main.java.renderers.StaticRenderer;
import main.java.simulation.state.Tile;
import main.java.simulation.state.Vector2D;

public class MainController implements Initializable {
	private HashSet<StaticRenderer> activeRenderers = new HashSet<>();
	
	@FXML
	private ScrollPane scroller;
	@FXML
	private Pane scaler;
	
	@FXML
	private Group foreground;
	@FXML
	private Group middleground;
	@FXML
	private Group background;
	@FXML
	private Group action;
	
	@FXML
	private Label timer;
	@FXML
	private Label cursorPosition;

	private Double clamp(Double min, Double value, Double max) {
		return Math.min(Math.max(min, value), max);
	}

	private void updateMapScale(Scale mapScale, Vector2D renderedMapSize, Double targetScale) {
		// the map should cover at least 75% of the space
		Double minScale = scroller.getWidth() / renderedMapSize.getX() * 0.75;
		// limit scaling by map's resolution
		Double maxScale = 1.0;

		Double clampedScale = clamp(minScale, targetScale, maxScale);

		if (clampedScale == mapScale.getX()) {
			return;
		}

		mapScale.setX(clampedScale);
		mapScale.setY(mapScale.getX());

		Double oldWidth = scaler.getPrefWidth();
		Double oldHeight = scaler.getPrefHeight();

		// adjust mapScaler size to scaled map size
		scaler.setPrefWidth(renderedMapSize.getX() * mapScale.getX());
		scaler.setPrefHeight(renderedMapSize.getY() * mapScale.getY());

		// scroll map in such a way so it scales to the centre of view

		// horizontally
		Double deltaWidth = scaler.getPrefWidth() - oldWidth;
		Double deltaHvalue = -deltaWidth / (scaler.getWidth() - scroller.getWidth());

		Double viewLeftMapX = (scaler.getPrefWidth() - scroller.getWidth()) * scroller.getHvalue();
		Double viewCenterMapX = viewLeftMapX + scroller.getWidth() / 2;
		Double viewCenterMapXFraction = viewCenterMapX / scaler.getPrefWidth();

		if (Double.isFinite(deltaHvalue) && Double.isFinite(viewCenterMapXFraction)) {
			scroller.setHvalue(clamp(0.0, scroller.getHvalue() - deltaHvalue * viewCenterMapXFraction, 1.0));
		}
		// vertically
		Double deltaHeight = scaler.getPrefHeight() - oldHeight;
		Double deltaVvalue = -deltaHeight / (scaler.getPrefHeight() - scroller.getHeight());

		Double viewTopMapY = (scaler.getPrefHeight() - scroller.getHeight()) * scroller.getVvalue();
		Double viewCenterMapY = viewTopMapY + scroller.getHeight() / 2;
		Double viewCenterMapYFraction = viewCenterMapY / scaler.getPrefHeight();

		if (Double.isFinite(deltaVvalue) && Double.isFinite(viewCenterMapYFraction)) {
			scroller.setVvalue(clamp(0.0, scroller.getVvalue() - deltaVvalue * viewCenterMapYFraction, 1.0));
		}
	}

	private void updateMapScale(Scale mapScale, Vector2D renderedMapSize) {
		updateMapScale(mapScale, renderedMapSize, mapScale.getX());
	}

	private void setupScaling() {
		Scale mapScale = new Scale();
		mapScale.setPivotX(0);
		mapScale.setPivotY(0);
		scaler.getTransforms().add(mapScale);

		scaler.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		scaler.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		Vector2D simulationMapDimensions = App.getSimulation().getMap().getDimensions();
		Vector2D renderedMapSize = new Vector2D(
			simulationMapDimensions.getX() * Config.getTileSize(),
			simulationMapDimensions.getY() * Config.getTileSize()
		);

		// initialise map with the smallest scale
		updateMapScale(mapScale, renderedMapSize, 0.0);

		scroller.widthProperty().addListener((obs, oldVal, newVal) -> {
			updateMapScale(mapScale, renderedMapSize);
		});

		scroller.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent e) {
				if (e.getDeltaY() != 0) {
					Double scrollMultiplier = 0.001;
					Double targetScale = mapScale.getX() + e.getDeltaY() * scrollMultiplier;
					updateMapScale(mapScale, renderedMapSize, targetScale);
				}
				e.consume();
			}
		});
	}

	private void renderEnvironment() {
		ObservableList<Node> backgroundComponents = background.getChildren();
		ObservableList<Node> foregroundComponents = foreground.getChildren();
		ObservableList<Node> actionComponents = action.getChildren();

		for (Tile[] column : App.getSimulation().getMap().getTiles()) {
			for (Tile tile : column) {
				// render background
				Node backgroundComponent = new StaticRenderer("tile.png", tile.getPosition()).getComponent();
				backgroundComponents.add(backgroundComponent);
				
				// render locations
				if (tile.getLocation() != null) {
					Node locationComponent = new StaticRenderer("lion.png", tile.getPosition()).getComponent();
					foregroundComponents.add(locationComponent);
				}
				
				// render component for handling events on tile
				Node actionComponent = new StaticRenderer(tile.getPosition()).getComponent();
				actionComponents.add(actionComponent);
				
				// animate tile hover
		        final FadeTransition fadeIn = new FadeTransition(Duration.millis(20));
		        fadeIn.setNode(actionComponent);
		        fadeIn.setToValue(0.05);

		        actionComponent.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
		        	@Override
		        	public void handle(MouseEvent e) {
		        		fadeIn.playFromStart();
		        	}
		        });
		        
		        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
		        fadeOut.setNode(actionComponent);
		        fadeOut.setToValue(0);
		        
		        actionComponent.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
		        	@Override
		        	public void handle(MouseEvent e) {
		        		fadeOut.playFromStart();
		        	}
		        });
		        
		        actionComponent.setOpacity(0);
		        
		        // pointer position on status bar
		        String defaultCursorPositionText = "cursor out of map bounds";
		        
		        actionComponent.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
		        	@Override
		        	public void handle(MouseEvent e) {
		        		String cursorPositionText = String.format(
	        				"X: %02d, Y: %02d",
	        				tile.getPosition().getX(),
	        				tile.getPosition().getY()
		        		);
		        		cursorPosition.setText(cursorPositionText);
		        	}
		        });
		        
		        actionComponent.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
		        	@Override
		        	public void handle(MouseEvent e) {
		        		cursorPosition.setText(defaultCursorPositionText);
		        	}
		        });
		        
		        cursorPosition.setText(defaultCursorPositionText);
		        
		        // timer
		        Integer allSeconds = (int) Math.round(App.getSecondsElapsed());
		        String timeElapsed = String.format("%02d:%02d:%02d", allSeconds / 3600, (allSeconds % 3600) / 60, allSeconds % 60);
		        timer.setText(timeElapsed);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupScaling();
		renderEnvironment();

//		Parent list = FXMLLoader.load(getClass().getResource("../resources/views/list.fxml"));
//		Stage stage = new Stage();
//		stage.setTitle("Predators");
//		stage.setScene(new Scene(list, 250, 350));
//		stage.show();
	}
}
