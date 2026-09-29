import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Relic {
    private Rectangle hitbox;
    private BufferedImage sprite;
    private boolean collected=false;

    public Relic(int x,int y){
        hitbox=new Rectangle(x,y,70,80);
        sprite=AssetLoader.load("relic.png");
    }

    public void draw(Graphics g,int cameraX){
        if(collected) return;
        if(sprite!=null){
            g.drawImage(sprite,
                    hitbox.x-cameraX-35,
                    hitbox.y-42,
                    140,
                    130,
                    null);
        }
    }
    public void collect(){
        collected=true;
    }
    public boolean isCollected(){
        return collected;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
}