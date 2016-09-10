package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyView implements View {

	@Override
	public void viewNotifyMazeIsReady(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewDisplayMaze(Maze3d maze) {
		System.out.println(maze);

	}

	@Override
	public void viewDisplayMessage(String msg) {
		System.out.println(msg);

	}

	@Override
	public void viewDisplayCrossSection(int[][] maze2d) {
		for (int i=0;i<maze2d[0].length;i++)
		{
			for (int j=0;j<maze2d.length;j++)
				System.out.print(maze2d[i][j]);
			System.out.println();
		}
	}

	@Override
	public void viewSetCommands(HashMap map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewDisplaySolution(Solution<Position> solve) {
		System.out.println(solve);
	}

}
