package controller;

import model.Model;
import view.View;

/**
 * 
 * This class implements all common methods of CONTROLLER interface
 * @author Maria&Amiran
 *
 */
public class MyController implements Controller {

	private View view;
	private Model model;
	private CommandsManager commandsManager;
	
	/**
	 * CTOR
	 * @param view
	 * @param model
	 */
	public MyController(View view, Model model) {
		this.view = view;
		this.model = model;
		
		commandsManager = new CommandsManager(model,view);
		view.viewSetCommands (commandsManager.getCommandsMap());
	}

	/**
	 * This method sends a string to the VIEW
	 * and notifies if the maze with the given name is ready
	 * @param name
	 */
	@Override
	public void c_notifyMazeIsReady(String name) {
		view.viewNotifyMazeIsReady(name);
	}

	/**
	 * This method sends a string to the VIEW 
	 * and displays the message
	 * @param msg
	 */
	@Override
	public void c_displayMessage(String msg) {
		view.viewDisplayMessage(msg);	
	}
}
