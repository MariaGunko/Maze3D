package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {
	void viewNotifyMazeIsReady(String name);
	void viewDisplayMaze(Maze3d maze);
	void viewDisplayMessage(String msg);	
	void start();
	
	void viewDisplayCrossSection (int [][] maze2d);	
	void viewDisplaySolution (Solution<Position> solve);
	void viewExit ();
	void viewDisplayHint(Solution<Position> s);
}
