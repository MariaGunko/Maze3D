package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {
	void viewNotifyMazeIsReady(String name);
	void viewDisplayMaze (Maze3d maze);
	void viewDisplayMessage (String msg);
	void viewDisplayCrossSection (int [][] maze2d);
	void viewSetCommands (HashMap map);
	void viewDisplaySolution (Solution<Position> solve);
}
