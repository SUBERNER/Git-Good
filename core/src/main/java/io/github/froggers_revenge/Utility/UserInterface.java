package io.github.froggers_revenge.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;


/**
 * This class holds all the data related to the UI. This includes, how the score, timer, and other non-game elements are displayed.
 */
public class UserInterface {
    private Label scoreLabel; //displays score
    private Label highScoreLabel; //displays highscore
    private ProgressBar timeBar; //displays timer
    private Skin skin;
    private Table table;
    

    /**
     * This method displays key information while the game is being played. This includes the current score, the
     * high score, the progress bar, and the timer.
     * 
     * @param score value holds the user's current score
     * @param highscore value holds the highest recorded score 
     * @param time value holds the remaining time left in a level
     */
    public UserInterface(int score, int highscore, float time)
    {
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        table = new Table();

        //displays data
        scoreLabel = new Label("Score: " + score, skin );
        highScoreLabel = new Label("HighS.: " + highscore, skin);
        timeBar = new ProgressBar(0, time, 0.1f, false, skin);
        timeBar.setValue(time); //full bar

        //creates a table to layout elements
        table = new Table();
        table.top(); //aligns elements to top of screen
        table.setFillParent(true); 

        table.add(scoreLabel).expandX().align(Align.topLeft);
        table.add(timeBar).expandX().align(Align.right).width(300);
        table.row();
        table.add(highScoreLabel).expandX().align(Align.topLeft).height(10);;
    }

    //updates the text in the labels
    /**
     * This method will update the labels whenever a value is changed.
     * 
     * @param score value holds the user's current score
     * @param highscore value holds the highest recorded score 
     * @param time value holds the remaining time left in a level
     */
    public void UpdateLabels(int score, int highscore, float time)
    {
        scoreLabel.setText("Score: " + score);
        highScoreLabel.setText("HighS.: " + highscore);
        timeBar.setValue(time);
    }

    //displaying the UI on the stage
    public Label getScoreLabel() {
        return scoreLabel;
    }
    public Label getHighScoreLabel() {
        return highScoreLabel;
    }
    public ProgressBar getTimeLabel() {
        return timeBar;
    }
    public Table getTable() {
        return table;
    }

}
