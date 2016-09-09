package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.RandomNextMove;
import controller.Controller;

public class MyModel implements Model {

	private Controller controller;
	private Map <String,Maze3d> mazes = new ConcurrentHashMap<String,Maze3d>(); // synchronized hashmap
	private List <Thread> threads = new ArrayList<Thread>();
	
	public MyModel (Controller controller){
		this.controller=controller;
	}
	
	@Override
	public void modelGenerateMaze(String name, int floors, int rows, int cols) {
		Thread thread = new Thread (new Runnable() {
			
			@Override
			public void run() {
				GrowingTreeGenerator generator = new GrowingTreeGenerator(new RandomNextMove());
				Maze3d maze = generator.generate(floors, rows, cols);
				mazes.put(name, maze);
				
				controller.notifyMazeIsReady(name);
			}
		});
		thread.start();	
		threads.add(thread);
	}

	@Override
	public Maze3d modelGetMaze(String name) {
		if (mazes.containsKey(name)){
			return mazes.get(name);
		}
		return null;
	}

	@Override
	public void modelGetCrossSection(int index, String XYZ, String mazeName) {
		// TODO Auto-generated method stub
		if (!mazes.containsKey(mazeName)){
			
		}
		else
		{
			Maze3d maze = mazes.get(mazeName);
			int [][] maze2d;
			if ((XYZ.compareTo("X")==0)||(XYZ.compareTo("x")==0))
			{
			
			}
			else
			{
				if ((XYZ.compareTo("Y")==0)||(XYZ.compareTo("y")==0))
				{
				
				}
				else
				{
					if ((XYZ.compareTo("Z")==0)||(XYZ.compareTo("z")==0))
					{
					
					}
					else
					{
						
					}
				}
			}
		}
	}
}
