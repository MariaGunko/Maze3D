package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class GameCharacter{
	private Position pos;
	private Image img;
	int x,y;
	
	public GameCharacter() {
		img = new Image (null, "Images/silvestre.jpg");
	}

	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
//	public void paint(PaintEvent e,int w,int h){
//		e.gc.setForeground(new Color(null,255,0,0));
//		e.gc.setBackground(new Color(null,255,0,0));
//		e.gc.fillOval(pos.x,pos.y, w, h);
//	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
}
