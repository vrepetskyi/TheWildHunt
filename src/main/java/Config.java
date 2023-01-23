package main.java;

public class Config {
	private static final Integer tileSize = 144;
	private static final Integer referenceTimeMs = 1000;
	private static Double speedMultiplier = 1.0;
	
	public static Integer getTileSize() {
		return tileSize;
	}
	public static Integer getTickMs() {
		return (int) Math.round(referenceTimeMs * speedMultiplier);
	}
}
