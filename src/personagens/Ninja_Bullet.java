package personagens;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import Mundo1.Dragao;
import Mundo1.Inimigo;
import Mundo1.Morcego;
import Mundo1.World1;
import info.Blocks;
import info.Bullet;
import info.Camera;
import info.Game;
import info.Spritesheet;

public class Ninja_Bullet extends Rectangle {
    public int dir = 0;
    public int bulletVerticalDirection = 0;
    public int speed = 7;
    public int frames = 0;
    public static int dano = 50;
    public List<Bullet> bullets;

    public Ninja_Bullet(int x, int y, int dir, int bulletVerticalDirection, List<Bullet> bullets) {
        super(x + 16, y + 16, 10, 10);
        this.dir = dir;
        this.bulletVerticalDirection = bulletVerticalDirection;
        this.bullets = bullets;
    }

    public void tick() {
        if (dir != 0) {
            x += speed * dir;
        }

        if (bulletVerticalDirection != 0) {
            y += speed * bulletVerticalDirection;
        }

        frames++;

        if (frames == 60) {
            bullets.remove(this);
        }
        
        for (int i = 0; i < World1.blocos.size(); i++) {
            Blocks blocoAtual = World1.blocos.get(i);
            if (this.intersects(blocoAtual)) {
                bullets.remove(this);
                break;
            }
        }
        
        for (int i = 0; i < Game.inimigos.size(); i++) {
            Inimigo inimigoAtual = Game.inimigos.get(i);
            if (this.intersects(inimigoAtual) && inimigoAtual.alive) {
                bullets.remove(this);
                inimigoAtual.alive = false;
                Game.countInimigoMorto++;
                break;
            }
        }
        
        for (int i = 0; i < Game.morcegos.size(); i++) {
            Morcego morcegoAtual = Game.morcegos.get(i);
            if (this.intersects(morcegoAtual) && morcegoAtual.alive) {
                bullets.remove(this);
                morcegoAtual.alive = false;
                Game.countMorcegoMorto++;
                break;
            }
        }
        
        for (int i = 0; i < Game.dragoes.size(); i++) {
            Dragao dragaoAtual = Game.dragoes.get(i);
            if (this.intersects(dragaoAtual) && dragaoAtual.alive) {
            	dragaoAtual.vida -= 50;
                Game.countDragaoMorto++;
                break;
            }
        }
    }

    public void render(Graphics g) {
        if (dir == 1) {
            g.drawImage(Spritesheet.ninja_bullet[0], x - Camera.x, y - Camera.y, 16, 16, null);
        } else if (dir == -1) {
            g.drawImage(Spritesheet.ninja_bullet[0], x + 32 - Camera.x, y - Camera.y, -16, 16, null);
        }

        if (bulletVerticalDirection == -1) {
            g.drawImage(Spritesheet.ninja_bullet[0], x - Camera.x, y - Camera.y, 16, 16, null);
        } else if (bulletVerticalDirection == 1) {
            g.drawImage(Spritesheet.ninja_bullet[0], x - Camera.x, y + 16 - Camera.y, 16, -16, null);
        }
    }
}
