package io.github.froggers_revenge.Objects;
import io.github.froggers_revenge.TileMap;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.audio.Sound;

/**
 * This class handles all data related to the character (frogger). This includes attributes such as width, height,
 * speed, shooting cooldown/delay, location, textures, hitboxes, projectiles (the bullets frogger will shoot),
 * as well as booleans determining if frogger is moving or in in revenge mode.
 */
public class Frogger {

    // Player data
    private int width, height;
    private int speed;

    //sound effects
    Sound jump = Gdx.audio.newSound(Gdx.files.internal("sounds/sound-frogger-hop.wav"));
    Sound shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/retro_laser_gun_shoot_01.wav"));

    //used for frog death
    private Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sound-frogger-squash.wav"));
    private TextureRegion deathRegion[]; //teaxutres used when a frog dies
    private Animation<TextureRegion> deathAnimation;
    private float deadFrame = 0; //determins what frame will be used when using animations when dead

    private float shootingCooldown = 0; //time remaining until next move allowed
    private float shootingDelay = 0.50f; //delay between movements

    //used for polished movement
    private float movingCooldown = 0; //time remaining until next move allowed
    private float movingDelay = 0.2f; //delay between movements
    private boolean isMoving = false;
    private float movingProgress = 0; //0 to 1
    private float startX, startY; //Starting position of the movement
    private float targetX, targetY; //Target position of the movement
    private Interpolation interpolation = Interpolation.exp10Out; //used for smooth movement
    
    //textures used for playing
    private TextureRegion normal[]; //textures when frog is normal
    private TextureRegion revenge[]; //textures for when frog has the gun and is in revenge mode
    private int aliveFrame = 0; //determins what frame will be used when using animations when alive

    private Sprite sprite;
    private Rectangle hitbox;
    
    private boolean hasGun;
    public List<Projectile> projectiles = new ArrayList<>();
    private boolean isDead;
    private boolean isFloating; //stores if frogger is on logs or a turtle
    float frameTimer; //used to determine what frame to use



