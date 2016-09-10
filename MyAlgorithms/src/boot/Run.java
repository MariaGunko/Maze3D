package boot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomNextMove;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import algorithms.demo.Demo;
import algorithms.demo.MazeAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.LastNextMove;

public class Run {
	private static void testMazeGenerator(Maze3dGenerator mg){  
		// set the size of the maze
		int n=4;
		// prints the time it takes the algorithm to run   
		System.out.println("Algorithm Run Time: " + mg.measureAlgorithmTime(2*n+1,2*n+1,2*n+1));   
		// generate another 3d maze   
		Maze3d maze=mg.generate(2*n+1,2*n+1,2*n+1);  
		// get the maze entrance   
		Position p=maze.getStartPosition();   
		// print the position   
		System.out.println("Start Position: " + p); // format (z,x,y)  
		// get all the possible moves from a position   
		String[] moves=maze.getPossibleMoves(p);   
		// print the moves  
		System.out.println("Possible Moves: ");
		for(String move : moves) 
			if (move!=null)
				System.out.println(move);   
		// prints the maze exit position   
		System.out.println("Goal Position: " + maze.getGoalPosition());    
		// prints the maze after generate 
		System.out.println("The Maze: ");
		System.out.println(maze);
		try{    // get 2d cross sections of the 3d maze   
			
			System.out.println("Crossed Section By X: ");
			int[][] maze2dx=maze.getCrossSectionByX(2); 
			for (int i=0;i<maze.getFloors();i++)
			{
				for (int j=0;j<maze.getCols();j++)
					System.out.print(maze2dx[i][j]);
				System.out.println();
			}
			
			System.out.println("Crossed Section By Y: "); 
			int[][] maze2dy=maze.getCrossSectionByY(3);    
			for (int i=0;i<maze.getFloors();i++)
			{
				for (int j=0;j<maze.getRows();j++)
					System.out.print(maze2dy[i][j]);
				System.out.println();
			}
			
			System.out.println("Crossed Section By Z: ");
			int[][] maze2dz=maze.getCrossSectionByZ(1); 
			for (int i=0;i<maze.getRows();i++)
			{
				for (int j=0;j<maze.getCols();j++)
					System.out.print(maze2dz[i][j]);
				System.out.println();
			}
			     
			// this should throw an exception!    
			maze.getCrossSectionByX(-1);       
		} 
		catch (IndexOutOfBoundsException e){    
			System.out.println("good!");   
		}  
	}

	public static void main(String[] args) {
//		System.out.println("~*~*~*~*~*~*~*~*~ Simple Maze ~*~*~*~*~*~*~*~*~");   
//		testMazeGenerator(new SimpleMaze3dGenerator()); 
//		System.out.println("~*~*~*~*~*~ Growing Tree by Random ~*~*~*~*~*~*~"); 
//		testMazeGenerator(new GrowingTreeGenerator(new RandomNextMove())); 
//		System.out.println("~*~*~*~*~*~ Growing Tree By Last ~*~*~*~*~*~*~*~"); 
//		testMazeGenerator(new GrowingTreeGenerator(new LastNextMove())); 
		
		// Demo.run();
		
		Maze3dGenerator myGenerator = new GrowingTreeGenerator(new RandomNextMove());
		Maze3d maze=myGenerator.generate(2,2,2);  
		System.out.println(maze);
		
		int a,b;
		
		// save it to a file
		OutputStream out;
		try {
			out = new MyCompressorOutputStream(
					new FileOutputStream("1.maz"));
			byte[] arr = maze.toByteArray();
		
			a = arr.length/255;	
			b = arr.length%255;
			
			out.write(a);
			out.write(b);
		
			out.write(arr);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		InputStream in;
		try {
			in = new MyDecompressorInputStream(
				new FileInputStream("1.maz"));
			
			int sizeA = in.read();
			int sizeB = in.read();
			int totalSize = sizeA * 255 + sizeB;
			
			byte bytes[]=new byte[totalSize];

			in.read(bytes);
			in.close();	
			
			Maze3d loaded = new Maze3d(bytes);
			System.out.println("Maze loaded from file:");
			System.out.println(loaded);
			System.out.println(maze.equals(loaded));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

}
