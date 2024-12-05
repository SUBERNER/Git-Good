package io.github.froggers_revenge.Objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * This class handles the sprite, movement, and collision logic of the turtle object
 */
public class Turtle extends ObjectMover {

    /**
     * 
     * @param projectileSpeed speed at which turtle moves
     * @param projectileDirection direction in which turtle moves
     * @param projectileX location of projectile on X-axis
     * @param projectileY location of projectile on Y-axis
     * @param texture texture of the turtle
     */
    public Turtle(float projectileSpeed, float projectileDirection, int projectileX, int projectileY, TextureRegion texture)
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

    public void SetTexture(TextureRegion turtleTexture) {
        texture = turtleTexture; 
    }

    
    /**
     * This method will check if an explosion hitbox overlaps with a turtle hitbox
     * 
     * @param objects holds the hitbox of explosion in order to check collision
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
