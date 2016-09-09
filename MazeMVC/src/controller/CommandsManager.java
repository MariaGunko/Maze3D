package controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

public class CommandsManager {
	
	private Model model;
	private View view;
	
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
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
	
	public class DisplayDirectoriesCommand implements Command{
		@Override
		public void execute(String[] args) {
			
		}
	}
	
	public class GenerateMazeCommand implements Command {

		@Override
		public void execute(String[] args) {
			String name = args[0];
			int floors = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int cols = Integer.parseInt(args[3]);
			model.modelGenerateMaze(name, floors, rows, cols);		
		}
		
	}
	
	public class DisplayMazeCommand implements Command{

		@Override
		public void execute(String[] args) {
			String name = args[0];
			Maze3d maze = model.modelGetMaze(name);
			view.displayMaze(maze);
		}
		
	}
	
	public class DisplayCrossSectionCommand implements Command{
		@Override
		public void execute(String[] args) {
			int index = Integer.parseInt(args[0]);
			String XYZ = args[1];
			String mazeName = args[2];
			model.modelGetCrossSection(index, XYZ, mazeName);
		}
	}
	
	public class saveMazeCommand implements Command{
		@Override
		public void execute(String[] args) {
			
		}
	}
	
	public class loadMazeCommand implements Command{
		@Override
		public void execute(String[] args) {
			
		}
	}
	
	public class solveMazeCommand implements Command{
		@Override
		public void execute(String[] args) {
			
		}
	}
	
	public class ExitCommand implements Command{
		@Override
		public void execute(String[] args) {
			
		}
	}
	
	public class DisplaySolutionCommand implements Command{
		@Override
		public void execute(String[] args) {
			
		}
	}
}
