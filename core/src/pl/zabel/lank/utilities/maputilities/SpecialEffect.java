package pl.zabel.lank.utilities.maputilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import pl.zabel.lank.texturecontainers.SpecialEffectTextureContainer;
import pl.zabel.lank.views.MapView;

public class SpecialEffect extends Actor{
	
	private MapView mapView;
	private TextureRegion displayTexture;
	private int timeKey = 0, frameNumber;
	private boolean repeat;
	protected String key;
	
	public SpecialEffect(float posX, float posY, int frameNumber, MapView mapView)
	{
		this("effect", posX, posY, 100, 100, 0, frameNumber, false, mapView);
	}
	public SpecialEffect(String key, float posX, float posY, float height, float width, float rotation, int frameNumber, boolean repeat, MapView mapView)
	{
		this.mapView = mapView;
		this.setTouchable(Touchable.disabled);
		this.setBounds(posX, posY, width, height);
		this.setRotation(rotation);
		this.frameNumber = frameNumber;
		this.repeat = repeat;
		this.key = key;
		//this.setOrigin(this.getWidth()/2,  this.getHeight()/2);
		this.displayTexture = SpecialEffectTextureContainer.findRegion(this.key+timeKey);
		this.mapView.registerSpecialEffect(this);
	}
	public void nextFrame()
	{
		if(frameNumber>this.timeKey)
		{
			this.timeKey+=1;
			this.displayTexture = SpecialEffectTextureContainer.findRegion(this.key+timeKey);
		}
		else if(repeat)
		{
			timeKey=0;
			this.displayTexture = SpecialEffectTextureContainer.findRegion(this.key+timeKey);
		}
		else
		{
			this.dispose();
		}
	}
	public void dispose()
	{
		this.mapView.removeSpecialEffect(this);
	}
	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(this.displayTexture,
				this.getX(), this.getY(),
				this.getWidth()/2, this.getHeight()/2, this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}
}
