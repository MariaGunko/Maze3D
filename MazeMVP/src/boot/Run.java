package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import view.GeneralWindow;
import view.MyView;

public class Run {

	public static void main(String[] args) {	

		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		GeneralWindow win = new GeneralWindow (1700,950);
		MyModel model = new MyModel();	
		Presenter presenter = new Presenter(model, win);
		model.addObserver(presenter);
		win.addObserver(presenter);
		win.start();	
		
		//MyView view = new MyView(in, out);
		//MyModel model = new MyModel();
		//Presenter presenter = new Presenter(model, view);
		//model.addObserver(presenter);
		//view.addObserver(presenter);
		//view.start();
	}
}


