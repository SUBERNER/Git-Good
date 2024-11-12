package io.github.froggers_revenge.Objects;
import io.github.froggers_revenge.TileMap;
import io.github.froggers_revenge.Collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Frogger {

    // Player data
    private int width, height;
    private int speed;

    private float shootingCooldown = 0; //time remaining until next move allowed
    private float shootingDelay = 0.50f; //delay between movements

    //used for polished movement
    private float movingCooldown = 0; //time remaining until next move allowed
    private float movingDelay = 0.15f; //delay between movements
    private boolean isMoving = false;
    private float movingProgress = 0; //0 to 1
    private float startX, startY; //Starting position of the movement
    private float targetX, targetY; //Target position of the movement
    private Interpolation interpolation = Interpolation.exp10Out; //used for smooth movement
    
    private TextureRegion normal[]; //textures when frog is normal
    private TextureRegion revenge[]; //textures for when frog has the gun and is in revenge mode
    private Sprite sprite;
    private Rectangle hitbox;
    
    private boolean hasGun;
    public List<Projectile> projectiles = new ArrayList<>();
    private boolean isDead;
    private boolean isFloating; //stores if frogger is on logs or a turtle

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

        //stores if frogger is on logs or a turtle
        this.isFloating = false;

        //hitbox
        this.hitbox = new Rectangle((int)sprite.getX(), (int)sprite.getY(), width, height);

    }

    //Move methods
    public void moveUp() {
        startMove(0, speed,0);
    }
    public void moveDown() {
        startMove(0, -speed,180);
    }
    public void moveLeft() {
        startMove(-speed, 0,90);
    }
    public void moveRight() {
        startMove(speed, 0,-90);
    }

    //starts the process of moving the frog
    private void startMove(float deltaX, float deltaY, int rotation) {
        if (!isMoving && movingCooldown <= 0) {
            sprite.setRotation(rotation); //rotates frog
            //Set start and target positions
            startX = sprite.getX();
            startY = sprite.getY();
            targetX = startX + deltaX;
            targetY = startY + deltaY;
            movingProgress = 0;  // Reset movement progress
            isMoving = true;
            movingCooldown = movingDelay;  // Set cooldown to prevent input during move
        }
    }

    public void UpdateMoving(float deltaTime)
    {
        if (isMoving)
        {
            //Calculate progress based on deltaTime and movingdelay
            movingProgress += deltaTime / movingDelay;
            float interpolatedProgress = interpolation.apply(movingProgress);

            float newX = startX + (targetX - startX) * interpolatedProgress;
            float newY = startY + (targetY - startY) * interpolatedProgress;
            sprite.setPosition(newX, newY);

            //Check if movement is complete
            if (movingProgress >= 1) {
                sprite.setPosition(targetX, targetY);  //Ensure final position is exact
                updateHitbox();  //Update hitbox at the new position
                isMoving = false; //Movement is complete
            }
        }

        //Updates cooldown timer
        if (movingCooldown > 0) {
            movingCooldown -= deltaTime;
        }
    }

    //shoots the gun the player has
    public void shoot() {
        if (hasGun && shootingCooldown <= 0)
        {
            Projectile projectile = new Projectile(200, sprite.getRotation(), (int)sprite.getX(), (int)sprite.getY());
            projectiles.add(projectile);
            shootingCooldown = shootingDelay; //delay between shots
        }
    }

    //used for shooting
    public void updateCooldown(float deltaTime) {
        if (shootingCooldown > 0) {
            shootingCooldown -= deltaTime;
        }
    }


    //gets sprites information
    public Sprite getSprite() {
        return sprite;
    }

    //gets if frogger is on a log or turtle
    public boolean getFloating() {
        return isFloating;
    }

    //sets if frogger is on a log or turtle
    public void setFloating(boolean isFloating) {
        this.isFloating = isFloating;
    }

    //set gun to true when they reach the end
    public void revengeMode() {
        sprite.setRegion(revenge[0]);
        
        this.hasGun = true; //gives frog the ability to use gun
    }

    //move hitbox along with frogger
    public void updateHitbox() {
        hitbox.setPosition(sprite.getX(), sprite.getY());
    }

    //Getter for hitbox
    public Rectangle getHitbox() {
        return hitbox;
    }

    //checks if frog is on a tile with properties
    public void checkTile(TileMap tileMap) {
        if (!isFloating)
        {
            if(tileMap.isDeadly((int)sprite.getX(), (int)sprite.getY()))
            { 
                if (!isDead){ isDead = true; System.out.println("<FROG DEAD>"); } //makes sure it only happens once
            }
        }
        if(tileMap.isRevange((int)sprite.getX(), (int)sprite.getY()))
        {
            if (!hasGun){ revengeMode(); } //makes sure it only happens once
        }
    }

    //checks if frog is on a tile with properties
    //checks if frog is colliding with a obstacle that can kill them or effect them
    public void checkCollision(Collision collision) {
        if (collision.testTargets(hitbox, collision.getLogHitboxs())) { isFloating = true;}
        else { isFloating = false; }

        if (collision.testTargets(hitbox, collision.getVehicleHitboxs()))
        {
            if (!isDead){ isDead = true; System.out.println("<FROG DEAD>"); } //makes sure it only happens once
        }
    }

}
