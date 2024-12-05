package io.github.froggers_revenge.Objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*Projectile Class: attributes of projectiles
 * projectileSpeed: speed of the object
 * projectileX/projectileY: location of the projectile
 */
public class Projectile extends ObjectMover {

    //code used for moving the object is inside ObjectMover

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
    
    //actives the explosions and sends it back to main
    public Explosion dispose()
    {
        Explosion explosion = new Explosion(sprite.getX(), sprite.getY(), 48, 48);
        return explosion;
    }
}
