package info;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spritesheet {

	public static BufferedImage spritesheet;
	
	public static BufferedImage[] player_front;
	
	public static BufferedImage tileWall;
	
	public static BufferedImage[] player_lado;
	
	public static BufferedImage[] player_cima;
	
	public static BufferedImage[] player_bullet_lado;
	
	public static BufferedImage[] player_bullet_cima;
	
	public static BufferedImage[] dragao_bullet_lado;
	
	public static BufferedImage[] dragao_bullet_cima;
	
	public static BufferedImage[] inimigo_front;
	
	public static BufferedImage backgroundSprite;
	
	public static BufferedImage[] morcego;
	
	public static BufferedImage[] dragao_front_esquerda;
	
	public static BufferedImage[] dragao_front_direita;
	
	public static BufferedImage escadaSprite;
	
	public static BufferedImage[] bg_World1;
	
	public static BufferedImage[] bg_World2;
	
	public static BufferedImage[] bg_World3;
	
	public static BufferedImage[] boss;
	
	public static BufferedImage escadaLocked;
	
	
	public Spritesheet() {
		try {
			spritesheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		player_front = new BufferedImage[2];
		player_lado = new BufferedImage[2];
		player_cima = new BufferedImage[2];
		morcego = new BufferedImage[2];
		bg_World1 = new BufferedImage[3];
		bg_World2 = new BufferedImage[3];
		bg_World3 = new BufferedImage[3];
		boss = new BufferedImage[1];
		
		inimigo_front = new BufferedImage[2];
		
		dragao_front_esquerda = new BufferedImage[2];
		dragao_front_direita = new BufferedImage[2];
		
		bg_World1[0] = Spritesheet.getSprite(227, 209, 16, 16);
		bg_World1[1] = Spritesheet.getSprite(244, 209, 16, 16);
		bg_World1[2] = Spritesheet.getSprite(261, 209, 16, 16);
		
		bg_World2[0] = Spritesheet.getSprite(278, 209, 16, 16);
		bg_World2[1] = Spritesheet.getSprite(295, 209, 16, 16);
		bg_World2[2] = Spritesheet.getSprite(244, 226, 16, 16);
		
		bg_World3[0] = Spritesheet.getSprite(281, 259, 16, 16);
		bg_World3[1] = Spritesheet.getSprite(300, 259, 16, 16);
		bg_World3[2] = Spritesheet.getSprite(281, 277, 16, 16);
		
		player_front[0] = Spritesheet.getSprite(0, 11, 16, 16);
		player_front[1] = Spritesheet.getSprite(16, 11, 16, 16);
		
		player_lado[0] = Spritesheet.getSprite(51, 11, 16, 16);
		player_lado[1] = Spritesheet.getSprite(35, 11, 16, 16);
		
		player_cima[0] = Spritesheet.getSprite(69, 11, 16, 16);
		player_cima[1] = Spritesheet.getSprite(86, 11, 16, 16);
		
		inimigo_front[0] = Spritesheet.getSprite(142, 209, 16, 16);
		inimigo_front[1] = Spritesheet.getSprite(160, 209, 16, 16);
		
		player_bullet_lado = new BufferedImage[1];
		player_bullet_cima = new BufferedImage[1];
		
		player_bullet_lado[0] = Spritesheet.getSprite(10, 189, 16, 16);
		player_bullet_cima[0] = Spritesheet.getSprite(178, 209, 16, 16);
		
		dragao_bullet_lado = new BufferedImage[1];
		dragao_bullet_cima = new BufferedImage[1];
		
		dragao_bullet_lado[0] = Spritesheet.getSprite(354, 251, 10, 6);
		dragao_bullet_cima[0] = Spritesheet.getSprite(344, 270, 6, 10);
		
		morcego[0] = Spritesheet.getSprite(196, 215, 8, 10);
        morcego[1] = Spritesheet.getSprite(205, 217, 16, 8);
        
        dragao_front_esquerda[0] = Spritesheet.getSprite(329, 182 , 22 , 16);
        dragao_front_esquerda[1] = Spritesheet.getSprite(329, 203 , 24 , 15);
        
        dragao_front_direita[0] = Spritesheet.getSprite(328, 223 , 22 , 16);
        dragao_front_direita[1] = Spritesheet.getSprite(328, 246 , 24 , 15);
		
		tileWall = Spritesheet.getSprite(281,241,16,16);
		
		backgroundSprite = Spritesheet.getSprite(300, 241, 16, 16);
		
		escadaSprite = Spritesheet.getSprite(249, 270, 24, 24);
		
		escadaLocked = Spritesheet.getSprite(327, 270, 24, 24);
		
		boss[0] = Spritesheet.getSprite(292, 78, 64, 64);
	}
	
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
		
}
