package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import view.GeneralWindow;
import view.GenerateMazeWindow;
import view.MyView;

public class Run {

	public static void main(String[] args) {	
		
		//GenerateMazeWindow window1= new GenerateMazeWindow(2000, 1500);
		GeneralWindow window = new GeneralWindow(1400,900);
		window.start();
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
				
		MyView view = new MyView(in, out);
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		view.start();
		
	}

}
