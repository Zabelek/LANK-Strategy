package pl.zabel.lank.userinterface;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.Siege;
import pl.zabel.lank.gameplayobjects.SupportOfficer;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;
import pl.zabel.lank.gameplayobjects.missionview.Mission;
import pl.zabel.lank.texturecontainers.CharacterTextureContainer;
import pl.zabel.lank.texturecontainers.GalaxyTextureContainer;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.views.GalaxyRegionView;
import pl.zabel.lank.views.GalaxyView;

public class StarSystemView extends MenuWindow {

	private GalaxyView galaxyView;
	private GalaxyRegionView galaxyRegionView;
	private StarSystem starSystem;
	private MenuButton landButton, buildButton, siegeButton;
	private Label starStateLabel;
	private Image titanium1, petroleum1, uranium1, gold1, titanium2, petroleum2, uranium2, gold2, titanium3, petroleum3,
			uranium3, gold3, research, build;
	private Siege currentSiege;
	private SiegeWindow siegeWindow;

	public StarSystemView(GalaxyView galaxyView, GalaxyRegionView galaxyRegionView) {
		super("", 300 * GameVariables.getScaleX(), 200 * GameVariables.getScaleY(), 1320 * GameVariables.getScaleX(),
				680 * GameVariables.getScaleY());
		this.galaxyView = galaxyView;
		this.galaxyRegionView = galaxyRegionView;
		this.okButton.setPosition(50 * GameVariables.getScaleX(), 30);
		this.landButton = new MenuButton(LanguageMap.findString("starWindowLand"), 1100 * GameVariables.getScaleX(), 30 * GameVariables.getScaleY());
		this.buildButton = new MenuButton(LanguageMap.findString("starWindowBuild"), 800 * GameVariables.getScaleX(), 30 * GameVariables.getScaleY());
		this.starStateLabel = new Label(LanguageMap.findString("starWindowEnemyTer"), TextStylesContainer.genericButtonStyle);
		this.starStateLabel.setPosition(500 * GameVariables.getScaleX(), 600 * GameVariables.getScaleY());
		this.textDisplayed.setBounds(50 * GameVariables.getScaleX(), 570 * GameVariables.getScaleY(), 250 * GameVariables.getScaleX(),
				80 * GameVariables.getScaleY());
		this.landButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				StarSystemView.this.setVisible(false);
				if(starSystem.getCaptureState()==CaptureState.AVAILABLE)
					StarSystemView.this.galaxyView.getMainGame().goToMissionView(new Mission(StarSystemView.this.starSystem, StarSystemView.this.starSystem.getName()), (byte)0);
				else if(starSystem.getCaptureState()==CaptureState.CAPTURED || starSystem.getCaptureState()==CaptureState.CITY)
				{
					Mission trainingMission = new Mission(StarSystemView.this.starSystem);
					StarSystemView.this.galaxyView.getMainGame().goToMissionView(trainingMission,(byte)0);
				}
				event.stop();
			}
		});
		this.addActor(landButton);
		this.addActor(buildButton);
		this.addActor(starStateLabel);
		this.setUpMaterialIcons();
		this.research = new Image(InterfaceTextureContainer.findRegion("research-sign"));
		this.build = new Image(InterfaceTextureContainer.findRegion("build-sign"));
		this.research.setBounds(1240 * GameVariables.getScaleX(), 330 * GameVariables.getScaleY(), 40 * GameVariables.getScaleX(),
				40 * GameVariables.getScaleY());
		this.build.setBounds(1170 * GameVariables.getScaleX(), 330 * GameVariables.getScaleY(), 40 * GameVariables.getScaleX(),
				40 * GameVariables.getScaleY());
		this.addActor(research);
		this.addActor(build);
		this.siegeButton = new MenuButton(LanguageMap.findString("starWindowSiegeActive"), 920 * GameVariables.getScaleX(), 130 * GameVariables.getScaleY());
		this.siegeButton.setVisible(false);
		this.siegeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				StarSystemView.this.siegeWindow.setVisible(true);
				StarSystemView.this.siegeWindow.loadData();
			}
		});
		this.addActor(siegeButton);
		this.siegeWindow = new SiegeWindow();
		this.siegeWindow.setVisible(false);
		this.addActor(this.siegeWindow);
		
	}

	public void setStarSystem(final StarSystem starSystem) {
		this.starSystem = starSystem;
		this.textDisplayed.setText(starSystem.getName());
		buildButton.remove();
		this.removeActor(buildButton);
		buildButton = null;
		this.titanium1.setVisible(false);
		this.petroleum1.setVisible(false);
		this.uranium1.setVisible(false);
		this.gold1.setVisible(false);
		this.titanium2.setVisible(false);
		this.petroleum2.setVisible(false);
		this.uranium2.setVisible(false);
		this.gold2.setVisible(false);
		this.titanium3.setVisible(false);
		this.petroleum3.setVisible(false);
		this.uranium3.setVisible(false);
		this.gold3.setVisible(false);
		if (starSystem.getCaptureState() == CaptureState.AVAILABLE) {
			this.background = GalaxyTextureContainer.findStarRegion("planeta_atakowana");
			starStateLabel.setText(LanguageMap.findString("starWindowEnemyTer"));
			buildButton = new MenuButton(LanguageMap.findString("starWindowBuild"), 800 * GameVariables.getScaleX(), 30 * GameVariables.getScaleY());
			this.addActor(buildButton);
			buildButton.lock();
		} else if (starSystem.getCaptureState() == CaptureState.CAPTURED) {
			this.background = GalaxyTextureContainer.findStarRegion("planeta_bez_miasta");
			starStateLabel.setText(LanguageMap.findString("starWindowPlayerTer"));
			buildButton = new MenuButton(LanguageMap.findString("starWindowBuild"), 800 * GameVariables.getScaleX(), 30 * GameVariables.getScaleY());
			buildButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					StarSystemView.this.setVisible(false);
					StarSystemView.this.galaxyRegionView.showBuildWindow(StarSystemView.this.starSystem);
					event.stop();
				}
			});
			this.addActor(buildButton);
			buildButton.unlock();
		} else {
			this.currentSiege = null;
			this.background = GalaxyTextureContainer.findStarRegion("planeta_z_miastem");
			starStateLabel.setText(LanguageMap.findString("starWindowPlayerTer"));
			buildButton = new MenuButton(starSystem.getCity().getName(), 800 * GameVariables.getScaleX(),
					30 * GameVariables.getScaleY());
			buildButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					StarSystemView.this.setVisible(false);
					galaxyView.getMainGame().goToCityView(starSystem.getCity());
				}
			});
			this.addActor(buildButton);
			buildButton.unlock();
		}
		// ladowanie ikonek zasobow
		if (starSystem.getResourcePotential() % 4 > 0) {
			this.titanium1.setVisible(true);
		}
		if (starSystem.getResourcePotential() % 4 > 1) {
			this.titanium2.setVisible(true);
		}
		if (starSystem.getResourcePotential() % 4 > 2) {
			this.titanium3.setVisible(true);
		}

		if (starSystem.getResourcePotential() % 16 > 3) {
			this.petroleum1.setVisible(true);
		}
		if (starSystem.getResourcePotential() % 16 > 7) {
			this.petroleum2.setVisible(true);
		}
		if (starSystem.getResourcePotential() % 16 > 11) {
			this.petroleum3.setVisible(true);
		}

		if (starSystem.getResourcePotential() % 64 > 15) {
			this.uranium1.setVisible(true);
		}
		if (starSystem.getResourcePotential() % 64 > 31) {
			this.uranium2.setVisible(true);
		}
		if (starSystem.getResourcePotential() % 64 > 47) {
			this.uranium3.setVisible(true);
		}

		if (starSystem.getResourcePotential() % 256 > 63) {
			this.gold1.setVisible(true);
		}
		if (starSystem.getResourcePotential() % 256 > 127) {
			this.gold2.setVisible(true);
		}
		if (starSystem.getResourcePotential() % 256 > 191) {
			this.gold3.setVisible(true);
		}
		this.build.setVisible(false);
		this.research.setVisible(false);
		if (starSystem.getCity() != null && !starSystem.getCity().hasBuiltInThisTurn())
			this.build.setVisible(true);
		if (starSystem.getCity() != null && !starSystem.getCity().hasResearchedInThisTurn())
			this.research.setVisible(true);
		// sprawdzanie, czy jest oblezenie
		if (this.starSystem.getCity() != null) {
			for (Siege siege1 : GameSession.getActiveSieges()) {
				if (siege1.getCity() == this.starSystem.getCity()) {
					this.currentSiege = siege1;					
				}
			}
			if (this.currentSiege!=null)
			{
				this.siegeButton.setVisible(true);
				if(currentSiege.alreadyStarted())
				{
					this.siegeButton.setText(LanguageMap.findString("starWindowSiegeActive"));
				}
				else
				{
					this.siegeButton.setText(LanguageMap.findString("starWindowEnemySeen"));
				}
			}
			else
			{
				this.siegeButton.setVisible(false);
			}
		}
		else
		{
			this.siegeButton.setVisible(false);
		}
	}

	public void setUpMaterialIcons() {
		titanium1 = new Image(InterfaceTextureContainer.findRegion("material_titanium"));
		titanium1.setBounds(1100 * GameVariables.getScaleX(), 600 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(titanium1);
		petroleum1 = new Image(InterfaceTextureContainer.findRegion("material_petroleum"));
		petroleum1.setBounds(1100 * GameVariables.getScaleX(), 530 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(petroleum1);
		uranium1 = new Image(InterfaceTextureContainer.findRegion("material_uranium"));
		uranium1.setBounds(1100 * GameVariables.getScaleX(), 470 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(uranium1);
		gold1 = new Image(InterfaceTextureContainer.findRegion("material_gold"));
		gold1.setBounds(1100 * GameVariables.getScaleX(), 400 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(gold1);
		titanium2 = new Image(InterfaceTextureContainer.findRegion("material_titanium"));
		titanium2.setBounds(1170 * GameVariables.getScaleX(), 600 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(titanium2);
		petroleum2 = new Image(InterfaceTextureContainer.findRegion("material_petroleum"));
		petroleum2.setBounds(1170 * GameVariables.getScaleX(), 530 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(petroleum2);
		uranium2 = new Image(InterfaceTextureContainer.findRegion("material_uranium"));
		uranium2.setBounds(1170 * GameVariables.getScaleX(), 470 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(uranium2);
		gold2 = new Image(InterfaceTextureContainer.findRegion("material_gold"));
		gold2.setBounds(1170 * GameVariables.getScaleX(), 400 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(gold2);
		titanium3 = new Image(InterfaceTextureContainer.findRegion("material_titanium"));
		titanium3.setBounds(1240 * GameVariables.getScaleX(), 600 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(titanium3);
		petroleum3 = new Image(InterfaceTextureContainer.findRegion("material_petroleum"));
		petroleum3.setBounds(1240 * GameVariables.getScaleX(), 530 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(petroleum3);
		uranium3 = new Image(InterfaceTextureContainer.findRegion("material_uranium"));
		uranium3.setBounds(1240 * GameVariables.getScaleX(), 470 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(uranium3);
		gold3 = new Image(InterfaceTextureContainer.findRegion("material_gold"));
		gold3.setBounds(1240 * GameVariables.getScaleX(), 400 * GameVariables.getScaleY(), 50 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY());
		this.addActor(gold3);
	}

	public class SiegeWindow extends MenuWindow {
		private Image anna, zack, kaite, simon, selector;
		private MenuButton cancelButton, sendButton;
		private SupportOfficer selectedOfficer;

		public SiegeWindow() {
			super(LanguageMap.findString("starWindowSiegeActive"), 130* GameVariables.getScaleX(), 140* GameVariables.getScaleY(), 750* GameVariables.getScaleX(), 400* GameVariables.getScaleY());
			this.textDisplayed.setY(200* GameVariables.getScaleY());
			this.anna = new Image(CharacterTextureContainer.findRegion("anna_profile"));
			this.zack = new Image(CharacterTextureContainer.findRegion("zack_profile"));
			this.kaite = new Image(CharacterTextureContainer.findRegion("kaite_profile"));
			this.simon = new Image(CharacterTextureContainer.findRegion("simon_profile"));
			this.selector = new Image(InterfaceTextureContainer.findRegion("white"));
			this.cancelButton = new MenuButton(LanguageMap.findString("backToGalaxyView"), 450* GameVariables.getScaleX(), 50* GameVariables.getScaleY());
			this.sendButton = new MenuButton(LanguageMap.findString("starWindowSend"), 100* GameVariables.getScaleX(), 50* GameVariables.getScaleY());
			this.anna.setBounds(100* GameVariables.getScaleX(), 150* GameVariables.getScaleY(), 150* GameVariables.getScaleX(), 150* GameVariables.getScaleY());
			this.zack.setBounds(300* GameVariables.getScaleX(), 150* GameVariables.getScaleY(), 150* GameVariables.getScaleX(), 150* GameVariables.getScaleY());
			this.kaite.setBounds(300* GameVariables.getScaleX(), 150* GameVariables.getScaleY(), 150* GameVariables.getScaleX(), 150* GameVariables.getScaleY());
			this.simon.setBounds(500* GameVariables.getScaleX(), 150* GameVariables.getScaleY(), 150* GameVariables.getScaleX(), 150* GameVariables.getScaleY());
			this.selector.setBounds(500* GameVariables.getScaleX(), 150* GameVariables.getScaleY(), 150* GameVariables.getScaleX(), 150* GameVariables.getScaleY());
			this.addActor(selector);
			this.addActor(cancelButton);
			this.addActor(sendButton);
			this.addActor(anna);
			this.addActor(zack);
			this.addActor(kaite);
			this.addActor(simon);
			setUpListeners();
		}

		public void setUpListeners() {
			this.anna.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SiegeWindow.this.selectedOfficer = GameSession.getAnna();
					SiegeWindow.this.sendButton.unlock();
					SiegeWindow.this.selector.setBounds(90* GameVariables.getScaleX(), 140* GameVariables.getScaleY(), 170* GameVariables.getScaleX(), 170* GameVariables.getScaleY());
					SiegeWindow.this.selector.setVisible(true);
				}
			});
			this.kaite.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SiegeWindow.this.selectedOfficer = GameSession.getKaite();
					SiegeWindow.this.sendButton.unlock();
					SiegeWindow.this.selector.setBounds(290* GameVariables.getScaleX(), 140* GameVariables.getScaleY(), 170* GameVariables.getScaleX(), 170* GameVariables.getScaleY());
					SiegeWindow.this.selector.setVisible(true);
				}
			});
			this.simon.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SiegeWindow.this.selectedOfficer = GameSession.getSimon();
					SiegeWindow.this.sendButton.unlock();
					SiegeWindow.this.selector.setBounds(490* GameVariables.getScaleX(), 140* GameVariables.getScaleY(), 170* GameVariables.getScaleX(), 170* GameVariables.getScaleY());
					SiegeWindow.this.selector.setVisible(true);
				}
			});
			this.zack.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SiegeWindow.this.selectedOfficer = GameSession.getZack();
					SiegeWindow.this.selector.setBounds(290* GameVariables.getScaleX(), 140* GameVariables.getScaleY(), 170* GameVariables.getScaleX(), 170* GameVariables.getScaleY());
					SiegeWindow.this.sendButton.unlock();
	
					SiegeWindow.this.selector.setVisible(true);
				}
			});
			this.cancelButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SiegeWindow.this.setVisible(false);
				}
			});
			this.sendButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SiegeWindow.this.selectedOfficer.setCurrentSystem(starSystem);
					currentSiege.setOfficer(SiegeWindow.this.selectedOfficer);
					SiegeWindow.this.setVisible(false);
					GameSession.notifyObservers();
				}
			});
		}

		public void loadData() {
			this.selector.setVisible(false);
			anna.setVisible(false);
			simon.setVisible(false);
			kaite.setVisible(false);
			zack.setVisible(false);
			if (currentSiege.getOfficer() == null) {
				this.sendButton.lock();
				this.sendButton.setVisible(true);
				this.cancelButton.setVisible(true);
				this.okButton.setVisible(false);				
				if (currentSiege.alreadyStarted()) {
					this.setText(LanguageMap.findString("starWindowSiegeMessage"));
				} else {
					this.setText(LanguageMap.findString("starWindowEnemySeenMessage"));
				}
				if (!GameSession.getAnna().isLocked() && GameSession.getAnna().getCurrentSystem() == null) {
					anna.setVisible(true);
				}
				if (!GameSession.getSimon().isLocked() && GameSession.getSimon().getCurrentSystem() == null) {
					simon.setVisible(true);
				}
				if (!GameSession.getKaite().isLocked() && GameSession.getKaite().getCurrentSystem() == null) {
					kaite.setVisible(true);
				}
				if (!GameSession.getZack().isLocked() && GameSession.getZack().getCurrentSystem() == null) {
					zack.setVisible(true);
				}
			}
			else
			{
				this.setText(LanguageMap.findString("starWindowDefSent"));
				this.sendButton.setVisible(false);
				this.cancelButton.setVisible(false);
				this.okButton.setVisible(true);
			}
		}

	}

}
