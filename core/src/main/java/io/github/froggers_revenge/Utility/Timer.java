package io.github.froggers_revenge.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/*Timer Class: creates game timer
 * currentTime: time left in game (maxTime - elapsed time)
 * maxTime: time limit to complete game
 * updateTime: constantly updates the timer
 */
public class Timer {
    private float currentTime = 0; //counts down (seconds)
    private float maxTime = 0; //for reseting the time (seconds)
    private Sound outTime = Gdx.audio.newSound(Gdx.files.internal("sounds/sound-frogger-time.wav")); //plays whe user runs out of time
    private Boolean hasTime = true; //make ssure sound only plays once

    public Timer(float maxTime) {
        this.maxTime = maxTime;
        this.currentTime = maxTime;
    }

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
