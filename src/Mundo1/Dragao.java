package Mundo1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import info.Camera;
import info.Game;
import info.Player;
import info.Spritesheet;
import personagens.Cavaleiro;
import personagens.Cowboy;
import personagens.Mago;
import personagens.Ninja;
import personagens.Pirata;


public class Dragao extends Rectangle{
	
	public boolean alive = true;

	public int spd = 1;
	public int right = 1, up = 0, down = 0, left = 0;
	
	public int curAnimation = 0;
	
	public int curFrames = 0, targetFrames = 15;
	
	private List<Dragon_Bullet> dg_bullets = new ArrayList<Dragon_Bullet>();
	
	private boolean shoot = false;
	
	private int dano;
	
	public int vida = 150;
	
	public static int cooldown;
	
	public static int COOLDOWN_INTERVAL = 20;
	
    public static boolean canShoot = true;
	
	private long lastShootTime = 0;
	
	private long timer_to_shoot = 2500;
	
	private int hor, ver;
	
	public Dragao(int x, int y, int dano) {
		super(x,y,32,32);
		this.dano = dano;
	}
	
	public void perseguirPlayer() {
		Player pl = Game.player;
		Cowboy co = Game.cowboy;
		Mago ma = Game.mago;
		Ninja ni = Game.ninja;
		Pirata pi = Game.pirata;
		Cavaleiro ca = Game.cavaleiro;
		
		if(x < pl.x && World1.isFree(x+spd, y) || x < co.x && World1.isFree(x+spd, y) || x < ma.x && World1.isFree(x+spd, y) || x < ni.x && World1.isFree(x+spd, y) || x < pi.x && World1.isFree(x+spd, y) || x < ca.x && World1.isFree(x+spd, y)) {
			if(new Random().nextInt(100) < 50)
				x+=spd;
		}
		else if(x > pl.x && World1.isFree(x-spd, y) || x > co.x && World1.isFree(x-spd, y) || x > ma.x && World1.isFree(x-spd, y) || x > ni.x && World1.isFree(x-spd, y) || x > pi.x && World1.isFree(x-spd, y) || x > ca.x && World1.isFree(x-spd, y)) {
			if(new Random().nextInt(100) < 50)
				x-=spd;
		}
		
		if(y < pl.y && World1.isFree(x, y+spd) || y < co.y && World1.isFree(x, y+spd) || y < ma.y && World1.isFree(x, y+spd) || y < ni.y && World1.isFree(x, y+spd) || y < pi.y && World1.isFree(x, y+spd) || y < ca.y && World1.isFree(x, y+spd)) {
			if(new Random().nextInt(100) < 50)
				y+=spd;
		}
		else if(y > pl.y && World1.isFree(x, y-spd) || y > co.y && World1.isFree(x, y-spd) || y > ma.y && World1.isFree(x, y-spd) || y > ni.y && World1.isFree(x, y-spd) || y > pi.y && World1.isFree(x, y-spd) || y > ca.y && World1.isFree(x, y-spd)) {
			if(new Random().nextInt(100) < 50)
				y-=spd;
		}
	}
	
	public void tick() {
		if (!alive) {
	        return;
	    }
		
		if(vida <= 0) {
			alive = false;
		}
		
		boolean moved = true;
		
		perseguirPlayer();
		
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
		
		long currentTime = System.currentTimeMillis();
	    if (currentTime - lastShootTime >= timer_to_shoot) {
	        lastShootTime = currentTime;
	        // Calcular a direção em relação à posição do personagem
	        if(Game.personagem == "default") {
	        	hor = Player.x;
		        ver = Player.y;
	        } else if(Game.personagem == "cowboy") {
	        	hor = Cowboy.x;
		        ver = Cowboy.y;
	        } else if(Game.personagem == "mago") {
	        	hor = Mago.x;
		        ver = Mago.y;
	        } else if(Game.personagem == "ninja") {
	        	hor = Ninja.x;
		        ver = Ninja.y;
	        } else if(Game.personagem == "pirata") {
	        	hor = Pirata.x;
		        ver = Pirata.y;
	        } else if(Game.personagem == "cavaleiro") {
	        	hor = Cavaleiro.x;
		        ver = Cavaleiro.y;
	        }
	        
            int deltaX = hor - this.x;
            int deltaY = ver - this.y;

            // Determinar a direção dos projéteis com base nas diferenças
            int bulletHorizontalDirection = deltaX >= 0 ? 1 : -1;
            int bulletVerticalDirection = deltaY >= 0 ? 1 : -1;
            
            if(Game.personagem.equals("default")) {
            	if(this.x >= Player.x - 192 && this.x <= Player.x + 192) {
            		bulletHorizontalDirection = 0;
            	}
            } else if(Game.personagem.equals("cowboy")) {
            	if(this.x >= Cowboy.x - 192 && this.x <= Cowboy.x + 192) {
            		bulletHorizontalDirection = 0;
            	}
            } else if(Game.personagem.equals("mago")) {
            	if(this.x >= Mago.x - 192 && this.x <= Mago.x + 192) {
            		bulletHorizontalDirection = 0;
            	}
            } else if(Game.personagem.equals("ninja")) {
            	if(this.x >= Ninja.x - 192 && this.x <= Ninja.x + 192) {
            		bulletHorizontalDirection = 0;
            	}
            } else if(Game.personagem.equals("pirata")) {
            	if(this.x >= Pirata.x - 192 && this.x <= Pirata.x + 192) {
            		bulletHorizontalDirection = 0;
            	}
            } else if(Game.personagem.equals("cavaleiro")) {
            	if(this.x >= Cavaleiro.x - 192 && this.x <= Cavaleiro.x + 192) {
            		bulletHorizontalDirection = 0;
            	}
            }
            
            // Criar um novo projétil com a direção calculada
            dg_bullets.add(new Dragon_Bullet(x, y, bulletHorizontalDirection, bulletVerticalDirection));
	    }

	    for (int i = 0; i < dg_bullets.size(); i++) {
	        dg_bullets.get(i).tick();
	    }
		
		if(cooldown > 0)
			cooldown --;
		
		// Remover os projéteis que atingiram o limite de tempo
	    for (int i = 0; i < dg_bullets.size(); i++) {
	        Dragon_Bullet bullet = dg_bullets.get(i);
	        bullet.tick();
	        if (bullet.frames >= 500) {
	            dg_bullets.remove(bullet);
	            i--; // Atualizar o índice após a remoção
	        }
	    }
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
			if(x<Player.x || x<Cowboy.x || x<Mago.x || x<Ninja.x || x<Pirata.x || x<Cavaleiro.x) {
				g.drawImage(Spritesheet.dragao_front_direita[0],x-Camera.x,y-Camera.y,44,32,null);
			}else {
				g.drawImage(Spritesheet.dragao_front_esquerda[0],x-Camera.x,y-Camera.y,44,32,null);
			}
		else {
			if(x<Player.x || x<Cowboy.x || x<Mago.x || x<Ninja.x || x<Pirata.x || x<Cavaleiro.x) {
				g.drawImage(Spritesheet.dragao_front_direita[1],x-Camera.x,y-Camera.y,44,32,null);
			}else {
				g.drawImage(Spritesheet.dragao_front_esquerda[1],x-Camera.x,y-Camera.y,48,30,null);
			}	
		}
		
		if (dg_bullets != null) {
			for (int i = 0; i < dg_bullets.size(); i++) {
				dg_bullets.get(i).render(g);
			}
		}
	}
}

