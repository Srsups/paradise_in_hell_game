package zelda1;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Bullet extends Rectangle {
    public int dir = 0;
    public int bulletVerticalDirection = 0;
    public int speed = 7;
    public int frames = 0;
    public static List<Bullet> bullets;

    public Bullet(int x, int y, int dir, int bulletVerticalDirection, List<Bullet> bullets) {
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
                break;
            }
        }
        
        for (int i = 0; i < Game.morcegos.size(); i++) {
            Morcego morcegoAtual = Game.morcegos.get(i);
            if (this.intersects(morcegoAtual) && morcegoAtual.alive) {
                bullets.remove(this);
                morcegoAtual.alive = false;
                break;
            }
        }
        
        for (int i = 0; i < Game.dragoes.size(); i++) {
            Dragao dragaoAtual = Game.dragoes.get(i);
            if (this.intersects(dragaoAtual) && dragaoAtual.alive) {
                bullets.remove(this);
                dragaoAtual.alive = false;
                break;
            }
        }
    }

    public void render(Graphics g) {
        if (dir == 1) {
            g.drawImage(Spritesheet.player_bullet_lado[0], x - Camera.x, y - Camera.y, 32, 32, null);
        } else if (dir == -1) {
            g.drawImage(Spritesheet.player_bullet_lado[0], x + 32 - Camera.x, y - Camera.y, -32, 32, null);
        }

        if (bulletVerticalDirection == -1) {
            g.drawImage(Spritesheet.player_bullet_cima[0], x - Camera.x, y - Camera.y, 32, 32, null);
        } else if (bulletVerticalDirection == 1) {
            g.drawImage(Spritesheet.player_bullet_cima[0], x - Camera.x, y + 32 - Camera.y, 32, -32, null);
        }
    }
}
