package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpecialEffectTextureContainer {
	public static TextureAtlas specialEffectAtlas;
	public static void load()
	{
		specialEffectAtlas = new TextureAtlas(Gdx.files.internal("graphics/effects.atlas"));
	}
	public static void dispose()
	{
		specialEffectAtlas.dispose();
		specialEffectAtlas = null;
	}
	public static TextureRegion findRegion(String name)
	{
		return specialEffectAtlas.findRegion(name);
		
	}
}
