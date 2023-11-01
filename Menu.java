package zelda1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Menu {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Paradise in Hell");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        
        if (gd.isFullScreenSupported()) {
            frame.setUndecorated(true);
            gd.setFullScreenWindow(frame);
        } else {
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage;

            {
                setLayout(null);

                addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        int width = getWidth();
                        int height = getHeight();
                        ImageIcon originalImage = new ImageIcon("C:\\Users\\vsjun\\eclipse-workspace\\zelda1\\res\\Foto_BG.jpg");
                        backgroundImage = originalImage.getImage().getScaledInstance(-1, -1, Image.SCALE_SMOOTH);
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }
        };

        frame.add(backgroundPanel);

        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int imageHeight = backgroundPanel.getPreferredSize().height;
        int verticalPosition = (screenHeight - imageHeight) / 2;

        backgroundPanel.setPreferredSize(new Dimension(0, imageHeight));
        backgroundPanel.setBounds(0, verticalPosition, backgroundPanel.getPreferredSize().width, imageHeight);

        RoundedButton startButton = new RoundedButton("START");
        RoundedButton charactersButton = new RoundedButton("CHARACTERS");

        startButton.setBounds(600, verticalPosition + 160, 100, 50);
        charactersButton.setBounds(800, verticalPosition + 160, 190, 50);

        startButton.setFont(new Font("Comic Sans", Font.BOLD, 20));
        charactersButton.setFont(new Font("Comic Sans", Font.BOLD, 20));
        startButton.setBackground(new Color(75, 219, 110));
        charactersButton.setBackground(new Color(75, 219, 110));
        startButton.setForeground(Color.WHITE);
        charactersButton.setForeground(Color.WHITE);

        backgroundPanel.add(startButton);
        backgroundPanel.add(charactersButton);
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.main(null);
            }
        });
        
        charactersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Tela de personagens");
            }
        });

        ImageIcon title = new ImageIcon("C:\\Users\\vsjun\\eclipse-workspace\\zelda1\\res\\titulo.jpg");
        int imageWidth = 200; // Largura desejada
        int imageHeight2 = (imageWidth * title.getIconHeight()) / title.getIconWidth();
        Image image = title.getImage().getScaledInstance(imageWidth, imageHeight2, Image.SCALE_SMOOTH);

        int centerX = (frame.getWidth() - imageWidth) / 2 + 760;
        int centerY = (frame.getHeight() - imageHeight2) / 2 + 300;

        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBounds(centerX, centerY, imageWidth, imageHeight2);
        backgroundPanel.add(imageLabel);

        frame.setVisible(true);

        try {
            File audioFile = new File("C:\\Users\\vsjun\\eclipse-workspace\\zelda1\\res\\musica_menu.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class RoundedButton extends JButton {
    public RoundedButton(String label) {
        super(label);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
    }
}