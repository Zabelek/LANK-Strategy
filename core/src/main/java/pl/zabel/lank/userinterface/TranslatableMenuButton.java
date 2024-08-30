package pl.zabel.lank.userinterface;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.LaserowaAnihilacjaNajdzdzcowzKosmosu;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.GameVariables;



public class TranslatableMenuButton extends MenuButton{
	
	private Container<Label> container;
	
	public TranslatableMenuButton(String title, float x, float y)
	{
		this.textLabel = new Label(title, TextStylesContainer.genericButtonStyle);
		this.setBounds(x, y, calculateWidth(title)*GameVariables.getScaleX(), 70*GameVariables.getScaleY());		
		textLabel.setAlignment(Align.center);
		unlock();
		this.setUpListeners();
		this.locked=false;
		this.container = new Container<Label>(this.textLabel);
		this.container.setTransform(true);
		this.container.setBounds(x, y, this.getWidth(), this.getHeight());
		this.container.setTouchable(Touchable.disabled);		
	}
	public void addAction(Action action1, Action action2, float duration)
	{
		super.addAction(action1);
		this.container.addAction(action2);
		Timer.schedule(new Timer.Task() {			
			@Override
			public void run() {						
				setPartBounds();
				this.cancel();
			}
		},duration, 0.01f, 1);
	}
	public void addAction(MoveToAction action)
	{
		MoveToAction labelAction = new MoveToAction();
		labelAction.setPosition(action.getX(), action.getY());
		labelAction.setDuration(action.getDuration());
		super.addAction(action);
		this.container.addAction(labelAction);
		Timer.schedule(new Timer.Task() {			
			@Override
			public void run() {						
				setPartBounds();
				this.cancel();
			}
		},action.getDuration(), 0.01f, 1);
	}
	@Override
	public void setPosition(float x, float y)
	{
		this.container.setPosition(x, y);
		this.setX(x);
		this.setY(y);
	}
	public Container<Label> getContainer()
	{
		return this.container;
	}
	@Override
	public void draw(Batch batch, float alpha)
	{
		if(this.hasActions())
		{
			setPartBounds();
		}
		batch.draw(background, this.getX(), backgroundY, this.getWidth(), backgroundH);
		batch.draw(leftBorder, this.getX(), this.getY(), borderW, this.getHeight());
		batch.draw(rightBorder, rightBorderX, this.getY(), borderW, this.getHeight());
	}
	

}
