package io.github.froggers_revenge;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class MainTest {

    private Main main;

    @Before
    public void setup() {
        // Mock Gdx.graphics to avoid null pointer exceptions when Gdx is used in the create method
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(800);  // Mocking the screen width
        when(Gdx.graphics.getHeight()).thenReturn(600); // Mocking the screen height

        // Mock or initialize other dependencies, like score and timer
        main = new Main();  // Initialize Main object for each test
        main.create(); // Call create method (with mocked graphics)
    }

    @Test
    public void testCreate() {
        // Ensure that the Main object is created correctly
        assertNotNull(main);
    }

    @Test
    public void testScore() {
        // Example test to check if score is initialized correctly
        assertNotNull(main.score);  // Assuming 'score' is a field in Main
    }

    @Test
    public void testTimer() {
        // Ensure that the timer is running and initialized correctly
        assertTrue(main.timer.getCurrentTime() > 0);  // Assuming 'timer' is a field in Main
    }

    // Add any other tests if necessary
}