package main.java.simulation.entities.animals;

public class Species {
	final String name;
	final Integer strength;
	final Integer speed; // # of actions per unit of time
	
	public Species(String name, Integer strength, Integer speed) {
		this.name = name;
		this.strength = strength;
		this.speed = speed;
	}
}
