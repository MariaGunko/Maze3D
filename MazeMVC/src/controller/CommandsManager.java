package controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Model;
import view.View;

/** 
 * Manager of commands implemented in controller architecture
 * includes access for both MODEL and VIEW
 * @author Maria&Amiran
 *
 */
public class CommandsManager {

	private Model model;
	private View view;

	/**
	 * CTOR
	 * 
	 * @param model
	 * @param view
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Getter for the hashMap of commands
	 * @return commands
	 */
	public HashMap <String,Command> getCommandsMap(){
		HashMap<String,Command> commands = new HashMap <String, Command>();
		commands.put("dir", new DisplayDirectoriesCommand());
		commands.put("generate_3d_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("save_maze", new saveMazeCommand());
		commands.put("load_maze", new loadMazeCommand());
		commands.put("solve", new solveMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("exit", new ExitCommand());

		return commands;			
	}

	/**
	 * This class displays all directories of given path
	 * @author Maria&Amiran
	 *
	 */
	public class DisplayDirectoriesCommand implements Command{
		@Override
		public void execute(String[] args) {
			if (args.length!=1)
				view.viewDisplayMessage("Missing or unnecessary parameters");
			String path = args[0];
			String files = model.modelDir(path);
			view.viewDisplayMessage(files);
		}
	}

	/**
	 * This class generates a new maze according to the given arguments:
	 * name, floors, rows, columns
	 * @author Maria&Amiran
	 *
	 */
	public class GenerateMazeCommand implements Command {

		@Override
		public void execute(String[] args) {
			if (args.length!=4)
				view.viewDisplayMessage("Missing or unnecessary parameters");
			else{
				String name = args[0];
				try{


					int floors = Integer.parseInt(args[1]);
					int rows = Integer.parseInt(args[2]);
					int cols = Integer.parseInt(args[3]);
					model.modelGenerateMaze(name, floors, rows, cols);	
				}
				catch(NumberFormatException e)  
				{  
					view.viewDisplayMessage("Invalid parameters");
				}
			}
		}

	}

	/**
	 * This class displays a maze according to the given name
	 * @author Maria&Amiran
	 *
	 */
	public class DisplayMazeCommand implements Command{

		@Override
		public void execute(String[] args) {

			String name = args[0];
			Maze3d maze = model.modelGetMaze(name);
			view.viewDisplayMaze(maze);
		}

	}

	/**
	 * This class displays crossed section 2D maze according to the given arguments:
	 * index, one of the axis X, Y or Z and a mazeName
	 * @author Maria&Amiran
	 *
	 */

	public class DisplayCrossSectionCommand implements Command{
		@Override
		public void execute(String[] args) {
			if (args.length!=3)
				view.viewDisplayMessage("Missing or unnecessary parameters");
			else
			{
				try  
				{  
					int index = Integer.parseInt(args[0]); 
					String XYZ = args[1];
					String mazeName = args[2];
					int [][] maze2d= model.modelGetCrossSection(index, XYZ, mazeName);
					view.viewDisplayCrossSection(maze2d);
				}  
				catch(NumberFormatException e)  
				{  
					view.viewDisplayMessage("Invalid parameters");
				}
			} 

		}
	}

	/**
	 * This class saves the maze under fileName  
	 * @author Maria&Amiran
	 *
	 */
	public class saveMazeCommand implements Command{
		@Override
		public void execute(String[] args) {
			if (args.length!=2)
				view.viewDisplayMessage("Missing or unnecessary parameters");
			String mazeName = args[0];
			String fileName = args[1];
			model.modelSaveMaze(mazeName, fileName);
		}
	}

	/**
	 * This class loads the maze from file
	 * @author Maria&Amiran
	 *
	 */
	public class loadMazeCommand implements Command{
		@Override
		public void execute(String[] args){
			String fileName = args[0];
			String mazeName = args[1];
			model.modelLoadMaze(fileName, mazeName);
		}
	}

	/**
	 * This class solves the maze get maze name and algorithm:BFS,DFS
	 * @author Maria&Amiran 
	 *
	 */
	public class solveMazeCommand implements Command{
		@Override
		public void execute(String[] args) {
			if (args.length!=2)
				view.viewDisplayMessage("Missing or unnecessary parameters");
			String mazeName = args[0];
			String algorithm = args[1];
			model.modelSolveMaze(mazeName, algorithm);
		}
	}
	/**
	 * This class displays the maze solution 
	 * @author Maria&Amiran
	 *
	 */
	public class DisplaySolutionCommand implements Command{
		@Override
		public void execute(String[] args) {

			String mazeName = args[0];
			Solution <Position> s = model.modelGetSolution(mazeName);
			view.viewDisplaySolution(s);
		}
	}

	/**
	 * This class interrupts all running threads and shuts down the program
	 * @author Maria&Amiran
	 *
	 */
	public class ExitCommand implements Command{
		@Override
		public void execute(String[] args) {
			model.modelExit();	
			view.viewExit();
		}
	}
}
