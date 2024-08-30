package pl.zabel.lank.gameplayobjects.mapview;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import pl.zabel.lank.utilities.JsonHandler;

public class GameplayMap{
	
	private AssetManager manager;
	private TiledMap tiledMap;
	private int[][] tileIdTab;
	private int mapWidth, mapHeight, playerStartX, playerStartY, enemyStartX, enemyStartY;	
	public GameplayMap(String name)
	{
		manager = new AssetManager();
	    manager.setLoader(TiledMap.class, new TmxMapLoader());
	    manager.load("data/maps/"+name+".tmx", TiledMap.class);
	    manager.finishLoading();	 
	    tiledMap = manager.get("data/maps/"+name+".tmx", TiledMap.class);
	    TiledMapTileLayer lay = (TiledMapTileLayer) tiledMap.getLayers().get(0);
	    this.tileIdTab = new int[lay.getWidth()][lay.getHeight()];
	    mapWidth = (Integer) this.tiledMap.getProperties().get("width");
		mapHeight = (Integer) this.tiledMap.getProperties().get("height");
		for (int i = 0; i < mapHeight; ++i) {
			for (int j = 0; j < mapWidth; ++j) {
				Cell cell = lay.getCell(j, mapHeight - i);
				if (cell == null)
					continue;
				tileIdTab[j][i - 1] = (cell.getTile().getId());
			}
		}
		for (int i = 0; i < mapWidth; ++i)
			tileIdTab[0][i] = 0;
		
	}
	public void dispose()
	{
		manager.dispose();
	}
	public TiledMap getTiledMap() {
		return tiledMap;
	}
	public boolean isEnterableArea(float x, float y)
	{
		try {
			int tile = tileIdTab[(int) x / 100][(mapHeight-1) - (int) y / 100];
			if (tile%10 == 1 || tile == 4 || tile%10 == 5)
				return true;
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}	
	}
	public int getTileValue(float x, float y)
	{
		try {
			return tileIdTab[(int) x / 100][(mapHeight-1) - (int) y / 100];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return 0;
		}
	}
	public void loadSpawnPoints(ArrayList<Integer> points) {
		playerStartX = points.get(0);
		playerStartY = points.get(1);
		enemyStartX = points.get(2);
		enemyStartY = points.get(3);		
	}
	public int getPlayerStartX() {
		return playerStartX;
	}
	public int getPlayerStartY() {
		return playerStartY;
	}
	public int getEnemyStartX() {
		return enemyStartX;
	}
	public int getEnemyStartY() {
		return enemyStartY;
	}
}
