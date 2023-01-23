package main.java.simulation.state;

import java.util.HashSet;

import main.java.simulation.entities.animals.Predator;
import main.java.simulation.entities.animals.Prey;
import main.java.simulation.entities.locations.AbstractLocation;

public final class Tile {
	private final Vector2D position;
	
	private AbstractLocation location;
	
	private final HashSet<Predator> predators;
	private final HashSet<Prey> preys;
	
	private final HashSet<Vector2D> reachablePoints;
	
	public Tile(Vector2D position) {
		this.position = position;
		this.predators = new HashSet<>();
		this.preys = new HashSet<>();
		this.reachablePoints = new HashSet<>();
	}
	
	public Vector2D getPosition() {
		return position;
	}

	public void setLocation(AbstractLocation location) {
		this.location = location;
	}

	public AbstractLocation getLocation() {
		return location;
	}
	
	public HashSet<Predator> getPredators() {
		return predators;
	}
	
	public HashSet<Prey> getPreys() {
		return preys;
	}
	
	public HashSet<Vector2D> getReachablePoints() {
		return reachablePoints;
	}
}
