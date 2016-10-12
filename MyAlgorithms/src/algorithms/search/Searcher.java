package algorithms.search;

/**
 * Algorithm Interface 
 * 
 * @author MariaAmiran
 * 
 *
 * @param <T>
 */
public interface Searcher <T> {
	
	/**
	 * The function gets a searchable object, makes a search by one of the algorithms
	 * and returns the founded solution
	 * @param s
	 * @return Solution
	 */
	public Solution <T> search (Searchable <T> s); // search method	
	
	/**
	 * The function counts the number of nodes evaluated 
	 * @return int
	 */
	public int getNumberOfNodesEvaluated(); 
}
