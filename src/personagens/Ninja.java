package personagens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import Mundo1.BossRoom1;
import Mundo1.World1;
import info.Bullet;
import info.Camera;
import info.Game;
import info.Spritesheet;

public class Ninja extends Rectangle{

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
	
	private static final int DASH_DURATION = 15; // Duração do dash em milissegundos
	private long dashStartTime = 0; // Tempo de início do dash
	private boolean isDashing = false; // Flag para indicar se o personagem está realizando um dash
	private int dashDestinationX; // Destino do dash (coordenada X)
	private int dashDestinationY; // Destino do dash (coordenada Y)
	
	
	public static int x,y;
	
	public Ninja(int x, int y,int screenWidth,int screenHeight) {
	    super(x, y, 32, 32);
	    this.x = 32; // Posição X no centro do mundo
	    this.y = 32;  // Posição Y no centro do mundo
	}
	
	
	public void tick(int screenWidth,int screenHeight) {
		boolean moved = false;
		int newX = x;
	    int newY = y;
	    
		if(right && World1.isFree(x+spd, y) && x <= 200000) {
			newX = x+=spd;
			moved = true;
			dir = 1;
			bulletVerticalDirection = 0;
		}else if(left && World1.isFree(x-spd, y) && x >= 0) {
			newX = x-=spd;
			moved = true;
			dir = -1;
			bulletVerticalDirection = 0;
		}
		
		if(up && World1.isFree(x, y-spd) && y >= 0) {
			newY = y-=spd;
			moved = true;
			bulletVerticalDirection = -1;
			dir = 0;
		}else if(down && World1.isFree(x, y+spd) && y <= 150000) {
			newY = y+=spd;
			moved = true;
			bulletVerticalDirection = 1;
			dir = 0;
		}
		
		int novaPosicaoY = y - spd;
		
		if(World1.entrada_Boss_Room(x, novaPosicaoY, 32, 32) == false) {
			Game.paused = true;
			Game.inimigos.clear();
			Game.morcegos.clear();
			Game.dragoes.clear();
			World1.blocos.clear();
			World1.escadas.clear();
			Game.normal_zone = false;
			BossRoom1.initialize_bg();
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
			if(curAnimation == Spritesheet.player_front.length) {
				curAnimation = 0;
			}
		}
		
		
		if(shoot) {
			shoot = false;
			bullets.add(new Bullet(x,y,dir,bulletVerticalDirection,bullets));
		}
		
		if (dash) {
	        // Inicia o dash apenas se não estiver atualmente realizando um
			isDashing = true;
	        dashStartTime = System.currentTimeMillis();
	        // Define o destino do dash com base na direção atual
	        dashDestinationX = x + (dir * 128); // 64 é a distância do dash
	        dashDestinationY = y + (bulletVerticalDirection * 64); // 64 é a distância do dash
	        dash = false; // Reseta a flag de dash
	    }

	    // Verifica se o personagem está realizando um dash
	    if (isDashing) {
	        // Calcula o progresso do dash com base no tempo decorrido
	        double elapsedTime = (System.currentTimeMillis() - dashStartTime) / (double) DASH_DURATION;
	        double t = (double) elapsedTime / DASH_DURATION;
	        // Aplica a função de easing ease-out
	        double easedT = 1 - Math.pow(1 - t, 2);
	        
	        System.out.printf("%d\n", x);

	        // Interpola a posição do personagem usando o valor de t suavizado
	        x = (int) (x + (dashDestinationX - x) * easedT);
	        y = (int) (y + (dashDestinationY - y) * easedT);

	        // Verifica se o dash foi concluído
	        if (elapsedTime >= DASH_DURATION) {
	            isDashing = false; // Completa o dash
	        }
	    }
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
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
			g.drawImage(Spritesheet.ninja_front[curAnimation],x - Camera.x, y - Camera.y,32,32,null);
		
		else if(right == true)
			g.drawImage(Spritesheet.ninja_lado[curAnimation], x - Camera.x, y - Camera.y, 32, 32, null);
		
		else if(left == true)
			g.drawImage(Spritesheet.ninja_lado[curAnimation], x + 32 - Camera.x, y - Camera.y, -32, 32, null);
		
		else if(up == true)
			g.drawImage(Spritesheet.ninja_cima[curAnimation], x - Camera.x, y - Camera.y, 32, 32, null);
		
		else {
			g.drawImage(Spritesheet.ninja_front[0], x - Camera.x, y - Camera.y, 32 , 32, null);
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
	
	
	
	
}
