package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x;
	public int y;
	public int z;
	
	public Position (int z, int x, int y){
		this.z=z;
		this.x=x;
		this.y=y;
	}

	@Override
	public String toString() {
		return "(" + z + "," + x + "," + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		Position pos = (Position) obj;
		return (this.x==pos.x&&this.y ==pos.y && this.z==pos.z);
	}
	
	
}
