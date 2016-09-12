package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

public class CLI {
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String, Command> commands;
	
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;		
	}
	
	private void printMenu() {
		out.print("Choose command: (");
		for (String command : commands.keySet()) {
			out.print(command + ",");
		}
		out.println(")");
		out.flush();
	}
	
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
				
					printMenu();
					try {
						String commandLine = in.readLine();
						String arr[] = commandLine.split(" ");
						String command = arr[0];			
						
						if(!commands.containsKey(command)) {
							out.println("Command doesn't exist");
						}
						else {
							String[] args = null;
							if (arr.length > 1) {
								String commandArgs = commandLine.substring(
										commandLine.indexOf(" ") + 1);
								args = commandArgs.split(" ");							
							}
							Command cmd = commands.get(command);
							cmd.execute(args);
							
							if (command.equals("exit"))
								break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		});
		thread.start();		
	}
	
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}
	
}