    /**
     * Constructor for class Frogger
     * 
     * @param intialX Froggers initial position along the x-axis
     * @param intialY Froggers initial position along the y-axis
     * @param initialWidth Distance from the leftmost edge of froggers sprite to the rightmost
     * @param initialHeight Distance from the lowest edge of froggers sprite to the highest
     * @param initialSpeed How fast frogger is going initially
     * @param normalTextures Holds all of froggers textures while he is in "normal mode" (not revenge mode)
     * @param revengeTextures Holds all of froggers textures after he picks up a weapon and enters revenge mode
     * @param deathTextures Holds all of the textures that are played while frogger is going through his death sequence
     */
    public Frogger(int initialX, int initialY, int initialWidth, int initialHeight, int initialSpeed, TextureRegion[] normalTexures, TextureRegion[] revengeTextures, TextureRegion[] deathTextures) {

        normal = normalTexures;
        revenge = revengeTextures;
        
        deathRegion = deathTextures;
        deathAnimation = new Animation<>(0.1f, deathRegion);

        sprite = new Sprite(normal[aliveFrame]);

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

    
    /**
     * When called this method will move check if frogger can move up, then call startMove method.
     * 
     */
    public void moveUp() {
        if (sprite.getY() < 208) {
        startMove(0, speed,0);
        }
    }
    /**
     * When called this method will move check if frogger can move downward, then call startMove method.
     * 
     */
    public void moveDown() {
        if (sprite.getY() > 0) {
        startMove(0, -speed,180);
        }
    }
    /**
     * When called this method will move check if frogger can move left, then call startMove method.
     * 
     */
    public void moveLeft() {
        if (sprite.getX() > 0) {
        startMove(-speed, 0,90);
        }
    }
    /**
     * When called this method will move check if frogger can move right, then call startMove method.
     * 
     */
    public void moveRight() {
        if (sprite.getX() < 208) {
        startMove(speed, 0,-90);
        }
    }

    /**
     * This method prepares frogger to move by rotating him the correct direction, calculating the target space, setting
     * the movement cooldown, and changing isMoving to true.
     * 
     * @param deltaX This holds distance frogger will move to the left or right. It will be added to his initial position to calculate the target space.
     * @param deltaY This holds distance frogger will move up or down. It will be added to his initial position to calculate the target space.
     * @param rotation This holds the amount frogger needs to rotate in order to face the correct direction before moving.
     * 
     */
    private void startMove(float deltaX, float deltaY, int rotation) {
        if (!isMoving && movingCooldown <= 0 && !isDead) {
            jump.play(); //plays jump sound effect
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

    /**
     * This method will first check if isMoving is set to true. If true, frogger will move. Finally, this method will check if frogger
     * moved correctly and then update the movement cooldown timer.
     * 
     * @param deltaTime The value holds elapsed time. It will be used to calculate froggers progress in moving.
     */
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

    
    /**
     * This method will first make sure that all conditions are met for frogger to be able to shoot his weapon. If all conditions are met, the
     * shooting sound effect will play, a new projectile will be created, and the shooting cooldown will be updated.
     */
    public void shoot() {
        if (hasGun && shootingCooldown <= 0 && !isDead)
        {
            shoot.play(); //plays the shoot sound effect
            Projectile projectile = new Projectile(200, sprite.getRotation(), (int)sprite.getX(), (int)sprite.getY());
            projectiles.add(projectile);
            shootingCooldown = shootingDelay; //delay between shots
        }
    }

    /**
     * This method is called in order to update froggers shooting cooldown variable.
     * 
     * @param deltaTime This variable is used to count down froggers shooting cooldown.
     */
    public void updateCooldown(float deltaTime) {
        if (shootingCooldown > 0) {
            shootingCooldown -= deltaTime;
        }
    }


    
    /**
     * This method is used to return sprites
     */
    public Sprite getSprite() {
        return sprite;
    }

    
    //determines if frogger is on a log or alligator over the river
    public boolean getFloating() {
        return isFloating;
    }

    //sets if frogger is on a log or turtle
    public void setFloating(boolean isFloating) {
        this.isFloating = isFloating;
    }

    /**
     * This method is used to give frogger the ability to use his gun
     */
    public void revengeMode() {
        this.hasGun = true; //gives frog the ability to use gun
    }

    /**
     * This method will move froggers hitbox to his current location
     */
    public void updateHitbox() {
        hitbox.setPosition(sprite.getX(), sprite.getY());
    }

    public boolean hasGun() {
        return hasGun;
    }
    
    public boolean isDead() {
        return isDead;
    }

    //Getter for hitbox
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * This method is used to check if frogger is on a tile with properties. These properties include if the tile is over the river (floating), if a
     * tile is deadly (in the river), or if a tile is on the highest row which would give frogger his revenge mode.
     * 
     * @param tileMap This variable holds the properties associated with each type of tile
     */
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

    
    /**
     * This method is used to determine what happens when frogger collides with friendly objects or deadly objects such as vehicles or the river. "Friendly" objects
     * include the turtle and the log as they do not kill frogger when he collides with them.
     * 
     * @param objects This variable holds the list of all objects that frogger could collide with in game.
     */
    public void checkCollision(List<ObjectMover> objects) {
        boolean floatCollided = false; //used to beter detemine if frog is floating on object
        for (ObjectMover object : objects) {
            if (object.getHitbox().overlaps(this.hitbox)) {
                if (object instanceof Turtle || object instanceof Log) {
                    isFloating = true;
                    floatCollided = true;
                }
                else { isFloating = false; }

                if (object instanceof Vehicle) {
                    death();
                }
            }
        }
        if (!floatCollided) {
            isFloating = false;
        }
    }

    /**
     * This method will check if frogger is dead. If he is, the death animation and sound effect will play.
     */
    public void death() {
        if (!isDead)
        {
            isDead = true;
            deathSound.play();
            deathAnimation.setPlayMode(Animation.PlayMode.NORMAL); // Play the animation once
        }
    }

    /**
     * This method will first check if frogger is dead or alive. If he is alive, whether he has a gun will be determined and then the correct frogger
     * will be displayed. If frogger is dead, the death sprite of frogger will be displayed for a set amount of time.
     * 
     * @param deltaTime This variable is used to count how long to display froggers death frame sprite.
     */
    public void spriteState(float deltaTime)
    {
        TextureRegion currentRegion; //stores what region will be rendered
        //when frogger is alive
        if (!isDead) {
            aliveFrame = isMoving ? 1 : 0;
            currentRegion = hasGun ? revenge[aliveFrame] : normal[aliveFrame];
        }
        //when frogger is dead
        else {
            if (deadFrame <= deathRegion.length) { deadFrame += deltaTime; }
            else {deadFrame = 7; }

            currentRegion = deathAnimation.getKeyFrame(deadFrame);
        }
        sprite.setRegion(currentRegion); //changes what the sprite looks like
    }

}
