package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GalaxyTextureContainer {
	public static TextureAtlas backgroundAtlas;
	public static TextureAtlas galaxyRegionsAtlas;
	public static TextureAtlas starSystemAtlas;
	
	public static void load()
	{
		backgroundAtlas = new TextureAtlas(Gdx.files.internal("graphics/background.atlas"));
		galaxyRegionsAtlas = new TextureAtlas(Gdx.files.internal("graphics/galaxyregions.atlas"));
		starSystemAtlas = new TextureAtlas(Gdx.files.internal("graphics/star_system.atlas"));
	}
	
	public static void dispose()
	{
		backgroundAtlas.dispose();
		backgroundAtlas = null;
		galaxyRegionsAtlas.dispose();
		galaxyRegionsAtlas = null;
		starSystemAtlas.dispose();
		starSystemAtlas = null;
	}
	public static TextureRegion findRegion(String name)
	{
		return backgroundAtlas.findRegion(name);
		
	}
	public static TextureRegion findGalaxyRegion(String name)
	{
		return galaxyRegionsAtlas.findRegion(name);
		
	}
	public static TextureRegion findStarRegion(String name)
	{
		return starSystemAtlas.findRegion(name);
		
	}
	public static int galaxyRegiongetOffsetX(String name)
	{
		return galaxyRegionsAtlas.findRegion(name).getRegionX();
		
	}
	public static int galaxyRegiongetOffsetY(String name)
	{
		return galaxyRegionsAtlas.findRegion(name).getRegionY();
		
	}	
}
