package io.github.froggers_revenge.Spawners;

import com.badlogic.gdx.utils.Timer;

public abstract class HazardSpawner {
    protected float delay; //the delay between each object
    protected float initialDelay; //the delay before spawning the first object
    protected boolean spawning; 
    
    protected float speed;
    protected float direction;
    //the location it spawns the cars
    protected int posX;
    protected int posY; 

    //method overriden to spawn whatever you want from the spawner
    protected void Spawn() {}

    //tracks the delay between each object spawned
    //ONLY CALL ONCE
    public void StartSpawning() {
        if (spawning) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run()
                {
                    Spawn();
                } 
            }, initialDelay, delay);
        }
    }

    public final void setspawnDelay(float spawnDelay) {
        delay = spawnDelay;
    }

    public final void enableSpawning(Boolean enableSpawning) {
        spawning = enableSpawning;
    }

    public final void setspawnDirection(float spawnDirection) {
        direction = spawnDirection;
    }

    public final void objectSpeed(float objectSpeed) {
        speed = objectSpeed;
    }

    public final void setPosition(int X, int Y) {
        posX = X;
        posY = Y;
    }
}
