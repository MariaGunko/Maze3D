package controller;

import model.Model;
import view.View;

public class MyController implements Controller {

	private View view;
	private Model model;
	private CommandsManager commandsManager;
	
	public MyController(View view, Model model) {
		this.view = view;
		this.model = model;
		
		commandsManager = new CommandsManager(model,view);
		view.viewSetCommands (commandsManager.getCommandsMap());
	}

	@Override
	public void c_notifyMazeIsReady(String name) {
		view.viewNotifyMazeIsReady(name);
	}

	@Override
	public void c_displayMessage(String msg) {
		view.viewDisplayMessage(msg);	
	}
}
