package algorithms.search;

import java.util.ArrayList;
import java.util.List;


/**
 * @author MariaAmiran
 *
 * @param <T>
 */
public abstract class CommonSearcher<T> implements Searcher <T> {

	protected int evaluatedNodes;
	
	public CommonSearcher (){
		evaluatedNodes=0;
	}
	
	@Override
	public abstract Solution<T> search (Searchable<T> s);
	
	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}
	
	
	/**
	 * The function gets a goal state and calculates 
	 * the whole path of the solution by moving back with the 
	 * cameFrom field  
	 * @param goalState
	 * @return Solution
	 */
	protected Solution<T> backTrace(State<T> goalState){
		Solution<T> sol = new Solution<T>();
		State<T> currState = goalState;
		List<State<T>> states = new ArrayList<State<T>>();
		//List<State<T>> states = sol.getStates();
		
		while (currState !=null) {
			states.add(0, currState);
			currState = currState.getCameFrom();
		}
		
		sol.setStates(states);
		return sol;
	}
}
