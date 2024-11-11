package io.github.froggers_revenge;
import io.github.froggers_revenge.Objects.*;
import io.github.froggers_revenge.Spawners.*;
import io.github.froggers_revenge.Utility.*;

import java.util.Iterator;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Frogger frogger;
    private MovementControls movementControls;

    //all the hazard spawners
    VehicleSpawner[] vehicleSpawners;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tileMapRenderer;
    private TileMap tileMap;
    public Score score;
    public Timer timer;
    private Collision collision;

    World world; //stores all the physics objects such as logs, cars, rockets, and cars

    static Texture sheet;
    private SpriteBatch batch;
    public TextureRegion[] smallCar; //cars 1 tile big
    public TextureRegion bigCar; //cars 2 tile big
    public TextureRegion[] log; //log and gator

    @Override
    public void create() //runs when program is started
    {
        Gdx.graphics.setTitle("Frogger's Revenge");

        //everything used to create the world
        world = new World(new Vector2(0,0), true);

        //setup and displaying timer <TEMP FOR NOW> 
        timer = new Timer(60);
        System.out.println("CURRENT TIME: " + timer.getCurrentTime());
        System.out.println("MAX TIME: " + timer.getMaxTime());

        //setup and displaying highscore <TEMP FOR NOW>
        score = new Score();
        System.out.println("HIGHSCORE: " + score.getHighScore());
        System.out.println("CURRENT SCORE: " + score.getScore());

        tileMap = new TileMap();
        camera = new OrthographicCamera(224,224);
        camera.position.set(112,112,0); //sets camera to middle of screen
        camera.update();

        tileMapRenderer = tileMap.setup();
        tileMapRenderer.render(); //renders the tiles once allowing for tile data to be edited
        tileMap.setData(); //sets the data of each tile

        batch = new SpriteBatch(); //draws textures onto the screen
        sheet = new Texture("froggerSpriteSheet.png");
        
        frogger = new Frogger(112, 0, 16, 16, 16, createTextureRegion(sheet, 2, 0, 0, 16, 16, 2), createTextureRegion(sheet, 2, 0, 36, 16, 16, 2));
        movementControls = new MovementControls(frogger);

        //creates the textures for everything
        smallCar = createTextureRegion(sheet, 4, 0, 90, 16, 16, 2);
        bigCar = new TextureRegion(sheet, 72, 90, 32, 16);
        log = createTextureRegion(sheet, 3, 0, 108, 48, 16, 2);

        //sets up spawners
        vehicleSpawners = new VehicleSpawner[5]; //number of spawners
        vehicleSpawners[0] = new VehicleSpawner(3, 10, true, 180, -30, 80, bigCar, 0.5f);
        vehicleSpawners[1] = new VehicleSpawner(3, 10, true, 0, 225, 64, smallCar[2], 0.5f);
        vehicleSpawners[2] = new VehicleSpawner(3, 10, true, 180, -30, 48, smallCar[0], 0.5f);
        vehicleSpawners[3] = new VehicleSpawner(3, 10, true, 0, 225, 32, smallCar[3], 0.5f);
        vehicleSpawners[4] = new VehicleSpawner(3, 10, true, 180, -30, 16, smallCar[1], 0.5f);

        //sets up collision detection
        collision = new Collision();
        collision.setFrogHitbox(frogger.getHitbox());


        //starts all the spawners
        for (VehicleSpawner vs: vehicleSpawners) {
            vs.StartSpawning();
        }
    }

    @Override
    public void render() //runs every frame, used for rednering
    {
        //updates timer
        timer.updateTime();
        System.out.println("CURRENT TIME: " + timer.getCurrentTime());

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f); //background color
        batch.setProjectionMatrix(camera.combined); //zooms camera to make 200x200 seem bigger
        tileMapRenderer.setView(camera);
        tileMapRenderer.render();

        movementControls.keyReleased(null); //gets player input
        //tests how frogger is interacting with the world
        frogger.checkTile(tileMap);
        frogger.checkCollision(collision);

        //all the draws are temporary for testing the drawing
        batch.begin(); //between begin and end used to draw and update textures
        frogger.getSprite().draw(batch);
        frogger.updateHitbox();

        updateCollision();
        updateProjectiles();
        updateHazards();

        batch.end();


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

    //used to update the movement of all projectiles
    private void updateProjectiles()
    {
        Iterator<Projectile> projectileIterator = frogger.projectiles.iterator();
        
        //updates each projectile and where they are moving
        while (projectileIterator.hasNext()) {
            Projectile p = projectileIterator.next();
            p.getSprite().draw(batch);
            p.moveObject(); // Moves the projectile
            //test if projectiles is out of bounds
            if ((p.getSprite().getX() >= 234 || p.getSprite().getX() <= -11) || (p.getSprite().getY() >= 234 || p.getSprite().getY() <= -11)) {
                projectileIterator.remove();
            }
        }
    }

    //used to update the movement and spawning of hazards
    private void updateHazards()
    {
        //gose through each spawner and updates the objects
        for (VehicleSpawner vs: vehicleSpawners) {
            Iterator<Vehicle> vehicleIterator = vs.vehicles.iterator();
            
            while (vehicleIterator.hasNext()) {
                Vehicle v = vehicleIterator.next();
                
                if ((collision.testTargets(v.getHitbox(), collision.getProjectileHitboxs())) == true) //test if vehicle has collided with projectile
                {
                    score.addScore(100);
                    System.out.println("SCORE: " + score.getScore());

                    vehicleIterator.remove();
                    continue; //ends loop early
                }
                
                v.getSprite().draw(batch);
                v.moveObject(); // Moves the vehicle
                //test if vehicle is out of bounds
                if ((v.getSprite().getX() >= 324 || v.getSprite().getX() <= -101) || (v.getSprite().getY() >= 324 || v.getSprite().getY() <= -101)) {
                    vehicleIterator.remove();
                }
            }
        }
    }

    //updates the locations of all hitboxes
    private void updateCollision()
    {
        collision.setFrogHitbox(frogger.getHitbox());

        collision.getVehicleHitboxs().clear();
        for (VehicleSpawner vs: vehicleSpawners) {
            for (Vehicle v: vs.vehicles) {
                collision.addVehicleHitboxs(v.getHitbox());
            }
        }

        collision.getProjectileHitboxs().clear();
        for (Projectile p: frogger.projectiles) {
            collision.addProjectileHitboxs(p.getHitbox());
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
