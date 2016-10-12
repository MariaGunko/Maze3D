package view;


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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * This class is responsible for the maze display
 * @author Maria&Amiran
 *
 */
public class MazeDisplay extends Canvas  {

	Image SilvesPic = new Image (null, "images/silvestre.gif");
	Image goalImg = new Image (null, "images/tweety3.gif");
	Image img = new Image (null, "Images/wall_Black.jpg");
	Image hint = new Image (null, "Images/coin.gif");
	Image image=new Image(null,"images/back.jpg");

	Position startPosition ;
	Position goalPosition ;
	Position currentPosition ;

	boolean gameStarted;
	int floors, rows, cols, currentFloor;

	Maze3d maze;
	Position checkPos;
	int [][] checkZ;

	Timer timer;
	TimerTask myTask;

	GameCharacter gameCharacter;
	GameCharacter tweety;
	GameCharacter coin;

	PaintListener PL;
	PaintListener PLwalls;

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

	/**
	 * This function gets a maze and updates all its details
	 * @param maze
	 */
	public void setMazeData(Maze3d maze) {
		this.maze = maze;
		System.out.println(maze);
		startPosition=maze.getStartPosition();
		goalPosition = maze.getGoalPosition();
		this.mazeData = this.maze.getCrossSectionByZ(startPosition.z);
		currentFloor=maze.getStartPosition().z;

		gameCharacter.setImg(SilvesPic);
		tweety.setImg(goalImg);
		coin.setImg(hint);

		gameCharacter.setPosition(new Position(startPosition.z, startPosition.y, startPosition.x));
		tweety.setPosition(new Position(goalPosition.z, goalPosition.y, goalPosition.x));

		floors=maze.getFloors();
		rows=maze.getRows();
		cols=maze.getCols();

		gameStarted = true;
		this.redraw();
	}

	/**
	 * This function gets two positions and moves the character between both
	 * @param currPos
	 * @param newPos
	 */
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
		else if (newPos.z == currPos.z + 2){
			setZ(currPos.z +2);
			//setZ(newPos.z);
			gameCharacter.moveFloorDown();
			redraw();

		}
		else if (newPos.z == currPos.z -2){
			//setZ(newPos.z);
			setZ(currPos.z -2);
			gameCharacter.moveFloorUp();
			redraw();
		}
	}

	/**
	 * This function returns the character to its starting position
	 * for displaying the automatic solution
	 */
	public void GoBack ()
	{
		if (!gameCharacter.getPosition().equals(maze.getStartPosition()))
		{
			gameCharacter.setPosition2(startPosition.z,startPosition.y,startPosition.x);
			redraw();
		}
	}

	/**
	 * This function gets a solution of positions and draws coins 
	 * through the path
	 * @param solve
	 */
	public void showHint (Solution<Position> solve){
		List<State<Position>> states = solve.getStates();
		this.addPaintListener(PL = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				e.gc.setBackground(new Color(null,0,0,0));

				int width=getSize().x;
				int height=getSize().y;

				int w=width/mazeData[0].length;
				int h=height/mazeData.length;

				for (int i=1;i<states.size()-1;i++){
					Position pos = states.get(i).getValue();
					if (pos.z==currentFloor)
					{
						int x=pos.y*w;
						int y=pos.x*h;
						e.gc.drawImage(hint, 0, 0, hint.getBounds().width, hint.getBounds().height, 
								x, y, w, h);
					}
					gameCharacter.draw(w, h, e.gc);
				}
			}
		});

	}


	/**
	 * This function gets a solution of positions and makes sylvester
	 * move in this path
	 * @param solve
	 */
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

							if ((s2.z==s1.z+1)||(s2.z==s1.z-1))
							{
								i++;
								s2 = states.get(i).getValue();
							}
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
		timer.scheduleAtFixedRate(task, 0, 250);
	}

	/**
	 * This function gets a floor and makes a
	 * crossed section by this number for displaying the 2d maze
	 * @param z
	 */
	public void setZ (int z)
	{
		currentFloor=z;
		this.mazeData = this.maze.getCrossSectionByZ(z);
	}

	/**
	 * This function checks if the character arrived to the goal position 
	 * and displays a winning message
	 */
	public void winner () {

		if(tweety.getPosition().equals(gameCharacter.getPosition()))
		{


			Shell GenerateShell = new Shell(getDisplay());
			GenerateShell.setLayout(new  GridLayout(2, false));

			MessageBox msg = new MessageBox(GenerateShell, SWT.OK);
			msg.setText("Winner");
			msg.setMessage("Congratulations you got tweety :)");
			msg.open();
			
			if (PL!=null)
				this.removePaintListener(PL);
			//this.removePaintListener(PLwalls);

			gameStarted = false;
		}
	}

	public MazeDisplay(Shell parent, int style) {
		super(parent, style);

		setBackground(new Color (null, 255,255,0));
		gameCharacter = new GameCharacter();
		tweety = new GameCharacter();
		coin = new GameCharacter();

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				currentPosition = gameCharacter.getPosition();
				if (gameStarted){
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
							setZ(currentPosition.z - 2);
							gameCharacter.moveFloorUp();
							redraw();
							winner();
						}
						break;


					case SWT.PAGE_DOWN:	

						if (maze.getMaze()[currentPosition.z+1][currentPosition.y][currentPosition.x] == 0) 
						{
							setZ(currentPosition.z + 2);
							gameCharacter.moveFloorDown();
							redraw();
							winner();
						}
						break;

					}
				}

			}
		});
		PLwalls = new PaintListener() {

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

				// draw all the walls
				for(int i=0;i<mazeData.length;i++)
					for(int j=0;j<mazeData[i].length;j++){
						int x=j*w;
						int y=i*h;
						if(mazeData[i][j]!=0)

							e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
									x, y, w, h);
					}

				gameCharacter.draw(w, h, e.gc);
				if (tweety.getPosition().z==currentFloor)
					tweety.draw(w, h, e.gc);

			}
		};

		this.addPaintListener(PLwalls);
	}
}
