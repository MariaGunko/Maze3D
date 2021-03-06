package model;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.MazeAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.LastNextMove;
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
import properties.Properties;
import properties.PropertiesLoader;

/**
 * The implementation of the MODEL interface
 * @author MariaAmiran
 *
 */
public class MyModel extends Observable implements Model {

	private ExecutorService executor;
	private Properties properties;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();


	/** 
	 * CTOR
	 */
	public MyModel() {
		properties = PropertiesLoader.getInstance().getProperties();
		executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
		loadSolutions();
	}	

	/**
	 * The method loads the solution file into a HashMap - CASHING
	 */
	@SuppressWarnings("unchecked")
	private void loadSolutions() {
		File file = new File ("Solutions.dat");
		if (!file.exists())
			return;

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream (new GZIPInputStream(new FileInputStream("solutions.dat")));
			mazes = (Map<String,Maze3d>)ois.readObject();
			solutions = (Map<String,Solution<Position>>)ois.readObject();	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	/**
	 * The method saves the maze and its solution into a solution file - CASHING
	 * for not calculating the same solution again
	 */
	private void saveSolutions (){
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream (new GZIPOutputStream (new FileOutputStream("Solutions.dat")));
			oos.writeObject(mazes);
			oos.writeObject(solutions);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * The method sets the properties and saves it to XML file
	 * @param prop1
	 * @param prop2
	 * @param prop3
	 */
	public void modelSetProperties(String prop1, String prop2, String prop3){
		properties.setSolveMazeAlgorithm(prop1);
		properties.setGenerateMazeAlgorithm(prop2);
		properties.setViewForm(prop3);

		XMLEncoder xmlEncoder = null;
		try {
			xmlEncoder = new XMLEncoder (new FileOutputStream("properties.xml"));
			xmlEncoder.writeObject(properties);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			xmlEncoder.close();
		}
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
	public void generateMaze(String name, int floors, int rows, int cols) {
		String formGenerate = properties.getGenerateMazeAlgorithm();
		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				switch(formGenerate)
				{
				case "GrowingTree_RandomNextMove":
					GrowingTreeGenerator generator = new GrowingTreeGenerator(new RandomNextMove());
					Maze3d maze = generator.generate(floors, rows, cols);
					mazes.put(name, maze);
					setChanged();
					notifyObservers("maze_ready " + name);		
					return maze;

				default:
					//case "GrowingTree_LastNextMove":
					GrowingTreeGenerator generator1 = new GrowingTreeGenerator(new LastNextMove());
					Maze3d maze1 = generator1.generate(floors, rows, cols);
					mazes.put(name, maze1);
					setChanged();
					notifyObservers("maze_ready " + name);		
					return maze1;

					//default:
					//SimpleMaze3dGenerator simpleMaze = new SimpleMaze3dGenerator();
					//Maze3d maze11 = simpleMaze.generate(floors, rows, cols);
				}
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
		saveSolutions();
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
				else if(solutions.containsKey(mazeName))
				{
					setChanged();
					notifyObservers("The Maze is already solved, the solution exsits in HashMap");
					return solutions.get(mazeName);
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
	 * @param mazeName
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
	public Properties getProperties() {
		return properties;
	}
}
