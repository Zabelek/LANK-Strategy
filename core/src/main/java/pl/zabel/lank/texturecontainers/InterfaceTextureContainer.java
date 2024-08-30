package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class InterfaceTextureContainer {
	
	public static TextureAtlas interfaceAtlas;
	
	public static void load()
	{
		interfaceAtlas = new TextureAtlas(Gdx.files.internal("graphics/interface.atlas"));
	}
	
	public static void dispose()
	{
		interfaceAtlas.dispose();
		interfaceAtlas = null;
	}
	public static TextureRegion findRegion(String name)
	{
		return interfaceAtlas.findRegion(name);
	}

}
