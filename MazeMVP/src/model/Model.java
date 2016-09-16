package model;

import algorithms.mazeGenerators.Maze3d;

public interface Model {
	void generateMaze(String name, int floors, int rows, int cols);
	Maze3d getMaze(String name);
	void exit();
	String modelDir(String path);	// TO DO
}
