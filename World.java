package zelda1;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class World {
	
	public static int larguraDoMundo = 200000;
    public static int alturaDoMundo = 150000;
	
	public static List<Blocks> blocos = new ArrayList<Blocks>();
	
	public World() {
        
        blocos.add(new Blocks(128,128));
        
        blocos.add(new Blocks(152,104));
        
        blocos.add(new Blocks(152,128));
        
        blocos.add(new Blocks(176,128));
        
        blocos.add(new Blocks(152,152));
        
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
	
	public void render(Graphics g) {
		for(int i = 0; i < blocos.size(); i++) {
			blocos.get(i).render(g);
		}
	}
}
