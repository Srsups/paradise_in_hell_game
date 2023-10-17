package zelda1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Inimigo extends Rectangle{
	
	public boolean alive = true;

	public int spd = 2;
	public int right = 1, up = 0, down = 0, left = 0;
	
	public int curAnimation = 0;
	
	public int curFrames = 0, targetFrames = 15;
	
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	
	public boolean shoot = false;
	
	public int dir = 1;
	
	public int bulletVerticalDirection = 0;
	
	public static Map<Inimigo, Long> ultimosDanos = new HashMap<>();
	
	private int dano;
	
	public static int cooldown;
	
	public static int COOLDOWN_INTERVAL = 60;
	
	public Inimigo(int x, int y, int dano) {
		super(x,y,32,32);
		this.dano = dano;
		this.cooldown = COOLDOWN_INTERVAL;
	}
	
	public void perseguirPlayer() {
		Player p = Game.player;
		if(x < p.x && World.isFree(x+spd, y)) {
			if(new Random().nextInt(100) < 33)
				x+=spd;
		}
		else if(x > p.x && World.isFree(x-spd, y)) {
			if(new Random().nextInt(100) < 33)
				x-=spd;
		}
		
		if(y < p.y && World.isFree(x, y+spd)) {
			if(new Random().nextInt(100) < 33)
				y+=spd;
		}
		else if(y > p.y && World.isFree(x, y-spd)) {
			if(new Random().nextInt(100) < 33)
				y-=spd;
		}
	}
	
	public void tick() {
		if (!alive) {
	        return;
	    }
		
		boolean moved = true;
		
		perseguirPlayer();
		
		if(moved) {
		
		curFrames++;
		
		if(curFrames == targetFrames) {
			curFrames = 0;
			curAnimation++;
			if(curAnimation == Spritesheet.inimigo_front.length) {
				curAnimation = 0;
			}
		}
		}
		
		if(shoot) {
			shoot = false;
			bullets.add(new Bullet(x,y,dir,bulletVerticalDirection, bullets));
		}
		
		if(cooldown > 0)
			cooldown --;
		
	}
	
	public static boolean isCooldownReady() {
        return cooldown <= 0;
    }
	
	public void resetCooldown() {
        cooldown = COOLDOWN_INTERVAL;
    }
	
	 public int getDano() {
	        return dano;
	 }
	
	public void render(Graphics g) {
		if (!alive) {
	        return;
	    }
		
		g.drawImage(Spritesheet.inimigo_front[curAnimation],x-Camera.x,y-Camera.y,32,32,null);
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
}
