import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {
    private final Main window;
    private final BufferedImage background;
    private JButton playButton, storyButton, controlsButton;

    public MenuPanel(Main window) {
        this.window = window;
        setPreferredSize(new Dimension(1000,600));
        setLayout(null);
        background = AssetLoader.loadBackground("costa_floresta.png");

        playButton = createButton("JOGAR");
        storyButton = createButton("HISTÓRIA");
        controlsButton = createButton("COMO JOGAR");

        playButton.setBounds(390,315,220,52);
        storyButton.setBounds(390,380,220,52);
        controlsButton.setBounds(390,445,220,52);
        add(playButton); add(storyButton); add(controlsButton);

        playButton.addActionListener(e -> window.showMap());
        storyButton.addActionListener(e -> window.showStory());
        controlsButton.addActionListener(e -> window.showControls());
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Serif",Font.BOLD,22));
        b.setBackground(new Color(185,145,65));
        b.setForeground(new Color(20,18,15));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(245,210,120),1));
        return b;
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background!=null){
            g.drawImage(background,0,0,getWidth(),getHeight(),null);
            g.setColor(new Color(4,7,14,165)); g.fillRect(0,0,getWidth(),getHeight());
        } else { g.setColor(new Color(10,15,25)); g.fillRect(0,0,getWidth(),getHeight()); }

        Graphics2D g2=(Graphics2D)g.create();
        g2.setColor(new Color(8,12,20,175));
        g2.fillRoundRect(180,65,640,500,28,28);
        g2.setColor(new Color(215,178,92));
        g2.drawRoundRect(180,65,640,500,28,28);

        g2.setColor(new Color(245,210,125));
        g2.setFont(new Font("Serif",Font.BOLD,62));
        drawCentered(g2,"A ÚLTIMA RELÍQUIA",175);
        g2.setFont(new Font("Serif",Font.ITALIC,23));
        drawCentered(g2,"Uma aventura esquecida",215);

        g2.setColor(new Color(245,210,125));
        g2.fillRect(355,235,290,1);
        g2.dispose();
    }
    private void drawCentered(Graphics2D g,String t,int y){int x=(getWidth()-g.getFontMetrics().stringWidth(t))/2;g.drawString(t,x,y);}
}
