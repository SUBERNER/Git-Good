package io.github.froggers_revenge.Objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*Turtle Class: attributes of the turtle
 * projectileSpeed: speed at which turtle moves
 * projectileDirection: direction in which turtle moves
 * projectileX/projectileY: location of projectile
 * texture: texture of the turtle
 */
public class Turtle extends ObjectMover {

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
