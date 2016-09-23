package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;

public class GeneralWindow extends BasicWindow {

	private Maze3d maze;
	private MazeDisplay mazeDisplay;
	MouseWheelListener mouseZoomlListener;
	String nameMaze = null;

	public GeneralWindow(int width, int height) {
		super(width, height);
	}

	@Override
	protected void initWidgets() {

		shell.setText("Welcome to the MAZE GAME");
		shell.setLayout(new org.eclipse.swt.layout.GridLayout(2, false));

		Color blue = display.getSystemColor(SWT.COLOR_CYAN);
		Color yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		shell.setBackground(yellow);


		Button generateButton = new Button(shell, SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		generateButton.setBackground(blue);

		generateButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Thread thread = new Thread (new Runnable() {
					public void run() {
						Display GenerateDisplay = new Display();
						Shell GenerateShell = new Shell(GenerateDisplay);

						GenerateShell.setLayout(new  GridLayout(2, false));

						Label lblName = new Label(GenerateShell, SWT.NONE);
						lblName.setText("Maze name: ");
						Text txtName = new Text(GenerateShell, SWT.BORDER);
						txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

						Label lblFloors = new Label(GenerateShell, SWT.NONE);
						lblFloors.setText("Floors: ");
						Text txtFloors = new Text(GenerateShell, SWT.BORDER);
						txtFloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));


						Label lblRows = new Label(GenerateShell, SWT.NONE);
						lblRows.setText("Rows: ");
						Text txtRows = new Text(GenerateShell, SWT.BORDER);
						txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));


						Label lblColumns = new Label(GenerateShell, SWT.NONE);
						lblColumns.setText("Columns: ");
						Text txtColumns = new Text(GenerateShell, SWT.BORDER);
						txtColumns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));


						Button GenerateMaze = new Button(GenerateShell, SWT.PUSH);
						GenerateShell.setDefaultButton(GenerateMaze);
						GenerateMaze.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
						GenerateMaze.setText("Generate maze");

						GenerateShell.setText("Generate Maze");
						GenerateShell.setSize(400,250);
						GenerateShell.open();

						GenerateMaze.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent arg0) {				
								MessageBox msg = new MessageBox(GenerateShell, SWT.OK);
								nameMaze=txtName.getText();
								msg.setText("Create Maze -> "+nameMaze);

								int rows = Integer.parseInt(txtRows.getText());
								int cols = Integer.parseInt(txtColumns.getText());
								int floors = Integer.parseInt(txtFloors.getText());
								

								String setMaze="generate_maze "+nameMaze+" "+floors+" "+rows+" "+cols;
								
								msg.setMessage("Generating maze: "+nameMaze +" Floors: "+floors+ " rows: " + rows + " cols: " + cols);
								msg.open();
								
								setChanged();
								notifyObservers(setMaze);
								notifyMazeIsReady(nameMaze);

								GenerateShell.close();
								
								//setChanged();
								//notifyObservers("display"+" "+ nameMaze);
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent arg0) {			

							}
						});	


						while(!GenerateShell.isDisposed()){
							if(!GenerateDisplay.readAndDispatch()){
								GenerateDisplay.sleep();
							}
						}
						GenerateDisplay.dispose();
					}
				});
				thread.start();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

//		Text t=new Text(shell,SWT.MULTI|SWT.BORDER);
//		t.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,7));
//		final Image image=new Image(display,"images/background.jpg");
//		t.setBackgroundImage(image);	

		mazeDisplay = new MazeDisplay(shell, SWT.NONE);		
		//mazeDisplay.setMazeData(maze);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,7));
		mazeDisplay.setFocus();

		Button displayButton = new Button(shell, SWT.PUSH);
		displayButton.setText("Display Maze");
		displayButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		displayButton.setBackground(blue);


		displayButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				setChanged();
				notifyObservers("display"+" "+ nameMaze);	
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});


		Button solveButton = new Button(shell, SWT.PUSH);
		solveButton.setText("Solve Maze");
		solveButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		solveButton.setBackground(blue);


		solveButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {	
				setChanged();
				//nameMaze=windowGenerate.getMazeName();
				//notifyObservers("solve"+" "+ "amiran" +" "+"BFS");
				notifyObservers("solve"+" "+ nameMaze +" "+"BFS");				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});


		Button displayMazeSolutionButton = new Button(shell, SWT.PUSH);
		displayMazeSolutionButton.setText("Display Maze solution");
		displayMazeSolutionButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		displayMazeSolutionButton.setBackground(blue);

		displayMazeSolutionButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {	
				setChanged();
				//notifyObservers("display_solution"+" "+ "amiran");	
				notifyObservers("display_solution"+" "+ nameMaze);	
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button saveButton = new Button(shell, SWT.PUSH);
		saveButton.setText("Save Maze");
		saveButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		saveButton.setBackground(blue);

		Button loadButton = new Button(shell, SWT.PUSH);
		loadButton.setText("load Maze");
		loadButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		loadButton.setBackground(blue);

		Button exitButton = new Button(shell, SWT.PUSH);
		exitButton.setText("EXIT");
		exitButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		exitButton.setBackground(blue);

		exitButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {	
				setChanged();
				notifyObservers("exit");
				shell.close();			
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});


		Button musicButton = new Button(shell, SWT.PUSH);
		musicButton.setText("Music");
		musicButton.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false, 1, 1));
		musicButton.setBackground(blue);



		//	shell.pack();
		//mouse zoom do it
		mouseZoomlListener = new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent e) {
				// if both ctrl and wheel are being operated
				//	if ((e.stateMask & SWT.CTRL) != 0)
				//mazePainterAdapter.mazePainter.setSize(mazePainterAdapter.mazePainter.getSize().x + e.count,
				//		mazePainterAdapter.mazePainter.getSize().y + e.count);

			}
		};
		shell.addMouseWheelListener(mouseZoomlListener);
	}
	
	public void notifyMazeIsReady(String name) {
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				//MessageBox msg = new MessageBox(shell);

				setChanged();
				notifyObservers("display"+" "+ nameMaze);
			}
		});			
	}

	public void displayMaze(Maze3d maze) {

//		int[][] mazeData={
//				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//				{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
//				{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
//				{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
//				{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
//				{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
//				{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
//				{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
//				{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
//				{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
//		};
		
		//int [][] mazeData = maze.getCrossSectionByZ(0);
		//mazeDisplay.setMazeData(mazeData);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
}
