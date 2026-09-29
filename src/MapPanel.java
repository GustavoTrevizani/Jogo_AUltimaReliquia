import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapPanel extends JPanel {
    private final Main window;
    private final BufferedImage background;
    private JButton startButton;

    public MapPanel(Main window) {
        this.window=window;
        setPreferredSize(new Dimension(1000,600));
        setLayout(null);
        background=AssetLoader.loadBackground("ruinas.png");

        startButton=new JButton("COMEÇAR AVENTURA");
        startButton.setBounds(360,515,280,48);
        startButton.setFont(new Font("Serif",Font.BOLD,19));
        startButton.setBackground(new Color(185,145,65));
        startButton.setForeground(new Color(20,18,15));
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createLineBorder(new Color(245,210,120),1));
        add(startButton);
        startButton.addActionListener(e -> {
            window.startGame();
        });
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background!=null){
            g.drawImage(background,0,0,getWidth(),getHeight(),null);
            g.setColor(new Color(4,7,14,195)); g.fillRect(0,0,getWidth(),getHeight());
        } else { g.setColor(new Color(12,18,28)); g.fillRect(0,0,getWidth(),getHeight()); }

        Graphics2D g2=(Graphics2D)g.create();
        g2.setColor(new Color(8,12,20,220)); g2.fillRoundRect(90,45,820,455,28,28);
        g2.setColor(new Color(215,178,92)); g2.drawRoundRect(90,45,820,455,28,28);

        g2.setColor(new Color(245,210,125));
        g2.setFont(new Font("Serif",Font.BOLD,46));
        drawCentered(g2,"A ILHA PERDIDA",105);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif",Font.ITALIC,18));
        drawCentered(g2,"Três regiões. Um único destino.",135);

        // Rota
        g2.setStroke(new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(215,178,92));
        g2.drawLine(230,300,500,260);
        g2.drawLine(500,260,770,300);

        drawNode(g2,230,300,"COSTA","FASE 1",new Color(60,145,180));
        drawNode(g2,500,260,"RUÍNAS","FASE 2",new Color(190,120,55));
        drawNode(g2,770,300,"TEMPLO","FASE 3",new Color(155,55,55));

        g2.setColor(new Color(245,210,125));
        g2.setFont(new Font("Serif",Font.PLAIN,17));
        drawCentered(g2,"Colete as chaves, atravesse os portais e recupere a Relíquia.",440);
        g2.dispose();
    }

    private void drawNode(Graphics2D g2,int x,int y,String title,String phase,Color color){
        g2.setColor(new Color(10,12,18,240));
        g2.fillOval(x-42,y-42,84,84);
        g2.setColor(color);
        g2.fillOval(x-28,y-28,56,56);
        g2.setColor(new Color(255,230,150));
        g2.drawOval(x-42,y-42,84,84);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif",Font.BOLD,18));
        int tw=g2.getFontMetrics().stringWidth(title);
        g2.drawString(title,x-tw/2,y+68);
        g2.setFont(new Font("Serif",Font.PLAIN,15));
        tw=g2.getFontMetrics().stringWidth(phase);
        g2.drawString(phase,x-tw/2,y+89);
    }
    private void drawCentered(Graphics2D g,String t,int y){int x=(getWidth()-g.getFontMetrics().stringWidth(t))/2;g.drawString(t,x,y);}
}
