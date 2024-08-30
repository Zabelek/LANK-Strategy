package pl.zabel.lank.userinterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.galaxyview.GalaxyRegion;
import pl.zabel.lank.gameplayobjects.galaxyview.GalaxyRegion.RegionCaptureState;
import pl.zabel.lank.texturecontainers.GalaxyTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.GalaxyView;

public class RegionIcon extends Actor{
	
	TextureRegion region;
	Color currentRegionColor;
	float fullX, fullY, offsetX, offsetY;
	private GalaxyRegion galaxyRegion;
	
	public RegionIcon(String name, float x, float y, float width, float height, GalaxyRegion galaxyRegion)
	{
		this.galaxyRegion = galaxyRegion;
		this.region = GalaxyTextureContainer.findGalaxyRegion(name);
		this.setBounds(x*GameVariables.getScaleX(), y*GameVariables.getScaleY(), width*GameVariables.getScaleX(), height*GameVariables.getScaleY());
		this.currentRegionColor = new Color();
		this.currentRegionColor.a=0.5f;
		changeColor();
		this.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				changeColor();
				currentRegionColor.a=0.7f;
				event.stop();
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				changeColor();
				currentRegionColor.a=0.5f;
				event.stop();
			} 	
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if(RegionIcon.this.galaxyRegion.getCaptureState() == RegionCaptureState.NOT_AVAILABLE)
					SoundEffectManager.playClickLockedSound();
				else
					SoundEffectManager.playGalaxyNavigationSound();
				event.stop();
			}
		});
		fullX = GalaxyTextureContainer.galaxyRegiongetOffsetX(name);
		fullY = GalaxyTextureContainer.galaxyRegiongetOffsetY(name);
	}
	public void changeColor()
	{
		RegionCaptureState state = this.galaxyRegion.getCaptureState();
		if(state==RegionCaptureState.NOT_AVAILABLE)
		{
			currentRegionColor.r=0.6f;
			currentRegionColor.g=0f;
			currentRegionColor.b=0f;
		}
		if(state==RegionCaptureState.AVAILABLE)
		{
			currentRegionColor.r=1f;
			currentRegionColor.g=0.1f;
			currentRegionColor.b=0.1f;
		}
		if(state==RegionCaptureState.CAPTURED)
		{
			currentRegionColor.r=0.2f;
			currentRegionColor.g=1f;
			currentRegionColor.b=0.2f;
		}
	}
	public GalaxyRegion getGalaxyRegion() {
		return galaxyRegion;
	}
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && this.getTouchable() != Touchable.enabled) return null;
		if (!isVisible()) return null;
		if (x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight()) {
			float scalex = (this.region.getRegionWidth()/this.getWidth());
			float scaley = (this.region.getRegionHeight()/this.getHeight());			
			x = fullX+((x*scalex));
			y = fullY+(((this.region.getRegionHeight()-(y*scaley))));
			Color test = new Color(GalaxyView.texturesPixmap.getPixel((int) x, (int) y));
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
		batch.setColor(currentRegionColor.r, currentRegionColor.g, currentRegionColor.b, currentRegionColor.a*alpha*GameVariables.getStageFadingAlpha());
		batch.draw(region, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		batch.setColor(1, 1, 1, 1);
	}

}
