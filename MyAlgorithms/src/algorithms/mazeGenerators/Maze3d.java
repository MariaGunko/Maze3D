package algorithms.mazeGenerators;

import java.util.ArrayList;

public class Maze3d {
	private int [][][] maze;
	private int rows;
	private int cols;
	private int floors;
	private Position startPosition;
	private Position goalPosition;

	
	public Maze3d (int floors, int rows, int cols){
		this.floors=((2*floors)+1);
		this.rows=((2*rows)+1);
		this.cols=((2*cols)+1);
		
		maze = new int [this.floors][this.rows][this.cols];
	}
	
	public Maze3d (int a, int b, int c, byte [] arr){
		int k=0;
		this.floors = a;
		this.rows = b;
		this.cols = c;
		maze = new int [floors][rows][cols];
		
		Position startPos = new Position (arr[k++], arr[k++], arr[k++]);
		this.setStartPosition(startPos);
		Position goalPos = new Position (arr[k++], arr[k++], arr[k++]);
		this.setStartPosition(goalPos);
		
		for (int z=0;z<floors ;z++)
		{
			for (int x=0;x< rows;x++)
			{
				for (int y=0;y< cols;y++)
				{
					maze[z][x][y]=arr[k++];
				}
			}
		}
	}

	public byte [] toByteArray (){
		ArrayList <Byte> arr = new ArrayList<Byte>();
		arr.add((byte)floors);
		arr.add((byte)rows);
		arr.add((byte)cols);
		arr.add((byte)startPosition.z);
		arr.add((byte)startPosition.x);
		arr.add((byte)startPosition.y);
		arr.add((byte)goalPosition.z);
		arr.add((byte)goalPosition.x);
		arr.add((byte)goalPosition.y);
		
		for (int i=0; i<floors;i++){
			for (int j=0;j<rows;j++){
				for (int k=0;k<cols;k++){
					arr.add((byte)maze[i][j][k]);
				}
			}
		}
		
		byte [] bytes = new byte [arr.size()];
		for (int m=0;m<bytes.length;m++)
		{
			bytes[m] = (byte)arr.get(m);
		}
		return bytes;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int z=0;z<floors ;z++)
		{
			for (int x=0;x< rows;x++)
			{
				for (int y=0;y< cols;y++)
				{
					if (z==startPosition.z&&x==startPosition.x&&y==startPosition.y)
					{
						sb.append("E");
					}
					else 
					{ 
						if (z==goalPosition.z&&x==goalPosition.x&&y==goalPosition.y)
						{
							sb.append("X");
						}
						else
							sb.append(maze[z][x][y]);
					}	
				}
			sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String [] getPossibleMoves (Position p)
	{
		int index=0;
		String [] possibleMoves = new String[6];
		
		if (p.z!=0)
		{
			if (maze[p.z-1][p.x][p.y]==0)
			{
				possibleMoves [index] = "(" + (p.z-1) + "," + p.x + "," + p.y  + ")";
				index++;
			}
		}
			
		if (p.z+1!=this.getFloors())
		{
			if (maze[p.z+1][p.x][p.y]==0)
			{
				possibleMoves [index] = "(" + (p.z+1) + "," + p.x + "," + p.y  + ")";
				index++;
			}
		}
		
		if (p.x!=0)
		{
			if (maze[p.z][p.x-1][p.y]==0)
			{
				possibleMoves [index] = "(" + p.z + "," + (p.x-1) + "," + p.y  + ")";
				index++;
			}
		}
			
		if (p.x+1!=this.getRows())
		{
			if (maze[p.z][p.x+1][p.y]==0)
			{
				possibleMoves [index] = "(" + p.z + "," + (p.x+1) + "," + p.y  + ")";
				index++;
			}
		}
		
		if (p.y!=0)
		{
			if (maze[p.z][p.x][p.y-1]==0)
			{
				possibleMoves [index] = "(" + p.z + "," + p.x + "," + (p.y-1)  + ")";
				index++;
			}
		}
			
		if (p.y+1!=this.getCols())	
		{
			if (maze[p.z][p.x][p.y+1]==0)
			{
				possibleMoves [index] = "(" + p.z + "," + p.x + "," + (p.y+1)  + ")";
				index++;
			}
		}
		
		return possibleMoves;
	}

	public int [][] getCrossSectionByX (int x)
	{
		int [][] CrossSectionByX = new int [this.floors][this.cols];
		for (int i=0;i<this.floors;i++)
			for (int j=0;j<this.cols;j++)
				CrossSectionByX[i][j]=this.getMaze()[i][x][j];
		return CrossSectionByX;
	}
	
	public int [][] getCrossSectionByY (int y)
	{
		int [][] CrossSectionByY = new int [this.floors][this.rows];
		for (int i=0;i<this.floors;i++)
			for (int j=0;j<this.rows;j++)
				CrossSectionByY[i][j]=this.getMaze()[i][j][y];
		return CrossSectionByY;
	}
	
	public int [][] getCrossSectionByZ (int z)
	{
		int [][] CrossSectionByZ = new int [this.rows][this.cols];
		for (int i=0;i<this.rows;i++)
			for (int j=0;j<this.cols;j++)
				CrossSectionByZ[i][j]=this.getMaze()[z][i][j];
		return CrossSectionByZ;
	}
	
	public void setFreeCell (Position pos)
	{
		maze[pos.z][pos.x][pos.y]=0;
	}
	
	public Position getStartPosition() {
		return startPosition;
	}


	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}


	public Position getGoalPosition() {
		return goalPosition;
	}


	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}


	public int getRows() {
		return rows;
	}


	public int getCols() {
		return cols;
	}


	public int getFloors() {
		return floors;
	}

	public int[][][] getMaze() {
		return maze;
	}
	
	public Position [] getPossiblePossitions(Position p)
	{
		int index=0;
		Position [] possibleMoves = new Position[6];
		
		if (p.z!=0)
		{
			if (maze[p.z-1][p.x][p.y]==0)
			{
				possibleMoves [index] =new Position (p.z-1, p.x, p.y);
				index++;
			}
		}
			
		if (p.z+1!=this.getFloors())
		{
			if (maze[p.z+1][p.x][p.y]==0)
			{
				possibleMoves [index] = new Position (p.z+1, p.x, p.y);
				index++;
			}
		}
		
		if (p.x!=0)
		{
			if (maze[p.z][p.x-1][p.y]==0)
			{
				possibleMoves [index] = new Position (p.z, p.x-1, p.y);
				index++;
			}
		}
			
		if (p.x+1!=this.getRows())
		{
			if (maze[p.z][p.x+1][p.y]==0)
			{
				possibleMoves [index] = new Position (p.z, p.x+1, p.y);
				index++;
			}
		}
		
		if (p.y!=0)
		{
			if (maze[p.z][p.x][p.y-1]==0)
			{
				possibleMoves [index] = new Position (p.z, p.x, p.y-1);
				index++;
			}
		}
			
		if (p.y+1!=this.getCols())	
		{
			if (maze[p.z][p.x][p.y+1]==0)
			{
				possibleMoves [index] = new Position (p.z, p.x, p.y+1);
				index++;
			}
		}
		
		return possibleMoves;
	}
}
