package info;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Boss2 extends Rectangle{
	
	public boolean alive = true;

	public int spd = 3;
	public int right = 1, up = 0, down = 0, left = 0;
	
	public int curAnimation = 0;
	
	public int curFrames = 0, targetFrames = 15;
	
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	
	public boolean shoot = false;
	
	public int dir = 1;
	
	public int bulletVerticalDirection = 0;
	
	private int dano;
	
	public static int cooldown;
	
	public static int COOLDOWN_INTERVAL = 60;
	
	public Boss2(int x, int y, int dano) {
		super(x,y,128,128);
		this.dano = dano;
		this.cooldown = COOLDOWN_INTERVAL;
	}
	
	public void ataque() {
		Timer timer = new Timer();
        
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // 1º ataque
                System.out.println("Boss atacou!");
            }
        }, 0, 15000); // Segundo parâmetro é o atraso inicial, terceiro é o intervalo entre os ataques
	}
	
	public void entrada() {
		if(y > -100) {
				y-=spd;
		}
	}
	
	public void tick() {
		if (!alive) {
	        return;
	    }
		
		boolean moved = true;
		
		if(Game.entrada == true) {
			entrada();
		}
		
		ataque();
		
		if(moved) {
		
		curFrames++;
		
		if(curFrames == targetFrames) {
			curFrames = 0;
			curAnimation++;
			if(curAnimation == Spritesheet.dragao_front_esquerda.length) {
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
			if(x<Player.x) {
				g.drawImage(Spritesheet.boss[0],x-Camera.x,y-Camera.y,128,128,null);
			}else {
				g.drawImage(Spritesheet.boss[0],x-Camera.x,y-Camera.y,128,128,null);
			}
		else {
			if(x<Player.x) {
				g.drawImage(Spritesheet.boss[0],x-Camera.x,y-Camera.y,128,128,null);
			}else {
				g.drawImage(Spritesheet.boss[0],x-Camera.x,y-Camera.y,128,128,null);
			}	
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
}

