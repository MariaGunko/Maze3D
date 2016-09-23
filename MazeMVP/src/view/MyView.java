package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyView extends Observable implements View, Observer {
	
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	private GeneralWindow win;

	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
				
		cli = new CLI(in, out);
		cli.addObserver(this);
		
		win = new GeneralWindow (1700,950);
		win.addObserver(this);
	}	

	@Override
	public void viewNotifyMazeIsReady(String name) {
		out.println("Maze " + name + " is ready");
		out.flush();
	}

	@Override
	public void viewDisplayMaze(Maze3d maze) {
		
		out.println(maze);
		out.flush();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		win.start();
		 //cli.start();	
	}

	@Override
	public void viewDisplayMessage(String msg) {
		out.println(msg);
		out.flush();		
	}

	@Override
	public void update(Observable o, Object arg) {
		if ((o == cli)||(o == win)) {
			setChanged();
			notifyObservers(arg);
		}
	}

	@Override
	public void viewDisplayCrossSection(int[][] maze2d) {
		// TODO Auto-generated method stub
		if (maze2d!=null){
			for (int i=0;i<maze2d[0].length;i++)
			{
				for (int j=0;j<maze2d.length;j++)
					out.print(maze2d[i][j]);
				out.println();
			}
		}
		else 
			out.println("Try again");
	}

	@Override
	public void viewDisplaySolution(Solution<Position> solve) {
		out.println(solve);	
	}

	@Override
	public void viewExit() {
		out.println("Bye Bye :)");	
		out.flush();	
	}
}
