package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CharacterTextureContainer {
	public static TextureAtlas characterAtlas;
	public static void load()
	{
		characterAtlas = new TextureAtlas(Gdx.files.internal("graphics/characters.atlas"));
	}
	public static void dispose()
	{
		characterAtlas.dispose();
		characterAtlas = null;
	}
	public static TextureRegion findRegion(String name)
	{
		return characterAtlas.findRegion(name);
		
	}
}
