package pl.zabel.lank.userinterface;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;

public class MenuButton extends Actor{
	
	protected TextureRegion background, leftBorder, rightBorder;
	protected Label textLabel;
	protected boolean locked;
	protected float borderW,rightBorderX,backgroundY,backgroundH;
	
	public MenuButton(String title, float x, float y)
	{
		this.textLabel = new Label(title, TextStylesContainer.genericButtonStyle);
		this.setBounds(x, y, calculateWidth(title)*GameVariables.getScaleX(), 70*GameVariables.getScaleY());
		textLabel.setBounds(x, y, this.getWidth(), this.getHeight());
		textLabel.setAlignment(Align.center);
		unlock();
		this.setUpListeners();
		this.locked=false;
		this.setPartBounds();
	}
	public MenuButton()
	{}
	
	protected void setPartBounds()
	{
		borderW = (40*GameVariables.getScaleX());
		rightBorderX = this.getX()+this.getWidth()-(40*GameVariables.getScaleX());
		backgroundY = this.getY()+(7*GameVariables.getScaleY());
		backgroundH = this.getHeight()-(14*GameVariables.getScaleY());
	}
	protected void setUpListeners()
	{
		this.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				if (locked==false)
					background = InterfaceTextureContainer.findRegion("text-button-background-hover");
				event.stop();
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				if (locked==false)
					background = InterfaceTextureContainer.findRegion("text-button-background-normal");
				event.stop();
			}
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if(MenuButton.this.locked == false)
					SoundEffectManager.playClickSound();
				else
					SoundEffectManager.playClickLockedSound();
				event.stop();
			}
		});
	}
	protected float calculateWidth(String text)
	{
		int temp=text.length();
		if(temp>6)
		{
			if(temp>10)
				return (180+((temp-6)*20));
			else
				return (180+((temp-6)*17));
		}
		return 180;
	}
	public void lock()
	{
		this.locked = true;
		this.setTouchable(Touchable.disabled);
		this.leftBorder = InterfaceTextureContainer.findRegion("text-button-left-border-locked");
		this.rightBorder = InterfaceTextureContainer.findRegion("text-button-right-border-locked");
		this.background = InterfaceTextureContainer.findRegion("text-button-background-locked");
	}
	public void unlock()
	{
		this.leftBorder = InterfaceTextureContainer.findRegion("text-button-left-border");
		this.rightBorder = InterfaceTextureContainer.findRegion("text-button-right-border");
		this.background = InterfaceTextureContainer.findRegion("text-button-background-normal");
		this.locked = false;
		this.setTouchable(Touchable.enabled);
	}
	@Override
	public void setX(float x)
	{
		super.setX(x);
		setPartBounds();
	}
	@Override
	public void setY(float y)
	{
		super.setY(y);
		setPartBounds();
	}
	@Override
	public void setWidth(float w)
	{
		super.setWidth(w);
		setPartBounds();
	}
	@Override
	public void setHeight(float h)
	{
		super.setHeight(h);
		setPartBounds();
	}
	@Override
	public void setPosition(float x, float y)
	{
		this.textLabel.setPosition(x, y);
		super.setPosition(x, y);
		setPartBounds();
	}
	@Override
	public void setSize(float w, float h)
	{
		this.textLabel.setSize(w, h);
		super.setSize(w, h);
		setPartBounds();
	}
	@Override
	public void setBounds(float x, float y, float w, float h)
	{	
		super.setBounds(x, y, w, h);
		this.textLabel.setBounds(x, y, w, h);
		setPartBounds();
	}
	public void setText(String string)
	{
		this.textLabel.setText(string);
		this.setWidth(calculateWidth(string)*GameVariables.getScaleX());
	}
	public String getText()
	{
		return this.textLabel.getText().toString();
	}
	
	@Override
	public void draw(Batch batch, float alpha)
	{
		batch.draw(background, this.getX(), backgroundY, this.getWidth(), backgroundH);
		batch.draw(leftBorder, this.getX(), this.getY(), borderW, this.getHeight());
		batch.draw(rightBorder, rightBorderX, this.getY(), borderW, this.getHeight());
		textLabel.draw(batch, alpha);
	}
	

}
