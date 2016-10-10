package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

/**
 * Game Character class for Tweety and Sylvester
 * @author Maria&Amiran
 *
 */
public class GameCharacter{
	private Image img;
	private Position pos;


	public GameCharacter() {
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
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

	public boolean win() 
	{
		if(!getPosition().equals(pos))
		{
			return false;

		}
		return true;
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
		pos.z=pos.z-2;
	}

	public void moveFloorDown(){
		pos.z=pos.z+2;
	}
}
