package zelda1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



import javax.swing.JOptionPane;


public class Game extends Canvas implements Runnable, KeyListener {

	public static boolean paused = false;
    public static Player player;
    public static World1 world1;
    public static World2 world2;
    public static World3 world3;
    private long tempoMinimoEntreTiros = 500;
    public static List<Inimigo> inimigos = new ArrayList<Inimigo>();
    public static List<Morcego> morcegos = new ArrayList<Morcego>();
    public static List<Dragao> dragoes = new ArrayList<Dragao>();
    public static int LarguraTela;
    public static int AlturaTela;
    private JFrame frame;
    private Timer enemySpawnTimer;
    private Timer morcegoSpawnTimer;
    private Timer dragaoSpawnTimer;
    private int enemySpawnInterval = 7000;
    private int morcegoSpawnInterval = 5000;
    private int dragaoSpawnInterval = 10000;
    public static int screenWidth;
    public static int screenHeight;
    public int startX, startY;
    public int danodragao = 100;
    public int danoinimigo = 50;
    public int danomorcego = 25;
    
    public Game() {
        new Spritesheet();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        player = new Player(32, 32, screenWidth, screenHeight);
        world1 = new World1();
        world2 = new World2();
        world3 = new World3();
        
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
        
        if(Player.vida<=0) {
        	paused = true;
            int escolha = JOptionPane.showOptionDialog(null, "O que deseja fazer?", "Você morreu!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, "Botao 3");
            if (escolha == 1) {
                System.exit(0);
            } else if(escolha == 0) {
            	Menu.main(null);
            } else {
                paused = false;
            }
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
        
        if(Dragao.isCooldownReady()) {
        	colisaodragao();
        	for (Dragao dragao :dragoes) {
        		dragao.resetCooldown();
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
        
        world2.render(g);        
        
        // Desenhe o personagem na posição calculada
        player.render(g, player.x, player.y);
        
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

    public static void main(String[] args) {
            Game game = new Game();
            game.frame = new JFrame();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();

            if (gd.isFullScreenSupported()) {
                game.frame.setUndecorated(true);
                game.frame.addKeyListener(game);
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
    	while (true) {
            if (!paused) {
                tick();
                render();
            }
            try {
                Thread.sleep(1000 / 60);
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE && Player.canShoot) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - Player.lastShootTime >= tempoMinimoEntreTiros) {
                player.shoot = true;
                Player.lastShootTime = currentTime;
                Player.canShoot = false;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            paused = true;
            int escolha = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja sair do jogo?",
                    "Sair do Jogo",
                    JOptionPane.YES_NO_OPTION
            );

            if (escolha == JOptionPane.YES_OPTION) {
                Menu.main(null);
            } else {
                paused = false;
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.shoot = false;
            Player.canShoot = true; // Permite que o jogador atire novamente quando a tecla de espaço for liberada
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

        Dragao novoDragao = new Dragao(startX, startY, danomorcego);
        novoDragao.alive = true;
        dragoes.add(novoDragao);
    }}
