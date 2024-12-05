package io.github.froggers_revenge.Objects;


import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import io.github.froggers_revenge.TileMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

public class FroggerTest {

    private Frogger frogger;
    private TileMap tileMap;
    private List<ObjectMover> objects;

    private Sound mockJumpSound;
    private Sound mockShootSound;
    private Sound mockDeathSound;
    private TextureRegion[] normalTextures;
    private TextureRegion[] revengeTextures;
    private TextureRegion[] deathTextures;

    @Before
    public void setup() {
        // Mocking necessary objects
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        FileHandle fileHandleMock = mock(FileHandle.class);
        when(Gdx.files.internal("sounds/sound-frogger-hop.wav")).thenReturn(fileHandleMock);
        when(Gdx.files.internal("sounds/retro_laser_gun_shoot_01.wav")).thenReturn(fileHandleMock);
        when(Gdx.files.internal("sounds/sound-frogger-squash.wav")).thenReturn(fileHandleMock);
        Gdx.audio = mock(com.badlogic.gdx.Audio.class);
        Sound soundMock = mock(Sound.class);
        when(Gdx.audio.newSound(fileHandleMock)).thenReturn(soundMock);

        mockJumpSound = mock(Sound.class);
        mockShootSound = mock(Sound.class);
        mockDeathSound = mock(Sound.class);

        // Mocking texture arrays for normal, revenge, and death states
        normalTextures = new TextureRegion[2];  // Assuming 2 frames for normal state
        revengeTextures = new TextureRegion[2]; // Assuming 2 frames for revenge state
        deathTextures = new TextureRegion[2];   // Assuming 2 frames for death state

        // Initialize the Frogger object with mocked dependencies
        frogger = new Frogger(100, 100, 50, 50, 5, normalTextures, revengeTextures, deathTextures);

        // Mock tileMap and object list for collision checks
        tileMap = mock(TileMap.class);
        objects = new ArrayList<>();
    }

    @After
    public void tearDown() {
        // Clean up if necessary
    }
    @Test
    public void testFroggerInitialization() {
        // Example test to check if Frogger is initialized
        assertNotNull(frogger);
    }

    @Test
    public void testMoveUp() {
        // Mock the position before move
        frogger.moveUp();
        assertTrue(frogger.getSprite().getY() > 100);  // Verify that frogger's Y position has increased
    }

    @Test
    public void testMoveDown() {
        frogger.moveDown();
        assertTrue(frogger.getSprite().getY() < 100);  // Verify that frogger's Y position has decreased
    }

    @Test
    public void testMoveLeft() {
        frogger.moveLeft();
        assertTrue(frogger.getSprite().getX() < 100);  // Verify that frogger's X position has decreased
    }

    @Test
    public void testMoveRight() {
        frogger.moveRight();
        assertTrue(frogger.getSprite().getX() > 100);  // Verify that frogger's X position has increased
    }

    @Test
    public void testShootingCooldown() {
        // Ensure cooldown works by checking shooting ability with and without cooldown
        frogger.shoot();
        verify(mockShootSound, times(1)).play();  // Check if the shooting sound was played

        frogger.shoot();  // Attempt to shoot again while the cooldown is active
        verify(mockShootSound, times(1)).play();  // Ensure the sound was not played again (cooldown in effect)
    }

    @Test
    public void testRevengeMode() {
        frogger.revengeMode();
        assertTrue(frogger.hasGun());  // Verify that revenge mode gives the frog a gun
    }

    @Test
    public void testCollisionWithVehicle() {
        // Mock the behavior of objects in the game
        ObjectMover vehicle = mock(Vehicle.class);
        when(vehicle.getHitbox()).thenReturn(frogger.getHitbox());  // Ensure the hitboxes overlap
        objects.add(vehicle);  // Add vehicle to the list of objects
    
        frogger.checkCollision(objects);  // Call the collision check
    
        // Verify the frogger's state after collision
        assertTrue(frogger.isDead());  // Verify that frogger is marked as dead after collision with a vehicle
        verify(mockDeathSound, times(1)).play();  // Ensure the death sound is played
    }

    @Test
    public void testDeathLogic() {
        // Simulate frogger death
        frogger.death();
        verify(mockDeathSound, times(1)).play();  // Ensure death sound is played once
    }

    @Test
    public void testCheckTileRevengeMode() {
        // Mock the TileMap behavior for entering revenge mode
        when(tileMap.isRevange((int) frogger.getSprite().getX(), (int) frogger.getSprite().getY())).thenReturn(true);

        frogger.checkTile(tileMap);
        assertTrue(frogger.hasGun());  // Verify frogger has gun after stepping on revenge tile
    }

    @Test
    public void testCheckCollisionWithLog() {
        // Mock the behavior of a floating object (like a Log)
        ObjectMover log = mock(Log.class);
        when(log.getHitbox()).thenReturn(frogger.getHitbox());
        objects.add(log);

        frogger.checkCollision(objects);
        assertTrue(frogger.getFloating());  // Verify frogger is floating on the log
    }
}