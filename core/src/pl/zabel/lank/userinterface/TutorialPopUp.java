package pl.zabel.lank.userinterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.FadeableActor;

public class TutorialPopUp extends WidgetGroup{
	
	private float a;
	
	public TutorialPopUp(String text, float x, float y, float width, float height, float arrowX, float arrowY)
	{
		this.setBounds(x*GameVariables.getScaleX(), y*GameVariables.getScaleY(), width*GameVariables.getScaleX(), height*GameVariables.getScaleY());
		Image background = new Image(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("menu-window-background")));		
		background.setBounds(0, 0, width*GameVariables.getScaleX(), height*GameVariables.getScaleX());
		Image arrow = new Image(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("tutorial-arrow")));
		if(arrowY>0)
			arrow.setBounds(arrowX*GameVariables.getScaleX(), -arrowY*GameVariables.getScaleY(), 70*GameVariables.getScaleX(), (arrowY-background.getY())*GameVariables.getScaleY());
		else
			arrow.setBounds(arrowX*GameVariables.getScaleX(), (height-arrowY)*GameVariables.getScaleY(), 70*GameVariables.getScaleX(), arrowY*GameVariables.getScaleY());
		Label textLabel = new Label(text, TextStylesContainer.smallTextStyle);
		textLabel.setBounds(0, 0, width*GameVariables.getScaleX(), height*GameVariables.getScaleY());
		textLabel.setAlignment(Align.center);
		this.addActor(background);
		this.addActor(arrow);
		this.addActor(textLabel);
		this.setTouchable(Touchable.disabled);
		this.fadeIn();
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				fadeOut();
			}
		}, 9);
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				TutorialPopUp.this.remove();
			}
		}, 10);
	}
	public void fadeIn()
	{
		this.a = 0;
		this.setColor(1, 1, 1, a);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if(TutorialPopUp.this.getColor().a<1)
				{
					a += 1f/(30f);
					TutorialPopUp.this.getColor().a = a;
				}
				else
				{
					TutorialPopUp.this.getColor().a = 1;
					this.cancel();
				}
			}
		},0, 1f/30f);
	}
	public void fadeOut()
	{
		this.a = 1;
		this.setColor(1, 1, 1, a);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if(TutorialPopUp.this.getColor().a>0)
				{
					a -= 1f/(30f);
					TutorialPopUp.this.getColor().a = a;
				}
				else
				{
					TutorialPopUp.this.getColor().a = 0;
					this.cancel();
				}
			}
		},0, 1f/30f);
	}
	@Override
	public void draw(Batch batch, float alpha){
		Color color = getColor();
	    batch.setColor(color.r, color.g, color.b, color.a * alpha);
		super.draw(batch, alpha);
		batch.setColor(color.r, color.g, color.b, 1);
	}
}
