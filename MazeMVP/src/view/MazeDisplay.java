package view;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class MazeDisplay extends Canvas {
	
	Image img = new Image (null, "Images/wall_Black.jpg");
	Image back = new Image (null, "Images/Optimus.jpg");
	Position startPosition ;
	Position goalPosition ;
	Position curentPosition ;
	int floors, rows, cols;
	Maze3d maze;
	
	Timer timer;
	TimerTask myTask;
	
	GameCharacter gameCharacter;
	//private int[][] mazeData;
	private int[][] mazeData = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
			{1,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
			{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
			{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
			{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
			{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
			{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}		
	};
	
	public void setMazeData(Maze3d maze) {
		int [][] mazeData = maze.getCrossSectionByZ(0);
		this.mazeData = mazeData;
		this.redraw();
	}
	
	public void setCanvas(Object arg){
		if(arg.getClass() == Maze3d.class){
		this.maze = (Maze3d)arg;
		this.floors = maze.getFloors();
		this.rows = maze.getRows();
		this.cols = maze.getCols();
		startPosition = maze.getStartPosition();
		goalPosition = maze.getGoalPosition();
		curentPosition = maze.getStartPosition();
		}
}
	
//	public void setMazeData(int[][] mazeData) {
//		this.mazeData = mazeData;
//		this.redraw();
//	}
	
	public MazeDisplay(Shell parent, int style) {
		super(parent, style);
		setBackground(new Color (null, 255,255,255));
		gameCharacter = new GameCharacter();
		gameCharacter.setPosition(new Position(2, 3, 3));
		
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
				              //e.gc.fillRectangle(x,y,w,h);
				        	  e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				      				 x, y, w, h);
				      }

//				   gameCharacter.paint(e, w, h);
				   gameCharacter.draw(w, h, e.gc);
			}
		});
	}
}
