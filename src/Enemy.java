import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy {
    private Rectangle hitbox;
    private BufferedImage sprite;
    private int health = 3;
    private boolean alive = true;

    public Enemy(int x, int y) {
        hitbox = new Rectangle(x, y, 55, 64);
        sprite = AssetLoader.load("enemy.png");
    }

    public void update() { 
        
    }

    public void draw(Graphics g, int cameraX) {
        if (!alive) return;
        if (sprite != null) {
            g.drawImage(sprite,
                    hitbox.x - cameraX - 18,
                    hitbox.y - 42,
                    92,
                    106,
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
    public boolean isAlive() {
        return alive; 
    }
}
