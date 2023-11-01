package zelda1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Player extends Rectangle{

	public int spd = 5;
	
	public static int vida = 100;
	
	public int cameraX, cameraY;
	
	public static boolean right, up, down, left;
	
	public int curAnimation = 0;
	
	public int curFrames = 0, targetFrames = 15;
	
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	
	private List<Inimigo> inimigos = new ArrayList<Inimigo>();
	
	public boolean shoot = false;
	
	public int dir = 1;
	
	public static boolean canShoot = true;
	
	public static long lastShootTime = 0;
	
	public int bulletVerticalDirection = 0;
	
	public static int x,y;
	
	public Player(int x, int y,int screenWidth,int screenHeight) {
	    super(x, y, 32, 32);
	    this.x = 32; // Posição X no centro do mundo
	    this.y = 32;  // Posição Y no centro do mundo
	}
	
	
	public void tick(int screenWidth,int screenHeight) {
		boolean moved = false;
		int newX = x;
	    int newY = y;
	    
		if(right && World1.isFree(x+spd, y) && x <= 200000 && World1.entrada_Boss_Room(x+spd, y)) {
			newX = x+=spd;
			moved = true;
			dir = 1;
			bulletVerticalDirection = 0;
		}else if(left && World1.isFree(x-spd, y) && x >= 0 && World1.entrada_Boss_Room(x-spd, y)) {
			newX = x-=spd;
			moved = true;
			dir = -1;
			bulletVerticalDirection = 0;
		}
		
		if(up && World1.isFree(x, y-spd) && y >= 0 && World1.entrada_Boss_Room(x, y-spd)) {
			newY = y-=spd;
			moved = true;
			bulletVerticalDirection = -1;
			dir = 0;
		}else if(down && World1.isFree(x, y+spd) && y <= 150000 && World1.entrada_Boss_Room(x, y+spd)) {
			newY = y+=spd;
			moved = true;
			bulletVerticalDirection = 1;
			dir = 0;
		}
		
		if(World1.entrada_Boss_Room(x, y-spd) == false) {
			World1.BossRoom();
		}
		
		if (newX >= 0 && newX + width <= screenWidth) {
	        x = newX;
	    }

	    if (newY >= 0 && newY + height <= screenHeight) {
	        y = newY;
	    }
		
	    Camera.x =  x - (Game.screenWidth/2);
	    Camera.y =  y - (Game.screenHeight/2);
		
		curFrames++;
		
		if(curFrames == targetFrames) {
			curFrames = 0;
			curAnimation++;
			if(curAnimation == Spritesheet.player_front.length) {
				curAnimation = 0;
			}
		}
		
		
		if(shoot) {
			shoot = false;
			bullets.add(new Bullet(x,y,dir,bulletVerticalDirection,bullets));
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		
	}
	
	public void render(Graphics g, int renderX, int renderY) {
		
		if(Game.player.down == true)
			g.drawImage(Spritesheet.player_front[curAnimation],x - Camera.x, y - Camera.y,32,32,null);
		
		else if(Game.player.right == true)
			g.drawImage(Spritesheet.player_lado[curAnimation], x - Camera.x, y - Camera.y, 32, 32, null);
		
		else if(Game.player.left == true)
			g.drawImage(Spritesheet.player_lado[curAnimation], x + 32 - Camera.x, y - Camera.y, -32, 32, null);
		
		else if(Game.player.up == true)
			g.drawImage(Spritesheet.player_cima[curAnimation], x - Camera.x, y - Camera.y, 32, 32, null);
		
		else {
			g.drawImage(Spritesheet.player_front[0], x - Camera.x, y - Camera.y, 32 , 32, null);
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
	
	
	
	
}
