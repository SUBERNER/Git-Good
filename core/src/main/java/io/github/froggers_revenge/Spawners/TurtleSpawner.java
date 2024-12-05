package io.github.froggers_revenge.Spawners;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.froggers_revenge.Objects.Turtle;

public class TurtleSpawner extends HazardSpawner {
    public TextureRegion texture[]; //thes texture the spawner will use for the car
    public List<Turtle> turtles = new ArrayList<>(); //spores all the vehicles spawned by the spawner

    
    /**
     * This method will set the spawn delay, spawning(enabled or disabled), direction, location, and texture of the turtles.
     * 
     * @param spawnDelay delay between spawns
     * @param initialSpawnDelay time between starting the game and the first turtle spawning
     * @param enableSpawning boolean that indicates if spawning is enabled or not
     * @param spawnDirection direction that the turtle is moving in
     * @param positionX starting X-axis position of the turtle
     * @param positionY starting Y-axis position of the turtle
     * @param turtleTexture holds the texture of the turtle
     * @param turtleSpeed holds the speed of the turtle
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

    
    /**
     * This method will spawn 3 turtles at a time.
     */
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
