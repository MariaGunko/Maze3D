package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Class represents the final solution of the game
 * 
 * @author MariaAmiran
 *
 * @param <T>
 */
public class Solution<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<State<T>> states = new ArrayList <State<T>>();

	public List<State<T>> getStates() {
		return states;
	}

	public void setStates(List<State<T>> states) {
		this.states = states;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (State<T> s: states){
			sb.append(s.toString()).append(" ");
		}
		return sb.toString();
	}

}
