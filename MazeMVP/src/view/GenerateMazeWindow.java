package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class GenerateMazeWindow extends DialogMazeWindow{


	@Override
	protected void initWidgets() {
		shell.setText("Generate Maze");
		shell.setSize(500,500);
		
		shell.setLayout(new  GridLayout(2, false));
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		Text txtRows = new Text(shell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
	}
}
