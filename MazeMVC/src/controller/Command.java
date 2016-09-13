package controller;

/**
 * Command Interface holds 'execute' method which is implemented differently in each case
 * @author Maria&Amiran
 *
 */

public interface Command {
	/**
	 * this method gets an array of parameters given by the user
	 * and do a command according to the case
	 * @param args
	 */
	void execute(String[] args);
}

