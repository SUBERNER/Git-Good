package io.github.froggers_revenge.Game_Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    //original frogger resolution but scaled up by 3.5x
    final int screenWidth = 784;
    final int screenHeight = 896;
    
    Thread gameThread;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);   //can improve rendering performance
        
    }


    //Create Game Loop

    public void startGameThread() {
        
        gameThread = new Thread(this);  //instantiates a thread
        gameThread.start();
    }

    @Override
    public void run() {

        while(gameThread != null) {

            //this function will continuously call update and repaint

            //1. update information
            update();

            //2. draw the screen with updated information
            repaint();  //this is how to call paint component method.
            
        }

    }

    public void update() {

    }

    //Create white square on screen
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;  //changes graphics function to graphics 2d

        g2.setColor(Color.white);
        
        g2.fillRect(100, 100, 56, 56);  //this is the size of 1 tile

        g2.dispose(); //dispose of graphics content (not necessary but frees up some memory)
    }


    



}
