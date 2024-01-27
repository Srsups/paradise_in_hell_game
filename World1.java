package info;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class World1 {
	
	public static List<Blocks> blocos = new ArrayList<Blocks>();
	public static List<Escada> escadas = new ArrayList<Escada>();
	
	private BufferedImage worldBuffer; // Adicione esta variável
	
	private boolean requisitosAtendidos = false;
	
	public World1() {       
	}
	
	public void initBuffer() {
        worldBuffer = new BufferedImage(Game.screenWidth, Game.screenHeight, BufferedImage.TYPE_INT_ARGB);
    }
	
	public static boolean isFree(int x, int y) {
		for(int i = 0; i < blocos.size(); i++) {
			Blocks blocoAtual = blocos.get(i);
			if(blocoAtual.intersects(new Rectangle(x,y,24,24))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean entrada_Boss_Room(int x, int y, int largura, int altura) {
	    for (int i = 0; i < escadas.size(); i++) {
	        Escada escadaAtual = escadas.get(i);
	        Rectangle areaPersonagem = new Rectangle(Player.x, Player.y, largura, altura);
	        if (areaPersonagem.intersects(escadaAtual)) {            
	                return false;
	        }
	    }
	    return true;
	}
	
	public void renderToBuffer() {
		Graphics g = worldBuffer.getGraphics();
		int xstart = Camera.x >> 4; //mesma coisa que dividir por 16 só que mais rápido
        int ystart = Camera.y >> 4;
        
        int xfinal = xstart + (Game.screenWidth >> 4);
        int yfinal = ystart + (Game.screenHeight >> 4);
        
        int spriteWidth = 16;
        int spriteHeight = 16;
        
        // Desenhar o background repetidamente em blocos de 16x16
        for (int xBlock = xstart; xBlock <= xfinal; xBlock++) {
            for (int yBlock = ystart; yBlock <= yfinal; yBlock++) {
                int xPos = xBlock * spriteWidth;
                int yPos = yBlock * spriteHeight;

                // Lógica avançada para geração aleatória
                Random rand = new Random((xBlock * 31) + yBlock); // Use valores únicos baseados na posição
                int num_rand = rand.nextInt(100); // Use o valor desejado para o range
                
                BufferedImage spriteSelecionado = null;

                if (num_rand <= 33) {
                    spriteSelecionado = Spritesheet.bg_World1[0];
                } else if (num_rand <= 66) {
                    spriteSelecionado = Spritesheet.bg_World1[1];
                } else {
                    spriteSelecionado = Spritesheet.bg_World1[2];
                }

                g.drawImage(spriteSelecionado, xPos - Camera.x, yPos - Camera.y, spriteWidth, spriteHeight, null);
            }
        }       
		g.dispose();
	}

		
	public void render(Graphics g) {
		renderToBuffer();
	    g.drawImage(worldBuffer, 0, 0, null);
	    if (Game.countMorcegoMorto >= 1 && Game.countInimigoMorto >= 1 && Game.countDragaoMorto >= 1) {
	        if (!requisitosAtendidos) {
	        	escadas.add(new Escada(Player.x, Player.y - 100));
		        blocos.add(new Blocks(Escada.x - 24, Escada.y));
		        blocos.add(new Blocks(Escada.x - 24, Escada.y + 24));
		        blocos.add(new Blocks(Escada.x - 24, Escada.y - 24));
		        blocos.add(new Blocks(Escada.x, Escada.y - 24));
		        blocos.add(new Blocks(Escada.x + 24, Escada.y - 24));
		        blocos.add(new Blocks(Escada.x + 48, Escada.y - 24));
		        blocos.add(new Blocks(Escada.x + 48, Escada.y));
		        blocos.add(new Blocks(Escada.x + 48, Escada.y + 24));

	            requisitosAtendidos = true;
	        }

	        // Movemos esta parte do código para dentro do bloco condicional, para garantir que só seja executada quando necessário
	        for (int i = 0; i < blocos.size(); i++) {
	            blocos.get(i).render(g);
	        }

	        for (int i = 0; i < escadas.size(); i++) {
	            escadas.get(i).render(g);
	        }
	    }
    }
}
