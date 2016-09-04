package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMaze3dGenerator extends Maze3dGeneratorBase{
	
	Random rand = new Random();
	
	public SimpleMaze3dGenerator() {
		super();
	}

	private void setWalls (Maze3d maze){
		// Set the lowest and highest floor
			for (int j=0;j<	maze.getRows();j++)
			{
				for (int k=0;k<maze.getCols();k++)
				{
					maze.getMaze()[0][j][k]=1;
					maze.getMaze()[maze.getFloors()-1][j][k]=1;
				}
				
			}
			
		// set the frame of each floor
			for (int i=1;i<maze.getFloors()-1;i++){
				for (int k=0;k<maze.getCols();k++)
				{
					maze.getMaze()[i][0][k]=1;
					maze.getMaze()[i][maze.getRows()-1][k]=1;
				}
				for (int m=0;m<maze.getRows();m++)
				{
					maze.getMaze()[i][m][0]=1;
					maze.getMaze()[i][m][maze.getCols()-1]=1;
				}
				
			}
	}
	
	private Position getRandomPosition (Maze3d maze, Position start){
		int z = rand.nextInt(maze.getFloors() - 2) + 1;
		int x = rand.nextInt(maze.getRows() - 2) + 1;
		int y = rand.nextInt(maze.getCols() - 2) + 1;
		
		if (start!=null)
		{
			while ((z==start.z)&&(x==start.x)&&(y==start.y)){
				z = rand.nextInt(maze.getFloors() - 2) + 1;
				x = rand.nextInt(maze.getRows() - 2) + 1;
				y = rand.nextInt(maze.getCols() - 2) + 1;
			}
		}

		Position p = new Position (z,x,y);
		return p;
	}
	
	@Override
	public Maze3d generate(int floors, int rows, int cols) {
		Maze3d randomMaze = new Maze3d(floors,rows,cols);
		setWalls(randomMaze);
		
		Position start = getRandomPosition(randomMaze,null);
		Position goal = getRandomPosition(randomMaze, start);
		
		randomMaze.setStartPosition(start);
		randomMaze.setGoalPosition(goal);
		
		int  n;
		for (int i=1; i<floors - 1;i++)
		{
			for (int j=1;j<	rows - 1;j++)
			{
				for (int k=1;k<cols - 1;k++)
				{
					n = rand.nextInt(2);
					randomMaze.getMaze()[i][j][k]=n;
				}
				
			}
		}	
		
		int x=start.x;
		int y=start.y;
		int z=start.z;
		
		// set the path on the X axis
		if (x<goal.x)
		{
			while (x!=goal.x)
			{
				randomMaze.getMaze()[z][x][y]=0;
				x++;
			}
		}
		else if (x>goal.x)
		{
			while (x!=goal.x)
			{
				randomMaze.getMaze()[z][x][y]=0;
				x--;
			}
		}
		
		// set the path on the Y axis
		if (y<goal.y)
		{
			while (y!=goal.y)
			{
				randomMaze.getMaze()[z][x][y]=0;
				y++;
			}
		}
		else if (y>goal.y)
		{
			while (y!=goal.y)
			{
				randomMaze.getMaze()[z][x][y]=0;
				y--;	
			}
		}
		
		// set the path on the Z axis
		if (z<goal.z)
		{
			while (z!=goal.z)
			{
				randomMaze.getMaze()[z][x][y]=0;
				z++;
			}
		}
		else if (z>goal.z)
		{
			while (z!=goal.z)
			{
				randomMaze.getMaze()[z][x][y]=0;
				z--;	
			}
		}

		return randomMaze;
	}
}
