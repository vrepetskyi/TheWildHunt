package main.java.simulation.state;

public final class Vector2D {
	private Integer x;
	private Integer y;
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public void setY(Integer y) {
		this.y = y;
	}
	
	public Vector2D(Integer x, Integer y) {
		setX(x);
		setY(y);
	}
	
	public Integer getX() {
		return x;
	}

	
	public Integer getY() {
		return y;
	}
}
