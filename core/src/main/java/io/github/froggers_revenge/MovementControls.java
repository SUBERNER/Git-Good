package io.github.froggers_revenge;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MovementControls implements KeyListener{
    private Frogger frogger = null;

    public MovementControls(Frogger frogger)
    {
        this.frogger = frogger;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            frogger.moveUp();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            frogger.moveDown();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            frogger.moveLeft();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            frogger.moveRight();
        }
    }
    



}
