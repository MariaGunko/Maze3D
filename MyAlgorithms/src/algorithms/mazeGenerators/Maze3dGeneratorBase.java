package algorithms.mazeGenerators;

public abstract class Maze3dGeneratorBase implements Maze3dGenerator {
	
	protected CommonGrowingTree nextMove;
	
	@Override
	public Maze3d generate(int floors, int rows, int cols) {
		return null;
	}

	@Override
	public String measureAlgorithmTime(int floors, int rows, int cols) {
		long startTime = System.currentTimeMillis();
		this.generate(floors, rows, cols);
		long endTime = System.currentTimeMillis();
		double totalTime = (double)(endTime - startTime)/1000L;
		return String.valueOf(totalTime);
	}

}
