package algorithms.demo;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomNextMove;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;

public class Demo {

	public static void run() {
		// Create a 3D maze
		Maze3dGenerator myGenerator = new GrowingTreeGenerator(new RandomNextMove());
		Maze3d myMaze=myGenerator.generate(5,5,5);  
		System.out.println(myMaze);
		
		Searchable<Position> ma = new MazeAdapter(myMaze);
		
		// Solve the maze with BFS Algorithm
		Searcher<Position> BfsSolution = new BFS<Position>();
		Solution<Position> s1 = BfsSolution.search(ma);

		System.out.println("~*~*~*~*~ BFS solution ~*~*~*~*~");
		System.out.println(s1.toString());
		System.out.println("Number of evaluated nodes:");
		System.out.println(BfsSolution.getNumberOfNodesEvaluated());

	
		// Solve the maze with DFS Algorithm
		Searcher<Position> DfsSolution = new DFS<Position>();
		Solution<Position> s2 =  DfsSolution.search(ma);

		System.out.println("~*~*~*~*~ DFS solution ~*~*~*~*~");
		System.out.println(s2.toString());
		System.out.println("Number of evaluated nodes:");
		System.out.println(DfsSolution.getNumberOfNodesEvaluated());
		
	}

}
