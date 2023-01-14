package main.java.simulation.entities;

import main.java.simulation.Simulation;
import main.java.simulation.state.Vector2D;

public abstract class AbstractEntity {
	protected final Simulation simulation;
	
	protected final Vector2D position;
	
	public AbstractEntity(Simulation simulation, Vector2D position) {
		this.simulation = simulation;
		this.position = position;
	}
	
	public Vector2D getPosition() {
		return this.position;
	}
}
