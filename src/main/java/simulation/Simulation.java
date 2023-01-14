package main.java.simulation;

import java.util.HashSet;

import main.java.simulation.entities.animals.Predator;
import main.java.simulation.entities.animals.Prey;
import main.java.simulation.state.Map;

// TODO: observer
public final class Simulation {
	private final Map map;
	private final HashSet<Predator> predatorPhenotypes;
	private final HashSet<Prey> preyPhenotypes;

	Simulation(Map map) {
		this.map = map;
		this.predatorPhenotypes = new HashSet<>();
		this.preyPhenotypes = new HashSet<>();
	}

	public Map getMap() {
		return map;
	}

	void addPredatorPhenotype(Predator predator) {
		predatorPhenotypes.add(predator);
	}
	
	public HashSet<Predator> getPredatorPhenotypes() {
		return predatorPhenotypes;
	}

	void addPreyPhenotype(Prey prey) {
		preyPhenotypes.add(prey);
	}
	
	public HashSet<Prey> getPreyPhenotypes() {
		return preyPhenotypes;
	}
}
