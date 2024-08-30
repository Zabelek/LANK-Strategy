package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MissionTextureContainer {
	public static TextureAtlas missionAtlas;
	public static void load()
	{
		missionAtlas = new TextureAtlas(Gdx.files.internal("graphics/missions.atlas"));
	}
	public static void dispose()
	{
		missionAtlas.dispose();
		missionAtlas = null;
	}
	public static TextureRegion findRegion(String name)
	{
		return missionAtlas.findRegion(name);
		
	}
}
