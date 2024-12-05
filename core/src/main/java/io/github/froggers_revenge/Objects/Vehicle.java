package io.github.froggers_revenge.Objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/** 
 * This class handles the sprite, location, and hitbox data of Vehicles
 */
public class Vehicle extends ObjectMover {
    /**
     * This method sets the initial position, direction, and texture of a vehicle.
     * @param projectileSpeed speed of a vehicle
     * @param projectileDirection holds the current direction(rotation) of a vehicle
     * @param projectileX holds the X-axis value associated with a vehicle
     * @param projectileY holds the Y-axis value associated with a vehicle
     * @param texture holds the textures of vehicles
     */
    public Vehicle(float projectileSpeed, float projectileDirection, int projectileX, int projectileY, TextureRegion texture)
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

    public void SetTexture(TextureRegion carTexture) {
        texture = carTexture; 
    }

    //checks if a explosion interacts with it
    /**
     * Method checks for overlapping hitboxes with projectiles or explosions
     * @param projectiles holds projectile attributes
     * @param explosions holds explosion attributes
     * @return boolean of whether collision occured
     */ 
    public boolean checkCollision(List<Projectile> projectiles, List<Explosion> explosions) {
        for (Projectile projectile : projectiles) {
            if (projectile.getHitbox().overlaps(this.hitbox)) {
                return true;
            }
        }
        for (Explosion explosion : explosions) {
            if (explosion.getHitbox().overlaps(this.hitbox)) {
                return true;
            }
        }
        return false;
    }
}
