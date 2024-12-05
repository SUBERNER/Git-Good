package io.github.froggers_revenge.Utility;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Score {

    private int score = 0;
    private Preferences preference;

    //gets the highscore data from file and checks if file is empty before setting new highscore
    public Score()
    {
        //initialize Preferences with a unique name
        preference = Gdx.app.getPreferences("FroggersRevenge");

        //if no high score exists, initialize it with 0
        if (!preference.contains("highScore"))
        {
            preference.putInteger("highScore", 0);
            preference.flush(); //save 
        }
    }

    //getter and setter for score
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

        //adds points to score
    public void addScore(int points)
    {
        score += points;
    }

    //reads in the current high score
    public int getHighScore() {
        return preference.getInteger("highScore", 0); // Default to 0 if no high score exists
    }

    //saves new highscore in file
    //force means it will change highscore even if score is smaller
    public void setHighScore(int highScore, boolean force) {
        if (highScore > getHighScore() || force)
        {
            preference.putInteger("highScore", highScore);
            preference.flush(); // Save changes to persistent storage
            System.out.println("NEW HIGHSCORE: " + getHighScore());
        }
        else
        {
            System.out.println("HIGHSCORE: " + getHighScore());
        }
    }
    
}
