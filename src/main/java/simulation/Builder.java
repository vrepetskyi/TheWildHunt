package main.java.simulation;

import main.java.simulation.entities.animals.Species;
import main.java.simulation.entities.locations.AbstractLocation;
import main.java.simulation.state.Vector2D;

public interface Builder {
	void reset(Vector2D mapDimensions);
	void addLocation(AbstractLocation location);
	void addPath(Vector2D from, Vector2D to);
	void addPredatorPhenotype(Species species, Vector2D spawnPosition);
	void addPreyPhenotype(Species species, Vector2D spawnPosition);
}
