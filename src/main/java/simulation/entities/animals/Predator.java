package main.java.simulation.entities.animals;

import main.java.simulation.Simulation;
import main.java.simulation.state.Vector2D;

public final class Predator extends AbstractAnimal {
	public Predator(Simulation simulation, Vector2D position, Species species) {
		super(simulation, position, species);
	}

	public void run() {
		
	}

	@Override
	public Predator getClone() {
		return new Predator(this.simulation, this.position, this.species);
	}
}
