package io.github.froggers_revenge.Objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * This class will handle the textures and logic of projectiles.
 */
public class Projectile extends ObjectMover {

    //code used for moving the object is inside ObjectMover

    /**
     * This method will first assign the bullet texture to the projectile. Then, it will set the sprite position, rotation, and hitbox.
     * 
     * @param projectileSpeed Holds the constant speed the bullet will be traveling
     * @param projectileX Holds the X value location of the bullet
     * @param projectileY Holds the Y value location of the bullet
     */
    public Projectile(int projectileSpeed, float projectileDirection, int projectileX, int projectileY)
    {
        duration = 0.5f;
        texture = new TextureRegion(sheet, 126, 54, 16, 16);
        sprite = new Sprite(texture);
        speed = projectileSpeed;

        //sets up bullet's position and rotation
        sprite.setPosition(projectileX, projectileY);
        sprite.setRotation(projectileDirection);
        direction = new Vector2(1, 0).setAngleDeg(projectileDirection + 90);  //gets angle to move twords

        //hitbox
        hitbox = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    /**
     * This method will check to see if a bullet has collided with an object/vehicle
     * 
     * @param objects This holds all objects that can possibly be destroyed by a bullet
     * @return boolean holding value of whether collision occured or not
     */
    public boolean checkCollision(List<ObjectMover> objects) {
        for (ObjectMover object : objects) {
            if (object.getHitbox().overlaps(this.hitbox)) {
                if (object instanceof Vehicle) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    /**
     * Gets rid of the explosion sprite
     * 
     * @return returns explosion object
     */
    public Explosion dispose()
    {
        Explosion explosion = new Explosion(sprite.getX(), sprite.getY(), 48, 48);
        return explosion;
    }
}
