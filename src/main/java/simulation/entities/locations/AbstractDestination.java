package main.java.simulation.entities.locations;

import main.java.simulation.Simulation;
import main.java.simulation.state.Vector2D;

public abstract class AbstractDestination extends AbstractPlace {
	public AbstractDestination(Simulation simulation, Vector2D position) {
		super(simulation, position);
	}
}
