package algorithms.search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Implementation of Searcher represents the DFS algorithm
 * 
 * @author MariaAmiran
 *
 * @param <T>
 */
public class DFS<T> extends CommonSearcher<T> {

	private Set<State<T>> visitedStates = new HashSet<State<T>>();
	private Solution <T> solution;

	
	@Override
	public Solution<T> search(Searchable<T> s) {
		dfs (s, s.getStartState());
		return solution;
	}


	/** 
	 * The function gets a searchable object and a state
	 * and implements the recursion DFS search
	 * finally returns the solution
	 * @param s
	 * @param state
	 */
	public void dfs (Searchable<T> s, State<T> state)
	{
		if (state.equals(s.getGoalState()))
		{
			solution = backTrace(state);
			return;
		}

		visitedStates.add(state);
		List <State<T>> neighbors = s.getAllPossibleStates(state);

		for (State<T> neighbor : neighbors)
		{

			if (!visitedStates.contains(neighbor))
			{	
				neighbor.setCameFrom(state);
				neighbor.setCost(state.getCost() + s.getMoveCost(state, neighbor));
				evaluatedNodes++;
				dfs(s, neighbor);	

			} 			
		}
		return;

	}
}