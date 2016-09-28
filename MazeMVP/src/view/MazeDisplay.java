package view;

import java.util.ArrayList;
import java.util.List;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.omg.CORBA.PUBLIC_MEMBER;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.Solution;
import algorithms.search.State;


public class MazeDisplay extends Canvas  {

	Image img = new Image (null, "Images/wall_Black.jpg");
	Image back = new Image (null, "Images/Optimus.jpg");
	Position startPosition ;
	Position goalPosition ;
	Position currentPosition ;
	int floors, rows, cols;
	int currentFloor;
	Maze3d maze;
	Position checkPos;
	int [][] checkZ;

	Timer timer;
	TimerTask myTask;

	GameCharacter gameCharacter;
	GameCharacter tweety;

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
		tweety.setTweetyPosition(new Position(goalPosition.z, goalPosition.y, goalPosition.x));

		floors=maze.getFloors();
		rows=maze.getRows();
		cols=maze.getCols();
		this.redraw();
	}

	private void moveCat (Position currPos, Position newPos) {
		if (newPos.y == currPos.y - 1)
		{
			gameCharacter.moveLeft();
			redraw();
		}
		else if (newPos.y == currPos.y + 1)
		{
			gameCharacter.moveRight();
			redraw();
		}
		else if (newPos.x == currPos.x - 1)
		{
			gameCharacter.moveUp();
			redraw();
		}
		else if (newPos.x == currPos.x + 1)
		{

			gameCharacter.moveDown();
			redraw();
		}
		else if (newPos.z == currPos.z + 1){
			setZ(newPos.z);
			gameCharacter.moveFloorDown();
			redraw();

		}
		else if (newPos.z == currPos.z -1){
			setZ(newPos.z);
			gameCharacter.moveFloorUp();
			redraw();
		}
	}

	public void GoBack ()
	{
		if (!gameCharacter.getPosition().equals(maze.getStartPosition()))
		{
			gameCharacter.setPosition2(startPosition.z,startPosition.y,startPosition.x);
			redraw();
		}
	}
	
	public void showSolution(Solution<Position> solve)
	{
		GoBack();

		Timer timer = new Timer();
		TimerTask task = new TimerTask() 
	
		{
			int i=1;
			List<State<Position>> states = solve.getStates();
			Position s1 = states.get(0).getValue();
			int goal=states.size()-1;

			@Override
			public void run() {	
				getDisplay().syncExec(new Runnable() {					


					@Override
					public void run() {
						if(i<=goal){
							//timer.cancel();
							Position s2 = states.get(i).getValue();
							System.out.println(s1+"        "+s2);

							moveCat(s1,s2);
							s1=s2;
							i=i+1;
							winner();
						}
						else timer.cancel();
					}	
				});
			}
		};
		timer.scheduleAtFixedRate(task, 0, 500);
	}


	public void setZ (int z)
	{
		currentFloor=z;
		this.mazeData = this.maze.getCrossSectionByZ(z);
	}

	public void winner () {
		Shell GenerateShell = new Shell(getDisplay());
		if(tweety.getTweetyPosition().equals(gameCharacter.getPosition()))
		{
			MessageBox msg = new MessageBox(GenerateShell, SWT.OK);
			msg.setText("Winner");
			msg.setMessage("Congratulations you got tweety :)");
			msg.open();
		}
	}

	public MazeDisplay(Shell parent, int style) {
		super(parent, style);
		setBackground(new Color (null, 255,255,0));
		gameCharacter = new GameCharacter();
		tweety = new GameCharacter();

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
						winner();
					}
					break;

				case SWT.ARROW_LEFT:
					checkPos=currentPosition;
					if(mazeData[checkPos.y][checkPos.x-1]==0)
					{
						gameCharacter.moveLeft();
						redraw();
						winner();
					}
					break;

				case SWT.ARROW_UP:	
					checkPos=currentPosition;
					if(mazeData[checkPos.y-1][checkPos.x]==0)
					{
						gameCharacter.moveUp();
						redraw();
						winner();
					}
					break;

				case SWT.ARROW_DOWN:	

					checkPos=currentPosition;
					if(mazeData[checkPos.y+1][checkPos.x]==0)
					{
						gameCharacter.moveDown();	
						redraw();
						winner();
					}
					break;

				case SWT.PAGE_UP:
					if (maze.getMaze()[currentPosition.z-1][currentPosition.y][currentPosition.x] == 0) 
					{
						setZ(currentPosition.z - 1);
						gameCharacter.moveFloorUp();
						redraw();
						winner();
					}
					break;


				case SWT.PAGE_DOWN:		


					if (maze.getMaze()[currentPosition.z+1][currentPosition.y][currentPosition.x] == 0) 
					{
						setZ(currentPosition.z + 1);
						gameCharacter.moveFloorDown();
						redraw();
						winner();
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
				if (tweety.getTweetyPosition().z==currentFloor)
					tweety.drawTweety(w, h, e.gc);


			}
		});


	}

}
