
package Mundo1;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import info.Camera;
import info.Game;
import info.Player;
import info.Spritesheet;
import personagens.Cavaleiro;
import personagens.Cowboy;
import personagens.Mago;
import personagens.Ninja;
import personagens.Pirata;

public class Dragon_Bullet extends Rectangle {
    private int speed = 2;
    int frames = 0;
    private int dano = 10;
    private int bulletHorizontalDirection;
    private int bulletVerticalDirection;
    private List<Dragon_Bullet> dg_bullets = new ArrayList<Dragon_Bullet>();

    public Dragon_Bullet(int x, int y, int bulletHorizontalDirection, int bulletVerticalDirection) {
        super(x + 16, y + 16, 10, 10);
        this.bulletHorizontalDirection = bulletHorizontalDirection;
        this.bulletVerticalDirection = bulletVerticalDirection;
        this.dg_bullets = dg_bullets;
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

    	if(Game.personagem == "default") {
    		if (this.intersects(new Rectangle(Player.x,Player.y,32,32))) {
        		dg_bullets.remove(this);
            	if (Player.vida > 0) {
                    Player.vida -= dano;       
                }
            }
    	} else if(Game.personagem == "cowboy") {
    		if (this.intersects(new Rectangle(Cowboy.x,Cowboy.y,32,32))) {
        		dg_bullets.remove(this);
            	if (Cowboy.vida > 0) {
            		Cowboy.vida -= dano;       
                }
            }
    	} else if(Game.personagem == "mago") {
    		if (this.intersects(new Rectangle(Mago.x,Mago.y,32,32))) {
        		dg_bullets.remove(this);
            	if (Mago.vida > 0) {
            		Mago.vida -= dano;       
                }
            }
    	} else if(Game.personagem == "ninja") {
    		if (this.intersects(new Rectangle(Ninja.x,Ninja.y,32,32))) {
        		dg_bullets.remove(this);
            	if (Ninja.vida > 0) {
            		Ninja.vida -= dano;       
                }
            }
    	} else if(Game.personagem == "pirata") {
    		if (this.intersects(new Rectangle(Pirata.x,Pirata.y,32,32))) {
        		dg_bullets.remove(this);
            	if (Pirata.vida > 0) {
            		Pirata.vida -= dano;       
                }
            }
    	} else if(Game.personagem == "cavaleiro") {
    		if (this.intersects(new Rectangle(Cavaleiro.x,Cavaleiro.y,32,32))) {
        		dg_bullets.remove(this);
            	if (Cavaleiro.vida > 0) {
            		Cavaleiro.vida -= dano;       
                }
            }
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
