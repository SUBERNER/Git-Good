package io.github.froggers_revenge;
import io.github.froggers_revenge.Objects.*;
import io.github.froggers_revenge.Spawners.*;
import io.github.froggers_revenge.Utility.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Frogger frogger;
    private MovementControls movementControls;

    //all the hazard spawners
    VehicleSpawner[] vehicleSpawners;
    LogSpawner[] logSpawners;
    TurtleSpawner[] turtleSpawners;

    //handles UserInterface
    private Stage stage;
    private UserInterface userInterface;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tileMapRenderer;
    private TileMap tileMap;
    public Score score;
    public Timer timer;

    World world; //stores all the physics objects such as logs, cars, rockets, and cars

    static Texture sheet;
    private SpriteBatch batch;
    public TextureRegion[] smallCar; //cars 1 tile big
    public TextureRegion bigCar; //cars 2 tile big
    public TextureRegion[] log; //log and gator
    public TextureRegion[] turtle; //turtles

    public Sound[] destroySounds; //sounds that can play when object is destroyed
    public Music music; //used to play music on loop

    List<Explosion> explosions = new ArrayList<>(); //used for explosions
    List<ObjectMover> objects = new ArrayList<>(); //used to check for collision
    Random random = new Random(); // creates object for randomizing

    @Override
    public void create() //runs when program is started
    {
        Gdx.graphics.setTitle("Frogger's Revenge"); //names the title at the top of the window

        //everything used to create the world
        world = new World(new Vector2(0,0), true); //handles physics of objects

        //setup and displaying timer <TEMP FOR NOW> 
        timer = new Timer(60); //creates and sets the time liit to 60 seconds
        System.out.println("CURRENT TIME: " + timer.getCurrentTime()); //displays current time
        System.out.println("MAX TIME: " + timer.getMaxTime()); //displays the highest number time can be

        //setup and displaying highscore <TEMP FOR NOW>
        score = new Score();
        System.out.println("HIGHSCORE: " + score.getHighScore()); //displays the highscore from the saved data file HighScore.txt
        System.out.println("CURRENT SCORE: " + score.getScore()); //displays the current score based on event in game, should be 0 

        //everything used to render UI
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        userInterface = new UserInterface(score.getScore(), score.getHighScore(), timer.getMaxTime());
        stage.addActor(userInterface.getTable());

        tileMap = new TileMap(); //creates the timemap to store and handle all things involving the tile map
        camera = new OrthographicCamera(224,240); //creates the camera/view used to see the game
        camera.position.set(112,120,0); //sets camera to middle of screen
        camera.update(); //updates position and traits of camera so see the scene

        tileMapRenderer = tileMap.setup(); //assigns tiles to tilemap and converts to tilemaprenderer
        tileMapRenderer.render(); //renders the tiles once allowing for tile data to be edited
        tileMap.setData(); //sets the data and parameters of each tile

        batch = new SpriteBatch(); //used for drawing textures onto the screen
        sheet = new Texture("froggerSpriteSheet.png"); //the sprite sheet used to get sprites and textures from
        
        frogger = new Frogger(112, 0, 8, 8, 16, createTextureRegion(sheet, 2, 0, 0, 16, 16, 2), createTextureRegion(sheet, 2, 0, 36, 16, 16, 2), createTextureRegion(sheet, 7, 0, 54, 16, 16, 2));
        movementControls = new MovementControls(frogger); //handles movement and stores the object taking and using the inputs

        //sets up collision sounds
        destroySounds = new Sound[3]; //randomized collison sounds
        destroySounds[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/retro_impact_hit_general_01.wav"));
        destroySounds[1] = Gdx.audio.newSound(Gdx.files.internal("sounds/retro_impact_hit_general_02.wav"));
        destroySounds[2] = Gdx.audio.newSound(Gdx.files.internal("sounds/retro_impact_hit_general_03.wav"));

        //sets up music
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/frogger-music.wav"));
        music.play();
        music.setLooping(true);


        //creates the textures for everything using the spritesheet
        smallCar = createTextureRegion(sheet, 4, 0, 90, 16, 16, 2);
        bigCar = new TextureRegion(sheet, 72, 90, 32, 16);
        log = createTextureRegion(sheet, 3, 0, 108, 48, 16, 2);
        turtle = createTextureRegion(sheet, 5, 0, 126, 16, 16, 2);

        //sets up spawners
        //creates each spawner and creates there location of spawning, rotation, and what kind of car they are spawning
        vehicleSpawners = new VehicleSpawner[5]; //number of vehicle spawners
        vehicleSpawners[0] = new VehicleSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,50), true, 180, -30, 80, bigCar, 40f);
        vehicleSpawners[1] = new VehicleSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,50), true, 0, 225, 64, smallCar[2], 40f);
        vehicleSpawners[2] = new VehicleSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,50), true, 180, -30, 48, smallCar[0], 40f);
        vehicleSpawners[3] = new VehicleSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,50), true, 0, 225, 32, smallCar[3], 40f);
        vehicleSpawners[4] = new VehicleSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,50), true, 180, -30, 16, smallCar[1], 40f);

        //creates each spawner and creates there location of spawning and rotation of spawning
        logSpawners = new LogSpawner[3]; //number of log spawners
        logSpawners[0] = new LogSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,100), true, 0, 225 ,128, log, 35f);
        logSpawners[1] = new LogSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,100), true, 0, 225 ,144, log, 35f);
        logSpawners[2] = new LogSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,100), true, 0, 225 ,176, log, 35f);

        turtleSpawners = new TurtleSpawner[2]; //number of turtle spawners
        turtleSpawners[0] = new TurtleSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,100), true, 180, -20 ,112, turtle, 45f);
        turtleSpawners[1] = new TurtleSpawner(random.nextFloat(1.5f, 4), random.nextInt(0,100), true, 180, -20 ,160, turtle, 45f);


        //starts all the spawners
        //starts the objects cycles to spawn objects
        for (VehicleSpawner vs: vehicleSpawners) { //vehicles
            vs.StartSpawning();
        }
        for (LogSpawner ls: logSpawners) { //logs
            ls.StartSpawning();
        }
        for (TurtleSpawner ts: turtleSpawners) { //turtles
            ts.StartSpawning();
        }

        
    }

    @Override
    public void render() //runs every frame, used for rednering
    {
        objects.clear(); //clears objects to update the group of objects

        //updates timer
        timer.updateTime();
        float deltaTime = Gdx.graphics.getDeltaTime(); //fixed time used to make objects move con

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f); //background color
        batch.setProjectionMatrix(camera.combined); //zooms camera to make 200x200 seem bigger
        tileMapRenderer.setView(camera);
        tileMapRenderer.render();

        //between begin and end used to draw and update textures
        batch.begin();

        updateHazards(deltaTime);
        updateProjectiles(deltaTime);
        updateExplosions(deltaTime);

        frogger.getSprite().draw(batch);

        batch.end();

        movementControls.keyReleased(null); //gets player input
        //tests how frogger is interacting with the world
        frogger.spriteState(deltaTime);
        frogger.UpdateMoving(deltaTime);
        frogger.updateCooldown(deltaTime); //updates cooldown between bullet shots
        frogger.updateHitbox();
        frogger.updateCooldown(deltaTime);
        frogger.checkCollision(objects);
        frogger.checkTile(tileMap);

        //used for drawing UI to the screen
        userInterface.UpdateLabels(score.getScore(), score.getHighScore(), timer.getCurrentTime());
        stage.act(deltaTime); // Update stage actors
        stage.draw();

        if (timer.getCurrentTime() <= 1) //kills frog if time runs out
        {
            frogger.death();
        }

        world.step(1/60f, 6, 2); //updates the world at 60 frames a second
    }

    @Override
    public void dispose() //runs when program ends or scene changes
    { 
        score.setHighScore(score.getScore(), false);
        batch.dispose();
        sheet.dispose();
        tileMapRenderer.dispose();
    }

    private void updateProjectiles(float deltaTime) {
        Iterator<Projectile> projectileIterator = frogger.projectiles.iterator();
    
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
    
            // Draw the projectile
            projectile.getSprite().draw(batch);
    
            // Update projectile position
            projectile.moveObject(deltaTime);
    
            // Check if projectile is out of bounds or expired
            if ((projectile.getDuration() <= 0) || ((projectile.getSprite().getX() >= 234 || projectile.getSprite().getX() <= -11) || (projectile.getSprite().getY() >= 234 || projectile.getSprite().getY() <= -11)) || projectile.checkCollision(objects)) {
                explosions.add(projectile.dispose());
                projectileIterator.remove();
            }
        }
    }

    // used to update the movement of all projectiles
    private void updateHazards(float deltaTime)
    {
        // Update vehicles
        for (VehicleSpawner vs : vehicleSpawners) {
            Iterator<Vehicle> vehicleIterator = vs.vehicles.iterator();
            objects.addAll(vs.vehicles); // adds all to list for frogger

            while (vehicleIterator.hasNext()) {
                Vehicle vehicle = vehicleIterator.next();

                // Check collisions for projectiles or explosions
                if (vehicle.checkCollision(frogger.projectiles, explosions)) {
                    score.addScore(100);
                    System.out.println("SCORE: " + score.getScore());

                    
                    destroySounds[random.nextInt(destroySounds.length)].play(0.5f);
                    vehicleIterator.remove();
                    continue;
                }

                vehicle.moveObject(deltaTime);

                // Remove vehicles out of bounds
                if ((vehicle.getSprite().getX() >= 324 || vehicle.getSprite().getX() <= -101) || (vehicle.getSprite().getY() >= 324 || vehicle.getSprite().getY() <= -101)) {
                    vehicleIterator.remove();
                }

                // Draw the vehicle
                vehicle.getSprite().draw(batch);
            }
        }

        // Update turtles
        for (TurtleSpawner ts : turtleSpawners) {
            Iterator<Turtle> turtleIterator = ts.turtles.iterator();
            objects.addAll(ts.turtles); // adds all to list for frogger

            while (turtleIterator.hasNext()) {
                Turtle turtle = turtleIterator.next();

                // Check collisions (explosions)
                if (turtle.checkCollision(explosions)) {
                    score.addScore(200);
                    System.out.println("SCORE: " + score.getScore());

                    destroySounds[random.nextInt(destroySounds.length)].play(0.5f);
                    turtleIterator.remove();
                    continue;
                }

                // Update turtle position
                turtle.moveObject(deltaTime);

                // Remove turtles out of bounds
                if ((turtle.getSprite().getX() >= 324 || turtle.getSprite().getX() <= -101) || (turtle.getSprite().getY() >= 324 || turtle.getSprite().getY() <= -101)) {
                    turtleIterator.remove();
                }

                // Draw the turtle
                turtle.getSprite().draw(batch);
            }
        }

        for (LogSpawner ls : logSpawners) {
            Iterator<Log> logIterator = ls.logs.iterator();
            objects.addAll(ls.logs); // adds all to list for frogger

            while (logIterator.hasNext()) {
                Log log = logIterator.next();

                // Check collisions for projectiles or explosions
                if (log.checkCollision(explosions)) {
                    score.addScore(200);
                    System.out.println("SCORE: " + score.getScore());

                    destroySounds[random.nextInt(destroySounds.length)].play(0.5f);
                    logIterator.remove();
                    continue;
                }

                log.moveObject(deltaTime);

                // Remove vehicles out of bounds
                if ((log.getSprite().getX() >= 324 || log.getSprite().getX() <= -101) || (log.getSprite().getY() >= 324 || log.getSprite().getY() <= -101)) {
                    logIterator.remove();
                }

                // Draw the vehicle
                log.getSprite().draw(batch);
            }
        }
    }


    // used to update the movement of all projectiles
    private void updateExplosions(float deltaTime)
    {
        Iterator<Explosion> explosionIterator = explosions.iterator();
        
        // updates explosion sizes
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.getSprite().draw(batch);
            explosion.UpdateExplosion(deltaTime);

            // test if explosion has lasted too long
            if (explosion.getSize() <= 0) {
                explosionIterator.remove();
                continue;
            }
        }
    }

    /* 
    creates new texture regions
    (this can only scan textures form left to right, not right to left or up and down)
    sheet: the sheet being textured
    size: the amount of textures it will have
    startX: the starting position on the sheet to start creating textures on the X plane
    startY: the starting position on the sheet to start creating textures on the Y plane
    width: width of each texture
    hight: hight of each texture
    padding: the space(pixels) between each texture
    */
    public TextureRegion[] createTextureRegion(Texture sheet, int size, int startX, int startY, int width, int hight, int padding) {

        TextureRegion[] region = new TextureRegion[size];
        int currentX = startX;
        int currentY = startY;

        for(int i = 0; i < size; i++)
        {
            region[i] = new TextureRegion(sheet, currentX, currentY, width, hight);
            currentX += width + padding;
        }

        return region;
    }
}
