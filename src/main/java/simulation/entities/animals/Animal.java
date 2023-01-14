package main.java.simulation.entities.animals;

import main.java.simulation.Simulation;
import main.java.simulation.entities.AbstractEntity;
import main.java.simulation.state.Vector2D;

public abstract class Animal extends AbstractEntity implements Runnable {
	private static Integer animalsCloned = 0;

	protected final Species species;
	protected final String name;

	public Animal(Simulation simulation, Vector2D position, Species species) {
		super(simulation, position);
		this.species = species;
		this.name = "#" + animalsCloned.toString();
	}

	protected abstract Animal getClone();

	public Animal clone() {
		Animal clone = getClone();
		animalsCloned += 1;
		return clone;
	};
}
