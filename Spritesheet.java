package zelda1;

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
	
	public static BufferedImage[] inimigo_front;
	
	public static BufferedImage backgroundSprite;
	
	public static BufferedImage[] morcego;
	
	
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

		inimigo_front = new BufferedImage[2];
		
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
		
		morcego[0] = Spritesheet.getSprite(196, 215, 8, 10);
        morcego[1] = Spritesheet.getSprite(205, 217, 16, 8);
		
		tileWall = Spritesheet.getSprite(281,241,16,16);
		
		backgroundSprite = Spritesheet.getSprite(300, 241, 16, 16);
	}
	
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
		
}
