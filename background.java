package info;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class background extends Rectangle{

	public background(int x, int y) {
		super(x,y,32,32);
	}
	
	public void render(Graphics g) {
		g.drawImage(Spritesheet.backgroundSprite, x,y,32,32,null);
	}
	
	
}
