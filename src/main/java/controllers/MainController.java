package main.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import main.java.App;
import main.java.simulation.state.Map;
import main.java.simulation.state.Tile;

public class MainController implements Initializable {
	private final Integer tileSize = 128;
	
	@FXML
	private ScrollPane zoomScrollPane;

	@FXML
	private Group zoomContent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		zoomScrollPane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent e) {
				zoomContent.setScaleX(Math.max(0.1, Math.min(zoomContent.getScaleX() + e.getDeltaY() * 0.01, 1)));
				zoomContent.setScaleY(zoomContent.getScaleX());
				e.consume();
			}
		});
		
		Map map = App.getSimulation().getMap();
		
		ObservableList<Node> renderedTiles = zoomContent.getChildren();

		Integer columnIndex = 0;
		for (Tile[] column : map.getTiles()) {
			Integer xTranslation = columnIndex * tileSize;
			
			Integer rowIndex = 0;
			for (Tile tile : column) {
				Integer yTranslation = rowIndex * tileSize;
				
				Label button = new Label(columnIndex.toString() + rowIndex.toString());
				button.setPrefWidth(tileSize);
				button.setPrefHeight(tileSize);
				button.setTranslateX(xTranslation);
				button.setTranslateY(yTranslation);
				
				renderedTiles.add(button);
				
				rowIndex++;
			}
			
			columnIndex++;
		}
	}
}
