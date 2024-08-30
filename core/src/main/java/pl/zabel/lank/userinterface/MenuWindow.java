package pl.zabel.lank.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.GameVariables;

public class MenuWindow extends WidgetGroup{
	
	private TextureRegion leftBorderUp,borderMid,leftBorderDown, rightBorderUp, rightBorderDown; 
	protected TextureRegion background;
	protected MenuButton okButton;
	protected Label textDisplayed;
	private float leftBorderX, borderMidDownY,borderMidW,borderUpY, borderUpDownW, borderUpDownH,rightBorderUpX;
	Image lightFade;
	
	public MenuWindow(String title, float x, float y, float width, float height)
	{
		this.setBounds(x, y, width, height);
		this.leftBorderUp = InterfaceTextureContainer.findRegion("menu-window-left-border-up");
		this.borderMid = InterfaceTextureContainer.findRegion("menu-window-border-mid");
		this.leftBorderDown = InterfaceTextureContainer.findRegion("menu-window-left-border-down");
		this.rightBorderUp = InterfaceTextureContainer.findRegion("menu-window-right-border-up");		
		this.rightBorderDown = InterfaceTextureContainer.findRegion("menu-window-right-border-down");
		this.background = InterfaceTextureContainer.findRegion("menu-window-background");
		
		this.okButton = new MenuButton("OK", this.getWidth()/2-(85*GameVariables.getScaleX()), 20*GameVariables.getScaleY());
		this.okButton.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				setVisible(false);
				event.stop();
				return true;
			}
		});
		this.addActor(this.okButton);
		this.textDisplayed = new Label(title, TextStylesContainer.genericButtonStyle);
		this.textDisplayed.setAlignment(Align.center);
		this.textDisplayed.setBounds(0, 100*GameVariables.getScaleY(), this.getWidth(), this.getHeight()-(100*GameVariables.getScaleY()));
		this.addActor(this.textDisplayed);
		lightFade = new Image(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("lightBlack")));
		lightFade.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		setPartBounds();
		
	}
	private void setPartBounds()
	{
		leftBorderX = this.getX()-(20f*GameVariables.getScaleX());
		borderMidW = (22f*GameVariables.getScaleX());
		borderUpDownW = (50f*GameVariables.getScaleX());
		borderUpDownH = (26f*GameVariables.getScaleY());
		borderMidDownY = this.getY()-(11f*GameVariables.getScaleY());
		rightBorderUpX = this.getX()+this.getWidth()-(28f*GameVariables.getScaleX());
		borderUpY = this.getY()+this.getHeight()-(15f*GameVariables.getScaleY());
		
	}
	public void addListenerToOkButton(ClickListener listener)
	{
		this.okButton.addListener(listener);
	}
	public void setText(String text)
	{
		this.textDisplayed.setText(text);
	}
	public Image getLightFade()
	{
		return this.lightFade;
	}
	@Override
	public void setVisible(boolean value)
	{
		super.setVisible(value);
		lightFade.setVisible(value);
		if(value == true)
			SoundEffectManager.playWindowPopUpSound();
	}
	@Override
	public void draw(Batch batch, float alpha)
	{	
		batch.draw(borderMid, leftBorderX, borderMidDownY, borderMidW, this.getHeight());
		batch.draw(borderMid, this.getX()+this.getWidth(), borderMidDownY, borderMidW, this.getHeight());
		batch.draw(leftBorderUp, leftBorderX, borderUpY, borderUpDownW, borderUpDownH);
		batch.draw(leftBorderDown, leftBorderX, borderMidDownY, borderUpDownW, borderUpDownH);
		batch.draw(rightBorderUp, rightBorderUpX, borderUpY, borderUpDownW, borderUpDownH);
		batch.draw(rightBorderDown, rightBorderUpX, borderMidDownY,  borderUpDownW, borderUpDownH);
		batch.draw(background, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		super.draw(batch, alpha);
	}
	public void hideOkButton() {
		this.okButton.setVisible(false);		
	}
	public void setTextDispayedY(float y)
	{
		this.textDisplayed.setY(y);
	}

}
