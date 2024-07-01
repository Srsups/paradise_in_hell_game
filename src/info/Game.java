package info;

import javax.imageio.ImageIO;
import javax.swing.*;

import Mundo1.Boss1;
import Mundo1.BossRoom1;
import Mundo1.Dragao;
import Mundo1.Dragon_Bullet;
import Mundo1.Inimigo;
import Mundo1.Morcego;
import Mundo1.World1;
import personagens.Cavaleiro;
import personagens.Cowboy;
import personagens.Mago;
import personagens.Ninja;
import personagens.Pirata;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	public static boolean paused = false;
    public static Player player;
    public static Cowboy cowboy;
    public static Mago mago;
    public static Pirata pirata;
    public static Ninja ninja;
    public static Cavaleiro cavaleiro;
    public static World1 world1;
    public static World2 world2;
    public static World3 world3;
    public static BossRoom1 bossroom;
    public static Pause_screen pausescreen;
    public long tempoMinimoEntreTiros = 500;
    private long dashcooldown = 500;
    public static List<Inimigo> inimigos = new ArrayList<Inimigo>();
    public static List<Morcego> morcegos = new ArrayList<Morcego>();
    public static List<Dragao> dragoes = new ArrayList<Dragao>();
    public static List<Dragon_Bullet> dg_bullet = new ArrayList<Dragon_Bullet>();
    public static Boss3 boss3;
    public static Boss2 boss2;
    public static Boss1 boss1;
    public static List<Escada> escadas = new ArrayList<Escada>();
    public static int LarguraTela;
    public static int AlturaTela;
    static JFrame frame;
    private Timer enemySpawnTimer;
    private Timer morcegoSpawnTimer;
    private Timer dragaoSpawnTimer;
    private int enemySpawnInterval = 7000;
    private int morcegoSpawnInterval = 5000;
    private int dragaoSpawnInterval = 5000;
    public static int screenWidth;
    public static int screenHeight;
    public int startX, startY;
    public int danodragao = 100;
    public int danoinimigo = 50;
    public int danomorcego = 25;
    public static int danoboss = 5000;
    public static boolean normal_zone = true;
    public static int larguraDaTela;
    public static int alturaDaTela;
    private static int frames = 0;
    private static long lastTime = System.nanoTime();
    private static double fps = 0.0;
    private static boolean render_bg = false;
    private static boolean deathmessage;
    public static boolean entrada = true;
    public static int countMorcegoMorto;
    public static int countInimigoMorto;
    public static int countDragaoMorto;
    public static String personagem = "default";
    
    public Game() {
        new Spritesheet();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        if(personagem == "default") {
        	player = new Player(32, 32, screenWidth, screenHeight);
        } else if(personagem == "cowboy") {
        	cowboy = new Cowboy(32,32, screenWidth, screenHeight);
        } else if(personagem == "mago") {
        	mago = new Mago(32,32, screenWidth, screenHeight);
        } else if(personagem == "ninja") {
        	ninja = new Ninja(32,32, screenWidth, screenHeight);
        } else if(personagem == "pirata") {
        	pirata = new Pirata(32,32, screenWidth, screenHeight);
        } else if(personagem == "cavaleiro") {
        	cavaleiro = new Cavaleiro(32,32, screenWidth, screenHeight);
        }
        
        world1 = new World1();
        world2 = new World2();
        world3 = new World3();
        bossroom = new BossRoom1();
        
        enemySpawnTimer = new Timer(enemySpawnInterval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(personagem == "default") {
                	gerarInimigoAleatorio(player.x, player.y);
            	} else if(personagem == "cowboy") {
            		gerarInimigoAleatorio(cowboy.x, cowboy.y);
            	} else if(personagem == "mago") {
            		gerarInimigoAleatorio(mago.x, mago.y);
            	} else if(personagem == "ninja") {
            		gerarInimigoAleatorio(ninja.x, ninja.y);
            	} else if(personagem == "pirata") {
            		gerarInimigoAleatorio(pirata.x, pirata.y);
            	} else if(personagem == "cavaleiro") {
            		gerarInimigoAleatorio(cavaleiro.x, cavaleiro.y);
            	} 
            }
        });
        enemySpawnTimer.start();
        
        morcegoSpawnTimer = new Timer(morcegoSpawnInterval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(personagem == "default") {
                	gerarMorcegoAleatorio(player.x, player.y);
            	} else if(personagem == "cowboy") {
            		gerarMorcegoAleatorio(cowboy.x, cowboy.y);
            	} else if(personagem == "mago") {
            		gerarMorcegoAleatorio(mago.x, mago.y);
            	} else if(personagem == "ninja") {
            		gerarMorcegoAleatorio(ninja.x, ninja.y);
            	} else if(personagem == "pirata") {
            		gerarMorcegoAleatorio(pirata.x, pirata.y);
            	} else if(personagem == "cavaleiro") {
            		gerarMorcegoAleatorio(cavaleiro.x, cavaleiro.y);
            	} 
            }
        });
        morcegoSpawnTimer.start();
        
        dragaoSpawnTimer = new Timer(dragaoSpawnInterval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(personagem == "default") {
            		gerarDragaoAleatorio(player.x, player.y);
            	} else if(personagem == "cowboy") {
            		gerarDragaoAleatorio(cowboy.x, cowboy.y);
            	} else if(personagem == "mago") {
            		gerarDragaoAleatorio(mago.x, mago.y);
            	} else if(personagem == "ninja") {
            		gerarDragaoAleatorio(ninja.x, ninja.y);
            	} else if(personagem == "pirata") {
            		gerarDragaoAleatorio(pirata.x, pirata.y);
            	} else if(personagem == "cavaleiro") {
            		gerarDragaoAleatorio(cavaleiro.x, cavaleiro.y);
            	} 
            }
        });
        dragaoSpawnTimer.start();
        
        
    }

    public static void tick() {
    	String[] opcoes = {"Retornar ao Menu", "Sair do Jogo"};
    	
    	if(personagem == "default") {
    		player.tick(LarguraTela, AlturaTela);
    	} else if(personagem == "cowboy") {
    		cowboy.tick(LarguraTela, AlturaTela);
        } else if(personagem == "mago") {
        	mago.tick(LarguraTela, AlturaTela);
        } else if(personagem == "ninja") {
        	ninja.tick(LarguraTela, AlturaTela);
        } else if(personagem == "pirata") {
        	pirata.tick(LarguraTela, AlturaTela);
        } else if(personagem == "cavaleiro") {
        	cavaleiro.tick(LarguraTela, AlturaTela);
        }
    	
    	if(normal_zone) {
	        for (int i = 0; i < inimigos.size(); i++) {
	            inimigos.get(i).tick();
	        }
	        for (int i = 0; i < morcegos.size(); i++) {
	            morcegos.get(i).tick();
	        }
	        
	        for (int i = 0; i < dragoes.size(); i++) {
	            dragoes.get(i).tick();
	        }
	        
	        if(Inimigo.cooldown <= 0) {
	        	colisaoinimigo();
	        	Inimigo.cooldown = 100;
	        }
	        
	        if(Morcego.cooldown <= 0) {
	        	colisaomorcego();
	        	Morcego.cooldown = 400;
	        }
	        
	        if(Dragao.cooldown <= 0) {
	        	colisaodragao();
	        	Dragao.cooldown = 200;
	        }
	        
	        for (int i = 0; i < inimigos.size(); i++) {
	        	Inimigo inimigoAtual = inimigos.get(i);
	        	inimigoAtual.tick();
	        }
	        
	        if(Inimigo.isCooldownReady()) {
	        	colisaoinimigo();
	        	for (Inimigo inimigo :inimigos) {
	        		inimigo.resetCooldown();
	        	}
	        }
	        
	        for (int i = 0; i < morcegos.size(); i++) {
	        	Morcego morcegoAtual = morcegos.get(i);
	        	morcegoAtual.tick();
	        }
	        
	        if(Dragao.isCooldownReady()) {
	        	colisaodragao();
	        	for (Dragao dragao :dragoes) {
	        		dragao.resetCooldown();
	        	}
	        }
    	} else {
    		if(boss1.cooldown <= 0) {
            	colisaoboss();
            	boss1.cooldown = 100;
            }
    		
    		if(Boss1.isCooldownReady()) {
            	colisaoboss();
            	boss1.resetCooldown();
            }
    	}
    	
    	// Incrementa o número de quadros
        frames++;

        // Calcula o FPS a cada segundo
        long now = System.nanoTime();
        if (now - lastTime >= 1_000_000_000) { // Um segundo em nanossegundos
            fps = (double) frames * 1e9 / (now - lastTime);
            frames = 0;
            lastTime = now;
        }
        
        if(Player.vida<=0 || Cavaleiro.vida <=0 || Cowboy.vida <=0 || Mago.vida <=0 || Ninja.vida <=0 || Pirata.vida <=0) {
        	paused = true;
        	deathmessage = true;
            int escolha = JOptionPane.showOptionDialog(null, "O que deseja fazer?", "Você morreu!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, "Botao 3");
            if (escolha == 1) {
                System.exit(0);
            } else if(escolha == 0) {
            	Menu.main(null);;
            } else {
                paused = false;
            }
        }        
    }
    
    public static void tick_boss_room() {
    	String[] opcoes = {"Retornar ao Menu", "Sair do Jogo"};
    	if(personagem == "default") {
    		player.tick(LarguraTela, AlturaTela);
    	} else if(personagem == "cowboy") {
    		cowboy.tick(LarguraTela, AlturaTela);
        } else if(personagem == "mago") {
        	mago.tick(LarguraTela, AlturaTela);
        } else if(personagem == "ninja") {
        	ninja.tick(LarguraTela, AlturaTela);
        } else if(personagem == "pirata") {
        	pirata.tick(LarguraTela, AlturaTela);
        } else if(personagem == "cavaleiro") {
        	cavaleiro.tick(LarguraTela, AlturaTela);
        }
    	boss1.tick();

    	if(Boss1.cooldown <= 0) {
            colisaoboss();
            boss1.cooldown = 100;
        }
    		
    	if(Boss1.isCooldownReady()) {
            colisaoboss();
            boss1.resetCooldown();
    	}
    	
    	// Incrementa o número de quadros
        frames++;

        // Calcula o FPS a cada segundo
        long now = System.nanoTime();
        if (now - lastTime >= 1_000_000_000) { // Um segundo em nanossegundos
            fps = (double) frames * 1e9 / (now - lastTime);
            frames = 0;
            lastTime = now;
        }
        
        if(Player.vida<=0 || Cavaleiro.vida <=0 || Cowboy.vida <=0 || Mago.vida <=0 || Ninja.vida <=0 || Pirata.vida <=0) {
        	paused = true;
            int escolha = JOptionPane.showOptionDialog(null, "O que deseja fazer agora?", "Você não conseguiu superar o deus do submundo!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, "Botao 3");
            if (escolha == 1) {
                System.exit(0);
            } else if(escolha == 0) {
            	Menu.main(null);;
            } else {
                paused = false;
            }
        }        
    }
    
    public static void colisaoinimigo() {
        for (int i = 0; i < inimigos.size(); i++) {
            Inimigo inimigoAtual = inimigos.get(i);
            if (inimigoAtual.intersects(new Rectangle(player.x,player.y,32,32)) && inimigoAtual.alive) {
            	if (Player.vida > 0) {
                    Player.vida -= inimigoAtual.getDano();
                } else if(Cavaleiro.vida > 0) {
                	Cavaleiro.vida -= inimigoAtual.getDano();
                } else if(Cowboy.vida > 0) {
                	Cowboy.vida -= inimigoAtual.getDano();
                } else if(Mago.vida > 0) {
                	Mago.vida -= inimigoAtual.getDano();
                } else if(Ninja.vida > 0) {
                	Ninja.vida -= inimigoAtual.getDano();
                } else if(Pirata.vida > 0) {
                	Pirata.vida -= inimigoAtual.getDano();
                }
            }
        }
    }

    public static void colisaomorcego() {
    	for (int i = 0; i < morcegos.size(); i++) {
            Morcego morcegoAtual = morcegos.get(i);
            if (morcegoAtual.intersects(new Rectangle(player.x,player.y,26,16)) && morcegoAtual.alive) {
            	if (Player.vida > 0) {
                    Player.vida -= morcegoAtual.getDano();
                } else if(Cavaleiro.vida > 0) {
                	Cavaleiro.vida -= morcegoAtual.getDano();
                } else if(Cowboy.vida > 0) {
                	Cowboy.vida -= morcegoAtual.getDano();
                } else if(Mago.vida > 0) {
                	Mago.vida -= morcegoAtual.getDano();
                } else if(Ninja.vida > 0) {
                	Ninja.vida -= morcegoAtual.getDano();
                } else if(Pirata.vida > 0) {
                	Pirata.vida -= morcegoAtual.getDano();
                }
            }
        }
    }
    
    public static void colisaodragao() {
    	for (int i = 0; i < dragoes.size(); i++) {
            Dragao dragaoAtual = dragoes.get(i);
            if (dragaoAtual.intersects(new Rectangle(player.x,player.y,46,31)) && dragaoAtual.alive) {
            	if (Player.vida > 0) {
                    Player.vida -= dragaoAtual.getDano();
                } else if(Cavaleiro.vida > 0) {
                	Cavaleiro.vida -= dragaoAtual.getDano();
                } else if(Cowboy.vida > 0) {
                	Cowboy.vida -= dragaoAtual.getDano();
                } else if(Mago.vida > 0) {
                	Mago.vida -= dragaoAtual.getDano();
                } else if(Ninja.vida > 0) {
                	Ninja.vida -= dragaoAtual.getDano();
                } else if(Pirata.vida > 0) {
                	Pirata.vida -= dragaoAtual.getDano();
                }
            }
        }
    }
    
    public static void colisaoboss() {
        if (boss1 != null) {
            Boss1 bossAtual = boss1;
            // Verifica a colisão com o jogador apenas se o boss estiver vivo
            if (bossAtual.alive && bossAtual.intersects(new Rectangle(player.x, player.y, 128, 128))) {
            	if (Player.vida > 0) {
                    Player.vida -= bossAtual.getDano();
                } else if(Cavaleiro.vida > 0) {
                	Cavaleiro.vida -= bossAtual.getDano();
                } else if(Cowboy.vida > 0) {
                	Cowboy.vida -= bossAtual.getDano();
                } else if(Mago.vida > 0) {
                	Mago.vida -= bossAtual.getDano();
                } else if(Ninja.vida > 0) {
                	Ninja.vida -= bossAtual.getDano();
                } else if(Pirata.vida > 0) {
                	Pirata.vida -= bossAtual.getDano();
                }
            }
        }
    }

    
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        // Obtém as dimensões reais da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        
        world1.initBuffer();
        
        world1.render(g); 
        // Desenhe o personagem na posição calculada
        
        
        if(personagem == "default") {
        	player.render(g, player.x, player.y);
    	} else if(personagem == "cowboy") {
    		cowboy.render(g, player.x, player.y);
        } else if(personagem == "mago") {
        	mago.render(g, player.x, player.y);
        } else if(personagem == "ninja") {
        	ninja.render(g, player.x, player.y);
        } else if(personagem == "pirata") {
        	pirata.render(g, player.x, player.y);
        } else if(personagem == "cavaleiro") {
        	cavaleiro.render(g, player.x, player.y);
        }
           
            //Renderiza a vida no canto superior da tela
        g.setColor(Color.BLACK); // Define a cor do texto
        Font fonte = new Font("Arial", Font.BOLD, 20); // Define a fonte do texto
        g.setFont(fonte);
        
        if(personagem == "default") {
        	g.drawString("Vida: " + Player.getVida(), 10, 20);
        } else if(personagem == "cowboy") {
        	g.drawString("Vida: " + Cowboy.getVida(), 10, 20);
        } else if(personagem == "mago") {
        	g.drawString("Vida: " + Mago.getVida(), 10, 20);
        } else if(personagem == "ninja") {
        	g.drawString("Vida: " + Ninja.getVida(), 10, 20);
        } else if(personagem == "pirata") {
        	g.drawString("Vida: " + Pirata.getVida(), 10, 20);
        } else if(personagem == "cavaleiro") {
        	g.drawString("Vida: " + Cavaleiro.getVida(), 10, 20);
        }
        
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + String.format("%.2f", fps), 250, 20);
            
        for (int i = 0; i < inimigos.size(); i++) {
            inimigos.get(i).render(g);
        }
        for (int i = 0; i < morcegos.size(); i++) {
            morcegos.get(i).render(g);
        }
        for (int i = 0; i < dragoes.size(); i++) {
            dragoes.get(i).render(g);
        }
            
        bs.show();
        g.dispose();        	
    }
        
        public void render_boss_room() {
            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(3);
                return;
            }
            Graphics g = bs.getDrawGraphics();
            
            bossroom.render_bg(g);
         
        	// Desenhe o personagem na posição calculada
            if(personagem == "default") {
            	player.render(g, player.x, player.y);
        	} else if(personagem == "cowboy") {
        		cowboy.render(g, player.x, player.y);
            } else if(personagem == "mago") {
            	mago.render(g, player.x, player.y);
            } else if(personagem == "ninja") {
            	ninja.render(g, player.x, player.y);
            } else if(personagem == "pirata") {
            	pirata.render(g, player.x, player.y);
            } else if(personagem == "cavaleiro") {
            	cavaleiro.render(g, player.x, player.y);
            }
            
            // Renderiza a vida no canto superior da tela
            g.setColor(Color.BLACK); // Define a cor do texto
            Font fonte = new Font("Arial", Font.BOLD, 20); // Define a fonte do texto
            g.setFont(fonte);

            if(personagem == "default") {
            	g.drawString("Vida: " + Player.getVida(), 10, 20);
            } else if(personagem == "cowboy") {
            	g.drawString("Vida: " + Cowboy.getVida(), 10, 20);
            } else if(personagem == "mago") {
            	g.drawString("Vida: " + Mago.getVida(), 10, 20);
            } else if(personagem == "ninja") {
            	g.drawString("Vida: " + Ninja.getVida(), 10, 20);
            } else if(personagem == "pirata") {
            	g.drawString("Vida: " + Pirata.getVida(), 10, 20);
            } else if(personagem == "cavaleiro") {
            	g.drawString("Vida: " + Cavaleiro.getVida(), 10, 20);
            }
            
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + String.format("%.2f", fps), 250, 20);
            
            boss1.render(g);
            
            bs.show();
            g.dispose();

            if(boss1.y == 142) {
                entrada = false;
            }
        }
        
        public void render_pause_menu() {
            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(3);
                return;
            }
            Graphics g = bs.getDrawGraphics();

            Pause_screen.render(g);

            bs.show();
            g.dispose();
        }

    public static void main(String[] args) {
    		paused = false;
            Game game = new Game();
            game.frame = new JFrame();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();

            if (gd.isFullScreenSupported()) {
                game.frame.setUndecorated(true);
                game.frame.addKeyListener(game);
                game.frame.addMouseListener(game);
                gd.setFullScreenWindow(game.frame);
            } else {
                game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                game.frame.setUndecorated(true);
            }
            game.frame.add(game);
            game.frame.setTitle("Zelda");
            game.frame.pack();
            game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            game.frame.setLocationRelativeTo(null);
            game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
            game.frame.getContentPane().setCursor(blankCursor);
            game.frame.setVisible(true);
            new Thread(game).start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long now;
        long delta;
        final double nsPerFrame = 1_000_000_000.0 / 60.0; // 1 bilhão de nanossegundos por segundo / 60 FPS        

        while (true) {
            now = System.nanoTime();
            delta = now - lastTime;

            if (delta >= nsPerFrame) {
                if (!paused && normal_zone) {
                    tick();
                    render();
                } else if (!paused && !normal_zone) {
                    tick_boss_room();
                    render_boss_room();
                }

                lastTime = now;
            }

            try {
                // Adicione algum código aqui para medir o tempo decorrido e controlar a taxa de quadros.
                // Isso pode incluir ajustes na lógica do jogo para manter a consistência, se necessário.

                // Calcule o tempo restante para dormir
                long sleepTime = (long) (nsPerFrame - (System.nanoTime() - lastTime));

                // Durma o thread para manter uma taxa constante de quadros
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 1_000_000, (int) (sleepTime % 1_000_000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = true;
            cowboy.right = true;
            cavaleiro.right = true;
            mago.right = true;
            ninja.right = true;
            pirata.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
            cowboy.left = true;
            cavaleiro.left = true;
            mago.left = true;
            ninja.left = true;
            pirata.left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
            cowboy.right = true;
            cavaleiro.right = true;
            mago.right = true;
            ninja.right = true;
            pirata.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
            cowboy.left = true;
            cavaleiro.left = true;
            mago.left = true;
            ninja.left = true;
            pirata.left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = true;
            cowboy.up = true;
            cavaleiro.up = true;
            mago.up = true;
            ninja.up = true;
            pirata.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.down = true;
            cowboy.down = true;
            cavaleiro.down = true;
            mago.down = true;
            ninja.down = true;
            pirata.down = true;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
            cowboy.up = true;
            cavaleiro.up = true;
            mago.up = true;
            ninja.up = true;
            pirata.up = true;
            
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
        	player.down = true;
            cowboy.down = true;
            cavaleiro.down = true;
            mago.down = true;
            ninja.down = true;
            pirata.down = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
        	
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (paused) {
                paused = false;
            } else {
                paused = true;
                Game.frame.setCursor(Cursor.getDefaultCursor());
                render_pause_menu();
            }
        } else if (paused) {
        }
    } 

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = false;
            cowboy.right = false;
            cavaleiro.right = false;
            mago.right = false;
            ninja.right = false;
            pirata.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
            cowboy.left = false;
            cavaleiro.left = false;
            mago.left = false;
            ninja.left = false;
            pirata.left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
            cowboy.right = false;
            cavaleiro.right = false;
            mago.right = false;
            ninja.right = false;
            pirata.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
            cowboy.left = false;
            cavaleiro.left = false;
            mago.left = false;
            ninja.left = false;
            pirata.left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = false;
            cowboy.up = false;
            cavaleiro.up = false;
            mago.up = false;
            ninja.up = false;
            pirata.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.down = false;
            cowboy.down = false;
            cavaleiro.down = false;
            mago.down = false;
            ninja.down = false;
            pirata.down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
            cowboy.up = false;
            cavaleiro.up = false;
            mago.up = false;
            ninja.up = false;
            pirata.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
            cowboy.down = false;
            cavaleiro.down = false;
            mago.down = false;
            ninja.down = false;
            pirata.down = false;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	// Se o botão esquerdo do mouse foi clicado
        if(personagem == "default") {
        	if (e.getButton() == MouseEvent.BUTTON1 && Player.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - player.lastShootTime >= tempoMinimoEntreTiros) {
                    player.shoot = true;
                    Player.lastShootTime = currentTime;
                    Player.canShoot = false;
                }
            }
            player.shoot = false;
            player.canShoot = true;
            if (e.getButton() == MouseEvent.BUTTON3 && Player.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - player.lastDashTime >= dashcooldown) {
                    player.dash = true;
                    Player.lastDashTime = currentTime;
                    Player.canDash = false;
                }
            }
            player.dash = false;
            player.canDash = true;
        } else if(personagem == "cowboy") {
        	if (e.getButton() == MouseEvent.BUTTON1 && cowboy.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - cowboy.lastShootTime >= tempoMinimoEntreTiros) {
                	cowboy.shoot = true;
                	cowboy.lastShootTime = currentTime;
                	cowboy.canShoot = false;
                }
            }
        	cowboy.shoot = false;
        	cowboy.canShoot = true;
            if (e.getButton() == MouseEvent.BUTTON3 && cowboy.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - cowboy.lastDashTime >= dashcooldown) {
                	cowboy.dash = true;
                    cowboy.lastDashTime = currentTime;
                    cowboy.canDash = false;
                }
            }
            cowboy.dash = false;
            cowboy.canDash = true;
        } else if(personagem == "mago") {
        	if (e.getButton() == MouseEvent.BUTTON1 && mago.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - mago.lastShootTime >= tempoMinimoEntreTiros) {
                	mago.shoot = true;
                	mago.lastShootTime = currentTime;
                	mago.canShoot = false;
                }
            }
        	mago.shoot = false;
        	mago.canShoot = true;
            if (e.getButton() == MouseEvent.BUTTON3 && mago.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - mago.lastDashTime >= dashcooldown) {
                	mago.dash = true;
                	mago.lastDashTime = currentTime;
                	mago.canDash = false;
                }
            }
            mago.dash = false;
            mago.canDash = true;
        } else if(personagem == "ninja") {
        	if (e.getButton() == MouseEvent.BUTTON1 && ninja.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - ninja.lastShootTime >= tempoMinimoEntreTiros) {
                	ninja.shoot = true;
                	ninja.lastShootTime = currentTime;
                	ninja.canShoot = false;
                }
            }
        	ninja.shoot = false;
        	ninja.canShoot = true;
            if (e.getButton() == MouseEvent.BUTTON3 && ninja.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - ninja.lastDashTime >= dashcooldown) {
                	ninja.dash = true;
                	ninja.lastDashTime = currentTime;
                	ninja.canDash = false;
                }
            }
            ninja.dash = false;
            ninja.canDash = true;
        } else if(personagem == "pirata") {
        	if (e.getButton() == MouseEvent.BUTTON1 && pirata.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - pirata.lastShootTime >= tempoMinimoEntreTiros) {
                	pirata.shoot = true;
                	pirata.lastShootTime = currentTime;
                	pirata.canShoot = false;
                }
            }
        	pirata.shoot = false;
        	pirata.canShoot = true;
            if (e.getButton() == MouseEvent.BUTTON3 && pirata.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - pirata.lastDashTime >= dashcooldown) {
                	pirata.dash = true;
                	pirata.lastDashTime = currentTime;
                	pirata.canDash = false;
                }
            }
            pirata.dash = false;
            pirata.canDash = true;
        } else if(personagem == "cavaleiro") {
        	if (e.getButton() == MouseEvent.BUTTON1 && cavaleiro.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - cavaleiro.lastShootTime >= tempoMinimoEntreTiros) {
                	cavaleiro.shoot = true;
                	cavaleiro.lastShootTime = currentTime;
                	cavaleiro.canShoot = false;
                }
            }
        	cavaleiro.shoot = false;
        	cavaleiro.canShoot = true;
            if (e.getButton() == MouseEvent.BUTTON3 && cavaleiro.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - cavaleiro.lastDashTime >= dashcooldown) {
                	cavaleiro.dash = true;
                	cavaleiro.lastDashTime = currentTime;
                	cavaleiro.canDash = false;
                }
            }
            cavaleiro.dash = false;
            cavaleiro.canDash = true;
        } 
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(personagem == "default") {
        	if (e.getButton() == MouseEvent.BUTTON1 && player.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - player.lastShootTime >= tempoMinimoEntreTiros) {
                    player.shoot = true;
                    player.lastShootTime = currentTime;
                    player.canShoot = false;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3 && player.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - player.lastDashTime >= dashcooldown) {
                    player.dash = true;
                    player.lastDashTime = currentTime;
                    player.canDash = false;
                }
            }
        } else if(personagem == "cowboy") {
        	if (e.getButton() == MouseEvent.BUTTON1 && cowboy.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - cowboy.lastShootTime >= tempoMinimoEntreTiros) {
                	cowboy.shoot = true;
                	cowboy.lastShootTime = currentTime;
                	cowboy.canShoot = false;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3 && cowboy.canDash && !cowboy.dash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - cowboy.lastDashTime >= dashcooldown) {
                	cowboy.dash = true;
                	cowboy.lastDashTime = currentTime;
                	cowboy.canDash = false;
                }
            }
        } else if(personagem == "mago") {
        	if (e.getButton() == MouseEvent.BUTTON1 && mago.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - mago.lastShootTime >= tempoMinimoEntreTiros) {
                	mago.shoot = true;
                	mago.lastShootTime = currentTime;
                	mago.canShoot = false;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3 && mago.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - mago.lastDashTime >= dashcooldown) {
                	mago.dash = true;
                	mago.lastDashTime = currentTime;
                	mago.canDash = false;
                }
            }
        } else if(personagem == "ninja") {
        	if (e.getButton() == MouseEvent.BUTTON1 && ninja.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - ninja.lastShootTime >= tempoMinimoEntreTiros) {
                	ninja.shoot = true;
                	ninja.lastShootTime = currentTime;
                	ninja.canShoot = false;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3 && ninja.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - ninja.lastDashTime >= dashcooldown) {
                	ninja.dash = true;
                	ninja.lastDashTime = currentTime;
                	ninja.canDash = false;
                }
            }
        } else if(personagem == "pirata") {
        	if (e.getButton() == MouseEvent.BUTTON1 && pirata.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - pirata.lastShootTime >= tempoMinimoEntreTiros) {
                	pirata.shoot = true;
                	pirata.lastShootTime = currentTime;
                	pirata.canShoot = false;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3 && pirata.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - pirata.lastDashTime >= dashcooldown) {
                	pirata.dash = true;
                	pirata.lastDashTime = currentTime;
                	pirata.canDash = false;
                }
            }
        } else if(personagem == "cavaleiro") {
        	if (e.getButton() == MouseEvent.BUTTON1 && cavaleiro.canShoot) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - cavaleiro.lastShootTime >= tempoMinimoEntreTiros) {
                	cavaleiro.shoot = true;
                	cavaleiro.lastShootTime = currentTime;
                	cavaleiro.canShoot = false;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3 && cavaleiro.canDash) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - cavaleiro.lastDashTime >= dashcooldown) {
                	cavaleiro.dash = true;
                	cavaleiro.lastDashTime = currentTime;
                	cavaleiro.canDash = false;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if(personagem == "default") {
    		if (e.getButton() == MouseEvent.BUTTON1 && player.canShoot) {
        		player.shoot = false;
                player.canShoot = true;
        	}
        	if (e.getButton() == MouseEvent.BUTTON3 && player.canDash) {
        		player.dash = false;
                player.canDash = true;
        	}
    	} else if(personagem == "cowboy") {
    		if (e.getButton() == MouseEvent.BUTTON1 && cowboy.canShoot) {
        		cowboy.shoot = false;
                cowboy.canShoot = true;
        	}
        	if (e.getButton() == MouseEvent.BUTTON3 && cowboy.canDash) {
        		cowboy.dash = false;
                cowboy.canDash = true;
        	}
    	} else if(personagem == "mago") {
    		if (e.getButton() == MouseEvent.BUTTON1 && mago.canShoot) {
        		mago.shoot = false;
                mago.canShoot = true;
        	}
        	if (e.getButton() == MouseEvent.BUTTON3 && mago.canDash) {
        		mago.dash = false;
        		mago.canDash = true;
        	}
    	} else if(personagem == "ninja") {
    		if (e.getButton() == MouseEvent.BUTTON1 && ninja.canShoot) {
    			ninja.shoot = false;
    			ninja.canShoot = true;
        	}
        	if (e.getButton() == MouseEvent.BUTTON3 && ninja.canDash) {
        		ninja.dash = false;
        		ninja.canDash = true;
        	}
    	} else if(personagem == "pirata") {
    		if (e.getButton() == MouseEvent.BUTTON1 && pirata.canShoot) {
    			pirata.shoot = false;
    			pirata.canShoot = true;
        	}
        	if (e.getButton() == MouseEvent.BUTTON3 && pirata.canDash) {
        		pirata.dash = false;
        		pirata.canDash = true;
        	}
    	} else if(personagem == "cavaleiro") {
    		if (e.getButton() == MouseEvent.BUTTON1 && cavaleiro.canShoot) {
    			cavaleiro.shoot = false;
    			cavaleiro.canShoot = true;
        	}
        	if (e.getButton() == MouseEvent.BUTTON3 && cavaleiro.canDash) {
        		cavaleiro.dash = false;
        		cavaleiro.canDash = true;
        	}
    	}
    }
    
    public void gerarInimigoAleatorio(int x, int y) {
        Random rand = new Random();
        
        int telax = screenWidth/2;
        int telay = screenHeight/2;

        // Determine aleatoriamente se o inimigo estará fora na horizontal ou vertical
        if (rand.nextBoolean()) {
            // Posicionamento horizontal fora da tela
            startX = rand.nextBoolean() ? x - telax - 32 : x + telax + 32;
            startY = rand.nextInt(432) + y - telay  -32;
        } else {
            // Posicionamento vertical fora da tela
            startX = rand.nextInt(768) + x - telax - 32;
            startY = rand.nextBoolean() ? y - telay  -32 : y + telay + 32;
        }
        
        // Crie o inimigo com as coordenadas calculadas
        Inimigo novoInimigo = new Inimigo(startX, startY, danoinimigo);
        novoInimigo.alive = true;
        inimigos.add(novoInimigo);
    }



    
    public void gerarMorcegoAleatorio(int x, int y) {
    	Random rand = new Random();
        
        int telax = screenWidth/2;
        int telay = screenHeight/2;

        // Determine aleatoriamente se o inimigo estará fora na horizontal ou vertical
        if (rand.nextBoolean()) {
            // Posicionamento horizontal fora da tela
            startX = rand.nextBoolean() ? x - telax - 32 : x + telax + 32;
            startY = rand.nextInt(432) + y - telay  -32;
        } else {
            // Posicionamento vertical fora da tela
            startX = rand.nextInt(768) + x - telax - 32;
            startY = rand.nextBoolean() ? y - telay  -32 : y + telay + 32;
        }

        Morcego novoMorcego = new Morcego(startX, startY, danomorcego);
        novoMorcego.alive = true;
        morcegos.add(novoMorcego);
    }
    
    public void gerarDragaoAleatorio(int x, int y) {
    	Random rand = new Random();
        
        int telax = screenWidth/2;
        int telay = screenHeight/2;

        // Determine aleatoriamente se o inimigo estará fora na horizontal ou vertical
        if (rand.nextBoolean()) {
            // Posicionamento horizontal fora da tela
            startX = rand.nextBoolean() ? x - telax - 32 : x + telax + 32;
            startY = rand.nextInt(432) + y - telay  -32;
        } else {
            // Posicionamento vertical fora da tela
            startX = rand.nextInt(768) + x - telax - 32;
            startY = rand.nextBoolean() ? y - telay  -32 : y + telay + 32;
        }

        Dragao novoDragao = new Dragao(startX, startY, danodragao);
        novoDragao.alive = true;
        dragoes.add(novoDragao);
    }
    
    public static void spawnBoss3() {
    	if(personagem == "default") {
    		boss3 = new Boss3(player.x - 45, player.y + 400, danoboss);
    	} else if(personagem == "cowboy") {
    		boss3 = new Boss3(cowboy.x - 45, cowboy.y + 400, danoboss);
        } else if(personagem == "mago") {
        	boss3 = new Boss3(mago.x - 45, mago.y + 400, danoboss);
        } else if(personagem == "ninja") {
        	boss3 = new Boss3(ninja.x - 45, ninja.y + 400, danoboss);
        } else if(personagem == "pirata") {
        	boss3 = new Boss3(pirata.x - 45, pirata.y + 400, danoboss);
        } else if(personagem == "cavaleiro") {
        	boss3 = new Boss3(cavaleiro.x - 45, cavaleiro.y + 400, danoboss);
        }
    }
    
    public static void spawnBoss2() {
    	if(personagem == "default") {
    		boss2 = new Boss2(player.x - 45, player.y + 400, danoboss);
    	} else if(personagem == "cowboy") {
    		boss2 = new Boss2(cowboy.x - 45, cowboy.y + 400, danoboss);
        } else if(personagem == "mago") {
        	boss2 = new Boss2(mago.x - 45, mago.y + 400, danoboss);
        } else if(personagem == "ninja") {
        	boss2 = new Boss2(ninja.x - 45, ninja.y + 400, danoboss);
        } else if(personagem == "pirata") {
        	boss2 = new Boss2(pirata.x - 45, pirata.y + 400, danoboss);
        } else if(personagem == "cavaleiro") {
        	boss2 = new Boss2(cavaleiro.x - 45, cavaleiro.y + 400, danoboss);
        }
    }

    public static void spawnBoss1() {
    	if(personagem == "default") {
    		boss1 = new Boss1(player.x - 45, player.y + 400, danoboss);
    	} else if(personagem == "cowboy") {
    		boss1 = new Boss1(cowboy.x - 45, cowboy.y + 400, danoboss);
        } else if(personagem == "mago") {
        	boss1 = new Boss1(mago.x - 45, mago.y + 400, danoboss);
        } else if(personagem == "ninja") {
        	boss1 = new Boss1(ninja.x - 45, ninja.y + 400, danoboss);
        } else if(personagem == "pirata") {
        	boss1 = new Boss1(pirata.x - 45, pirata.y + 400, danoboss);
        } else if(personagem == "cavaleiro") {
        	boss1 = new Boss1(cavaleiro.x - 45, cavaleiro.y + 400, danoboss);
        }
    }

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}}
