package pl.zabel.lank.userinterface;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;

public class MenuImageButton extends Actor{
	
	private TextureRegion region;
	private String name;
	
	public MenuImageButton(String name, float x, float y, float width, float height)
	{
		this.region = InterfaceTextureContainer.findRegion(name);
		this.name = name;
		this.setBounds(x, y, width, height);
		this.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{

				region = InterfaceTextureContainer.findRegion(MenuImageButton.this.name+"_hover");
				event.stop();
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				region = InterfaceTextureContainer.findRegion(MenuImageButton.this.name);
				event.stop();
			} 			
		});
	}
	@Override
	public void draw(Batch batch, float alpha)
	{
		batch.draw(region, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
