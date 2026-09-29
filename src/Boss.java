import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss {
    private static final int MAX_HEALTH = 6;
    private Rectangle hitbox;
    private BufferedImage sprite;
    private int health = MAX_HEALTH;
    private boolean alive = true;

    public Boss(int x, int y) {
        hitbox = new Rectangle(x, y, 92, 88);
        sprite = AssetLoader.load("boss.png");
    }

    public void draw(Graphics g, int cameraX) {
        if (!alive) return;
        if (sprite != null) {
            g.drawImage(sprite,
                    hitbox.x - cameraX - 45,
                    hitbox.y - 55,
                    180,
                    160,
                    null);
        }
    }

    public void takeDamage() {
        if (!alive) return;
        health--;
        if (health <= 0) alive = false;
    }

    public Rectangle getHitbox() {
        return hitbox; 
    }
    public int getHealth() {
        return health; 
    }
    public int getMaxHealth() { 
        return MAX_HEALTH;
    }
    public boolean isAlive() { 
        return alive; 
    }
}
