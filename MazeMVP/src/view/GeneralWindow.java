package view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.util.Observable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import properties.Properties;
import properties.PropertiesLoader;

/**
 * The class of the main window of the game implements view
 * @author MariaAmiran
 *
 */
public class GeneralWindow extends BasicWindow implements View {

	private Image exit, musicPic, hint, start, solve;
	private Properties p;
	//private Maze3d maze;
	private MazeDisplay mazeDisplay;
	MouseWheelListener mouseZoomlListener;
	String nameMaze = null;
	Clip music;
	boolean isMusicPlaying = false;
	public boolean isMazeGenerated = false;


	public GeneralWindow(int width, int height) {
		super(width, height);
	}

	/**
	 * This function opens a file dialog and notifies the observer to load and display
	 * the chosen maze
	 */
	public void loadFile() {
		FileDialog fd=new FileDialog(shell,SWT.OPEN);
		fd.setText("open");
		fd.setFilterPath("E:/workspace/89210 part3");
		String[] filterExt = {  "*.*" };
		fd.setFilterExtensions(filterExt);
		try{
			String selected = fd.open();
			setChanged();
			notifyObservers("load_maze"+" "+selected+" "+fd.getFileName());
			setChanged();
			notifyObservers("display"+" "+fd.getFileName());
			nameMaze=fd.getFileName();
			mazeDisplay.setFocus();
		}
		catch (Exception e) {

		}
	}

	@Override
	protected void initWidgets() {

		shell.setText("Welcome to Tweety & Silvestre MAZE GAME");
		p = PropertiesLoader.getInstance().getProperties();

		// set all the pictures used in the project
		exit = new Image (null, "images/EXIT_NEW.png");
		musicPic = new Image (null, "images/music.jpg");
		hint = new Image (null, "images/question.jpg");
		solve = new Image (null, "images/solution2.jpg");
		start = new Image (null, "images/start2.jpg");

		Shell GenerateShell = new Shell(getDisplay());

		Menu menuButton = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuButton);

		// File button in the bar
		MenuItem fileItem = new MenuItem(menuButton, SWT.CASCADE);
		fileItem.setText("Menu");

		//		MenuItem fileItem1 = new MenuItem(menuButton, SWT.CASCADE);
		//		fileItem1.setText("about");

		// Drop down functions for file button
		Menu subMenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(subMenu);

		MenuItem properties = new MenuItem(subMenu, SWT.PUSH);
		properties.setText("properties");

		properties.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				Shell GenerateShell = new Shell(display);

				GenerateShell.setLayout(new  GridLayout(2, false));

				Label generateAlg = new Label(GenerateShell, SWT.NONE);
				generateAlg.setText("Maze Generation Algorithm: ");

				String [] algo2={"GrowingTree_RandomNextMove","GrowingTree_LastNextMove"};
				Combo algorithems2=new Combo(GenerateShell, SWT.DROP_DOWN|SWT.READ_ONLY);
				algorithems2.setItems(algo2);
				algorithems2.setText("GrowingTree_RandomNextMove");

				Label SolveAlg = new Label(GenerateShell, SWT.NONE);
				SolveAlg.setText("Maze Solution Algorithm: ");

				String [] algo={"BFS","DFS"};
				Combo algorithems=new Combo(GenerateShell, SWT.DROP_DOWN|SWT.READ_ONLY);
				algorithems.setItems(algo);
				algorithems.setText("BFS");

				Label viewType = new Label(GenerateShell, SWT.NONE);
				viewType.setText("View Type: ");

				String [] type={"GUI","CLI"};
				Combo typesOfView=new Combo(GenerateShell, SWT.DROP_DOWN|SWT.READ_ONLY);
				typesOfView.setItems(type);
				typesOfView.setText("GUI");

				Button setProperties = new Button(GenerateShell, SWT.PUSH);
				GenerateShell.setDefaultButton(setProperties);
				setProperties.setLayoutData(new GridData(SWT.RIGHT, SWT.RIGHT, true, false, 2, 1));
				setProperties.setText("Set changes");

				GenerateShell.setText("Properties");
				GenerateShell.setSize(560,220);
				GenerateShell.open();

