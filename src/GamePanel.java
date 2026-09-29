import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final int WORLD_WIDTH = 3000;
    private static final int GROUND_Y = 520;

    private int cameraX = 0;
    private int currentPhase = 1;

    private Timer timer;
    private Player player;
    private Platform[] platforms;
    private List<Enemy> enemies;
    private List<Key> keys;
    private Boss boss;
    private Door door;
    private Relic relic;
    private BufferedImage background;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(15, 18, 24));
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        player = new Player(90, GROUND_Y - 64);
        setupPhase();
        loadBackground();

        addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e) { 
                player.keyPressed(e); 
            }

            @Override 
            public void keyReleased(KeyEvent e) {
                 player.keyReleased(e); 
            }
        });
    }

    public void startGame() {
        timer = new Timer(16, e -> {
            updateGame();
            repaint();
        });
        timer.start();
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }

    private void updateGame() {
        player.update(GROUND_Y, platforms);

        checkCombat();
        checkBossCombat();
        checkPlayerDamage();
        checkBossDamage();
        checkKeyCollection();
        checkDoor();
        checkRelicCollection();

        cameraX = player.getX() - WIDTH / 2;
        if (cameraX < 0) cameraX = 0;
        if (cameraX > WORLD_WIDTH - WIDTH) cameraX = WORLD_WIDTH - WIDTH;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);

        for (Platform platform : platforms) platform.draw(g, cameraX);
        for (Key key : keys) key.draw(g, cameraX);
        for (Enemy enemy : enemies) enemy.draw(g, cameraX);
        if (door != null) door.draw(g, cameraX);
        if (boss != null) boss.draw(g, cameraX);
        if (relic != null) relic.draw(g, cameraX);
        player.draw(g, cameraX);

        drawHUD(g);

        if (!player.isAlive()) drawDefeat(g);
    }

    private void checkCombat() {
        if (!player.isAlive()) return;
        for (Enemy enemy : enemies) {
            if (!player.hasAttackHit()
                    && enemy.isAlive()
                    && player.getAttackHitbox().intersects(enemy.getHitbox())) {
                enemy.takeDamage();
                player.registerAttackHit();
            }
        }
    }

    private void checkBossCombat() {
        if (boss == null || !boss.isAlive()) return;
        if (!player.hasAttackHit()
                && player.getAttackHitbox().intersects(boss.getHitbox())) {
            boss.takeDamage();
            player.registerAttackHit();
        }
    }

    private void checkPlayerDamage() {
        if (!player.isAlive()) return;
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && player.getHitbox().intersects(enemy.getHitbox())) {
                player.takeDamage();
            }
        }
    }

    private void checkBossDamage() {
        if (boss == null || !boss.isAlive() || !player.isAlive()) return;
        if (player.getHitbox().intersects(boss.getHitbox())) player.takeDamage();
    }

    private void checkKeyCollection() {
        for (Key key : keys) {
            if (!key.isCollected() && player.getHitbox().intersects(key.getHitbox())) key.collect();
        }
    }

    private int collectedKeys() {
        int total = 0;
        for (Key key : keys) if (key.isCollected()) total++;
        return total;
    }

    private void checkDoor() {
        if (door == null || door.isOpen()) return;
        if (player.getHitbox().intersects(door.getHitbox()) && collectedKeys() == 3) {
            door.open();
            if (currentPhase == 1) {
                currentPhase = 2;
                setupPhase();
                loadBackground();
                player.setPosition(90, GROUND_Y - 64);
                cameraX = 0;
            } else if (currentPhase == 2) {
                currentPhase = 3;
                setupPhase();
                loadBackground();
                player.setPosition(90, GROUND_Y - 64);
                cameraX = 0;
            }
        }
    }

    private void checkRelicCollection() {
        if (relic == null || relic.isCollected() || boss == null || boss.isAlive()) return;
        if (player.getHitbox().intersects(relic.getHitbox())) {
            relic.collect();
            timer.stop();
            Main main = (Main) SwingUtilities.getWindowAncestor(this);
            main.showVictory();
        }
    }

    private void setupPhase() {
        enemies = new ArrayList<>();
        keys = new ArrayList<>();
        boss = null;
        door = null;
        relic = null;

        if (currentPhase == 1) {
            platforms = new Platform[] {
                    new Platform(180, 430, 160, 20),
                    new Platform(450, 370, 160, 20),
                    new Platform(720, 430, 160, 20),
                    new Platform(990, 360, 170, 20),
                    new Platform(1280, 300, 170, 20),
                    new Platform(1570, 380, 170, 20),
                    new Platform(1860, 320, 170, 20),
                    new Platform(2150, 400, 170, 20),
                    new Platform(2440, 330, 170, 20)
            };

            keys.add(new Key(250, 390));
            keys.add(new Key(1040, 320));
            keys.add(new Key(1330, 260));

            enemies.add(new Enemy(620, GROUND_Y - 64));
            enemies.add(new Enemy(1750, GROUND_Y - 64));
            enemies.add(new Enemy(2350, GROUND_Y - 64));

            // Portal isolado no chão, sem plataforma colada nele.
            door = new Door(2820, GROUND_Y - 100);
        }
        else if (currentPhase == 2) {
            platforms = new Platform[] {
                    new Platform(170, 420, 170, 20),
                    new Platform(440, 350, 160, 20),
                    new Platform(710, 420, 170, 20),
                    new Platform(980, 340, 170, 20),
                    new Platform(1250, 280, 170, 20),
                    new Platform(1520, 360, 170, 20),
                    new Platform(1790, 300, 170, 20),
                    new Platform(2060, 380, 170, 20),
                    new Platform(2330, 320, 170, 20),
                    new Platform(2520, 420, 140, 20)
            };

            keys.add(new Key(235, 380));
            keys.add(new Key(1025, 300));
            keys.add(new Key(1295, 240));

            enemies.add(new Enemy(620, GROUND_Y - 64));
            enemies.add(new Enemy(1450, GROUND_Y - 64));
            enemies.add(new Enemy(2140, GROUND_Y - 64));

            door = new Door(2820, GROUND_Y - 100);
        }
        else {
            // Área final mais aberta para o combate do Guardião.
            platforms = new Platform[] {
                    new Platform(180, 420, 170, 20),
                    new Platform(450, 350, 160, 20),
                    new Platform(720, 420, 170, 20),
                    new Platform(990, 350, 170, 20),
                    new Platform(1260, 420, 170, 20),
                    new Platform(1530, 350, 170, 20),
                    new Platform(1800, 420, 170, 20)
            };

            enemies.add(new Enemy(650, GROUND_Y - 64));
            enemies.add(new Enemy(1150, GROUND_Y - 64));
            enemies.add(new Enemy(1800, GROUND_Y - 64));

            // Arena do Guardião sem plataforma sobre a cabeça.
            boss = new Boss(2380, GROUND_Y - 88);
            relic = new Relic(2770, GROUND_Y - 100);
        }
    }

    private void drawHUD(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(8, 12, 18, 205));
        g2.fillRoundRect(20, 18, 350, 82, 15, 15);
        g2.setColor(new Color(215, 178, 92));
        g2.drawRoundRect(20, 18, 350, 82, 15, 15);

        g2.setFont(new Font("Serif", Font.BOLD, 27));
        g2.setColor(new Color(235, 70, 70));
        String hearts = "";
        for (int i = 0; i < player.getHealth(); i++) hearts += "♥ ";
        g2.drawString(hearts, 35, 49);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        if (currentPhase < 3) {
            g2.drawString("CHAVES: " + collectedKeys() + " / 3", 35, 78);
        } else {
            g2.drawString("OBJETIVO: DERROTE O GUARDIÃO", 35, 78);
        }

        String phaseName = currentPhase == 1 ? "COSTA" : currentPhase == 2 ? "RUÍNAS" : "TEMPLO";
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("FASE " + currentPhase + " - " + phaseName, 750, 42);

        if (boss != null && boss.isAlive()) {
            int barWidth = 340;
            int x = WIDTH / 2 - barWidth / 2;
            int y = 28;
            g2.setColor(new Color(10,10,12,220));
            g2.fillRoundRect(x-4,y-4,barWidth+8,26,10,10);
            g2.setColor(new Color(70,20,20));
            g2.fillRect(x,y,barWidth,18);
            g2.setColor(new Color(210,45,45));
            g2.fillRect(x,y,barWidth * boss.getHealth() / boss.getMaxHealth(),18);
            g2.setColor(Color.WHITE);
            g2.drawRect(x,y,barWidth,18);
            g2.setFont(new Font("Arial",Font.BOLD,14));
            g2.drawString("GUARDIÃO — " + boss.getHealth() + "/" + boss.getMaxHealth(),x+100,y-6);
        }
        g2.dispose();
    }

    private void drawDefeat(Graphics g) {
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(new Color(245,210,125));
        g.setFont(new Font("Serif",Font.BOLD,48));
        g.drawString("VOCÊ FOI DERROTADO",275,270);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,20));
        g.drawString("Feche e execute o jogo novamente para tentar outra vez.",250,315);
    }

    private void loadBackground() {
        String fileName = currentPhase == 1 ? "costa_floresta.png" : currentPhase == 2 ? "ruinas.png" : "templo.png";
        background = AssetLoader.loadBackground(fileName);
    }

    private void drawBackground(Graphics g) {
        if (background != null) {
            // O mundo e a imagem possuem a mesma largura: não distorce e não corta o topo.
            g.drawImage(background, -cameraX, 0, WORLD_WIDTH, HEIGHT, null);
            // Contraste suave para destacar personagens e plataformas.
            g.setColor(new Color(0, 0, 0, 48));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        } else {
            g.setColor(new Color(18,22,30));
            g.fillRect(0,0,WIDTH,HEIGHT);
        }
    }
}
