package io.github.froggers_revenge.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


/**
 * This method is used to countdown the time left on the level. 
 */
public class Timer {
    private float currentTime = 0; //counts down (seconds)
    private float maxTime = 0; //for reseting the time (seconds)
    private Sound outTime = Gdx.audio.newSound(Gdx.files.internal("sounds/sound-frogger-time.wav")); //plays whe user runs out of time
    private Boolean hasTime = true; //make ssure sound only plays once

    /**
     * This method sets the max time as well as the current time to the maximum alotted time at the beginning of the level.
     * 
     * @param maxTime this holds the value of the maximum time limit
     */
    public Timer(float maxTime) {
        this.maxTime = maxTime;
        this.currentTime = maxTime;
    }

    /**
     * This method constantly updates time and makes sure that the time never goes below zero. When time hits zero, the has time variable
     * is changed to false and the player is out of time to play.
     */
    public void updateTime() {
        if (hasTime) {
            if (currentTime > 0) { //make ssure it does not go below 0
                currentTime -= Gdx.graphics.getDeltaTime(); //updates timer
            }
            else { //player has run out of time
                currentTime = 0;
                hasTime = false;
                outTime.play();
            }
        }
    }

    //getter and setter for currentTime
    public float getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    //resets time back to max time
    public void resetTimer() {
        currentTime = maxTime;
    }

    //getter and setter for max time
    public float getMaxTime() {
        return maxTime;
    }
    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }
}
