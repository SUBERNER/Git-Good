package io.github.froggers_revenge;

import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Frogger {

    // Player data
    private int width, height;
    private int speed;

    
    private TextureRegion normal[]; //textures when frog is normal
    private TextureRegion revenge[]; //textures for when frog has the gun and is in revenge mode
    private Sprite sprite;
    private Rectangle hitbox;
    
    private boolean hasGun;
    public List<Projectile> projectiles = new ArrayList<>();
    private boolean isDead;

    //Constructor
    public Frogger(int initialX, int initialY, int initialWidth, int initialHeight, int initialSpeed, TextureRegion[] normalTexures, TextureRegion[] revengeTextures) {


        normal = normalTexures;
        revenge = revengeTextures;

        sprite = new Sprite(normal[0]);

        //move
        sprite.setPosition(initialX, initialY);
        this.width = initialWidth;
        this.height = initialHeight;
        this.speed = initialSpeed;

        //passive/revenge mode
        this.hasGun = false;

        //dead/aive mode, determines what actions the frog can and cant do
        this.isDead = false;

        //hitbox
        this.hitbox = new Rectangle((int)sprite.getX(), (int)sprite.getY(), width, height);

    }

    //Move methods
    private void moving(float rotation) {
        sprite.setRotation(rotation);
        updateHitbox();
    } 
    public void moveUp() {
        if ((sprite.getY() + speed) < 224 )
        {
            sprite.translate(0, speed);
            moving(0);
        }
    }
    public void moveDown() {
        if ((sprite.getY() - speed) > -1 )
        {
            sprite.translate(0, -speed);
            moving(180);
        }
    }
    public void moveLeft() {
        if ((sprite.getX() - speed) > -1 )
        {
            sprite.translate(-speed, 0);
            moving(90);
        }
    }
    public void moveRight() {
        if ((sprite.getX() + speed) < 224 )
        {
            sprite.translate(speed, 0);
            moving(-90);
        }
    }

    //shoots the gun the player has
    public void shoot() {
        if (hasGun)
        {
            Projectile projectile = new Projectile(3, sprite.getRotation(), (int)sprite.getX(), (int)sprite.getY());
            projectiles.add(projectile);
        }
    }

    //gets sprites information
    public Sprite getSprite() {
        return sprite;
    }

    //set gun to true when they reach the end
    public void revengeMode() {
        sprite.setRegion(revenge[0]);
        this.hasGun = true; //gives frog the ability to use gun
    }

    //move hitbox along with frogger
    private void updateHitbox() {
        hitbox.setBounds((int)sprite.getX(), (int)sprite.getY(), width, height);
    }

    //Getter for hitbox
    public Rectangle getHitbox() {
        return hitbox;
    }

    //Check for collisions with other hitboxes
    public boolean checkCollision(Rectangle obstacleHitbox) {
        return hitbox.intersects(obstacleHitbox);
    }

    //checks if frog is on a tile with properties
    public void checkTile(TileMap tileMap) {
        if(tileMap.isDeadly((int)sprite.getX(), (int)sprite.getY()))
        { 
            if (!isDead){ isDead = true; System.out.println("<FROG DEAD>"); } //makes sure it only happens once
        }
        if(tileMap.isRevange((int)sprite.getX(), (int)sprite.getY()))
        {
            if (!hasGun){ revengeMode(); } //makes sure it only happens once
        }
    }

}
