package zelda1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Morcego extends Rectangle{
	
	public boolean alive = true;

	public int spd = 3;
	
	public int curAnimation = 0;
	
	public int curFrames = 0, targetFrames = 15;
	
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	
	public boolean shoot = false;
	
	public int dir = 1;
	
	public int bulletVerticalDirection = 0;
	
	private int dano;
	
	public static int cooldown;
	
	public static int COOLDOWN_INTERVAL = 60;
	
	public Morcego(int x, int y, int dano) {
		super(x,y,32,32);
		this.dano = dano;
		this.cooldown = COOLDOWN_INTERVAL;
	}
	
	public void perseguirPlayer() {
		Player p = Game.player;
		if(x < p.x && World1.isFree(x+spd, y)) {
			if(new Random().nextInt(100) < 50)
				x+=spd;
		}
		else if(x > p.x && World1.isFree(x-spd, y)) {
			if(new Random().nextInt(100) < 50)
				x-=spd;
		}
		
		if(y < p.y && World1.isFree(x, y+spd)) {
			if(new Random().nextInt(100) < 50)
				y+=spd;
		}
		else if(y > p.y && World1.isFree(x, y-spd)) {
			if(new Random().nextInt(100) < 50)
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
			if(curAnimation == Spritesheet.morcego.length) {
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
		
		if(curAnimation == 0)
			g.drawImage(Spritesheet.morcego[0],x-Camera.x,y-Camera.y,20,16,null);
		else {
			g.drawImage(Spritesheet.morcego[1],x-Camera.x,y-Camera.y,32,16,null);
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
}

