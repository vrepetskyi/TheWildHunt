package main.java.simulation.entities.locations;

import main.java.simulation.Simulation;
import main.java.simulation.entities.AbstractEntity;
import main.java.simulation.state.Vector2D;

public abstract class AbstractPlace extends AbstractEntity {
	public AbstractPlace(Simulation simulation, Vector2D position) {
		super(simulation, position);
	}
}
