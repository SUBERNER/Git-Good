package io.github.froggers_revenge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tileMapRenderer;
    private TileMap tileMap = new TileMap();


    private SpriteBatch batch;
    private Texture sheet;
    public TextureRegion frog[]; //frog textures
    public TextureRegion smallCar[]; //cars 1 tile big
    public TextureRegion bigCar; //cars 2 tile big
    public TextureRegion log[]; //log and gator

    @Override
    public void create() { //runs when program is started
        Gdx.graphics.setTitle("Frogger's Revenge");

        camera = new OrthographicCamera(224,224);
        camera.position.set(112,112,0); //sets camera to middle of screen
        camera.update();

        tileMapRenderer = tileMap.setup();
        
        batch = new SpriteBatch(); //draws textures onto the screen
        sheet = new Texture("froggerSpriteSheet.png");

        //creates the textures for everything
        frog = createTextureRegion(sheet, 2, 0, 0, 16, 16, 2);
        smallCar = createTextureRegion(sheet, 4, 0, 90, 16, 16, 2);
        bigCar = new TextureRegion(sheet, 72, 90, 32, 16);
        log = createTextureRegion(sheet, 3, 0, 108, 48, 16, 2);

    }

    @Override
    public void render() { //runs every frame, used for rednering

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f); //background color
        batch.setProjectionMatrix(camera.combined); //zooms camera to make 200x200 seem bigger
        tileMapRenderer.setView(camera);
        tileMapRenderer.render();

        batch.begin(); //between begin and end used to draw and update textures
        batch.draw(frog[0], 112, 112);
        batch.draw(frog[1], 132, 132);
        batch.draw(bigCar, 0, 0);
        batch.draw(smallCar[0], 20, 100);
        batch.draw(smallCar[1], 40, 100);
        batch.draw(smallCar[2], 60, 100);
        batch.draw(smallCar[3], 80, 100);
        batch.end();
    }

    @Override
    public void dispose() { //runs when program ends or scene changes
        batch.dispose();
        sheet.dispose();
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
