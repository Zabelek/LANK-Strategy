package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LabWojTextureContainer {
	public static TextureAtlas labWojAtlas;
	public static void load()
	{		
		labWojAtlas = new TextureAtlas(Gdx.files.internal("graphics/labwoj.atlas"));
	}
	public static void dispose()
	{
		labWojAtlas.dispose();
		labWojAtlas = null;
	}
	public static TextureRegion findRegion(String name)
	{
		return labWojAtlas.findRegion(name);		
	}
}
