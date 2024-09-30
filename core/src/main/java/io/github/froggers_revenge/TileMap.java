package io.github.froggers_revenge;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMap {
    
    private TiledMap tileMap;
    
    public OrthogonalTiledMapRenderer setup() {
        tileMap = new TmxMapLoader().load("maps/stage.tmx");
        return new OrthogonalTiledMapRenderer(tileMap);
    }
}
