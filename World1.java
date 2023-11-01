package zelda1;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class World1 {
	
	public static int larguraDoMundo = 200000;
    public static int alturaDoMundo = 150000;
	
	public static List<Blocks> blocos = new ArrayList<Blocks>();
	public static List<Escada> escadas = new ArrayList<Escada>();
	
	public World1() {
        
        escadas.add(new Escada(128,128));
        blocos.add(new Blocks(104,104));
        blocos.add(new Blocks(104,128));
        blocos.add(new Blocks(104,152));
        blocos.add(new Blocks(128,104));
        blocos.add(new Blocks(152,104));
        blocos.add(new Blocks(176,104));
        blocos.add(new Blocks(176,128));
        blocos.add(new Blocks(176,152));
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
	
	public static boolean entrada_Boss_Room(int x, int y) {
		for(int i = 0; i < escadas.size(); i++) {
			Escada escadaAtual = escadas.get(i);
			if(escadaAtual.intersects(new Rectangle(x,y,48,48))) {
				// Verifica se o personagem está no meio ou próximo do meio da escada
	            int middleOfStairsX = escadaAtual.x + (escadaAtual.width / 2);
	            int middleOfStairsY = escadaAtual.y + (escadaAtual.height / 2);

	            int distanciaMaximaDoMeio = 4; // ajuste conforme necessário

	            if (Math.abs(x - middleOfStairsX) <= distanciaMaximaDoMeio && Math.abs(y - middleOfStairsY) <= distanciaMaximaDoMeio) {
	                return false; // Personagem está no meio ou próximo do meio da escada
	            }
			}
		}
		return true;
	}
	
	public static void BossRoom() {
		String[] opcoes = {"Voltar", "Continuar"};
		Game.paused = true;
		int escolha = JOptionPane.showOptionDialog(null, "Boss Room", "Você entrou na boss room", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, "Botao 3");
		if (escolha == 0) {
			System.exit(0);
		}else {
			System.exit(0);
		}
	}
	
	public void render(Graphics g) {		
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

        
		for(int i = 0; i < blocos.size(); i++) {
			blocos.get(i).render(g);
		}
		
		for(int i = 0; i < escadas.size(); i++) {
			escadas.get(i).render(g);
		}
	}
}
