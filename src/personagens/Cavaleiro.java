package personagens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import info.Bullet;
import info.Camera;
import info.Game;
import info.Spritesheet;

public class Cavaleiro extends Rectangle{

	public int spd = 5;
	
	public static int vida = 100;
	
	public int cameraX, cameraY;
	
	public static boolean right, up, down, left;
	
	public int curAnimation = 0;
	
	public int curFrames = 0, targetFrames = 15;
	
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	
	public boolean shoot = false;
	
	public boolean dash = false;
	
	public int dir = 1;
	
	public static boolean canShoot = true;
	
	public static boolean canDash = true;
	
	public static long lastShootTime = 0;
	
	public static long lastDashTime = 0;
	
	public int bulletVerticalDirection = 0;
	
	private static final int DASH_DURATION = 500;  // Duração do dash em milissegundos
	private long dashStartTime = 0;  // Tempo de início do dash
	
	public static int x,y;
	
	public Cavaleiro(int x, int y,int screenWidth,int screenHeight) {
	    super(x, y, 32, 32);
	    this.x = 32; // Posição X no centro do mundo
	    this.y = 32;  // Posição Y no centro do mundo
	}
	
	
	public void tick(int screenWidth,int screenHeight) {
		boolean moved = false;
		int newX = x;
	    int newY = y;
	    
		if(right && Mundo1.World1.isFree(x+spd, y) && x <= 200000) {
			newX = x+=spd;
			moved = true;
			dir = 1;
			bulletVerticalDirection = 0;
		}else if(left && Mundo1.World1.isFree(x-spd, y) && x >= 0) {
			newX = x-=spd;
			moved = true;
			dir = -1;
			bulletVerticalDirection = 0;
		}
		
		if(up && Mundo1.World1.isFree(x, y-spd) && y >= 0) {
			newY = y-=spd;
			moved = true;
			bulletVerticalDirection = -1;
			dir = 0;
		}else if(down && Mundo1.World1.isFree(x, y+spd) && y <= 150000) {
			newY = y+=spd;
			moved = true;
			bulletVerticalDirection = 1;
			dir = 0;
		}
		
		int novaPosicaoY = y - spd;
		
		if(Mundo1.World1.entrada_Boss_Room(x, novaPosicaoY, 32, 32) == false) {
			Game.paused = true;
			Game.inimigos.clear();
			Game.morcegos.clear();
			Game.dragoes.clear();
			Mundo1.World1.blocos.clear();
			Mundo1.World1.escadas.clear();
			Game.normal_zone = false;
			Mundo1.BossRoom1.initialize_bg();
			Game.paused = false;	
			Game.spawnBoss1();
		}
		
		if (newX >= 0 && newX + width <= screenWidth) {
	        x = newX;
	    }

	    if (newY >= 0 && newY + height <= screenHeight) {
	        y = newY;
	    }
		
	    if(Game.normal_zone) {
	    Camera.x =  x - (Game.screenWidth/2);
	    Camera.y =  y - (Game.screenHeight/2);
	    }
		curFrames++;
		
		if(curFrames == targetFrames) {
			curFrames = 0;
			curAnimation++;
			if(curAnimation == Spritesheet.cowboy_front.length) {
				curAnimation = 0;
			}
		}
		
		
		if(shoot) {
			shoot = false;
			bullets.add(new Bullet(x,y,dir,bulletVerticalDirection,bullets));
		}
		
		if (dash) {
		    // Verifica se o dash está em andamento ou se pode começar um novo
		    if (System.currentTimeMillis() - dashStartTime >= DASH_DURATION) {
		        dashStartTime = System.currentTimeMillis();  // Inicia o tempo do dash
		    }

		    // Calcula a porcentagem de conclusão do dash com base no tempo decorrido
		    double dashProgress = (System.currentTimeMillis() - dashStartTime) / (double) DASH_DURATION;

		    // Aplica a interpolação (easing) ao movimento
		    double ease = easeInOutQuad(dashProgress);

		    // Atualiza a posição do personagem de acordo com a direção do dash
		    if (dir == 1) {
		        x = x + (int) (64 * ease);
		    } else if (dir == -1) {
		        x = x - (int) (64 * ease);
		    } else if (bulletVerticalDirection == 1) {
		        y = y + (int) (64 * ease);
		    } else if (bulletVerticalDirection == -1) {
		        y = y - (int) (64 * ease);
		    }

		    // Verifica se o dash foi concluído
		    if (dashProgress >= 1.0) {
		        dash = false;  // Reseta a flag de dash
		    }
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		
	}
	
	public static int getVida() {
        return vida;
    }
	
	// Função de interpolação (easing) - easeInOutQuad
	private double easeInOutQuad(double t) {
	    return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
	}
	
public void render(Graphics g, int renderX, int renderY) {
		
		if(down == true)
			g.drawImage(Spritesheet.cavaleiro_front[curAnimation],x - Camera.x, y - Camera.y,32,32,null);
		
		else if(right == true)
			g.drawImage(Spritesheet.cavaleiro_lado[curAnimation], x - Camera.x, y - Camera.y, 32, 32, null);
		
		else if(left == true)
			g.drawImage(Spritesheet.cavaleiro_lado[curAnimation], x + 32 - Camera.x, y - Camera.y, -32, 32, null);
		
		else if(up == true)
			g.drawImage(Spritesheet.cavaleiro_cima[curAnimation], x - Camera.x, y - Camera.y, 32, 32, null);
		
		else {
			g.drawImage(Spritesheet.cavaleiro_front[0], x - Camera.x, y - Camera.y, 32 , 32, null);
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
	
	
	
	
}
