package io.github.froggers_revenge;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


/**
 * This class manages the entirety of the tile map
 */
public class TileMap {
    
    private TiledMap map; //stores the tilemap
    private TiledMapTileLayer[] layers = new TiledMapTileLayer[2]; //0: ground, 1:background
    
    /**
     * This method sets up the map/stage texture.
     */
    public OrthogonalTiledMapRenderer setup() {
        map = new TmxMapLoader().load("maps/stage.tmx");
        return new OrthogonalTiledMapRenderer(map);
    }
    
    
    /**
     * This method alters the tile data to enable tile data on specific tiles
     */
    public void setData()
    {
        layers[0] = (TiledMapTileLayer)map.getLayers().get("Ground"); //ground will have the data the frog will interact with
        layers[1] = (TiledMapTileLayer)map.getLayers().get("Background"); //only makes better blending for ground tiles
        
        int columns = layers[0].getWidth();
        int rows = layers[0].getHeight(); 

        //goes through every single tile on the tilemap
        for(int c = 0; c < columns; c++)
        {
            for(int r = 0; r < rows; r++)
            {   
                //tiles will always be in a cell but a cell will not always have a tile
                TiledMapTileLayer.Cell cell = layers[0].getCell(c, r); //finds the cell to alter
                if (cell != null) //test if cell exists
                { 
                    TiledMapTile tile = cell.getTile(); //gets the tile in the cell to alter
                
                    //tests and sets cell as deadly, killing the frog
                    //the tiles with the river
                    if(r >= 7 && r <= 11) 
                    { 
                        tile.getProperties().put("deadly", true);
                    }
                    
                    //tests and sets cell as revenge, giving the frog a gun
                    //the tiles of grass behind the river
                    if(r == 12 || r == 13) 
                    {
                        tile.getProperties().put("revange", true); 
                    }
                }
            
            }
        }
    } 


    
    /**
     * This method will determine if frogger is on a deadly tile or not.
     * 
     * @param x x position of the tile
     * @param y y position of the tile
     * @return returns a boolean of whether the tile frogger is on is "deadly" or not
     */
    public boolean isDeadly(int x, int y)
    {
        //tests if the tile is deadly
        //if so kill frog
        TiledMapTileLayer.Cell cell = layers[0].getCell(convert(x), convert(y)); //gets the cell to get the tile
        if (cell != null)
        {
            TiledMapTile tile = cell.getTile(); //gets the tile to check from the cell
            if (tile != null && tile.getProperties().containsKey("deadly"))
            {
                if (tile.getProperties().get("deadly",boolean.class) == true) { return true; }
            }
        }
        return false;
    }

    //gets if tile activates revenge mode for the frog
    //if true, that means the frog is on the tile and false if not
    //if it detects the frog is on the correct tile,
    //it will give the frog revenge mode
    //THE X AND Y ARE NOT THE TILE ON BUT THE POSITION OF THE OBJECT ON THE TILE
    /**
     * This method will determine whether frogger is on a tile that starts revenge mode (highest row of tiles).
     * 
     * @param x x position of the tile being checked
     * @param y y position of the tile being checked
     * @return returns a boolean of whether the tile grants revenge mode or not
     */
    public boolean isRevange(int x, int y)
    {
        //tests if the tile is revenge
        //if so give the frog the gun
        TiledMapTileLayer.Cell cell = layers[0].getCell(convert(x), convert(y)); //gets the cell to get the tile
        if (cell != null)
        {
            TiledMapTile tile = cell.getTile(); //gets the tile to check from the cell
            if (tile != null && tile.getProperties().containsKey("revange"))
            {
                if (tile.getProperties().get("revange",boolean.class) == true) { return true; }
            }
        }
        return false;
    }

    //converts the position relative to the world to the tiles (#/16)
    //example: if frog is on 35, then the frog is on tile 2
    /**
     * This method converts the position relative to the world to the tiles. (#/16)
     * 
     * @param number position relative to the world
     * @return returns the position of frogger relative to the tiles
     */
    public int convert(int number) { return number / 16; }
    
}
