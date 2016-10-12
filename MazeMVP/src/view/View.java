package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * Interface of VIEW
 * responsible for all the visual side
 * @author MariaAmiran
 *
 */
public interface View {
	void start();
	/**
	 * The function gets a maze name and displays a notification if it is ready
	 * @param name
	 */
	void viewNotifyMazeIsReady(String name);
	
	/**
	 * The function gets a maze and displays it
	 * @param maze
	 */
	void viewDisplayMaze(Maze3d maze);
	
	/**
	 * The function gets a message and displays it for the user
	 * @param msg
	 */
	void viewDisplayMessage(String msg);	
	
	/**
	 *  The function gets a 2D maze and displays it
	 * @param maze2d
	 */
	void viewDisplayCrossSection (int [][] maze2d);	
	
	/**
	 *  The function gets a solution and displays it
	 * @param solve
	 */
	void viewDisplaySolution (Solution<Position> solve);
	
	/**
	 *  The function displays all the relevant messages after the shut down of the program
	 */
	void viewExit ();
	
	/**
	 *  The function gets a solution and displays hints along the path to the goal
	 * @param s
	 */
	void viewDisplayHint(Solution<Position> s);
}
