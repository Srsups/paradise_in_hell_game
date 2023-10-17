	package zelda1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blocks extends Rectangle{

	public Blocks(int x, int y) {
		super(x,y,24,24);
	}
	
	public void render(Graphics g) {
		g.drawImage(Spritesheet.tileWall, x-Camera.x,y-Camera.y,24,24,null);
	}
	
	
}
