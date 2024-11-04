package io.github.froggers_revenge.Utility;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class Score {

    private int score = 0;
    private File file;


    public Score()
    {
        //gets highscore in file
        file = new File("core\\src\\main\\java\\io\\github\\froggers_revenge\\Utility\\HighScore.txt"); //stores and gets data about player score

        //checks if file is empty and adds a value to be altered
        if(file.length() == 0) {
            setHighScore(0, true);
        }
    }


    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

        //adds pints to score
    public void addScore(int points)
    {
        score += points;
    }

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
