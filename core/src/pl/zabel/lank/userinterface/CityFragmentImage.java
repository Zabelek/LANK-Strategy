package pl.zabel.lank.userinterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.CityTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.CityView;

public class CityFragmentImage extends Actor{
	
	private boolean clickable, hovered;
	private TextureRegion displayRegion, hoverRegion, shadowRegion;
	private float fullX, fullY;
	
	public CityFragmentImage(String name, boolean clickable, float x, float y, float w, float h)
	{
		this.clickable=clickable;
		this.displayRegion = CityTextureContainer.findRegion(name);
		this.setBounds(x*GameVariables.getScaleX(), y*GameVariables.getScaleY(), w*GameVariables.getScaleX(), h*GameVariables.getScaleY());
		this.setTouchable(Touchable.disabled);
		if (this.clickable == true)
		{
			this.hoverRegion = CityTextureContainer.findRegion(name+"_hover");
			this.addListener(new ClickListener()
			{
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
				{

					hovered = true;
					event.stop();
				}
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
				{
					hovered = false;
					event.stop();
				} 
				@Override
				public void clicked (InputEvent event, float x, float y) {
					SoundEffectManager.playSmallClickSound();
					event.stop();
				}
			});
			this.setTouchable(Touchable.enabled);
			fullX = CityTextureContainer.regiongetOffsetX(name);
			fullY = CityTextureContainer.regiongetOffsetY(name);
		}
		this.shadowRegion = CityTextureContainer.findRegion(name+"_shadow");
		hovered = false;

	}
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && this.getTouchable() != Touchable.enabled) return null;
		if (!isVisible()) return null;
		if (x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight()) {
			float scalex = (this.displayRegion.getRegionWidth()/this.getWidth());
			float scaley = (this.displayRegion.getRegionHeight()/this.getHeight());			
			x = fullX+((x*scalex));
			y = fullY+(((this.displayRegion.getRegionHeight()-(y*scaley))));
			Color test = new Color(CityView.texturesPixmap.getPixel((int) x, (int) y));
			if (test.a>0.5f)
				return this;
			else
				return null;
		} else
			return null;

	}
	@Override
	public void draw(Batch batch, float alpha)
	{
		if(shadowRegion!=null)
			batch.draw(shadowRegion, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		if(hovered)
		{
			batch.draw(hoverRegion, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}
		batch.draw(displayRegion, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

}
