package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrowingTreeGenerator extends Maze3dGeneratorBase{

	private Random rand = new Random();

	public GrowingTreeGenerator(CommonGrowingTree CTG) {
		this.nextMove=CTG;
	}

	private Position getRandomGoalPosition (Maze3d maze, Position startPosition){
		int z = rand.nextInt(maze.getFloors());
		int x = rand.nextInt(maze.getRows());
		int y =  rand.nextInt(maze.getCols());


		// while wall - set random again
		while ((maze.getMaze()[z][x][y]==1)||((z==startPosition.z)&&(x==startPosition.x)&&(y==startPosition.y))){
			z = rand.nextInt(maze.getFloors());
			x = rand.nextInt(maze.getRows());
			y =  rand.nextInt(maze.getCols());
		}

		Position p = new Position (z,x,y);
		return p;
	}

	private Position getRandomPosition (Maze3d maze){
		int x = rand.nextInt(maze.getRows());
		while (x % 2 == 0){
			x = rand.nextInt(maze.getRows());
		}

		int y = rand.nextInt(maze.getCols());
		while (y % 2 == 0){
			y = rand.nextInt(maze.getCols());
		}

		int z = rand.nextInt(maze.getFloors());
		while (z % 2 == 0){
			z = rand.nextInt(maze.getFloors());
		}

		Position p = new Position (z,x,y);
		return p;
	}

	private void initializeMaze (Maze3d maze){
		for (int i=0; i<maze.getFloors();i++)
		{
			for (int j=0;j<	maze.getRows();j++)
			{
				for (int k=0;k<maze.getCols();k++)
				{
					maze.getMaze()[i][j][k]=1;
				}

			}
		}
	}

	private List <Position> getUnvisitedNeighbors (Maze3d maze,Position pos){
		int [][][] mat = maze.getMaze();
		List<Position> neighbors = new ArrayList<Position>(); 


		if (pos.x - 2 >= 0){ 		// if not lowest wall
			if (mat[pos.z][pos.x-2][pos.y] == 1)
			{
				neighbors.add(new Position (pos.z, pos.x-2, pos.y));
			}
		}

		if (pos.x + 2 < maze.getRows()){ 		// if not highest wall
			if (mat[pos.z][pos.x+2][pos.y] == 1)
			{
				neighbors.add(new Position (pos.z, pos.x+2, pos.y));
			}
		}

		if (pos.y - 2 >= 0){ 		// if not left wall
			if (mat[pos.z][pos.x][pos.y-2] == 1)
			{
				neighbors.add(new Position (pos.z, pos.x, pos.y-2));
			}
		}

		if (pos.y + 2 < maze.getCols()){ 		// if not right wall
			if (mat[pos.z][pos.x][pos.y+2] == 1)
			{
				neighbors.add(new Position (pos.z, pos.x, pos.y+2));
			}
		}

		if (pos.z - 2 >= 0){ 		// if not ground floor
			if (mat[pos.z-2][pos.x][pos.y] == 1)
			{
				neighbors.add(new Position (pos.z-2, pos.x, pos.y));
			}
		}

		if (pos.z + 2 < maze.getFloors()){ 		// if not highest floor
			if (mat[pos.z+2][pos.x][pos.y] == 1)
			{
				neighbors.add(new Position (pos.z+2, pos.x, pos.y));
			}
		}

		return neighbors;
	}

	@Override
	public Maze3d generate(int floors, int rows, int cols) {
		Maze3d algMaze = new Maze3d(floors,rows,cols);
		initializeMaze(algMaze);
		ArrayList<Position> C = new ArrayList<Position>(); 

		Position startPosition = getRandomPosition (algMaze);
		C.add(startPosition);
		algMaze.setStartPosition(startPosition);
		algMaze.setFreeCell(startPosition);

		while (!C.isEmpty())
		{
			Position pos = nextMove.buildPathBy(C);

			List<Position> neighbors = getUnvisitedNeighbors(algMaze, pos);
			if (neighbors.size()>0)
			{
				int idx = rand.nextInt(neighbors.size());
				Position neighbor = neighbors.get(idx);
				algMaze.setFreeCell(neighbor);

				createPassageBetweenCells (algMaze,pos,neighbor);
				C.add(neighbor);
			}
			else
			{
				C.remove(pos);
			}
		}

		Position goalPosition = getRandomGoalPosition (algMaze, startPosition);
		algMaze.setGoalPosition(goalPosition);

		return algMaze;
	}

	private void createPassageBetweenCells (Maze3d maze, Position pos,Position neighbor){
		if (neighbor.x == pos.x - 2)
			maze.setFreeCell (new Position(pos.z, pos.x-1,pos.y));

		if (neighbor.x == pos.x + 2)
			maze.setFreeCell (new Position(pos.z, pos.x+1,pos.y));

		if (neighbor.y == pos.y - 2)
			maze.setFreeCell (new Position(pos.z, pos.x,pos.y-1));

		if (neighbor.y == pos.y + 2)
			maze.setFreeCell (new Position(pos.z, pos.x,pos.y+1));

		if (neighbor.z == pos.z - 2)
			maze.setFreeCell (new Position(pos.z-1, pos.x,pos.y));

		if (neighbor.z == pos.z + 2)
			maze.setFreeCell (new Position(pos.z+1, pos.x,pos.y));

	}	
}
