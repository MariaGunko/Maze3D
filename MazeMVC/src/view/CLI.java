package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;


/**
 * Command Line Interface 
 * The class gets a command from the user and sends it to the controller
 * 
 * @param in 
 * read the input stream from user
 * 
 * @param out 
 * print out text stream
 * 
 * @param commands
 * HashMap of all possible command  
 * 
 * @author Maria&Amiran
 *
 */

public class CLI {

	//Variables
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String, Command> commands;

	/**
	 * CTOR
	 * 
	 * @param in
	 * @param out
	 */
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;		
	}
	
/**
 * Print all commands from the HashMap
 */
	private void printMenu() {
		out.print("Choose command: (");
		for (String command : commands.keySet()) {
			out.print(command + ",");
		}
		out.println(")");
		out.flush();
	}
	
/**
 * Main thread - gets the desired command from the user and implements it
 */
	public void start() {
		// create a thread using anonymous class
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					printMenu();
					try {
						// gets a commend line
						String commandLine = in.readLine();
						String arr[] = commandLine.split(" ");
						String command = arr[0];			

						if(!commands.containsKey(command)) {
							out.println("Command doesn't exist");
						}
						else {
							if (command.equals("exit"))
							{
								Command cmd = commands.get(command);
								cmd.execute(null);
								break;
							}
							String[] args = null;
							if (arr.length > 1) {
								String commandArgs = commandLine.substring(
										commandLine.indexOf(" ") + 1);
								args = commandArgs.split(" ");	
								Command cmd = commands.get(command);
								cmd.execute(args);
							}
							else {
								out.println("Invalid parameters");
								out.flush();
							}	
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			

		});
		thread.start();		
	}

	/**
	 * Setter of the HashMap
	 * 
	 * @param commands
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}

}
