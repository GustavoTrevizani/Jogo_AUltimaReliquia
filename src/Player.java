import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {

    private Rectangle hitbox;
    private BufferedImage sprite;

    private double x;
    private double y;
    private double velocityY;

    private final double speed = 5.2;
    private final double gravity = 0.75;
    private final double jumpStrength = -14.5;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean onGround;
    private boolean facingRight = true;
    private boolean jumpRequested;

    private int coyoteTimer = 0;
    private final int coyoteFrames = 7;

    private boolean attacking;
    private int attackTimer;
    private final int attackDuration = 12;
    private boolean attackHit;

    private int health = 3;
    private boolean alive = true;
    private int damageCooldown;
    private final int damageCooldownDuration = 45;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x, y, 42, 64);
        sprite = AssetLoader.load("player.png");
    }

    public void update(int groundY, Platform[] platforms) {
        if (!alive) return;

        if (damageCooldown > 0) damageCooldown--;

        // Movimento horizontal independente do salto.
        if (movingLeft && !movingRight) {
            x -= speed;
            facingRight = false;
        } else if (movingRight && !movingLeft) {
            x += speed;
            facingRight = true;
        }

        if (x < 0) x = 0;
        if (x > 2950) x = 2950;

        // Salto: SPACE/W funciona mesmo enquanto A/D está pressionado.
        if (jumpRequested && (onGround || coyoteTimer > 0)) {
            velocityY = jumpStrength;
            onGround = false;
            coyoteTimer = 0;
        }
        jumpRequested = false;

        double previousY = y;
        double previousBottom = y + hitbox.height;

        velocityY += gravity;
        if (velocityY > 14) velocityY = 14;
        y += velocityY;

        onGround = false;

        // Chão contínuo.
        if (y + hitbox.height >= groundY && velocityY >= 0) {
            y = groundY - hitbox.height;
            velocityY = 0;
            onGround = true;
        }

        // Plataformas: só pousa quando estava acima e está caindo.
        if (velocityY >= 0) {
            Rectangle horizontalProbe = new Rectangle(
                    (int)Math.round(x) + 4,
                    (int)Math.round(y),
                    hitbox.width - 8,
                    hitbox.height
            );

            for (Platform platform : platforms) {
                Rectangle p = platform.getHitbox();

                boolean overlapsX = horizontalProbe.x < p.x + p.width
                        && horizontalProbe.x + horizontalProbe.width > p.x;

                boolean crossedTop = previousBottom <= p.y
                        && y + hitbox.height >= p.y;

                if (overlapsX && crossedTop) {
                    y = p.y - hitbox.height;
                    velocityY = 0;
                    onGround = true;
                    break;
                }
            }
        }

        if (!onGround && previousY + hitbox.height <= groundY) {
            coyoteTimer++;
            if (coyoteTimer > coyoteFrames) coyoteTimer = 0;
        } else if (onGround) {
            coyoteTimer = coyoteFrames;
        }

        hitbox.x = (int)Math.round(x);
        hitbox.y = (int)Math.round(y);

        if (attacking) {
            attackTimer--;
            if (attackTimer <= 0) {
                attacking = false;
            }
        }
    }

    public void draw(Graphics g, int cameraX) {
        if (!alive) return;

        int drawWidth = 92;
        int drawHeight = 108;
        int drawX = hitbox.x - cameraX - 25;
        int drawY = hitbox.y + hitbox.height - drawHeight + 6;

        // Sem sombra artificial: o sprite já possui iluminação própria.
        if (sprite != null) {
            if (facingRight) {
                g.drawImage(sprite, drawX, drawY, drawWidth, drawHeight, null);
            } else {
                g.drawImage(sprite, drawX + drawWidth, drawY,
                        -drawWidth, drawHeight, null);
            }
        }
    }

    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            movingLeft = true;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            movingRight = true;
        }
        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W) {
            jumpRequested = true;
        }
        if (key == KeyEvent.VK_J || key == KeyEvent.VK_X) {
            if (!attacking) {
                attacking = true;
                attackTimer = attackDuration;
                attackHit = false;
            }
        }
    }

    public void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) movingLeft = false;
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) movingRight = false;
    }

    public int getX() { return hitbox.x; }
    public int getY() { return hitbox.y; }

    public Rectangle getAttackHitbox() {
        if (!attacking) {
            return new Rectangle(0, 0, 0, 0);
        }
        if (facingRight) {
            return new Rectangle(hitbox.x + hitbox.width - 4, hitbox.y + 12, 48, 40);
        }
        return new Rectangle(hitbox.x - 44, hitbox.y + 12, 48, 40);
    }

    public boolean hasAttackHit() { 
        return attackHit; }
    public void registerAttackHit() { 
        attackHit = true; }

    public void takeDamage() {
        if (!alive || damageCooldown > 0) return;
        health--;
        damageCooldown = damageCooldownDuration;
        if (health <= 0) alive = false;
    }

    public int getHealth() { 
        return health; }
    public boolean isAlive() { 
        return alive; }
    public Rectangle getHitbox() { 
        return hitbox; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        hitbox.x = x;
        hitbox.y = y;
        velocityY = 0;
        onGround = false;
        coyoteTimer = 0;
    }
}