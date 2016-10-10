package properties;
import java.io.Serializable;

/**
 * The properties class sets the basic properties of the game
 * @author Maria&Amiran
 *
 */
public class Properties implements Serializable{

	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private String generateMazeAlgorithm;
	private String solveMazeAlgorithm;
	private String viewForm;
	 
	/** 
	 * Default CTOR
	 */
	public Properties (){
//		numOfThreads = 50;
//		generateMazeAlgorithm = "GrowingTree";
//		solveMazeAlgorithm = "BFS";
	}
	
	
	/**
	 * getters
	 * @return
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}
	
	/**
	 * setters
	 * @param numOfThreads
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	
	public String getGenerateMazeAlgorithm() {
		return generateMazeAlgorithm;
	}
	
	public void setGenerateMazeAlgorithm(String generateMazeAlgorithm) {
		this.generateMazeAlgorithm = generateMazeAlgorithm;
	}
	
	public String getSolveMazeAlgorithm() {
		return solveMazeAlgorithm;
	}
	
	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}
	
	public String getViewForm() {
		return viewForm;
	}
	
	public void setViewForm(String viewForm) {
		this.viewForm = viewForm;
	}
}
