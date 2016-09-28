package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class GameCharacter{
	private Image img;
	private Position pos;
	
	private Image goalImg;
	private Position posT;


	public GameCharacter() {
		img = new Image (null, "images/silvestre.gif");
		goalImg = new Image (null, "images/tweety.gif");
	}

	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
	public void setPosition2(int a, int b, int c) {
		this.pos.z = a;
		this.pos.x = b;
		this.pos.y = c;
	}
	
	public Position getPosition(){
		return pos;
	}
	
	public void setTweetyPosition(Position pos) {
		this.posT = pos;
	}
	public Position getTweetyPosition(){
		return posT;
	}
	
	public boolean win() 
	{
		if(!getTweetyPosition().equals(pos))
		{
			return false;
			
		}
		return true;
	}

	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
	
	public void drawTweety(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(goalImg, 0, 0, goalImg.getBounds().width, goalImg.getBounds().height, 
				cellWidth * posT.x, cellHeight * posT.y, cellWidth, cellHeight);
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
