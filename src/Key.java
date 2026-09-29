import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Key {

    private Rectangle hitbox;
    private BufferedImage sprite;
    private boolean collected = false;

    public Key(int x, int y) {
        hitbox = new Rectangle(x, y, 38, 38);
        sprite = AssetLoader.load("key.png");
    }

    public void draw(Graphics g, int cameraX) {
        if (collected) return;
        if (sprite != null) {
            g.drawImage(sprite,
                    hitbox.x - cameraX - 10,
                    hitbox.y - 12,
                    58,
                    58,
                    null);
        }
    }

    public void collect() { 
        collected = true; 
    }
    public boolean isCollected() { 
        return collected; 
    }
    public Rectangle getHitbox() { 
        return hitbox; 
    }
}