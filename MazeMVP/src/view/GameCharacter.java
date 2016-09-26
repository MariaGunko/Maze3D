package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class GameCharacter{
	private Position pos;
	private Image img;
	private Image goalImg;


	public GameCharacter() {
		img = new Image (null, "images/silvestre.gif");
		goalImg = new Image (null, "images/goal.jpg");
	}

	public void setPosition(Position pos) {
		this.pos = pos;
	}
	public Position getPosition(){
		return pos;
	}


	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);

	}
	

	public void moveUp(){
		pos.y--;

	}

	public void moveDown(){
		pos.y++;


	}

	public void moveLeft(){
		pos.x--;
	}

	public void moveRight(){
		pos.x++;

	}

	public void moveFloorUp(){
		pos.z--;


	}

	public void moveFloorDown(){
		pos.z++;

	}
}
