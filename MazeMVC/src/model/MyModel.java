package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.demo.MazeAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomNextMove;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;

import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * 
 * This class implements all common methods of MODEL interface
 * 
 * @author Maria&Amiran
 *
 */
public class MyModel implements Model {

	private Controller controller;
	
	private Map <String,Maze3d> mazes = new ConcurrentHashMap<String,Maze3d>(); // synchronized hashmap
	private Map <String,Solution<Position>> solutions = new ConcurrentHashMap<String,Solution<Position>>();
	
	private List <Thread> threads = new ArrayList<Thread>();
	private List<GenerateMazeRunnable> generateMazeTasks= new ArrayList<GenerateMazeRunnable>();

	class GenerateMazeRunnable implements Runnable{
		private int floors,rows,cols;
		private String name;
		private GrowingTreeGenerator generator;
		
		public GenerateMazeRunnable(int floors,int rows, int cols, String name) {
			this.floors=floors;
			this.rows = rows;
			this.cols = cols;
			this.name = name;
		}

		@Override
		public void run() {
			generator=new GrowingTreeGenerator(new RandomNextMove());
			Maze3d maze = generator.generate(floors, rows, cols);
			mazes.put(name, maze);
			controller.c_notifyMazeIsReady(name);
		}
	}
	
/**
 * Controller setter	
 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * this method generates a maze due to the given parameters 
	 * 
     * @param name - the maze name
	 * @param floors - number of floors wanted
	 * @param rows - number of rows wanted
	 * @param cols - number of columns wanted
	 */	
	@Override
	public void modelGenerateMaze(String name, int floors, int rows, int cols) {
		GenerateMazeRunnable generateMaze= new GenerateMazeRunnable(floors, rows, cols, name); 
		generateMazeTasks.add(generateMaze);
		Thread thread = new Thread(generateMaze);
		thread.start();
		threads.add(thread);

	}

	/**
	 * this method gets a maze name and returns the relevant maze
	 * 
	 * @param name
	 * @return Maze3d
	 */
	@Override
	public Maze3d modelGetMaze(String name) {
		if (mazes.containsKey(name)){
			return mazes.get(name);
		}
		return null;
	}

	/**
	 * this method gets one of the axis: X,Y or Z, an index and a maze name
	 * and returns the crossed section maze 2D
	 * @param index
	 * @param XYZ
	 * @param mazeName
	 * @return maze2d
	 */
	@Override
	public int[][] modelGetCrossSection(int index, String XYZ, String mazeName) {
		int [][] maze2d = null;
		if (!mazes.containsKey(mazeName)){
			controller.c_displayMessage("Maze name not found");
		}
		else
		{
			Maze3d maze = mazes.get(mazeName);
			if ((XYZ.compareTo("X")==0)||(XYZ.compareTo("x")==0))
			{
				maze2d = maze.getCrossSectionByX(index);
			}
			else if ((XYZ.compareTo("Y")==0)||(XYZ.compareTo("y")==0))
			{
				maze2d = maze.getCrossSectionByY(index);
			}
			else if ((XYZ.compareTo("Z")==0)||(XYZ.compareTo("z")==0))
			{
				maze2d = maze.getCrossSectionByZ(index);
			}
			else
			{
				controller.c_displayMessage("Wrong input");
			}		
		}	
		return maze2d;
	}

	/**
	 * 
	 * this method saves the maze into a file
	 * @param mazeName
	 * @param fileName
	 */
	@Override
	public void modelSaveMaze(String mazeName, String fileName) {
		if (!mazes.containsKey(mazeName)){
			controller.c_displayMessage("Maze name not found");
		}
		else
		{
			Maze3d maze = mazes.get(mazeName);
			int a,b;
			OutputStream out;

			try {
				out = new MyCompressorOutputStream(
						new FileOutputStream(fileName));
				byte[] arr = maze.toByteArray();

				a = arr.length/255;	
				b = arr.length%255;

				out.write(a);
				out.write(b);

				out.write(arr);
				out.flush();
				out.close();
				controller.c_displayMessage("Maze: " + mazeName + " was saved successfully in file " + fileName);
			}
			catch (FileNotFoundException e) {
				controller.c_displayMessage("File " + fileName + " not exist");
				//e.printStackTrace();
			}
			catch (IOException e) {
				controller.c_displayMessage("File " + fileName + " can't be saved");
				//e.printStackTrace();
			}		
		}

	}

