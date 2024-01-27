	package info;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Escada extends Rectangle{
	
	public static int x,y;

	public Escada(int x, int y) {
		super(x,y,48,24);
		this.x = Player.x;
		this.y = Player.y-100;
	}
	
	public void render(Graphics g) {
		g.drawImage(Spritesheet.escadaSprite, x-Camera.x,y-Camera.y,48,48,null);
	}
}
