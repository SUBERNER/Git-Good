package io.github.froggers_revenge.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * This class will handle the movement of all objects in the game.
 */
public abstract class ObjectMover {
    
    protected Texture sheet = new Texture("froggerSpriteSheet.png");
    protected TextureRegion texture; //holds the texture used by the moving object
    protected Sprite sprite = new Sprite();
    protected Rectangle hitbox = new Rectangle();
    
    protected float speed; //how fast the bullet travels
    protected Vector2 direction; //the direction it moves
    protected Float duration = 100f; //how long the object moves for before being destroyed (default to 100 seconds)

    /**
     * This method will move an object a set distance, at a set speed, over a set amount of time.
     * 
     * @param deltaTime This variable is used to countdown the time an object is able to move before being destroyed.
     */
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

    //gets duration of time for object to be able to move
    public Float getDuration() {
        return duration;
    }
}
