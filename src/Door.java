import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Door {
    private Rectangle hitbox;
    private BufferedImage sprite;
    private boolean open = false;

    public Door(int x, int y) {
        hitbox = new Rectangle(x, y, 60, 100);
        sprite = AssetLoader.load("door.png");
    }

    public void draw(Graphics g, int cameraX) {
        int drawWidth = 150;
        int drawHeight = 165;
        int drawX = hitbox.x - cameraX - 45;
        int drawY = 520 - drawHeight;
        if (sprite != null) {
            g.drawImage(sprite, drawX, drawY, drawWidth, drawHeight, null);
        }
        if (open) {
            g.setColor(new Color(4,5,8,190));
            g.fillRect(hitbox.x-cameraX+2,425,56,92);
        }
    }

    public void open(){ 
        open=true;
    }
    public boolean isOpen(){ 
        return open; 
    }
    public Rectangle getHitbox(){ 
        return hitbox; 
    }
}
