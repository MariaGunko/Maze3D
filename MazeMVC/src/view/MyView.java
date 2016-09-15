package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

/**
 * This class implements all common methods of VIEW interface
 * 
 * @param in 
 * read the input stream from user
 * 
 * @param out 
 * print out text stream
 * 
 * @param cli
 * 
 * @param controller
 * 
 * @author Maria&Amiran
 */
public class MyView implements View {
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	private Controller controller;

	/**
	 * MyView CTOR
	 * 
	 * @param in
	 * @param out
	 */
	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;			
		cli = new CLI(in, out);
	}

	/**
	 * controller setter
	 * @param controller
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * this method gets a maze name and notifies if the maze is ready
	 * @param name
	 */
	@Override
	public void viewNotifyMazeIsReady(String mazeName) {
		out.println("Maze " + mazeName + " is ready");
		out.flush();
	}

	/**
	 * this method displays the 3D maze
	 * @param maze
	 */
	@Override
	public void viewDisplayMaze(Maze3d maze) {
		out.println(maze);
		out.flush();

	}

	/**
	 * this method gets a message and displays it to the user
	 * @param msg
	 */
	@Override
	public void viewDisplayMessage(String msg) {
		out.println(msg);
		out.flush();
	}

	/**
	 * this method displays the crossed section of the maze by one of the axis 
	 * @param maze2d
	 */
	@Override
	public void viewDisplayCrossSection(int[][] maze2d) {
		if (maze2d!=null){


			for (int i=0;i<maze2d[0].length;i++)
			{
				for (int j=0;j<maze2d.length;j++)
					out.print(maze2d[i][j]);
				out.println();
			}
		}
		else 
		{
			controller.c_displayMessage("Try again");
		}
	}
	/**
	 * this method updates the CLI
	 * @param commands
	 */
	@Override
	public void viewSetCommands(HashMap<String, Command> commands) {
		cli.setCommands(commands);

	}
	/**
	 * this method gets a solution to the maze and displays it 
	 * @param solve
	 */
	@Override
	public void viewDisplaySolution(Solution<Position> solve) {
		out.println(solve);
	}

	/**
	 * this method starts running CLI
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		cli.start();
	}

	/**
	 * this method notify exit
	 */
	@Override
	public void viewExit() {
		// TODO Auto-generated method stub
		out.println("Bye Bye :)");	
		out.flush();
	}
}
