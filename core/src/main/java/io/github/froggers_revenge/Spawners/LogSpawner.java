package io.github.froggers_revenge.Spawners;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.froggers_revenge.Objects.Log;

public class LogSpawner extends HazardSpawner {
    public TextureRegion texture[]; //thes texture the spawner will use for the car
    public List<Log> logs = new ArrayList<>(); //spores all the vehicles spawned by the spawner

    
    /** 
     * This method will enable spawns, and set the texture, spawn delay, direction, speed, and location of the logs.
     * 
    */
    public LogSpawner(float spawnDelay, float initialSpawnDelay, boolean enableSpawning, float spawnDirection, int positionX, int positionY, TextureRegion logTexture[], float logSpeed) {
        texture = logTexture;
        delay = spawnDelay;
        spawning = enableSpawning;
        direction = spawnDirection;
        speed = logSpeed;
        posX = positionX;
        posY = positionY;
    }

    //spawns logs
    /**
     * This method is used to create a log object.
     */
    @Override
    protected void Spawn() {
        Log log = new Log(speed, direction, posX, posY, texture[0]); //selects a random log texture
        logs.add(log);
    }
}
