package io.github.froggers_revenge.Utility;

import com.badlogic.gdx.Gdx;

public class Timer {
    private float currentTime = 0; //counts down (seconds)
    private float maxTime = 0; //for reseting the time (seconds)

    public Timer(float maxTime) {
        this.maxTime = maxTime;
        this.currentTime = maxTime;
    }

    public void updateTime() {
        if (currentTime > 0) { //make ssure it does not go below 0
            currentTime -= Gdx.graphics.getDeltaTime(); //updates timer
        }
        else { currentTime = 0; }
    }

    public float getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }
    public void resetTimer() {
        currentTime = maxTime;
    }

    public float getMaxTime() {
        return maxTime;
    }
    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }
}
