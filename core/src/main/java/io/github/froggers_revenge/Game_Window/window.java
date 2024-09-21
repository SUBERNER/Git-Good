package io.github.froggers_revenge.Game_Window;

import javax.swing.*;

public class window {

    public static void main(String[] args) {
        
        JFrame froggerFrame = new JFrame("Frogger");
        froggerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //lets the window close properly when user hits "x" button
        froggerFrame.setResizable(false);   //players cannot resize window
        froggerFrame.setVisible(true);
        froggerFrame.setSize(1016, 1024);

        froggerFrame.setLocationRelativeTo(null);   //window will be displayed at the center of screen

        GamePanel gamePanel = new GamePanel();
        froggerFrame.add(gamePanel);
        
        froggerFrame.pack();

        gamePanel.startGameThread();

    }

}
