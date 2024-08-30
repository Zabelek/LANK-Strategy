package pl.zabel.lank.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.MusicManager;

public class SettingsWindow extends MenuWindow{
	
	private Slider musicSlider, effectsSlider;
	private Image shadowsButton;
	private MenuButton renderDistanceButton;

	public SettingsWindow() {
		super(LanguageMap.findString("settingsWindowTitle"), 685*GameVariables.getScaleX(), 320*GameVariables.getScaleY(), 550*GameVariables.getScaleX(), 500*GameVariables.getScaleY());
		this.textDisplayed.setBounds(0, 400*GameVariables.getScaleY(), 550*GameVariables.getScaleX(), 100*GameVariables.getScaleY());
		this.okButton.setPosition(30*GameVariables.getScaleX(), this.okButton.getY()*GameVariables.getScaleY());
		MenuButton exitButton = new MenuButton(LanguageMap.findString("settingsWindowLeaveGame"), 240*GameVariables.getScaleX(), this.okButton.getY());
		this.addActor(exitButton);
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		Label musicLabel = new Label (LanguageMap.findString("settingsWindowMusic"), TextStylesContainer.smallTextStyle);
		musicLabel.setBounds(50*GameVariables.getScaleX(), 375*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		Label effectsLabel = new Label (LanguageMap.findString("settingsWindowSounds"), TextStylesContainer.smallTextStyle);
		effectsLabel.setBounds(50*GameVariables.getScaleX(), 285*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		Label shadowsLabel = new Label (LanguageMap.findString("settingsWindowShadows"), TextStylesContainer.smallTextStyle);
		shadowsLabel.setBounds(50*GameVariables.getScaleX(), 195*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		Label renderLabel = new Label (LanguageMap.findString("settingsWindowRender"), TextStylesContainer.smallTextStyle);
		renderLabel.setBounds(50*GameVariables.getScaleX(), 105*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		musicSlider = new Slider(0f, 1f, 0.01f, false, TextStylesContainer.sliderStyle);
		musicSlider.setPosition(50*GameVariables.getScaleX(), 350*GameVariables.getScaleY());
		musicSlider.setSize(450*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
		musicSlider.setValue(1.0f);
		musicSlider.addListener(new ChangeListener() {
	        @Override
	        public void changed(ChangeEvent event, Actor actor) {
	            MusicManager.setMusicVolume(musicSlider.getValue());
	            GameVariables.setMusicVolume(musicSlider.getValue());
	        }
	    });
		effectsSlider = new Slider(0f, 1f, 0.01f, false, TextStylesContainer.sliderStyle);
		effectsSlider.setPosition(50*GameVariables.getScaleX(), 260*GameVariables.getScaleY());               
		effectsSlider.setSize(450*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
		effectsSlider.setValue(1.0f);
		effectsSlider.addListener(new ChangeListener() {
	        @Override
	        public void changed(ChangeEvent event, Actor actor) {
	            GameVariables.setEffectsVolume(effectsSlider.getValue());
	        }
	    });
		shadowsButton = new Image(InterfaceTextureContainer.findRegion("white"));
		shadowsButton.setBounds(50*GameVariables.getScaleX(), 170*GameVariables.getScaleY(), 40*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
		shadowsButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if(GameVariables.isShadows()==true)
				{
					shadowsButton.setDrawable(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("lightBlack")));
					GameVariables.setShadows(false);
				}
				else
				{
					shadowsButton.setDrawable(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("white")));
					GameVariables.setShadows(true);
				}
			}
	    });
		renderDistanceButton = new MenuButton(LanguageMap.findString("settingsWindowMid"), 300*GameVariables.getScaleX(), 105*GameVariables.getScaleY());
		renderDistanceButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if(GameVariables.getRenderDistance()==500)
				{
					renderDistanceButton.setText(LanguageMap.findString("settingsWindowMid"));
					GameVariables.setRenderDistance(700);
				}
				else if(GameVariables.getRenderDistance()==700)
				{
					renderDistanceButton.setText(LanguageMap.findString("settingsWindowFar"));
					GameVariables.setRenderDistance(1000);
				}
				else if(GameVariables.getRenderDistance()==1000)
				{
					renderDistanceButton.setText(LanguageMap.findString("settingsWindowVFar"));
					GameVariables.setRenderDistance(1300);
				}
				else if(GameVariables.getRenderDistance()==1300)
				{
					renderDistanceButton.setText(LanguageMap.findString("settingsWindowVClose"));
					GameVariables.setRenderDistance(350);
				}
				else if(GameVariables.getRenderDistance()==350)
				{
					renderDistanceButton.setText(LanguageMap.findString("settingsWindowClose"));
					GameVariables.setRenderDistance(500);
				}
			}
	    });
		this.addActor(effectsLabel);
		this.addActor(musicLabel);
		this.addActor(shadowsLabel);
		this.addActor(renderLabel);
		this.addActor(musicSlider);
		this.addActor(effectsSlider);
		this.addActor(shadowsButton);
		this.addActor(renderDistanceButton);
	}
	@Override
	public void setVisible(boolean value)
	{
		super.setVisible(value);
		if(value==true)
		{
			if(GameVariables.getRenderDistance()==500)
			{
				renderDistanceButton.setText(LanguageMap.findString("settingsWindowClose"));
			}
			else if(GameVariables.getRenderDistance()==700)
			{
				renderDistanceButton.setText(LanguageMap.findString("settingsWindowMid"));
			}
			else if(GameVariables.getRenderDistance()==1000)
			{
				renderDistanceButton.setText(LanguageMap.findString("settingsWindowFar"));
			}
			else if(GameVariables.getRenderDistance()==1300)
			{
				renderDistanceButton.setText(LanguageMap.findString("settingsWindowVFar"));
			}
			else if(GameVariables.getRenderDistance()==350)
			{
				renderDistanceButton.setText(LanguageMap.findString("settingsWindowVClose"));
			}
		}
		if(GameVariables.isShadows()==false)
		{
			shadowsButton.setDrawable(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("lightBlack")));
		}
		else
		{
			shadowsButton.setDrawable(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("white")));
		}
		musicSlider.setValue(GameVariables.getMusicVolume());
		effectsSlider.setValue(GameVariables.getEffectsVolume());
	}

}
