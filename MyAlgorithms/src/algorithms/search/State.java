package algorithms.search;

import java.io.Serializable;

/**
 * Generic class represents a state in a game
 * 
 * @author MariaAmiran
 *
 */
public class State<T> implements Comparable<State<T>>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private State<T> cameFrom;
	private double cost; // solution cost
	private T value; // current value state	

	// Constructor
	public State (T point){
		this.value=point;
	}
	
	// getters & setters
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public int hashCode() {	
		return this.value.toString().hashCode();
	}

	@Override	
	public boolean equals(Object obj) {
		State<T> s = (State<T>)obj;
		return s.getValue().equals(this.value);
	}
	
	@Override	
	public int compareTo(State<T> s) {
		return (int)(this.getCost() - s.getCost());
		// return >0 if this >s
		//        <0 if this <s
	}
}
