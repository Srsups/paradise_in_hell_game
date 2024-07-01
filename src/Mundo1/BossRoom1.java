package Mundo1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import info.Bloco_inv;
import info.Escada;
import info.Game;

public class BossRoom1 {
	
	public static int larguraDaSala = 1536;
    public static int alturaDaSala = 864;
	
	public static List<Bloco_inv> bloco_inv = new ArrayList<Bloco_inv>();
	public static List<Escada> escadas = new ArrayList<Escada>();
	
	public static Image scaledImage;
	
	public BossRoom1() {
		int num = 16;
		for(int x = 0; x < Game.screenWidth ; x+=num) {
			bloco_inv.add(new Bloco_inv(x,0));
		}
		for(int x = 0; x < Game.screenWidth ; x+=num) {
			bloco_inv.add(new Bloco_inv(x,x+num));
		}
		for(int y = 0; y < Game.screenHeight ; y+=num) {
			bloco_inv.add(new Bloco_inv(0,y*16));
		}
		for(int y = 0; y < Game.screenHeight ; y+=num) {
			bloco_inv.add(new Bloco_inv(1536-16,y*16));
		}
	}
	
	public static boolean isFree(int x, int y) {
		for(int i = 0; i < bloco_inv.size(); i++) {
			Bloco_inv blocoinvAtual = bloco_inv.get(i);
			if(blocoinvAtual.intersects(new Rectangle(x,y,24,24))) {
				return false;
			}
		}
		return true;
	}	
	
	public static void initialize_bg() {
		BufferedImage backgroundImage = null;
		try {
			backgroundImage = ImageIO.read(new File("D:/eclipse-workspace/Paradise_in_hell/res/bg_boss_room.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scaledImage = backgroundImage.getScaledInstance(Game.screenWidth, Game.screenHeight, Image.SCALE_SMOOTH);
	}
	
	public void render_bg(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(scaledImage, 0, 0, null);
	}
	
	public void render(Graphics g) {	
		
	}
}