	/**
	 * this method loads a maze from a file to a Maze3d
	 * 
	 * @param fileName
	 * @param mazeName
	 */
	@Override
	public void modelLoadMaze(String fileName, String mazeName){
		InputStream in;
		try {
			in = new MyDecompressorInputStream(
					new FileInputStream(fileName));

			int sizeA = in.read();
			int sizeB = in.read();
			int totalSize = sizeA * 255 + sizeB;

			byte bytes[]=new byte[totalSize];

			in.read(bytes);
			in.close();	

			Maze3d loaded = new Maze3d(bytes);
			mazes.put(mazeName, loaded);
			controller.c_displayMessage("Maze: " + mazeName + " was loaded successfully from file " + fileName);
		}
		catch (FileNotFoundException e) {
			controller.c_displayMessage("File " + fileName + " can't be opened");
			//e.printStackTrace();
		}
		catch (IOException e) {
			controller.c_displayMessage("File " + fileName + " can't be loaded");
			//e.printStackTrace();
		}	
	}

	/**
	 * this method runs the wanted algorithm on the maze
	 * @param mazeName
	 * @param algorithm
	 */
	@Override
	public void modelSolveMaze(String mazeName, String algorithm) {
		Thread thread = new Thread (new Runnable() {

			@Override
			public void run() {
				if (!mazes.containsKey(mazeName)){
					controller.c_displayMessage("Maze name not found");
				}
				else
				{
					Maze3d myMaze = mazes.get(mazeName);
					Searchable<Position> adapter = new MazeAdapter(myMaze);
					Searcher <Position> myAlgorithm;

					switch (algorithm){
					case "BFS":
						myAlgorithm = new BFS <Position>();					
						break;

					case "DFS":
						myAlgorithm = new DFS <Position>();
						break;

					default:
						controller.c_displayMessage("Algorithm does not exist");
						return;
					}
					solutions.put(mazeName, myAlgorithm.search(adapter));
					controller.c_displayMessage("Solution for " + mazeName + " is ready");
				}
			}
		});
		thread.start();	
		threads.add(thread);
	}

	/**
	 * this method gets a maze name and returns the solution for it
	 * @param name
	 * @return mySolution
	 */
	@Override
	public Solution<Position> modelGetSolution(String name) {
		if (solutions.containsKey(name)){
			Solution<Position> mySolution = solutions.get(name);
			return mySolution;
		}
		return null;
	}

	/**
	 * this method closes all running threads and notify exit
	 */
	public void modelExit() {
//		for (GenerateMazeRunnable task : generateMazeTasks) {
//			task.terminate();
//		}
		for (int i=0;i<threads.size();i++){
			Thread t = threads.get(i);
			t.interrupt();
		}
		controller.c_displayMessage("All proccesses are closed");
	}

	/**
	 * this method gets a path and returns string of all folders and directories in it
	 * @param path
	 * @return StringBuilder
	 */
	@Override
	public String modelDir(String path) {
		StringBuilder sb = new StringBuilder();
		File folder = null;
		File[] listOfFiles = null;

		try{      
			folder = new File(path);         
			listOfFiles = folder.listFiles();

			for (File f: listOfFiles){
				sb.append(f.toString()).append("\n");
			}
		}
		catch(Exception e){
			// if any error occurs
			e.printStackTrace();
		}
		return sb.toString();
	}
}
