package io.github.froggers_revenge.Spawners;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.froggers_revenge.Objects.Log;

public class LogSpawner extends HazardSpawner {
    public TextureRegion texture[]; //thes texture the spawner will use for the car
    public List<Log> logs = new ArrayList<>(); //spores all the vehicles spawned by the spawner

    /*gets all attributes of the logs
     texture: holds the texture of a log
     delay: holds the time between spawns
     direction: holds the direction the logs will move in
     speed: holds the vehicles speed
     posX = holds the x position of a log
     posY = holds the y position of a log
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
    @Override
    protected void Spawn() {
        Log log = new Log(speed, direction, posX, posY, texture[0]); //selects a random log texture
        logs.add(log);
    }
}
