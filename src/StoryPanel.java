import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StoryPanel extends JPanel {
    private JButton backButton;
    private BufferedImage background;

    public StoryPanel(Main window) {
        setPreferredSize(new Dimension(1000, 600));
        setLayout(null);
        background = AssetLoader.loadBackground("ruinas.png");
        backButton = createButton("VOLTAR");
        backButton.setBounds(410, 525, 180, 46);
        add(backButton);
        backButton.addActionListener(e -> window.showMenu());
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Serif", Font.BOLD, 19));
        b.setBackground(new Color(185, 145, 65));
        b.setForeground(new Color(20, 18, 15));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(245, 210, 120), 1));
        return b;
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            g.setColor(new Color(4, 7, 14, 175)); g.fillRect(0,0,getWidth(),getHeight());
        } else { g.setColor(new Color(12,16,25)); g.fillRect(0,0,getWidth(),getHeight()); }

        Graphics2D g2=(Graphics2D)g;
        g2.setColor(new Color(12,15,22,205));
        g2.fillRoundRect(185,55,630,455,22,22);
        g2.setColor(new Color(215,178,92)); g2.drawRoundRect(185,55,630,455,22,22);
        g2.setColor(new Color(245,210,125));
        g2.setFont(new Font("Serif",Font.BOLD,48)); drawCentered(g2,"A HISTÓRIA",112);
        g2.setColor(Color.WHITE); g2.setFont(new Font("Serif",Font.PLAIN,21));
        drawCentered(g2,"Há séculos, um antigo reino desapareceu sem deixar vestígios.",175);
        drawCentered(g2,"Dizem que seus tesouros foram perdidos em uma ilha esquecida.",215);
        drawCentered(g2,"Entre as ruínas desse reino existe uma relíquia lendária.",255);
        drawCentered(g2,"Depois de encontrar um antigo mapa, você finalmente chegou à ilha.",315);
        drawCentered(g2,"Agora, atravesse a costa e as ruínas, enfrente o Guardião",365);
        drawCentered(g2,"e recupere A Última Relíquia.",400);
        g2.setColor(new Color(120,95,50)); g2.fillRect(260,430,480,1);
    }
    private void drawCentered(Graphics g,String text,int y){ int x=(getWidth()-g.getFontMetrics().stringWidth(text))/2; g.drawString(text,x,y); }
}
