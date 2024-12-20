package io.github.froggers_revenge.Spawners;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.froggers_revenge.Objects.Turtle;

public class TurtleSpawner extends HazardSpawner {
    public TextureRegion texture[]; //thes texture the spawner will use for the car
    public List<Turtle> turtles = new ArrayList<>(); //spores all the vehicles spawned by the spawner

    /*gets all attributes of the logs
     texture: holds the texture of a log
     delay: holds the time between spawns
     direction: holds the direction the logs will move in
     speed: holds the vehicles speed
     posX = holds the x position of a log
     posY = holds the y position of a log
     */
    public TurtleSpawner(float spawnDelay, float initialSpawnDelay, boolean enableSpawning, float spawnDirection, int positionX, int positionY, TextureRegion turtleTexture[], float turtleSpeed) {
        texture = turtleTexture;
        delay = spawnDelay;
        spawning = enableSpawning;
        direction = spawnDirection;
        speed = turtleSpeed;
        posX = positionX;
        posY = positionY;
    }

    //spawns turtles
    @Override
    protected void Spawn() {
        Turtle turtle1 = new Turtle(speed, direction, posX, posY, texture[0]);
        Turtle turtle2 = new Turtle(speed, direction, posX - 16, posY, texture[0]);
        Turtle turtle3 = new Turtle(speed, direction, posX - 32, posY, texture[0]);
        turtles.add(turtle1);
        turtles.add(turtle2);
        turtles.add(turtle3);
    }
}
