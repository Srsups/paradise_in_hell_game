
package info;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Dragon_Bullet extends Rectangle {
    private int speed = 2;
    int frames = 0;
    private int dano = 40;
    private int bulletHorizontalDirection;
    private int bulletVerticalDirection;
    private List<Dragon_Bullet> dg_bullets = new ArrayList<Dragon_Bullet>();

    public Dragon_Bullet(int x, int y, int bulletHorizontalDirection, int bulletVerticalDirection) {
        super(x + 16, y + 16, 10, 10);
        this.bulletHorizontalDirection = bulletHorizontalDirection;
        this.bulletVerticalDirection = bulletVerticalDirection;
    }

    public void tick() {
    	if (bulletHorizontalDirection == 1) {
            x += speed * bulletHorizontalDirection;
        } else if(bulletHorizontalDirection == -1){
        	x += speed * bulletHorizontalDirection;
        }

        else if (bulletVerticalDirection == -1) {
            y += speed * bulletVerticalDirection;
        } else if(bulletVerticalDirection == 1){
        	y += speed * bulletVerticalDirection;
        }

        frames++;
    }

    public void render(Graphics g) {  	  
        if (bulletHorizontalDirection == 1) {
            g.drawImage(Spritesheet.dragao_bullet_lado[0], x - Camera.x, y - Camera.y, 32, 32, null);
        } else if (bulletHorizontalDirection == -1) {
            g.drawImage(Spritesheet.dragao_bullet_lado[0], x + 32 - Camera.x, y - Camera.y, -32, 32, null);
        } else if (bulletVerticalDirection == -1) {
            g.drawImage(Spritesheet.dragao_bullet_cima[0], x - Camera.x, y - Camera.y, 32, 32, null);
        } else if (bulletVerticalDirection == 1) {
            g.drawImage(Spritesheet.dragao_bullet_cima[0], x - Camera.x, y + 32 - Camera.y, 32, -32, null);
        }
    }
}
