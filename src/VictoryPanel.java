import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VictoryPanel extends JPanel {
    private Main main; private BufferedImage background,relic;
    public VictoryPanel(Main main){
        this.main=main; setPreferredSize(new Dimension(1000,600)); setLayout(null);
        background=AssetLoader.loadBackground("templo.png"); relic=AssetLoader.load("relic.png");
        JButton restart=createButton("JOGAR NOVAMENTE"); restart.setBounds(350,470,300,48); restart.addActionListener(e->main.showMap()); add(restart);
        JButton exit=createButton("SAIR"); exit.setBounds(350,525,300,42); exit.addActionListener(e->System.exit(0)); add(exit);
    }
    private JButton createButton(String t){ JButton b=new JButton(t); b.setFont(new Font("Serif",Font.BOLD,18)); b.setBackground(new Color(185,145,65)); b.setForeground(new Color(20,18,15)); b.setFocusPainted(false); b.setBorder(BorderFactory.createLineBorder(new Color(245,210,120),1)); return b; }
    @Override protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(background!=null){g.drawImage(background,0,0,getWidth(),getHeight(),null);g.setColor(new Color(3,5,10,185));g.fillRect(0,0,getWidth(),getHeight());}
        else {g.setColor(new Color(10,8,15));g.fillRect(0,0,getWidth(),getHeight());}
        Graphics2D g2=(Graphics2D)g; g2.setColor(new Color(10,12,18,210));g2.fillRoundRect(190,55,620,400,24,24);g2.setColor(new Color(215,178,92));g2.drawRoundRect(190,55,620,400,24,24);
        g2.setColor(new Color(245,210,125));g2.setFont(new Font("Serif",Font.BOLD,50));drawCentered(g2,"A ÚLTIMA RELÍQUIA",125);
        g2.setColor(Color.WHITE);g2.setFont(new Font("Serif",Font.BOLD,28));drawCentered(g2,"Você encontrou o artefato perdido.",190);
        g2.setFont(new Font("Serif",Font.PLAIN,21));drawCentered(g2,"A antiga lenda finalmente chegou ao fim.",230);
        if(relic!=null)g2.drawImage(relic,445,260,110,110,null);
        g2.setColor(new Color(245,210,125));g2.setFont(new Font("Serif",Font.ITALIC,20));drawCentered(g2,"A Última Relíquia agora pertence a você.",405);
    }
    private void drawCentered(Graphics g,String t,int y){int x=(getWidth()-g.getFontMetrics().stringWidth(t))/2;g.drawString(t,x,y);}
}
