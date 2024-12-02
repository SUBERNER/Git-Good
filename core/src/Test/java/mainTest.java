package io.github.froggers_revenge;

import io.github.froggers_revenge.Objects.Frogger;
import io.github.froggers_revenge.Spawners.VehicleSpawner;
import io.github.froggers_revenge.Spawners.LogSpawner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static org.mockito.Mockito.*; // Use Mockito for mocking

class MainTest {

    private Main main;
    
    @BeforeEach
    void setup() {
        main = new Main();
        main.create();
    }

    @Test
    void testInitialScore() {
        assertNotNull(main.score, "Score should be initialized");
        assertEquals(0, main.score.getScore(), "Initial score should be zero");
    }

    @Test
    void testInitialTimer() {
        assertNotNull(main.timer, "Timer should be initialized");
        assertEquals(60, main.timer.getCurrentTime(), "Timer should start with 60 seconds");
    }

    @Test
    void testVehicleSpawnersInitialization() {
        assertNotNull(main.vehicleSpawners, "Vehicle spawners array should be initialized");
        assertEquals(5, main.vehicleSpawners.length, "Vehicle spawners array should have 5 elements");

        for (VehicleSpawner spawner : main.vehicleSpawners) {
            assertNotNull(spawner, "Each vehicle spawner should be initialized");
        }
    }

    @Test
    void testLogSpawnersInitialization() {
        assertNotNull(main.logSpawners, "Log spawners array should be initialized");
        assertEquals(5, main.logSpawners.length, "Log spawners array should have 5 elements");

        for (LogSpawner spawner : main.logSpawners) {
            assertNotNull(spawner, "Each log spawner should be initialized");
        }
    }

    @Test
    void testFroggerInitialization() {
        assertNotNull(main.frogger, "Frogger object should be initialized");
        assertEquals(112, main.frogger.getX(), "Frogger should start at the correct X position");
        assertEquals(0, main.frogger.getY(), "Frogger should start at the correct Y position");
    }

    @Test
    void testUpdateProjectiles() {
        // Create mock projectiles to simulate projectile movement
        Frogger frogger = mock(Frogger.class);
        main.frogger = frogger;

        // Add some mock projectiles to Frogger
        Projectile projectile = mock(Projectile.class);
        main.frogger.projectiles.add(projectile);

        // Simulate updating projectiles
        main.updateProjectiles(1/60f);
        
        // Verify that moveObject was called on the projectile
        verify(projectile, times(1)).moveObject(anyFloat());
    }

    @Test
    void testUpdateHazards() {
        // Test hazard update function calls and removal of out-of-bounds vehicles/logs
        
        VehicleSpawner spawner = main.vehicleSpawners[0];
        Vehicle vehicle = mock(Vehicle.class);
        when(vehicle.getSprite().getX()).thenReturn(400f); // Out of bounds
        spawner.vehicles.add(vehicle);

        // Simulate delta time to update hazards
        main.updateHazards(1/60f);

        // Verify that the out-of-bounds vehicle was removed
        assertTrue(spawner.vehicles.isEmpty(), "Out-of-bounds vehicle should be removed");
    }

    @Test
    void testCollisionUpdate() {
        // Setup some values for the collision system
        assertNotNull(main.collision, "Collision object should be initialized");

        // Mock frogger's hitbox to verify collision update
        main.collision.setFrogHitbox(main.frogger.getHitbox());

        main.updateCollision();

        // Ensure the frogger's hitbox is properly set in the collision system
        assertEquals(main.collision.getFrogHitbox(), main.frogger.getHitbox(), "Collision should update frog hitbox");
    }
}