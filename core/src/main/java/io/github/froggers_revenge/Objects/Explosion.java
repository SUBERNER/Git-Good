package io.github.froggers_revenge.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

    Sound explodes = Gdx.audio.newSound(Gdx.files.internal("sounds/retro_explosion_general_06.wav"));

    public Explosion(float explosionX, float explosionY, int width, int height) {
        //gets explosion sprite
        Texture sheet = new Texture("froggerSpriteSheet.png");

        //sets up sprite
        sprite = new Sprite(new TextureRegion(sheet, 108, 72, 32, 32));
        sprite.setPosition(explosionX - (width / 4.0f), explosionY - (height / 4.0f)); //centers explosion to bullet

        //hitbox
        hitbox = new Rectangle(sprite.getX(), sprite.getY(), width, height);

        explodes.play(); //playes the explosion effect.
    }

    public void UpdateExplosion(float deltaTime) {
        size -= deltaTime * decreaseMultiplier; // Decrease size over time
        decreaseMultiplier += deltaTime * 0.75;
    
        // Update sprite scaling
        sprite.setScale(size);
    
        // Update hitbox size and position
        hitbox.setSize(sprite.getWidth() * size, sprite.getHeight() * size);
        hitbox.setPosition(
            sprite.getX() + (sprite.getWidth() - hitbox.getWidth()) / 2,
            sprite.getY() + (sprite.getHeight() - hitbox.getHeight()) / 2
        );
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
