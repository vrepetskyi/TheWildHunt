package main.java.renderers;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import main.java.App;
import main.java.Config;
import main.java.simulation.state.Vector2D;

public class StaticRenderer {
	protected final Pane component;
	protected final Vector2D position;

	public StaticRenderer(Vector2D position, Node content) {
		this.position = position;
		this.component = content == null ? new Pane() : new Pane(content);

		this.component.setPrefWidth(Config.getTileSize());
		this.component.setPrefHeight(Config.getTileSize());

		this.component.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		this.component.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		this.component.setTranslateX(Config.getTileSize() * this.position.getX());
		this.component.setTranslateY(Config.getTileSize() * this.position.getY());
	}

	public StaticRenderer(String spriteSubpath, Vector2D position) {
		this(
			position,
			new ImageView(new Image(
				App.getResourcePath("sprites/" + spriteSubpath),
				Config.getTileSize(),
				Config.getTileSize(),
				true,
				false
			))
		);
	}
	
	public StaticRenderer(Vector2D position) {
		this(position, null);
	}

	public Node getComponent() {
		return component;
	}
}
