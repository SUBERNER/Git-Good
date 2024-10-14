package io.github.froggers_revenge;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementControls implements KeyListener{
    private Frogger frogger;

    public MovementControls(Frogger frogger)
    {
        this.frogger = frogger;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                frogger.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                frogger.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                frogger.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                frogger.moveRight();
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e){

    }
    



}
