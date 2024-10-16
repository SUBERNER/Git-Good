package io.github.froggers_revenge;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    
    private int speed; //how fast the bullet travels
    private Vector2 direction;

    private TextureRegion texture;
    private Sprite sprite;
    private Texture sheet = new Texture("froggerSpriteSheet.png");

    public Projectile(int projectileSpeed, float projectileDirection, int projectileX, int projectileY)
    {
        texture = new TextureRegion(sheet, 126, 54, 16, 16);
        sprite = new Sprite(texture);

        //sets up bullet's position and rotation
        sprite.setPosition(projectileX, projectileY);
        sprite.setRotation(projectileDirection);
        direction = new Vector2(1, 0).setAngleDeg(projectileDirection + 90);  //gets angle to move twords
        speed = projectileSpeed;
    }

    public void moveProjectile()
    {
        sprite.translate(direction.x * speed, direction.y * speed); //moves bullet twords a direction
    }

    //gets sprites information
    public Sprite getSprite() {
        return sprite;
    }

}
