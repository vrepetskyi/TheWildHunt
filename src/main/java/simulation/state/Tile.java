package main.java.simulation.state;

import java.util.HashSet;

import main.java.simulation.entities.animals.Predator;
import main.java.simulation.entities.animals.Prey;
import main.java.simulation.entities.locations.AbstractPlace;

public final class Tile {
	private AbstractPlace place;
	
	private final HashSet<Predator> predators;
	private final HashSet<Prey> preys;
	
	private final HashSet<Vector2D> reachablePoints;
	
	public Tile() {
		this.predators = new HashSet<>();
		this.preys = new HashSet<>();
		this.reachablePoints = new HashSet<>();
	}
	
	public void setPlace(AbstractPlace place) {
		this.place = place;
	}

	public AbstractPlace getPlace() {
		return place;
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
