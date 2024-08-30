package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CityTextureContainer {
	public static TextureAtlas cityAtlas;
	public static void load()
	{
		cityAtlas = new TextureAtlas(Gdx.files.internal("graphics/cities.atlas"));
	}
	public static void dispose()
	{
		cityAtlas.dispose();
		cityAtlas = null;
	}
	public static TextureRegion findRegion(String name)
	{
		return cityAtlas.findRegion(name);
		
	}
	public static int regiongetOffsetX(String name)
	{
		return cityAtlas.findRegion(name).getRegionX();
		
	}
	public static int regiongetOffsetY(String name)
	{
		return cityAtlas.findRegion(name).getRegionY();
		
	}
}
