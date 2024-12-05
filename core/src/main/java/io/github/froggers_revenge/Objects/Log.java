package io.github.froggers_revenge.Objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * This class will hold all of the attributes and logic associated with the Log.
 *
 * @param projectileSpeed speed at which log moves
 * @param projectileDirection direction in which log moves
 * @param projectileX holds the X value associated with the logs location
 * @param projectileY holds the Y value associated with the logs location
 * @param texture will hold the texture(s) associated with the log
 */
public class Log extends ObjectMover {

    /**
     * This method will load the log texture, set the logs position and direction, and also set the logs hitbox.
     * 
     * @param projectileSpeed speed at which log moves
     * @param projectileDirection direction in which log moves
     * @param projectileX holds the X value associated with the logs location
     * @param projectileY holds the Y value associated with the logs location
     * @param texture will hold the texture(s) associated with the log
     */
    public Log(float projectileSpeed, float projectileDirection, int projectileX, int projectileY, TextureRegion texture)
    {
        sprite = new Sprite(texture);
        speed = projectileSpeed;

        //sets up bullet's position and rotation
        sprite.setPosition(projectileX, projectileY);
        sprite.setRotation(projectileDirection);
        direction = new Vector2(1, 0).setAngleDeg(projectileDirection + 180);  //gets angle to move twords

        //hitbox
        hitbox = new Rectangle((int)sprite.getX(), (int)sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    //setter for log texture
    public void SetTexture(TextureRegion logTexture) {
        texture = logTexture; 
    }

    /**
     * This method will check to see if the logs hitbox interacts with the explosions hitbox and return a boolean.
     * 
     * @param objects This will hold the explosion object.
     * @return boolean holding value of whether collision occured or not
     */
    public boolean checkCollision(List<Explosion> objects) {
        for (Explosion object : objects) {
            if (object.getHitbox().overlaps(this.hitbox)) {
                return true;
            }
        }
        return false;
    }
}
