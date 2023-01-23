package main.java.simulation;

import main.java.simulation.entities.animals.Predator;
import main.java.simulation.entities.animals.Prey;
import main.java.simulation.entities.animals.Species;
import main.java.simulation.entities.locations.AbstractLocation;
import main.java.simulation.entities.locations.AbstractPlace;
import main.java.simulation.state.Map;
import main.java.simulation.state.Vector2D;

public class SimulationBuilder implements Builder {
	private Map map;
	private Simulation simulation;
	
	@Override
	public void reset(Vector2D mapDimensions) {
		this.map = new Map(mapDimensions);
		this.simulation = new Simulation(this.map);
	}
	
	public SimulationBuilder(Vector2D mapDimensions) {
		reset(mapDimensions);
	}

	@Override
	public void addLocation(AbstractLocation location) {
		this.map.getTiles()
			[location.getPosition().getX()]
			[location.getPosition().getY()]
			.setLocation(location);	
	}

	@Override
	public void addPath(Vector2D from, Vector2D to) {	
	}

	@Override
	public void addPredatorPhenotype(Species species, Vector2D spawnPosition) {
		Predator predatorPhenotype = new Predator(this.simulation, spawnPosition, species);
		this.simulation.addPredatorPhenotype(predatorPhenotype);
	}

	@Override
	public void addPreyPhenotype(Species species, Vector2D spawnPosition) {
		Prey preyPhenotype = new Prey(this.simulation, spawnPosition, species);
		this.simulation.addPreyPhenotype(preyPhenotype);	
	}
	
	public Simulation getResult() {
		return simulation;
	}
}
