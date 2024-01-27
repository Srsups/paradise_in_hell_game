package info;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pause_screen {

    private static BufferedImage preRenderedImage;

    // Carrega a imagem uma vez durante a inicialização
    static {
        preRenderedImage = loadImage("D:/eclipse-workspace/Paradise_in_hell/res/pause_screen.jpg");
    }

    public static void render(Graphics g) {
        if (Game.paused) {
            int centerX = (Game.screenWidth - preRenderedImage.getWidth()) / 2;
            int centerY = (Game.screenHeight - preRenderedImage.getHeight()) / 2;
            g.drawImage(preRenderedImage, centerX, centerY, null);
        }
    }

    private static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
