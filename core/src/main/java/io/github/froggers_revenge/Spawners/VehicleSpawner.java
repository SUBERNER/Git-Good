package io.github.froggers_revenge.Spawners;
import io.github.froggers_revenge.Objects.Vehicle;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VehicleSpawner extends HazardSpawner {
    public TextureRegion texture; //the texture the spawner will use for the car
    public List<Vehicle> vehicles = new ArrayList<>(); //spores all the vehicles spawned by the spawner

    //constructor
    public VehicleSpawner(float spawnDelay, float initialSpawnDelay, boolean enableSpawning, float spawnDirection, int positionX, int positionY, TextureRegion vehicalTexture, float VehicalSpeed) {
        texture = vehicalTexture;
        delay = spawnDelay;
        spawning = enableSpawning;
        direction = spawnDirection;
        speed = VehicalSpeed;
        posX = positionX;
        posY = positionY;
    }

    //spawns cars
    @Override
    protected void Spawn() {
        Vehicle vehicle = new Vehicle(speed, direction, posX, posY, texture);
        vehicles.add(vehicle);
    }
    
}
