package main.java.simulation.state;

import java.util.Arrays;
import java.util.HashSet;

import main.java.simulation.entities.animals.Predator;
import main.java.simulation.entities.animals.Prey;
import main.java.simulation.entities.locations.AbstractPlace;

public final class Map {
	private final Vector2D dimensions;

	private final Tile[][] tiles;

	private final HashSet<AbstractPlace> places;

	private final HashSet<Predator> predators;
	private final HashSet<Prey> preys;

	public Map(Vector2D dimensions) {
		this.dimensions = dimensions;

		Tile[][] tiles = new Tile[dimensions.getX()][dimensions.getY()];
		Arrays.stream(tiles).forEach(column -> Arrays.fill(column, new Tile()));
		this.tiles = tiles;

		this.places = new HashSet<>();

		this.predators = new HashSet<>();
		this.preys = new HashSet<>();
	}

	public Vector2D getDimensions() {
		return dimensions;
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public Boolean areCoordinatesValid(Vector2D coordinates) {
		Integer x = coordinates.getX();
		Integer y = coordinates.getY();

		if (x >= 0 && x < this.dimensions.getX() && y >= 0 && y < this.dimensions.getY()) {
			return true;
		}

		return false;
	}

	public HashSet<AbstractPlace> getPlaces() {
		return places;
	}

	public HashSet<Predator> getPredators() {
		return predators;
	}

	public HashSet<Prey> getPreys() {
		return preys;
	}
}
