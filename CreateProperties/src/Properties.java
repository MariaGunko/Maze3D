import java.io.Serializable;

public class Properties implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private String generateMazeAlgorithm;
	private String solveMazeAlgorithm;
	private String viewForm;
	
	public Properties (){
//		numOfThreads = 20;
//		generateMazeAlgorithm = "GrowingTreeGenerator_RandomNextMove";
//		solveMazeAlgorithm = "DFS";
//		viewForm="GUI";
	}
	
	public int getNumOfThreads() {
		return numOfThreads;
	}
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
