package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;

/**
 * The CLI class - gets a command from the user and sends a notification to the presenter
 * @author Maria&Amiran
 *
 */
public class CLI extends Observable {
	private BufferedReader in;
	private PrintWriter out;	

	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;		
	}

	private void printMenu() {
		out.print("Choose command:");
		/*for (String command : commands.keySet()) {
			out.print(command + ",");
		}
		out.println(")");*/
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
						setChanged();
						notifyObservers(commandLine);

						if (commandLine.equals("exit"))
							break;

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		});
		thread.start();		
	}
}
