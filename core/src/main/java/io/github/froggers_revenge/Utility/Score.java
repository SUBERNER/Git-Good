package io.github.froggers_revenge.Utility;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * This class will handle the calculation and storage of the user high score.
 */
public class Score {

    private int score = 0;
    private File file;

    
    /**
     * This method gets the highscore data from file and checks if file is empty before setting
     * new highscore
     */
    public Score()
    {
        //gets highscore in file
        file = new File("core\\src\\main\\java\\io\\github\\froggers_revenge\\Utility\\HighScore.txt"); //stores and gets data about player score

        //checks if file is empty and adds a value to be altered
        if(file.length() == 0) {
            setHighScore(0, true);
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

    
    /**
     * This method will find and return the highest score achieved
     * 
     * @return returns the highest score
     */
    public int getHighScore() {
        int highScore = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            highScore = Integer.parseInt(reader.readLine()); //gets highscore and converts to int
            reader.close();
        } 
        catch (IOException e) {}

        return highScore;
    }

    //saves new highscore in file
    //force means it will change highscore even if score is smaller
    /**
     * This method saves a new highscore in file if it is the highest, or if forced
     * 
     * @param highScore holds the value of the highest score
     * @param force used to force a value to become the high score
     */
    public void setHighScore(int highScore, boolean force) {
        
        if(highScore > getHighScore() || force)
        {
            try{
                FileWriter writer = new FileWriter(file);
                writer.write(String.valueOf(highScore));
                writer.close();
                System.out.println("NEW HIGHSCORE: " + getHighScore());
            } 
            catch (IOException e) {}
        }
        else {System.out.println("HIGHSCORE: " + getHighScore());}
    }
    
}
