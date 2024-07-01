	package info;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import personagens.Cavaleiro;
import personagens.Cowboy;
import personagens.Mago;
import personagens.Ninja;
import personagens.Pirata;

public class Escada extends Rectangle{
	
	public static int x,y;

	public Escada(int x, int y) {
		super(x,y,48,24);
		if(Game.personagem == "default") {
			this.x = Player.x;
			this.y = Player.y-100;
		} else if(Game.personagem == "cowboy") {
			this.x = Cowboy.x;
			this.y = Cowboy.y-100;
		} else if(Game.personagem == "mago") {
			this.x = Mago.x;
			this.y = Mago.y-100;
		} else if(Game.personagem == "ninja") {
			this.x = Ninja.x;
			this.y = Ninja.y-100;
		} else if(Game.personagem == "pirata") {
			this.x = Pirata.x;
			this.y = Pirata.y-100;
		} else if(Game.personagem == "cavaleiro") {
			this.x = Cavaleiro.x;
			this.y = Cavaleiro.y-100;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Spritesheet.escadaSprite, x-Camera.x,y-Camera.y,48,48,null);
	}
}