				setProperties.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {	
						String newProp = algorithems.getText() + " " + algorithems2.getText() + " " + typesOfView.getText();

						setChanged();
						notifyObservers("set_prop"+ " "+ newProp );	

						MessageBox msg = new MessageBox(GenerateShell, SWT.OK);
						msg.setText("Properties Saved");
						msg.setMessage("The properties has been saved successfully, \nIf you choose CLI please restart the program.");
						msg.open();

						GenerateShell.close();	
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		MenuItem LoadMaze = new MenuItem(subMenu, SWT.PUSH);
		LoadMaze.setText("LoadMaze");
		LoadMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				loadFile();			
				isMazeGenerated=true;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		MenuItem SaveMaze = new MenuItem(subMenu, SWT.PUSH);

		SaveMaze.setText("SaveMaze");
		SaveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (isMazeGenerated)
				{
					setChanged();
					notifyObservers("save_maze "+nameMaze+" "+nameMaze);
					MessageBox saved = new MessageBox(GenerateShell, SWT.OK);
					saved.setText("SUCCESS");
					saved.setMessage("The maze " + nameMaze +  " was saved");
					saved.open();
				}
				else
				{
					MessageBox error = new MessageBox(GenerateShell, SWT.OK);
					error.setText("ERROR");
					error.setMessage("Operation denied, you must generate maze first");
					error.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		MenuItem exitButtonmenu = new MenuItem(subMenu, SWT.PUSH);
		exitButtonmenu.setText("EXIT");

		exitButtonmenu.addSelectionListener(new SelectionListener() {

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


		shell.setLayout(new org.eclipse.swt.layout.GridLayout(2, false));

		Color blue = display.getSystemColor(SWT.COLOR_RED);
		Color yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		shell.setBackground(yellow);


		Button generateButton = new Button(shell, SWT.PUSH);
		//generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		generateButton.setBackground(blue);
		generateButton.setImage(start);

		generateButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//Thread thread = new Thread (new Runnable() {


				//	public void run() {

				//Display GenerateDisplay = new Display();
				Shell GenerateShell = new Shell(display);

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
				GenerateMaze.setText("Let's Play");


				GenerateShell.setText("Generate maze");
				GenerateShell.setSize(400,250);
				GenerateShell.open();

				GenerateMaze.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {				
						MessageBox msg = new MessageBox(GenerateShell, SWT.OK);
						nameMaze=txtName.getText();
						msg.setText("Create Maze -> "+nameMaze);

						try{
							isMazeGenerated = true;
							int rows = Integer.parseInt(txtRows.getText());
							int cols = Integer.parseInt(txtColumns.getText());
							int floors = Integer.parseInt(txtFloors.getText());	
							String setMaze="generate_maze "+nameMaze+" "+floors+" "+rows+" "+cols;
							setChanged();
							notifyObservers(setMaze);

							msg.setMessage("Generating maze: "+nameMaze +" Floors: "+floors+ " rows: " + rows + " cols: " + cols);
							msg.open();
							GenerateShell.close();

							setChanged();
							notifyObservers("display"+" "+ nameMaze);	
							mazeDisplay.setFocus();

						}
						catch(NumberFormatException e)  
						{
							MessageBox msg1 = new MessageBox(GenerateShell, SWT.OK);
							msg1.setText("ERROR");
							msg1.setMessage("Please try again, enter integer numbers between 2 to 40");
							msg1.open();
						}		

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {			

					}
				});	
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});	


		mazeDisplay = new MazeDisplay(shell, SWT.NONE|SWT.DOUBLE_BUFFERED);		
		//mazeDisplay.setMazeData(maze);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,8));
		final Image image=new Image(display,"images/back.jpg");
		mazeDisplay.setBackgroundImage(image);
		mazeDisplay.setFocus();

		//		Text t=new Text(shell,SWT.MULTI|SWT.BORDER);
		//		t.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,7));
		//		final Image image=new Image(display,"images/background.jpg");
		//		t.setBackgroundImage(image);

		Button displayMazeSolutionButton = new Button(shell, SWT.PUSH);
		//displayMazeSolutionButton.setText("Display Maze solution");
		displayMazeSolutionButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		displayMazeSolutionButton.setBackground(blue);
		displayMazeSolutionButton.setImage(solve);

		displayMazeSolutionButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {	
				if (isMazeGenerated)
				{
					setChanged();
					notifyObservers("solve"+" "+ nameMaze +" "+p.getSolveMazeAlgorithm());	

					MessageBox msg1 = new MessageBox(GenerateShell, SWT.OK);
					msg1.setText("Let's go :)");
					msg1.setMessage("Are you ready for the solution? ");
					msg1.open();

					setChanged();	
					notifyObservers("display_solution"+" "+ nameMaze);	
					isMazeGenerated = false;
				}
				else
				{
					MessageBox error = new MessageBox(GenerateShell, SWT.OK);
					error.setText("ERROR");
					error.setMessage("You can't view the solution, you must generate maze first");
					error.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		//		Button saveButton = new Button(shell, SWT.PUSH);
		//		saveButton.setText("Save Maze");
		//		saveButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		//		saveButton.setBackground(blue);
		//		saveButton.addSelectionListener(new SelectionListener() {
		//
		//			@Override
		//			public void widgetSelected(SelectionEvent arg0) {
		//
		//				if(nameMaze==null)
		//				{
		//					MessageBox msg = new MessageBox(GenerateShell, SWT.OK);
		//					msg.setText("ERROR");
		//					msg.setMessage("You must generate MAZE first");
		//					msg.open();
		//				}
		//				else{
		//					setChanged();
		//					notifyObservers("save_maze "+nameMaze+" "+nameMaze);
		//				}
		//			}
		//
		//			@Override
		//			public void widgetDefaultSelected(SelectionEvent arg0) {
		//				// TODO Auto-generated method stub
		//
		//			}
		//		});

		//		Button loadButton = new Button(shell, SWT.PUSH);
		//		loadButton.setText("load Maze");
		//		loadButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		//		loadButton.setBackground(blue);
		//		loadButton.addSelectionListener(new SelectionListener() {
		//
		//			@Override
		//			public void widgetSelected(SelectionEvent arg0) {
		//				loadFile();
		//
		//			}
		//
		//			@Override
		//			public void widgetDefaultSelected(SelectionEvent arg0) {
		//				// TODO Auto-generated method stub
		//
		//			}
		//		});

		Button exitButton = new Button(shell, SWT.PUSH);
		//exitButton.setText("EXIT");
		exitButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		exitButton.setBackground(blue);
		exitButton.setImage(exit);

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
		//musicButton.setText("Music");
		musicButton.setImage(musicPic);
		musicButton.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false, 1,1));
		musicButton.setBackground(blue);
		musicButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (!isMusicPlaying)
					playMusic(new File("Images/amiran.wav"));
				else
				{
					music.stop();
					isMusicPlaying = false;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});	


