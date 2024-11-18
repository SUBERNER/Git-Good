package io.github.froggers_revenge.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {
    
    private Sprite sprite;
    private Rectangle hitbox;

    //increases the amount the explosions shrink
    private float decreaseMultiplier = 1f;
    private float size = 1f; //the size of the explosion (decreases)

    public Explosion(float explosionX, float explosionY, int width, int height) {
        //gets explosion sprite
        Texture sheet = new Texture("froggerSpriteSheet.png");

        //sets up sprite
        sprite = new Sprite(new TextureRegion(sheet, 108, 72, 32, 32));
        sprite.setPosition(explosionX - (width / 4.0f), explosionY - (height / 4.0f)); //centers explosion to bullet

        //hitbox
        hitbox = new Rectangle(sprite.getX(), sprite.getY(), width, height);
    }

    public void UpdateExplosion(float deltaTime)
    {
        size -= deltaTime * decreaseMultiplier; //counts down time and removes object when it gets to 0
        decreaseMultiplier += deltaTime * 0.75;

        //updtes scale
        sprite.setScale(size);
        hitbox.setSize(size);

        //updates position
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

    public float getSize() {
        return size;
    }
}