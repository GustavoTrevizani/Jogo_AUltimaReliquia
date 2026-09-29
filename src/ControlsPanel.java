import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ControlsPanel extends JPanel {
    private final Main window;
    private BufferedImage background, player;
    private JButton backButton;

    public ControlsPanel(Main window) {
        this.window = window;
        setPreferredSize(new Dimension(1000, 600));
        setLayout(null);
        background = AssetLoader.loadBackground("templo.png");
        player = AssetLoader.load("player.png");

        backButton = createButton("VOLTAR");
        backButton.setBounds(410, 530, 180, 44);
        add(backButton);
        backButton.addActionListener(e -> window.showMenu());
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Serif", Font.BOLD, 19));
        b.setBackground(new Color(185,145,65));
        b.setForeground(new Color(20,18,15));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(245,210,120),1));
        return b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setColor(new Color(8,12,20,225));
        g2.fillRoundRect(95,35,810,480,26,26);
        g2.setColor(new Color(215,178,92));
        g2.drawRoundRect(95,35,810,480,26,26);

        g2.setColor(new Color(245,210,125));
        g2.setFont(new Font("Serif", Font.BOLD, 46));
        drawCentered(g2, "COMO JOGAR", 100);

        // Coluna esquerda
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 24));
        g2.drawString("CONTROLES", 175, 155);

        drawKey(g2, "A / ←", 160, 205, 105);
        drawKey(g2, "D / →", 160, 255, 105);
        drawKey(g2, "W / ESPAÇO", 160, 305, 155);
        drawKey(g2, "X / J", 160, 355, 105);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.PLAIN, 19));
        g2.drawString("Mover para esquerda", 285, 205);
        g2.drawString("Mover para direita", 285, 255);
        g2.drawString("Pular", 335, 305);
        g2.drawString("Atacar", 285, 355);

        // Divisor
        g2.setColor(new Color(215,178,92));
        g2.drawLine(505,145,505,450);

        // Coluna direita
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 24));
        g2.drawString("OBJETIVO", 590, 155);

        g2.setFont(new Font("Serif", Font.PLAIN, 18));
        g2.drawString("1. Colete as 3 chaves de cada fase.", 555, 205);
        g2.drawString("2. Encontre o portal para avançar.", 555, 245);
        g2.drawString("3. Derrote os inimigos pelo caminho.", 555, 285);
        g2.drawString("4. Na fase final, enfrente o Guardião.", 555, 325);
        g2.drawString("5. Pegue a Relíquia para vencer.", 555, 365);

        g2.setColor(new Color(245,210,125));
        g2.setFont(new Font("Serif", Font.BOLD, 18));
        g2.drawString("DICA", 555, 415);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.PLAIN, 17));
        g2.drawString("Você pode correr e pular ao mesmo tempo.", 600, 415);

        // Personagem decorativo sem sombra
        if (player != null) {
            g2.drawImage(player, 735, 420, 90, 88, null);
        }
        g2.dispose();
    }

    private void drawBackground(Graphics g) {
        if (background != null) {
            g.drawImage(background,0,0,getWidth(),getHeight(),null);
            g.setColor(new Color(3,5,10,205));
            g.fillRect(0,0,getWidth(),getHeight());
        } else {
            g.setColor(new Color(10,14,22));
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }

    private void drawKey(Graphics2D g2, String text, int x, int y, int width) {
        g2.setColor(new Color(210,175,90));
        g2.fillRoundRect(x,y-26,width,38,9,9);
        g2.setColor(new Color(20,18,15));
        g2.setFont(new Font("Serif", Font.BOLD, 17));
        g2.drawString(text,x+12,y);
    }

    private void drawCentered(Graphics2D g,String text,int y){
        int x=(getWidth()-g.getFontMetrics().stringWidth(text))/2;
        g.drawString(text,x,y);
    }
}