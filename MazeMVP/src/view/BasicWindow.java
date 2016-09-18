package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow extends Observable implements Runnable  {
	protected Display display;
	protected Shell shell;

	protected abstract void  initWidgets();
	
	public BasicWindow(int width,int height){
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
	}
	
	public void run(){
		initWidgets();
		shell.open();

		//main event loop
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
}
