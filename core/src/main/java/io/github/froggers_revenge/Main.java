package io.github.froggers_revenge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Frogger frogger;
    private MovementControls movementControls;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tileMapRenderer;
    private TileMap tileMap;

    World world; //stores all the physics objects such as logs, cars, rockets, and cars
    Box2DDebugRenderer debugRenderer;


    private SpriteBatch batch;
    private Texture sheet;
    public TextureRegion frog[]; //frog textures
    public TextureRegion smallCar[]; //cars 1 tile big
    public TextureRegion bigCar; //cars 2 tile big
    public TextureRegion log[]; //log and gator

    @Override
    public void create() //runs when program is started
    {
        Gdx.graphics.setTitle("Frogger's Revenge");

        //everything used to create the world
        world = new World(new Vector2(0,0), true);
        debugRenderer = new Box2DDebugRenderer(); //WILL REMOVE AFTR DEBUGGING

        tileMap = new TileMap();
        camera = new OrthographicCamera(224,224);
        camera.position.set(112,112,0); //sets camera to middle of screen
        camera.update();

        tileMapRenderer = tileMap.setup();
        tileMapRenderer.render(); //renders the tiles once allowing for tile data to be edited
        tileMap.setData(); //sets the data of each tile
        
        frogger = new Frogger();
        movementControls = new MovementControls(frogger);
        //this.addKeyListener(movementControls);

        batch = new SpriteBatch(); //draws textures onto the screen
        sheet = new Texture("froggerSpriteSheet.png");
        

        //creates the textures for everything
        frog = createTextureRegion(sheet, 2, 0, 0, 16, 16, 2);
        smallCar = createTextureRegion(sheet, 4, 0, 90, 16, 16, 2);
        bigCar = new TextureRegion(sheet, 72, 90, 32, 16);
        log = createTextureRegion(sheet, 3, 0, 108, 48, 16, 2);

        
       
    }

    @Override
    public void render() //runs every frame, used for rednering
    {
        debugRenderer.render(world, camera.combined);
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f); //background color
        batch.setProjectionMatrix(camera.combined); //zooms camera to make 200x200 seem bigger
        tileMapRenderer.setView(camera);
        tileMapRenderer.render();

        //all the draws are temporary for testing the drawing
        batch.begin(); //between begin and end used to draw and update textures
        batch.draw(frog[0], frogger.getX(), frogger.getY());
        batch.end();

        world.step(1/60f, 6, 2); //updates the world at 60 frames a second
    }

    @Override
    public void dispose() //runs when program ends or scene changes
    { 
        batch.dispose();
        sheet.dispose();
        tileMapRenderer.dispose();
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
