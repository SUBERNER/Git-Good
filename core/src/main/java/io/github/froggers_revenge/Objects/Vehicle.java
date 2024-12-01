package io.github.froggers_revenge.Objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*Vehicle Class: attributes of vehicles
 * projctileSpeed: speed of vehicle
 * projectileX/projectileY: location of vehicle
 * texture: desired texture for vehicle
 */
public class Vehicle extends ObjectMover {

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
    public boolean checkCollision(List<ObjectMover> objects, List<Explosion> explosions) {
        for (ObjectMover object : objects) {
            if (object.getHitbox().overlaps(this.hitbox)) {
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
