package model;

import java.io.File;
import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;

public interface Model {
	void modelGenerateMaze(String name, int floors, int rows, int cols);
	Maze3d modelGetMaze (String name);
	int[][] modelGetCrossSection (int index, String XYZ, String mazeName );
	void modelSaveMaze (String mazeName, String fileName);
	void modelLoadMaze (String fileName, String mazeName);
	void modelSolveMaze (String mazeName, String algorithm);
	Solution<Position> modelGetSolution(String name);
	void modelExit ();
	String modelDir (String path);
	void setController(Controller controller);
	
}
