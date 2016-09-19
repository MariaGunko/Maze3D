package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class GeneralWindow  extends BasicWindow {
	MouseWheelListener mouseZoomlListener;

	public GeneralWindow(int width, int height) {
		super(width, height);
	}
	public void close()
	{
		// Don't call shell.close(), because then
		// you'll have to re-create it
		shell.setVisible(false);
	}

	@Override
	protected void initWidgets() {

		shell.setText("Welcome to the MAZE GAME");
		shell.setLayout(new org.eclipse.swt.layout.GridLayout(2, false));

		Button generateButton = new Button(shell, SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		
		generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow();				
				win.start(display);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});


		Text t=new Text(shell,SWT.MULTI|SWT.BORDER);
		t.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,7));

		Button displayButton = new Button(shell, SWT.PUSH);
		displayButton.setText("Display Maze");
		displayButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));

		Button solveButton = new Button(shell, SWT.PUSH);
		solveButton.setText("Solve Maze");
		solveButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));


		Button displayMazeSolutionButton = new Button(shell, SWT.PUSH);
		displayMazeSolutionButton.setText("Display Maze solution");
		displayMazeSolutionButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));

		Button saveButton = new Button(shell, SWT.PUSH);
		saveButton.setText("Save Maze");
		saveButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));

		Button loadButton = new Button(shell, SWT.PUSH);
		loadButton.setText("load Maze");
		loadButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));

		Button exitButton = new Button(shell, SWT.PUSH);
		exitButton.setText("EXIT");
		exitButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
		exitButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				e.doit=false;
				//			
				setChanged();
				notifyObservers("exit");
				close();
				shell.dispose();
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});


	//	shell.pack();
		//mouse zoom do it
		mouseZoomlListener = new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent e) {
				// if both ctrl and wheel are being operated
				//	if ((e.stateMask & SWT.CTRL) != 0)
				//mazePainterAdapter.mazePainter.setSize(mazePainterAdapter.mazePainter.getSize().x + e.count,
				//		mazePainterAdapter.mazePainter.getSize().y + e.count);

			}
		};
		shell.addMouseWheelListener(mouseZoomlListener);
	}
}
