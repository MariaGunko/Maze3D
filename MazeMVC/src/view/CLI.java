package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

public class CLI {

	private BufferedReader in;
	private PrintWriter out;
	private HashMap <String, Command> commands;

	public CLI(BufferedReader in, PrintWriter out, HashMap<String, Command> commands) {
		super();
		this.in = in;
		this.out=out;
		this.commands = commands;
	}

	public void start(){
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				out.println("Please enter one of the following commands:");	
				out.println("dir <path name> - to see all files and directories");
				out.println("generate_3d_maze <name> <floolrs> <rows> <columns>");
				out.println("display <name> - display maze");
				out.println("display_cross_section <index {x,y,z}> <name>");
				out.println("save_maze <name> <filename>");
				out.println("load_maze <filename> <name>");
				out.println("solve <name> <algorithm> - algorithm: BFS or DFS");
				out.println("display_solution <name>");
				out.println("exit");

				Command cmd;

				try {
					String userEnterCommand=in.readLine();
					String splittedCommand=userEnterCommand.split(" ")[0];
					
					while(!(splittedCommand.compareTo("exit")==0))
					{
						cmd = commands.get(splittedCommand);
						String [] params = userEnterCommand.split(" ");
						if (cmd!=null)
						{
							if (params.length>1)
								cmd.execute(params);
							else
								out.println("Missing parameters");
						}
						else
							out.println("The command does not exist");
						
						out.println("Please enter a command");	
						userEnterCommand=in.readLine();
						splittedCommand=userEnterCommand.split(" ")[0];
					}
					
					cmd = commands.get("exit");
					cmd.execute(null);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		thread.start();
	}
}
