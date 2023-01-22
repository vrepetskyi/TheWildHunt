package main.java.simulation.entities.animals;

import main.java.simulation.Simulation;
import main.java.simulation.state.Vector2D;

public final class Prey extends AbstractAnimal {
	public Prey(Simulation simulation, Vector2D position, Species species) {
		super(simulation, position, species);
	}

	public void run() {
		
	}

	@Override
	public Prey getClone() {
		return new Prey(this.simulation, this.position, this.species);
	}
}