		Button hintButton = new Button(shell, SWT.PUSH);
		//musicButton.setText("Music");
		hintButton.setImage(hint);
		hintButton.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false, 1, 1));
		hintButton.setBackground(blue);
		hintButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (isMazeGenerated)
				{
					setChanged();
					notifyObservers("solve"+" "+ nameMaze +" "+p.getSolveMazeAlgorithm());	

					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					MessageBox msg = new MessageBox(GenerateShell, SWT.OK);
					msg.setText("TIP");
					msg.setMessage("Follow the coins :)");
					msg.open();

					setChanged();	
					notifyObservers("display_hint"+" "+ nameMaze);	
					mazeDisplay.setFocus();
				}
				else
				{
					MessageBox error = new MessageBox(GenerateShell, SWT.OK);
					error.setText("ERROR");
					error.setMessage("You can't use help, you should generate maze first");
					error.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});	

		//mouse zoom do it +5 point 
		mouseZoomlListener = new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent e) {
				// if both ctrl and wheel are being operated
				if ((e.stateMask & SWT.CTRL) != 0)
					mazeDisplay.setSize(mazeDisplay.getSize().x + e.count,
							mazeDisplay.getSize().y + e.count);

			}
		};
		shell.addMouseWheelListener(mouseZoomlListener);
	}

	private Display getDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This function gets a music file and plays it
	 * @param file
	 */
	private void playMusic(File file) {

		try {
			isMusicPlaying = true;
			music = AudioSystem.getClip();

			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			music.open(inputStream);
			// loop infinitely
			music.setLoopPoints(0, -1);
			music.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void viewNotifyMazeIsReady(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewDisplayMaze(Maze3d maze) {
		mazeDisplay.setMazeData(maze);

	}

	@Override
	public void viewDisplayMessage(String msg) {
		System.out.println(msg);
	}

	@Override
	public void viewDisplayCrossSection(int[][] maze2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewDisplaySolution(Solution<Position> solve) {
		mazeDisplay.showSolution(solve);

	}

	@Override
	public void viewExit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewDisplayHint(Solution<Position> s) {
		mazeDisplay.showHint(s);

	}
}
