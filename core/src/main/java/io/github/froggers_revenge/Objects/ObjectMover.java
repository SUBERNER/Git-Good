package io.github.froggers_revenge.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*Class ObjectMover: gets sprite of target object and moves object
 * sheet: holds sprite sheet
 * texture: holds desired texture
 * speed: how fast object moves
 * direction: direction object moves
 */
public abstract class ObjectMover {
    
    protected Texture sheet = new Texture("froggerSpriteSheet.png");
    protected TextureRegion texture; //holds the texture used by the moving object
    protected Sprite sprite = new Sprite();
    protected Rectangle hitbox = new Rectangle();
    
    protected float speed; //how fast the bullet travels
    protected Vector2 direction; //the direction it moves
    protected Float duration = 100f; //how long the object moves for before being destroyed (default to 100 seconds)

    public final void moveObject(float deltaTime)
    {
        duration -= deltaTime; //counts down time and removes object when it gets to 0
        sprite.translate(direction.x * speed * deltaTime, direction.y * speed * deltaTime); //moves bullet twords a direction
        hitbox.setPosition(sprite.getX(), sprite.getY());
    }

    //gets sprites information for drawing
    public final Sprite getSprite() {
        return sprite;
    }

    //gets hitbox to be tested
    public final Rectangle getHitbox() {
        return hitbox;
    }

    public Float getDuration() {
        return duration;
    }
}
