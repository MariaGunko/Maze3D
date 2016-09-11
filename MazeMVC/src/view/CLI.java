package view;

import java.io.BufferedReader;
import java.io.IOException;

public class CLI {

	private BufferedReader in;

	public void start(){
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Please enter one of the following commands:");	
				System.out.println("dir <path name> - to see all files and directories");
				System.out.println("generate_3d_maze <name> <floolrs> <rows> <columns>");
				System.out.println("display <name> - display maze");
				System.out.println("display_cross_section <index {x,y,z}> <name>");
				System.out.println("save_maze <name> <filename>");
				System.out.println("load_maze <filename> <name>");
				System.out.println("solve <name> <algorithm> - algorithm: BFS or DFS");
				System.out.println("display_solution <name>");
				System.out.println("exit");



				try {
					String UserEnterCommand=in.readLine();
					String Command=UserEnterCommand.split(" ")[0];

					while(!(Command.compareTo("exit")==0))
					{


					}


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		thread.start();


	}
}
