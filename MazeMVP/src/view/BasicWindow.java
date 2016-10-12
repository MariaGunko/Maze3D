package view;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The basic display window
 * @author MariaAmiran
 *
 */
public abstract class BasicWindow extends Observable implements Observer{
	protected Display display;
	protected Shell shell;

	protected abstract void initWidgets();

	public BasicWindow(int width,int height){
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
	}

	public void start(){
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
