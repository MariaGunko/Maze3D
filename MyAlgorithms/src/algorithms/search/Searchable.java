package algorithms.search;
import java.util.List;


/**
 * Interface that represents a problem that we can search for a solution for
 * 
 * @author MariaAmiran
 *
 * @param <T>
 */
public interface Searchable <T>{
	/**
	 * The function returns the initial state
	 * @return
	 */	
	State<T> getStartState();
	
	/**
	 * The function returns the goal state
	 * @return
	 */
	State<T> getGoalState();
	
	/**
	 * The function returns list of all possible states from given state 
	 * @return
	 */
	List <State<T>> getAllPossibleStates (State<T> s);	
	
	/**
	 * The function gets two states and returns the cost between them
	 * @param currState
	 * @param neighbor
	 * @return
	 */
	double getMoveCost(State<T> currState, State<T> neighbor);
}
