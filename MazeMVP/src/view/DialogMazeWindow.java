package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class DialogMazeWindow{
	protected Shell shell;	
	
	protected abstract void initWidgets();
	
	public void start(Display display) {		
		shell = new Shell(display);
		
		initWidgets();
		shell.open();		
	}
}
