package io.github.froggers_revenge.Objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*Log Class: attributes of the log
 * projectileSpeed: speed at which log moves
 * projectileDirection: direction in which log moves
 * projectileX/projectileY: location of projectile
 * texture: texture of the log
 */
public class Log extends ObjectMover {

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

    public void SetTexture(TextureRegion logTexture) {
        texture = logTexture; 
    }

    //checks if a explosion interacts with it
    public boolean checkCollision(List<Explosion> objects) {
        for (Explosion object : objects) {
            if (object.getHitbox().overlaps(this.hitbox)) {
                return true;
            }
        }
        return false;
    }
}
