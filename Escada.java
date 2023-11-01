	package zelda1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Escada extends Rectangle{

	public Escada(int x, int y) {
		super(x,y,24,24);
	}
	
	public void render(Graphics g) {
		g.drawImage(Spritesheet.escadaSprite, x-Camera.x,y-Camera.y,48,48,null);
	}
	
	
}
