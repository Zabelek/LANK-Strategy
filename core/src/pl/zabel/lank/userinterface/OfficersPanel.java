package pl.zabel.lank.userinterface;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.SupportOfficer;
import pl.zabel.lank.texturecontainers.CharacterTextureContainer;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.Observer;
import pl.zabel.lank.utilities.SoundEffectManager;

public class OfficersPanel extends MenuWindow implements Observer{
	
	SinglePanel sp1, sp2, sp3;
	private Image border1, border2;

	public OfficersPanel() {
		super("", 400*GameVariables.getScaleX(), 250*GameVariables.getScaleY(), 1120*GameVariables.getScaleX(), 580*GameVariables.getScaleY());
		sp1 = new SinglePanel();
		sp2 = new SinglePanel();
		sp3 = new SinglePanel();
		sp1.setPosition(10*GameVariables.getScaleX(), 100*GameVariables.getScaleY());
		sp2.setPosition(390*GameVariables.getScaleX(), 100*GameVariables.getScaleY());
		sp3.setPosition(770*GameVariables.getScaleX(), 100*GameVariables.getScaleY());
		this.addActor(sp1);
		this.addActor(sp2);
		this.addActor(sp3);
		this.border1 = new Image(InterfaceTextureContainer.findRegion("menu-window-border-mid"));
		this.border1.setBounds(360*GameVariables.getScaleX(), 100*GameVariables.getScaleY(), 10*GameVariables.getScaleX(), 450*GameVariables.getScaleY());
		this.border2 = new Image(InterfaceTextureContainer.findRegion("menu-window-border-mid"));
		this.border2.setBounds(740*GameVariables.getScaleX(), 100*GameVariables.getScaleY(), 10*GameVariables.getScaleX(), 450*GameVariables.getScaleY());
		this.addActor(border1);
		this.addActor(border2);
		GameSession.attach(this);
	}
	
	private class SinglePanel extends WidgetGroup{
		
		private Label namelabel, levelLabel, pointsLabel, attackLabel, flairLabel, deffenceLabel;
		private MenuImageButton plusAttackButton, plusDeffenceButton, plusFlairButton;
		private Image characterImage;
		private SupportOfficer officer;
		
