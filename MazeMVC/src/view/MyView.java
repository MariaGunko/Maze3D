package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

public class MyView implements View {
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	private Controller controller;
	
	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
				
		cli = new CLI(in, out);
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	
	@Override
	public void viewNotifyMazeIsReady(String name) {
		out.println("maze " + name + " is ready");
		out.flush();
	}

	@Override
	public void viewDisplayMaze(Maze3d maze) {
		out.println(maze);
		out.flush();

	}

	@Override
	public void viewDisplayMessage(String msg) {
		out.println(msg);
		out.flush();
	}

	@Override
	public void viewDisplayCrossSection(int[][] maze2d) {
		for (int i=0;i<maze2d[0].length;i++)
		{
			for (int j=0;j<maze2d.length;j++)
				out.print(maze2d[i][j]);
			out.println();
		}
	}

	@Override
	public void viewSetCommands(HashMap<String, Command> commands) {
		cli.setCommands(commands);

	}

	@Override
	public void viewDisplaySolution(Solution<Position> solve) {
		System.out.println(solve);
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub
		cli.start();
	}
}
