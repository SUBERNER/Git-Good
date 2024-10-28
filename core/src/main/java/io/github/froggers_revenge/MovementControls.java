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
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W))
        {
            frogger.moveUp();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S))
        {
            frogger.moveDown();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A))
        {
            frogger.moveLeft();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D))
        {
            frogger.moveRight();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            frogger.shoot();
        }
    }
    



}
