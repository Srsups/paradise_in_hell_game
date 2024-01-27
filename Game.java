package info;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    public static World1 world1;
    public static World2 world2;
    public static World3 world3;
    public static BossRoom bossroom;
    public static Pause_screen pausescreen;
    private long tempoMinimoEntreTiros = 500;
    private long dashcooldown = 2000;
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
    
    public Game() {
        new Spritesheet();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        player = new Player(32, 32, screenWidth, screenHeight);
        world1 = new World1();
        world2 = new World2();
        world3 = new World3();
        bossroom = new BossRoom();
        
        enemySpawnTimer = new Timer(enemySpawnInterval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gerarInimigoAleatorio();
            }
        });
        enemySpawnTimer.start();
        
        morcegoSpawnTimer = new Timer(morcegoSpawnInterval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gerarMorcegoAleatorio();
            }
        });
        morcegoSpawnTimer.start();
        
        dragaoSpawnTimer = new Timer(dragaoSpawnInterval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gerarDragaoAleatorio();
            }
        });
        dragaoSpawnTimer.start();
        
        
    }

    public static void tick() {
    	String[] opcoes = {"Retornar ao Menu", "Sair do Jogo"};
    	player.tick(LarguraTela, AlturaTela);
    	
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
        
        if(Player.vida<=0) {
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
    	player.tick(LarguraTela, AlturaTela);
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
        
        if(Player.vida<=0) {
        	paused = true;
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
    
    public static void colisaoinimigo() {
        for (int i = 0; i < inimigos.size(); i++) {
            Inimigo inimigoAtual = inimigos.get(i);
            if (inimigoAtual.intersects(new Rectangle(player.x,player.y,32,32)) && inimigoAtual.alive) {
            	if (Player.vida > 0) {
                    Player.vida -= inimigoAtual.getDano();
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
        player.render(g, player.x, player.y);
           
            //Renderiza a vida no canto superior da tela
        g.setColor(Color.BLACK); // Define a cor do texto
        Font fonte = new Font("Arial", Font.BOLD, 20); // Define a fonte do texto
        g.setFont(fonte);
        g.drawString("Vida: " + Player.getVida(), 10, 20);
        
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
            player.render(g, player.x, player.y);
            
            // Renderiza a vida no canto superior da tela
            g.setColor(Color.BLACK); // Define a cor do texto
            Font fonte = new Font("Arial", Font.BOLD, 20); // Define a fonte do texto
            g.setFont(fonte);

            g.drawString("Vida: " + Player.getVida(), 10, 20);
            
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
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.down = true;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
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
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	// Se o botão esquerdo do mouse foi clicado
        if (e.getButton() == MouseEvent.BUTTON1 && Player.canShoot) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - Player.lastShootTime >= tempoMinimoEntreTiros) {
                player.shoot = true;
                Player.lastShootTime = currentTime;
                Player.canShoot = false;
            }
        }
        player.shoot = false;
        Player.canShoot = true;
        if (e.getButton() == MouseEvent.BUTTON3 && Player.canDash) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - Player.lastDashTime >= dashcooldown) {
                player.dash = true;
                Player.lastDashTime = currentTime;
                Player.canDash = false;
            }
        }
        player.dash = false;
        Player.canDash = true;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && Player.canShoot) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - Player.lastShootTime >= tempoMinimoEntreTiros) {
                player.shoot = true;
                Player.lastShootTime = currentTime;
                Player.canShoot = false;
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3 && Player.canDash) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - Player.lastDashTime >= dashcooldown) {
                player.dash = true;
                Player.lastDashTime = currentTime;
                Player.canDash = false;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if (e.getButton() == MouseEvent.BUTTON1 && Player.canShoot) {
    		player.shoot = false;
            Player.canShoot = true;
    	}
    	if (e.getButton() == MouseEvent.BUTTON3 && Player.canDash) {
    		player.dash = false;
            Player.canDash = true;
    	}
    }
    
    public void gerarInimigoAleatorio() {
        Random rand = new Random();
        
        int telax = screenWidth/2;
        int telay = screenHeight/2;

        // Determine aleatoriamente se o inimigo estará fora na horizontal ou vertical
        if (rand.nextBoolean()) {
            // Posicionamento horizontal fora da tela
            startX = rand.nextBoolean() ? player.x - telax - 32 : player.x + telax + 32;
            startY = rand.nextInt(432) + player.y - telay  -32;
        } else {
            // Posicionamento vertical fora da tela
            startX = rand.nextInt(768) + player.x - telax - 32;
            startY = rand.nextBoolean() ? player.y - telay  -32 : player.y + telay + 32;
        }
        
        // Crie o inimigo com as coordenadas calculadas
        Inimigo novoInimigo = new Inimigo(startX, startY, danoinimigo);
        novoInimigo.alive = true;
        inimigos.add(novoInimigo);
    }



    
    public void gerarMorcegoAleatorio() {
    	Random rand = new Random();
        
        int telax = screenWidth/2;
        int telay = screenHeight/2;

        // Determine aleatoriamente se o inimigo estará fora na horizontal ou vertical
        if (rand.nextBoolean()) {
            // Posicionamento horizontal fora da tela
            startX = rand.nextBoolean() ? player.x - telax - 32 : player.x + telax + 32;
            startY = rand.nextInt(432) + player.y - telay  -32;
        } else {
            // Posicionamento vertical fora da tela
            startX = rand.nextInt(768) + player.x - telax - 32;
            startY = rand.nextBoolean() ? player.y - telay  -32 : player.y + telay + 32;
        }

        Morcego novoMorcego = new Morcego(startX, startY, danomorcego);
        novoMorcego.alive = true;
        morcegos.add(novoMorcego);
    }
    
    public void gerarDragaoAleatorio() {
    	Random rand = new Random();
        
        int telax = screenWidth/2;
        int telay = screenHeight/2;

        // Determine aleatoriamente se o inimigo estará fora na horizontal ou vertical
        if (rand.nextBoolean()) {
            // Posicionamento horizontal fora da tela
            startX = rand.nextBoolean() ? player.x - telax - 32 : player.x + telax + 32;
            startY = rand.nextInt(432) + player.y - telay  -32;
        } else {
            // Posicionamento vertical fora da tela
            startX = rand.nextInt(768) + player.x - telax - 32;
            startY = rand.nextBoolean() ? player.y - telay  -32 : player.y + telay + 32;
        }

        Dragao novoDragao = new Dragao(startX, startY, danodragao);
        novoDragao.alive = true;
        dragoes.add(novoDragao);
    }
    
    public static void spawnBoss3() {
    	boss3 = new Boss3(player.x - 45, player.y - 1200, danoboss);
    }
    
    public static void spawnBoss2() {
    	boss2 = new Boss2(player.x - 45, player.y - 1200, danoboss);
    }

    public static void spawnBoss1() {
    	boss1 = new Boss1(player.x - 45, player.y + 400, danoboss);
    }

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}}
