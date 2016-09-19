package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class GenerateMazeWindow extends DialogMazeWindow{


	@Override
	protected void initWidgets() {
		shell.setText("Generate Maze");
		shell.setSize(400,250);
		
		
		shell.setLayout(new  GridLayout(2, false));
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Maze name: ");
		Text txtName = new Text(shell, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		Text txtFloors = new Text(shell, SWT.BORDER);
		txtFloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		Text txtRows = new Text(shell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		
		Label lblColumns = new Label(shell, SWT.NONE);
		lblColumns.setText("Columns: ");
		Text txtColumns = new Text(shell, SWT.BORDER);
		txtColumns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		
		
		
		Button GenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(GenerateMaze);
		GenerateMaze.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		GenerateMaze.setText("Generate maze");
		
		GenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				String name=txtName.getText();
				msg.setText("Create Maze -> "+name);
				
				int rows = Integer.parseInt(txtRows.getText());
				int cols = Integer.parseInt(txtColumns.getText());
				int floors = Integer.parseInt(txtFloors.getText());
				
				msg.setMessage("Generating maze: "+name +" Floors: "+floors+ " rows: " + rows + " cols: " + cols);
				
				msg.open();
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	
		
	}
}
