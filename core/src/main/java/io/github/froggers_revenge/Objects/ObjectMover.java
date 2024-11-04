package io.github.froggers_revenge.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class ObjectMover {
    
    protected Texture sheet = new Texture("froggerSpriteSheet.png");
    protected TextureRegion texture; //holds the texture used by the moving object
    protected Sprite sprite = new Sprite();
    protected Rectangle hitbox = new Rectangle();
    
    protected float speed; //how fast the bullet travels
    protected Vector2 direction; //the direction it moves

    public final void moveObject()
    {
        sprite.translate(direction.x * speed, direction.y * speed); //moves bullet twords a direction
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
}
