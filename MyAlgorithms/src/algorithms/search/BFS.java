package algorithms.search;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementation of Searcher represents the BFS algorithm
 * 
 * @author Maria&Amiran
 *
 * @param <T>
 */


public class BFS<T> extends CommonSearcher<T>{

	// BFS Benefit: Returns the best solution
	// DFS Benefits: Can get a solution straight in the first iteration on the nodes

	private PriorityQueue<State<T>> openList = new PriorityQueue<State<T>>();
	private Set<State<T>> closedList = new HashSet<State<T>>();

	/**
	 * 
	 * This method build the solution with BFS algorithm 
	 * using hashset and arrayList 
	 * 
	 * @return solution <T> 
	 */

	@Override
	public Solution<T> search(Searchable<T> s) {
		State<T> StartState = s.getStartState();
		openList.add(StartState);

		State<T> goalState = s.getGoalState();

		while (!openList.isEmpty()){
			State<T> currState = openList.poll();
			closedList.add(currState);

			if (currState.equals(goalState)){
				return backTrace(currState);
			}

			List <State<T>> neighbors = s.getAllPossibleStates(currState);

			for (State<T> neighbor : neighbors){
				if (!openList.contains(neighbor)&& !closedList.contains(neighbor)){
					neighbor.setCameFrom(currState);
					neighbor.setCost(currState.getCost() + s.getMoveCost(currState, neighbor));
					openList.add(neighbor);
					evaluatedNodes++;
				}
				else
				{
					double newPathCost = (currState.getCost() + s.getMoveCost(currState, neighbor));
					if (neighbor.getCost() > newPathCost){
						neighbor.setCost(newPathCost);
						neighbor.setCameFrom(currState);

						if (!openList.contains(neighbor))
						{
							openList.add(neighbor);
							evaluatedNodes++;
						}
						else // must notify the priority queue about the change of cost
						{
							openList.remove(neighbor);
							openList.add(neighbor);
							evaluatedNodes++;
						}
					}
				}
			}
		}
		return null;
	}

}
