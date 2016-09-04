package algorithms.mazeGenerators;

public interface Maze3dGenerator {
	Maze3d generate (int floors, int rows, int cols);
	String measureAlgorithmTime (int floors, int rows, int cols);
}
