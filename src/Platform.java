import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Platform {
    private Rectangle hitbox;
    private BufferedImage sprite;

    public Platform(int x,int y,int width,int height){
        hitbox=new Rectangle(x,y,width,height);
        sprite=AssetLoader.load("platform.png");
    }

    public void draw(Graphics g,int cameraX){
        if(sprite!=null){
            g.drawImage(sprite,
                    hitbox.x-cameraX,
                    hitbox.y-12,
                    hitbox.width,
                    Math.max(48,hitbox.height+18),
                    null);
        }
    }
    
    public Rectangle getHitbox(){
        return hitbox;
    }
}
