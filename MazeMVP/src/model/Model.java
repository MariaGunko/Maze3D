package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import properties.Properties;

/**
 * MODEL interface 
 * responsible for all the calculations and algorithms
 * @author MariaAmiran
 *
 */
public interface Model {
	void generateMaze(String name, int floors, int rows, int cols);
	Maze3d getMaze(String name);
	void exit();
	String modelDir(String path);
	void modelSaveMaze(String mazeName, String fileName);
	int[][] modelGetCrossSection(int index, String XYZ, String mazeName);
	void modelLoadMaze(String fileName, String mazeName);
	void modelSolveMaze(String mazeName, String algorithm); // TO DO
	Solution<Position> modelGetSolution(String mazeName);
	public Properties getProperties();
	void modelSetProperties(String arg1, String arg2, String arg3);
}