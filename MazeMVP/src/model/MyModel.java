package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class MyModel extends Observable implements Model {
	
	private ExecutorService executor;
	
	public MyModel() {
		executor = Executors.newFixedThreadPool(50);
	}		
		
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();
			
	/**
	 * this method generates a maze due to the given parameters 
	 * 
     * @param name - the maze name
	 * @param floors - number of floors wanted
	 * @param rows - number of rows wanted
	 * @param cols - number of columns wanted
	 * @return maze
	 */	
	@Override
	public void generateMaze(String name, int floors, int rows, int cols) {
		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				GrowingTreeGenerator generator = new GrowingTreeGenerator(new RandomNextMove());
				Maze3d maze = generator.generate(floors, rows, cols);
				mazes.put(name, maze);
				
				setChanged();
				notifyObservers("maze_ready " + name);		
				return maze;
			}	
		});		
	}

	/**
	 * this method gets a maze name and returns the relevant maze
	 * 
	 * @param name
	 * @return Maze3d
	 */
	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}
	
	/**
	 * this method closes all running threads and notify exit
	 */
	public void exit() {
		executor.shutdownNow();
		setChanged();
		notifyObservers("All proccesses are closed");	
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

	/**
	 * 
	 * this method saves the maze into a file
	 * @param mazeName
	 * @param fileName
	 */
	@Override
	public void modelSaveMaze(String mazeName, String fileName) {
		if (!mazes.containsKey(mazeName)){
			setChanged();
			notifyObservers("Maze name not found");	
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
				
				setChanged();
				notifyObservers("Maze: " + mazeName + " was saved successfully in file " + fileName);	
			}
			catch (FileNotFoundException e) {
				setChanged();
				notifyObservers("File " + fileName + " not exist");	
			}
			catch (IOException e) {
				setChanged();
				notifyObservers("File " + fileName + " can't be saved");	
			}		
		}
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
			setChanged();
			notifyObservers("Maze name not found");	
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
				setChanged();
				notifyObservers("Wrong input");	
			}		
		}	
		return maze2d;
	}

	/**
	 * this method loads a maze from a file to a Maze3d
	 * 
	 * @param fileName
	 * @param mazeName
	 */
	@Override
	public void modelLoadMaze(String fileName, String mazeName) {
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
			setChanged();
			notifyObservers("Maze: " + mazeName + " was loaded successfully from file " + fileName);	
		}
		catch (FileNotFoundException e) {
			setChanged();
			notifyObservers("File " + fileName + " can't be opened");	
		}
		catch (IOException e) {
			setChanged();
			notifyObservers("File " + fileName + " can't be loaded");	
			}	
	}

	/**
	 * this method runs the wanted algorithm on the maze
	 * @param mazeName
	 * @param algorithm
	 */
	@Override
	public void modelSolveMaze(String mazeName, String algorithm) {
		executor.submit(new Callable<Solution<Position>>() {

			@Override
			public Solution<Position> call() throws Exception {
				if (!mazes.containsKey(mazeName)){
					setChanged();
					notifyObservers("Maze name not found");	
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
						setChanged();
						notifyObservers("Algorithm does not exist");	
						return null;
					}
					Solution <Position> sol = myAlgorithm.search(adapter);
					solutions.put(mazeName, sol);
					setChanged();
					notifyObservers("Solution for " + mazeName + " is ready");	
					return sol;
				}
				return null;
			}
				
		});		
	}

	/**
	 * this method gets a maze name and returns the solution for it
	 * @param name
	 * @return mySolution
	 */
	@Override
	public Solution<Position> modelGetSolution(String mazeName) {
		if (solutions.containsKey(mazeName)){
			Solution<Position> mySolution = solutions.get(mazeName);
			return mySolution;
		}
		return null;
	}
}
