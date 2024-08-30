package pl.zabel.lank.userinterface;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.City.CityType;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.SoundEffectManager;

public class CityBuildWindow extends MenuWindow{
	
	private FactionImage cyborgIcon, mutantIcon;
	private TextField nameTextField;
	private Label resourceStatusLabel;
	private StarSystem starSystem;
	private ClickListener listener;

	public CityBuildWindow() {
		super(LanguageMap.findString("buildCityInStarSystem"), 710*GameVariables.getScaleX(), 240*GameVariables.getScaleY(), 500*GameVariables.getScaleX(), 600*GameVariables.getScaleY());
		// TODO Auto-generated constructor stub
		this.okButton.setPosition(30*GameVariables.getScaleX(), 30*GameVariables.getScaleY());
		this.textDisplayed.setY(270*GameVariables.getScaleY());
		MenuButton cancelButton = new MenuButton(LanguageMap.findString("backToGalaxyView"), 290*GameVariables.getScaleX(), 30*GameVariables.getScaleY());
		cancelButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				CityBuildWindow.this.setVisible(false);
				event.stop();
			}
		});
		this.addActor(cancelButton);
		cyborgIcon = new FactionImage(InterfaceTextureContainer.findRegion("cyborg_logo"));
		cyborgIcon.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				SoundEffectManager.playSmallClickSound();
				cyborgIcon.selected = true;
				mutantIcon.selected = false;
				event.stop();
			}
		});
		mutantIcon = new FactionImage(InterfaceTextureContainer.findRegion("mutant_logo"));
		mutantIcon.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				SoundEffectManager.playSmallClickSound();
				cyborgIcon.selected = false;
				mutantIcon.selected = true;
				event.stop();
			}
		});
		cyborgIcon.selected = true;
		mutantIcon.selected = false;
		cyborgIcon.setPosition(50*GameVariables.getScaleX(), 150*GameVariables.getScaleY());
		mutantIcon.setPosition(300*GameVariables.getScaleX(), 150*GameVariables.getScaleY());
		this.addActor(cyborgIcon);
		this.addActor(mutantIcon);
		this.nameTextField = new TextField(LanguageMap.findString("cityBuildWindowCityName"), TextStylesContainer.textFieldStyle);
		this.nameTextField.setBounds(50*GameVariables.getScaleX(), 320*GameVariables.getScaleY(), 400*GameVariables.getScaleX(), 80*GameVariables.getScaleY());
		this.nameTextField.setAlignment(Align.center);
		this.resourceStatusLabel = new Label("tekst", TextStylesContainer.smallTextStyle);
		this.resourceStatusLabel.setBounds(50*GameVariables.getScaleX(), 420*GameVariables.getScaleY(), 400*GameVariables.getScaleX(), 80*GameVariables.getScaleY());
		this.resourceStatusLabel.setAlignment(Align.center);
		this.addActor(nameTextField);
		this.addActor(resourceStatusLabel);
		this.addListenerToOkButton(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if(cyborgIcon.selected)
				{
					CityBuildWindow.this.starSystem.setCaptureState(CaptureState.CITY, new City(nameTextField.getText(), CityType.CYBORG));
				}
				else
				{
					CityBuildWindow.this.starSystem.setCaptureState(CaptureState.CITY, new City(nameTextField.getText(), CityType.MUTANT));
				}
				GameSession.changeGalaxyEcoValue("titanium", -100);
				GameSession.changeGalaxyEcoValue("petroleum", -30);
				GameSession.changeGalaxyEcoValue("uranium", -10);
				GameSession.changeGalaxyEcoValue("gold", -50);
				event.stop();
			}
		});
	}

	public void setStarSystem(final StarSystem starSystem) {
		this.starSystem = starSystem;
		this.textDisplayed.setText(LanguageMap.findString("cityBuildWindowBuildCityIn")+this.starSystem.getName());
		if (GameSession.getCurrentTitanium()<100 || GameSession.getCurrentPetroleum()<30 ||
				GameSession.getCurrentUranium()<10 || GameSession.getCurrentPetroleum()<50)
		{
			this.okButton.lock();
			this.resourceStatusLabel.setText(LanguageMap.findString("cityBuildWindowNotEnoughResources"));
		}
		else
		{
			this.resourceStatusLabel.setText(LanguageMap.findString("cityBuildWindowBuildCost"));
		}
	}

	}
	class FactionImage extends Actor
	{
		public boolean selected;
		private TextureRegion texture;
		public FactionImage(TextureRegion texture)
		{
			this.texture = texture;
			this.setSize(150*GameVariables.getScaleX(), 150*GameVariables.getScaleY());
		}
		
		@Override
		public void draw(Batch batch, float alpha)
		{
			if(!selected)
			{
				batch.setColor(1,1,1,0.5f);
				batch.draw(texture, this.getX(),this.getY(),this.getWidth(),this.getHeight());
				batch.setColor(1,1,1,1);
			}
			else
			{
				batch.draw(texture, this.getX(),this.getY(),this.getWidth(),this.getHeight());
			}
	}
}