		public SinglePanel()
		{
			this.setBounds(0, 0, 320*GameVariables.getScaleX(), 500*GameVariables.getScaleY());
			this.namelabel = new Label(LanguageMap.findString("ofWindowlocked"), TextStylesContainer.genericButtonStyle);
			this.attackLabel = new Label("0", TextStylesContainer.genericButtonStyle);
			this.levelLabel = new Label("0", TextStylesContainer.genericButtonStyle);
			this.pointsLabel = new Label("0", TextStylesContainer.genericButtonStyle);
			this.flairLabel = new Label("0", TextStylesContainer.genericButtonStyle);
			this.deffenceLabel = new Label("0", TextStylesContainer.genericButtonStyle);
			
			this.namelabel.setBounds(0*GameVariables.getScaleX(), 220*GameVariables.getScaleY(), 320*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			this.levelLabel.setBounds(50*GameVariables.getScaleX(), 130*GameVariables.getScaleY(), 120*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			this.pointsLabel.setBounds(160*GameVariables.getScaleX(), 130*GameVariables.getScaleY(), 120*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			this.attackLabel.setBounds(20*GameVariables.getScaleX(), 40*GameVariables.getScaleY(), 80*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			this.deffenceLabel.setBounds(130*GameVariables.getScaleX(), 40*GameVariables.getScaleY(), 80*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			this.flairLabel.setBounds(240*GameVariables.getScaleX(), 40*GameVariables.getScaleY(), 80*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			
			this.namelabel.setAlignment(Align.center);
			this.levelLabel.setAlignment(Align.center);
			this.pointsLabel.setAlignment(Align.center);
			this.attackLabel.setAlignment(Align.center);
			this.deffenceLabel.setAlignment(Align.center);
			this.flairLabel.setAlignment(Align.center);
			
			this.plusAttackButton = new MenuImageButton("plus_button", 30*GameVariables.getScaleX(), 10*GameVariables.getScaleY(), 40*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.plusDeffenceButton = new MenuImageButton("plus_button", 145*GameVariables.getScaleX(), 10*GameVariables.getScaleY(), 40*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.plusFlairButton = new MenuImageButton("plus_button", 260*GameVariables.getScaleX(), 10*GameVariables.getScaleY(), 40*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.plusAttackButton.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					if(officer!=null)
					{
						if(officer.getAvailablePoints()>0)
						{
							SoundEffectManager.playSmallClickSound();
							officer.setAttack(officer.getRawAttack()+1);
							officer.setAvailablePoints(officer.getAvailablePoints()-1);
						}
					}
					event.stop();
				}
			});
			this.plusDeffenceButton.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					if(officer!=null)
					{
						if(officer.getAvailablePoints()>0)
						{
							SoundEffectManager.playSmallClickSound();
							officer.setDeffence(officer.getRawDeffence()+1);
							officer.setAvailablePoints(officer.getAvailablePoints()-1);
						}
					}
					event.stop();
				}
			});
			this.plusFlairButton.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					if(officer!=null)
					{
						if(officer.getAvailablePoints()>0)
						{
							SoundEffectManager.playSmallClickSound();
							officer.setFlair(officer.getRawFlair()+1);
							officer.setAvailablePoints(officer.getAvailablePoints()-1);
						}
					}
					event.stop();
				}
			});
			
			Label levelLabel1 = new Label(LanguageMap.findString("ofWindowLevel"), TextStylesContainer.smallTextStyle);
			levelLabel1.setBounds(50*GameVariables.getScaleX(), 160*GameVariables.getScaleY(), 120*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			levelLabel1.setAlignment(Align.center);
			this.addActor(levelLabel1);
			Label pointsLabel1 = new Label(LanguageMap.findString("ofWindowPoints"), TextStylesContainer.smallTextStyle);
			pointsLabel1.setBounds(160*GameVariables.getScaleX(), 160*GameVariables.getScaleY(), 120*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			pointsLabel1.setAlignment(Align.center);
			this.addActor(pointsLabel1);
			Label attackLabe1 = new Label(LanguageMap.findString("ofWindowAttack"), TextStylesContainer.smallTextStyle);
			attackLabe1.setBounds(20*GameVariables.getScaleX(), 70*GameVariables.getScaleY(), 80*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			attackLabe1.setAlignment(Align.center);
			this.addActor(attackLabe1);
			Label deffenceLabel1 = new Label(LanguageMap.findString("ofWindowDeffence"), TextStylesContainer.smallTextStyle);
			deffenceLabel1.setBounds(130*GameVariables.getScaleX(), 70*GameVariables.getScaleY(), 80*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			deffenceLabel1.setAlignment(Align.center);
			this.addActor(deffenceLabel1);
			Label flairLabel1 = new Label(LanguageMap.findString("ofWindowFlair"), TextStylesContainer.smallTextStyle);
			flairLabel1.setBounds(240*GameVariables.getScaleX(), 70*GameVariables.getScaleY(), 80*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
			flairLabel1.setAlignment(Align.center);
			this.addActor(flairLabel1);
			
			this.characterImage = new Image(InterfaceTextureContainer.findRegion("menu-window-border-mid"));
			this.characterImage.setBounds(70*GameVariables.getScaleX(), 270*GameVariables.getScaleY(), 180*GameVariables.getScaleX(), 180*GameVariables.getScaleY());
			this.addActor(characterImage);
			
			this.addActor(namelabel);
			this.addActor(attackLabel);
			this.addActor(levelLabel);
			this.addActor(pointsLabel);
			this.addActor(flairLabel);
			this.addActor(deffenceLabel);
			this.addActor(plusAttackButton);
			this.addActor(plusDeffenceButton);
			this.addActor(plusFlairButton);
		}
		public void setOfficer(SupportOfficer officer)
		{
			this.officer = officer;
			this.namelabel.setText(officer.getName());
			this.levelLabel.setText(officer.getLevel());
			this.pointsLabel.setText(officer.getAvailablePoints());
			this.attackLabel.setText(officer.getAttack());
			this.deffenceLabel.setText(officer.getDeffence());
			this.flairLabel.setText(officer.getFlair());
			this.characterImage.remove();
			this.characterImage = new Image(CharacterTextureContainer.findRegion(officer.getName().toLowerCase()+"_profile"));
			this.characterImage.setBounds(70*GameVariables.getScaleX(), 270*GameVariables.getScaleY(), 180*GameVariables.getScaleX(), 180*GameVariables.getScaleY());
			this.addActor(characterImage);
		}
		public void setEmpty()
		{
			this.officer = null;
			this.namelabel.setText(LanguageMap.findString("ofWindowlocked"));
			this.levelLabel.setText("0");
			this.pointsLabel.setText("0");
			this.attackLabel.setText("0");
			this.deffenceLabel.setText("0");
			this.flairLabel.setText("0");
			this.characterImage.remove();
			this.characterImage = new Image(InterfaceTextureContainer.findRegion("menu-window-border-mid"));
			this.characterImage.setBounds(70*GameVariables.getScaleX(), 270*GameVariables.getScaleY(), 180*GameVariables.getScaleX(), 180*GameVariables.getScaleY());
			this.addActor(characterImage);
		}
	}
	public void setStageForBlockingShadow(Stage stage)
	{
		this.lightFade.remove();
		stage.addActor(this.lightFade);
	}
	@Override
	public void update() {
		if(!GameSession.getAnna().isLocked())
		{
			sp1.setOfficer(GameSession.getAnna());
		}
		else
		{
			sp1.setEmpty();
		}
		if(!GameSession.getSimon().isLocked())
		{
			sp2.setOfficer(GameSession.getSimon());
		}
		else
		{
			sp2.setEmpty();
		}
		if(!GameSession.getKaite().isLocked())
		{
			sp3.setOfficer(GameSession.getKaite());
		}
		else if(!GameSession.getZack().isLocked())
		{
			sp3.setOfficer(GameSession.getZack());
		}
		else
		{
			sp3.setEmpty();
		}	
	}
}

