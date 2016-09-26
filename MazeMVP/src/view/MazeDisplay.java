package view;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;


public class MazeDisplay extends Canvas  {

	Image img = new Image (null, "Images/wall_Black.jpg");
	Image back = new Image (null, "Images/Optimus.jpg");
	Position startPosition ;
	Position goalPosition ;
	Position currentPosition ;
	int floors, rows, cols;
	Maze3d maze;
	Position checkPos;
	int [][] checkZ;

	Timer timer;
	TimerTask myTask;

	GameCharacter gameCharacter;
	private int[][] mazeData;
	//	private int[][] mazeData = {
	//			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	//			{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
	//			{1,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
	//			{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
	//			{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
	//			{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
	//			{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
	//			{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
	//			{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
	//			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}		
	//	};


	public void setMazeData(Maze3d maze) {
		this.maze = maze;
		System.out.println(maze);
		startPosition=maze.getStartPosition();
		goalPosition = maze.getGoalPosition();
		this.mazeData = this.maze.getCrossSectionByZ(startPosition.z);
		gameCharacter.setPosition(new Position(startPosition.z, startPosition.y, startPosition.x));
		floors=maze.getFloors();
		this.redraw();


	}

	public void setZ (int z)
	{
		this.mazeData = this.maze.getCrossSectionByZ(z);
//		if(mazeData[currentPosition.y][currentPosition.x]!=0)
//		{
//			return false;
//
//		}
//		else return true;
//		//this.redraw();
	}



	public void setCanvas(Object arg){
		if(arg.getClass() == Maze3d.class){
			this.maze = (Maze3d)arg;
			this.floors = maze.getFloors();
			this.rows = maze.getRows();
			this.cols = maze.getCols();
			startPosition = maze.getStartPosition();
			goalPosition = maze.getGoalPosition();
			currentPosition = maze.getStartPosition();
		}
	}


	public MazeDisplay(Shell parent, int style) {
		super(parent, style);
		setBackground(new Color (null, 255,255,0));
		gameCharacter = new GameCharacter();

		//gameCharacter.setPosition(startPosition);


		this.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				currentPosition = gameCharacter.getPosition();

				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:					
					checkPos=currentPosition;
					if(mazeData[checkPos.y][checkPos.x+1]==0)
					{
						gameCharacter.moveRight();	
						redraw();
					}
					break;

				case SWT.ARROW_LEFT:
					checkPos=currentPosition;
					if(mazeData[checkPos.y][checkPos.x-1]==0)
					{
						gameCharacter.moveLeft();	
						redraw();
					}
					break;

				case SWT.ARROW_UP:	
					checkPos=currentPosition;
					if(mazeData[checkPos.y-1][checkPos.x]==0)
					{
						gameCharacter.moveUp();	
						redraw();
					}
					break;

				case SWT.ARROW_DOWN:	
					//				Position checkPoint = new Position (currentPosition.z,currentPosition.x,currentPosition.y+1);
					//				Position [] arr = maze.getPossiblePossitions(currentPosition);
					//				for (int i=0;i<arr.length;i++)
					//				{
					//					
					checkPos=currentPosition;
					if(mazeData[checkPos.y+1][checkPos.x]==0)
					{
						gameCharacter.moveDown();	
						redraw();
					}
					break;

				case SWT.PAGE_UP:
					if (maze.getMaze()[currentPosition.z-1][currentPosition.y][currentPosition.x] == 0) 
					{
						setZ(currentPosition.z - 1);
						gameCharacter.moveFloorUp();
						redraw();
					}
					break;
					

				case SWT.PAGE_DOWN:		
					
					
					if (maze.getMaze()[currentPosition.z+1][currentPosition.y][currentPosition.x] == 0) 
					{
						setZ(currentPosition.z + 1);
						gameCharacter.moveFloorDown();
						redraw();
					}
					break;
					
		
				}
			}
		});


		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (mazeData == null)
					return;

				e.gc.setForeground(new Color(null,0,0,0));
				e.gc.setBackground(new Color(null,0,0,0));
				// e.gc.drawImage(back, 0, 0);


				int width=getSize().x;
				int height=getSize().y;

				int w=width/mazeData[0].length;
				int h=height/mazeData.length;

				for(int i=0;i<mazeData.length;i++)
					for(int j=0;j<mazeData[i].length;j++){
						int x=j*w;
						int y=i*h;
						if(mazeData[i][j]!=0)

							e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
									x, y, w, h);
					}
				gameCharacter.draw(w, h, e.gc);

			}
		});
		
		
	}
}
