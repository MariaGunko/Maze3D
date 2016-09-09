package model;

import algorithms.mazeGenerators.Maze3d;

public interface Model {
	void modelGenerateMaze(String name, int floors, int rows, int cols);
	Maze3d modelGetMaze (String name);
	void modelGetCrossSection (int index, String XYZ, String mazeName );
}
