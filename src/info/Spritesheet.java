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
	
	public static BufferedImage[] ninja_front;
	
	public static BufferedImage[] ninja_lado;
	
	public static BufferedImage[] ninja_cima;
	
	public static BufferedImage[] ninja_bullet;
	
	public static BufferedImage[] cowboy_front;
	
	public static BufferedImage[] cowboy_lado;
	
	public static BufferedImage[] cowboy_cima;
	
	public static BufferedImage[] mago_front;
	
	public static BufferedImage[] mago_lado;
	
	public static BufferedImage[] mago_cima;
	
	public static BufferedImage[] pirata_front;
	
	public static BufferedImage[] pirata_lado;
	
	public static BufferedImage[] pirata_cima;
	
	public static BufferedImage[] cavaleiro_front;
	
	public static BufferedImage[] cavaleiro_lado;
	
	public static BufferedImage[] cavaleiro_cima;
	
	
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
		
		cowboy_front = new BufferedImage[3];
		cowboy_lado = new BufferedImage[3];
		cowboy_cima = new BufferedImage[3];
		
		mago_front = new BufferedImage[3];
		mago_lado = new BufferedImage[3];
		mago_cima = new BufferedImage[3];
		
		pirata_front = new BufferedImage[3];
		pirata_lado = new BufferedImage[3];
		pirata_cima = new BufferedImage[3];
		
		cavaleiro_front = new BufferedImage[3];
		cavaleiro_lado = new BufferedImage[3];
		cavaleiro_cima = new BufferedImage[3];
		
		inimigo_front = new BufferedImage[2];
		
		dragao_front_esquerda = new BufferedImage[2];
		dragao_front_direita = new BufferedImage[2];
		
		ninja_front = new BufferedImage[3];
		ninja_lado = new BufferedImage[3];
		ninja_cima = new BufferedImage[3];
		
		bg_World1[0] = Spritesheet.getSprite(278, 209, 16, 16);
		bg_World1[1] = Spritesheet.getSprite(295, 209, 16, 16);
		bg_World1[2] = Spritesheet.getSprite(244, 226, 16, 16);
		
		bg_World2[0] = Spritesheet.getSprite(227, 209, 16, 16);
		bg_World2[1] = Spritesheet.getSprite(244, 209, 16, 16);
		bg_World2[2] = Spritesheet.getSprite(261, 209, 16, 16);
		
		bg_World3[0] = Spritesheet.getSprite(281, 259, 16, 16);
		bg_World3[1] = Spritesheet.getSprite(300, 259, 16, 16);
		bg_World3[2] = Spritesheet.getSprite(281, 277, 16, 16);
		
		player_front[0] = Spritesheet.getSprite(0, 11, 16, 16);
		player_front[1] = Spritesheet.getSprite(16, 11, 16, 16);
		
		player_lado[0] = Spritesheet.getSprite(51, 11, 16, 16);
		player_lado[1] = Spritesheet.getSprite(35, 11, 16, 16);
		
		player_cima[0] = Spritesheet.getSprite(69, 11, 16, 16);
		player_cima[1] = Spritesheet.getSprite(86, 11, 16, 16);
		
		cowboy_front[0] = Spritesheet.getSprite(2, 151, 15, 16);
		cowboy_front[1] = Spritesheet.getSprite(18, 151, 16, 16);
		cowboy_front[2] = Spritesheet.getSprite(34, 151, 15, 16);
		
		cowboy_lado[0] = Spritesheet.getSprite(50, 151, 15, 16);
		cowboy_lado[1] = Spritesheet.getSprite(66, 151, 15, 16);
		cowboy_lado[2] = Spritesheet.getSprite(82, 151, 15, 16);
		
		cowboy_cima[0] = Spritesheet.getSprite(98, 151, 15, 16);
		cowboy_cima[1] = Spritesheet.getSprite(114, 151, 16, 16);
		cowboy_cima[2] = Spritesheet.getSprite(130, 151, 16, 16);
		
		mago_front[0] = Spritesheet.getSprite(1, 168, 15, 16);
		mago_front[1] = Spritesheet.getSprite(17, 168, 14, 16);
		mago_front[2] = Spritesheet.getSprite(32, 168, 16, 16);
		
		mago_lado[0] = Spritesheet.getSprite(50, 168, 10, 16);
		mago_lado[1] = Spritesheet.getSprite(66, 168, 12, 16);
		mago_lado[2] = Spritesheet.getSprite(80, 168, 13, 16);
		
		mago_cima[0] = Spritesheet.getSprite(96, 168, 15, 16);
		mago_cima[1] = Spritesheet.getSprite(112, 168, 15, 16);
		mago_cima[2] = Spritesheet.getSprite(128, 168, 15, 16);
		
		ninja_front[0] = Spritesheet.getSprite(127, 208, 15, 16);
		ninja_front[1] = Spritesheet.getSprite(126, 226, 15, 16);
		ninja_front[2] = Spritesheet.getSprite(143, 226, 15, 16);
		
		ninja_lado[0] = Spritesheet.getSprite(145, 244, 10, 16);
		ninja_lado[1] = Spritesheet.getSprite(162, 244, 11, 16);
		ninja_lado[2] = Spritesheet.getSprite(178, 244, 11, 16);
		
		ninja_cima[0] = Spritesheet.getSprite(160, 226, 15, 16);
		ninja_cima[1] = Spritesheet.getSprite(176, 226, 15, 16);
		ninja_cima[2] = Spritesheet.getSprite(127, 244, 15, 16);
		
		pirata_front[0] = Spritesheet.getSprite(150, 151, 16, 16);
		pirata_front[1] = Spritesheet.getSprite(166, 151, 16, 16);
		pirata_front[2] = Spritesheet.getSprite(182, 151, 16, 16);
		
		pirata_lado[0] = Spritesheet.getSprite(200, 151, 11, 16);
		pirata_lado[1] = Spritesheet.getSprite(216, 151, 12, 16);
		pirata_lado[2] = Spritesheet.getSprite(232, 151, 11, 16);
		
		pirata_cima[0] = Spritesheet.getSprite(246, 151, 16, 16);
		pirata_cima[1] = Spritesheet.getSprite(262, 151, 16, 16);
		pirata_cima[2] = Spritesheet.getSprite(278, 151, 16, 16);
		
		cavaleiro_front[0] = Spritesheet.getSprite(150, 168, 15, 16);
		cavaleiro_front[1] = Spritesheet.getSprite(166, 168, 15, 16);
		cavaleiro_front[2] = Spritesheet.getSprite(182, 168, 15, 16);
		
		cavaleiro_lado[0] = Spritesheet.getSprite(198, 168, 18, 16);
		cavaleiro_lado[1] = Spritesheet.getSprite(217, 168, 15, 16);
		cavaleiro_lado[2] = Spritesheet.getSprite(233, 168, 18, 16);
		
		cavaleiro_cima[0] = Spritesheet.getSprite(246, 151, 16, 16);
		cavaleiro_cima[1] = Spritesheet.getSprite(262, 151, 16, 16);
		cavaleiro_cima[2] = Spritesheet.getSprite(278, 151, 16, 16);
		
		ninja_bullet = new BufferedImage[1];
		
		ninja_bullet[0] = Spritesheet.getSprite(3, 211, 16, 16);
		
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
