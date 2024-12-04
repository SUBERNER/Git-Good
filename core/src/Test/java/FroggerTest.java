package io.github.froggers_revenge;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.ApplicationListener;

public class FroggerTest {

    private Frogger frogger;
    private TextureRegion[] normalTextures;
    private TextureRegion[] revengeTextures;

    // Setup libGDX for headless testing
    @Before
    public void setUp() {
        // Initialize libGDX in a headless mode (no graphics)
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {}
            @Override
            public void render() {}
            @Override
            public void resize(int width, int height) {}
            @Override
            public void pause() {}
            @Override
            public void resume() {}
            @Override
            public void dispose() {}
        }, config);

        // Create simple TextureRegions for testing
        Texture texture = new Texture(16, 16); // Create an empty texture for the frog
        normalTextures = new TextureRegion[1];
        revengeTextures = new TextureRegion[1];
        normalTextures[0] = new TextureRegion(texture);  // Use an empty texture
        revengeTextures[0] = new TextureRegion(texture); // Use the same empty texture for revenge mode

        // Initialize Frogger
        frogger = new Frogger(10, 10, 16, 16, 5, normalTextures, revengeTextures);
    }

    @Test
    public void testMoveUp() {
        frogger.moveUp();
        assertEquals(15, frogger.getSprite().getY(), 0.01);
        assertEquals(10, frogger.getSprite().getX(), 0.01);
    }

    @Test
    public void testMoveDown() {
        frogger.moveDown();
        assertEquals(5, frogger.getSprite().getY(), 0.01);
    }

    @Test
    public void testMoveLeft() {
        frogger.moveLeft();
        assertEquals(5, frogger.getSprite().getX(), 0.01);
    }

    @Test
    public void testMoveRight() {
        frogger.moveRight();
        assertEquals(15, frogger.getSprite().getX(), 0.01);
    }

    @Test
    public void testRevengeMode() {
        assertFalse(frogger.hasGun); // Initially no gun
        frogger.revengeMode();
        assertTrue(frogger.hasGun);  // After revenge mode, the frogger has a gun
        assertEquals(revengeTextures[0], frogger.getSprite().getRegion()); // Ensure the revenge texture is set
    }

    @Test
    public void testShootWithGun() {
        // Activate revenge mode to give frog the gun
        frogger.revengeMode();
        frogger.shoot();

        // Ensure the projectile was created and added
        assertEquals(1, frogger.projectiles.size());
        Projectile projectile = frogger.projectiles.get(0);
        assertEquals(frogger.getSprite().getX(), projectile.getX(), 0.01);
        assertEquals(frogger.getSprite().getY(), projectile.getY(), 0.01);
    }

    @Test
    public void testShootWithoutGun() {
        // Attempt to shoot without the gun
        frogger.shoot();

        // Ensure no projectile was added
        assertEquals(0, frogger.projectiles.size());
    }

    @Test
    public void testCheckCollision() {
        Rectangle obstacle = new Rectangle(10, 10, 16, 16);
        assertTrue(frogger.checkCollision(obstacle)); // Should collide
    }

    @Test
    public void testCheckTileDeadly() {
        TileMap tileMap = new TileMap() {
            @Override
            public boolean isDeadly(int x, int y) {
                return x == 10 && y == 10;
            }

            @Override
            public boolean isRevange(int x, int y) {
                return false;
            }
        };

        frogger.checkTile(tileMap);
        assertTrue(frogger.isDead);
    }

    @Test
    public void testCheckTileRevenge() {
        TileMap tileMap = new TileMap() {
            @Override
            public boolean isDeadly(int x, int y) {
                return false;
            }

            @Override
            public boolean isRevange(int x, int y) {
                return x == 10 && y == 10;
            }
        };

        frogger.checkTile(tileMap);
        assertTrue(frogger.hasGun);
    }
}