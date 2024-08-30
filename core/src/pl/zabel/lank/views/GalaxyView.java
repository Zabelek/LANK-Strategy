package pl.zabel.lank.views;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.LaserowaAnihilacjaNajdzdzcowzKosmosu;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.Siege;
import pl.zabel.lank.gameplayobjects.City.CityType;
import pl.zabel.lank.gameplayobjects.galaxyview.GalaxyRegion;
import pl.zabel.lank.gameplayobjects.galaxyview.GalaxyRegion.RegionCaptureState;
import pl.zabel.lank.gameplayobjects.mapview.GameplayMap;
import pl.zabel.lank.gameplayobjects.missionview.Mission;
import pl.zabel.lank.texturecontainers.CharacterTextureContainer;
import pl.zabel.lank.texturecontainers.CityTextureContainer;
import pl.zabel.lank.texturecontainers.GalaxyTextureContainer;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.LabWojTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.userinterface.CitiesSummaryWindow;
import pl.zabel.lank.userinterface.LoadGameWindow;
import pl.zabel.lank.userinterface.MenuImageButton;
import pl.zabel.lank.userinterface.MenuWindow;
import pl.zabel.lank.userinterface.OfficersPanel;
import pl.zabel.lank.userinterface.TranslatableMenuButton;
import pl.zabel.lank.userinterface.TutorialPopUp;
import pl.zabel.lank.utilities.FadeableActor;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.MusicManager;
import pl.zabel.lank.utilities.Observer;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.userinterface.RegionIcon;
import pl.zabel.lank.userinterface.SaveGameWindow;
import pl.zabel.lank.userinterface.SettingsWindow;

public class GalaxyView implements Screen, Observer{

	public static Pixmap texturesPixmap;

	LaserowaAnihilacjaNajdzdzcowzKosmosu mainGame;
	private Stage interfaceStage, backgroundStages, regionsStage; // tempStage do pobierania viewportu do innych stagów
	private FadeableActor spark0, spark1, spark2;

	private Timer.Task sparklingTask, interfaceOpeningTask, regionsFadeIn, regionsFadeOut, regionsFadeInTask;

	private TranslatableMenuButton loadButton, saveButton, showRegionButton;
	private Image titaniumBackground, petroleumBackground, uraniumBackground, goldBackground, citiesPanel,
			hintsBackground;
	private MenuImageButton companionsButton, settingsButton;
	private Label titaniumLabel, petroleumLabel, uraniumLabel, goldLabel, citiesLabel, hintsLabel;
	private InputMultiplexer inpM;
	private ArrayList<RegionIcon> registeredRegionIcons;
	ArrayList<Actor> sharedInterfaceComponents;
	private OfficersPanel officersPanel;
	private CitiesSummaryWindow citiesSummaryWindow;
	private MenuWindow endWindow;
	private SettingsWindow settingsWindow;
	// views

	// tutorial flags
	private boolean firstMessagePopUp;
	private TutorialPopUp firstPopUp;
	private SaveGameWindow saveGameWindow;
	private LoadGameWindow loadGameWindow;

	public GalaxyView(LaserowaAnihilacjaNajdzdzcowzKosmosu mainGame) {
		this.mainGame = mainGame;
		interfaceStage = new Stage(new ScreenViewport());
		backgroundStages = new Stage(new ScreenViewport());
		regionsStage = new Stage(new ScreenViewport());
		this.registeredRegionIcons = new ArrayList<RegionIcon>();
		this.sharedInterfaceComponents = new ArrayList<Actor>();
		Image galaxyBackground = new Image(GalaxyTextureContainer.findRegion("galaktyka"));
		galaxyBackground.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spark0 = new FadeableActor(GalaxyTextureContainer.findRegion("galaktyka_mryganie1"), 0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spark1 = new FadeableActor(GalaxyTextureContainer.findRegion("galaktyka_mryganie2"), 0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spark2 = new FadeableActor(GalaxyTextureContainer.findRegion("galaktyka_mryganie3"), 0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		backgroundStages.addActor(galaxyBackground);
		backgroundStages.addActor(spark0);
		backgroundStages.addActor(spark1);
		backgroundStages.addActor(spark2);
		backgroundStages.addActor(mainGame.getBlack());
		// przygotowanie ogólnej Pixmapy dla wszystkich regionow do szukania kolorow
		// pikseli
		TextureRegion tempRegion = GalaxyTextureContainer.findGalaxyRegion("szakala");
		tempRegion.getTexture().getTextureData().prepare();
		texturesPixmap = tempRegion.getTexture().getTextureData().consumePixmap();

		mainGame.getBlack().fadeOut(2);
		initiateAnimationTasks();
		animateInterfaceOpeningGW();
		startSparkling();
		this.saveGameWindow = new SaveGameWindow();
		this.loadGameWindow = new LoadGameWindow();
		this.saveGameWindow.setVisible(false);
		this.loadGameWindow.setVisible(false);
		this.loadButton = new TranslatableMenuButton(LanguageMap.findString("loadGameButtonText"),
				50 * GameVariables.getScaleX(), 150 * GameVariables.getScaleY());
		this.saveButton = new TranslatableMenuButton(LanguageMap.findString("saveGameButtonText"),
				50 * GameVariables.getScaleX(), 250 * GameVariables.getScaleY());
		this.showRegionButton = new TranslatableMenuButton(LanguageMap.findString("showRegionsButtonText"),
				50 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
		this.titaniumBackground = new Image(InterfaceTextureContainer.findRegion("material_panel_titanium"));
		this.petroleumBackground = new Image(InterfaceTextureContainer.findRegion("material_panel_petroleum"));
		this.uraniumBackground = new Image(InterfaceTextureContainer.findRegion("material_panel_uranium"));
		this.goldBackground = new Image(InterfaceTextureContainer.findRegion("material_panel_gold"));
		this.citiesPanel = new Image(InterfaceTextureContainer.findRegion("cities_panel"));
		this.companionsButton = new MenuImageButton("companions_button", 2000 * GameVariables.getScaleX(),
				50 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(), 150 * GameVariables.getScaleY());
		this.settingsButton = new MenuImageButton("settings-button", 1130 * GameVariables.getScaleX(),
				-150 * GameVariables.getScaleY(), 60 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.settingsWindow = new SettingsWindow();
		this.settingsWindow.setVisible(false);
		this.titaniumLabel = new Label("0", TextStylesContainer.genericButtonStyle);
		this.petroleumLabel = new Label("0", TextStylesContainer.genericButtonStyle);
		this.uraniumLabel = new Label("0", TextStylesContainer.genericButtonStyle);
		this.goldLabel = new Label("0", TextStylesContainer.genericButtonStyle);
		this.citiesLabel = new Label("0", TextStylesContainer.genericButtonStyle);
		this.hintsBackground = new Image(InterfaceTextureContainer.findRegion("menu-window-background"));
		this.hintsLabel = new Label(LanguageMap.findString("hintsLabekDefaultText"),
				TextStylesContainer.smallTextStyle);
		CharacterTextureContainer.load();
		this.officersPanel = new OfficersPanel();
		this.officersPanel.setVisible(false);
		this.citiesSummaryWindow = new CitiesSummaryWindow();
		this.citiesSummaryWindow.setVisible(false);
		this.hintsLabel.setAlignment(Align.center);
		this.titaniumLabel.setTouchable(Touchable.disabled);
		this.petroleumLabel.setTouchable(Touchable.disabled);
		this.uraniumLabel.setTouchable(Touchable.disabled);
		this.goldLabel.setTouchable(Touchable.disabled);
		this.citiesLabel.setTouchable(Touchable.disabled);
		this.hintsBackground.setTouchable(Touchable.disabled);
		this.hintsLabel.setTouchable(Touchable.disabled);
		setInterfaceStartPosition();
		this.interfaceStage.addActor(loadButton);
		this.interfaceStage.addActor(saveButton);
		this.interfaceStage.addActor(saveGameWindow);
		this.interfaceStage.addActor(loadGameWindow);
		this.interfaceStage.addActor(showRegionButton);
		this.interfaceStage.addActor(this.saveButton.getContainer());
		this.interfaceStage.addActor(this.loadButton.getContainer());
		this.interfaceStage.addActor(this.showRegionButton.getContainer());
		this.interfaceStage.addActor(titaniumBackground);
		this.interfaceStage.addActor(petroleumBackground);
		this.interfaceStage.addActor(uraniumBackground);
		this.interfaceStage.addActor(goldBackground);
		this.interfaceStage.addActor(titaniumLabel);
		this.interfaceStage.addActor(petroleumLabel);
		this.interfaceStage.addActor(uraniumLabel);
		this.interfaceStage.addActor(goldLabel);
		this.interfaceStage.addActor(citiesPanel);
		this.interfaceStage.addActor(citiesLabel);
		this.interfaceStage.addActor(companionsButton);
		this.interfaceStage.addActor(settingsButton);
		this.interfaceStage.addActor(hintsLabel);
		this.interfaceStage.addActor(hintsBackground);
		this.interfaceStage.addActor(officersPanel);
		this.interfaceStage.addActor(citiesSummaryWindow);
		this.interfaceStage.addActor(citiesSummaryWindow.getSiegeReportsWindow());
		this.interfaceStage.addActor(settingsWindow);
		inpM = new InputMultiplexer();
		inpM.addProcessor(regionsStage);
		inpM.addProcessor(interfaceStage);
		Gdx.input.setInputProcessor(inpM);
		setUpListeners();
		registerSharedInterfaceComponents();
		setUpRegionIcons();
		GameSession.attach(this);
		firstMessagePopUp = false;
		endWindow = new MenuWindow(LanguageMap.findString("dataLoadErrorMessage"), 500, 300, 920, 480);
		endWindow.addListenerToOkButton(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
				event.stop();
			}
		});
	}

	public void setInterfaceStartPosition() {
		titaniumBackground.setBounds(50 * GameVariables.getScaleX(), 1200 * GameVariables.getScaleY(),
				290 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		petroleumBackground.setBounds(350 * GameVariables.getScaleX(), 1200 * GameVariables.getScaleY(),
				290 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		uraniumBackground.setBounds(650 * GameVariables.getScaleX(), 1200 * GameVariables.getScaleY(),
				290 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		goldBackground.setBounds(950 * GameVariables.getScaleX(), 1200 * GameVariables.getScaleY(),
				290 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		titaniumLabel.setBounds(150 * GameVariables.getScaleX(), 1200 * GameVariables.getScaleY(),
				190 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		petroleumLabel.setBounds(450 * GameVariables.getScaleX(), 1200 * GameVariables.getScaleY(),
				190 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		uraniumLabel.setBounds(750 * GameVariables.getScaleX(), 1200 * GameVariables.getScaleY(),
				190 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		goldLabel.setBounds(1050 * GameVariables.getScaleX(), 1200 * GameVariables.getScaleY(),
				190 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		saveButton.setPosition(-300 * GameVariables.getScaleX(), 250 * GameVariables.getScaleY());
		loadButton.setPosition(-300 * GameVariables.getScaleX(), 150 * GameVariables.getScaleY());
		showRegionButton.setPosition(-300 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
		citiesPanel.setBounds(2000 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY(),
				200 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		citiesLabel.setBounds(2100 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY(),
				200 * GameVariables.getScaleX(), 75 * GameVariables.getScaleY());
		hintsBackground.setBounds(1200 * GameVariables.getScaleX(), -150 * GameVariables.getScaleY(),
				480 * GameVariables.getScaleX(), 100 * GameVariables.getScaleY());
		hintsLabel.setBounds(1200 * GameVariables.getScaleX(), -150 * GameVariables.getScaleY(),
				480 * GameVariables.getScaleX(), 100 * GameVariables.getScaleY());
		settingsButton.setBounds(1130 * GameVariables.getScaleX(), -150 * GameVariables.getScaleY(),
				60 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		companionsButton.setBounds(2000 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 150 * GameVariables.getScaleY());
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		backgroundStages.act(Gdx.graphics.getDeltaTime());
		backgroundStages.draw();
		regionsStage.act(Gdx.graphics.getDeltaTime());
		regionsStage.draw();
		interfaceStage.act(Gdx.graphics.getDeltaTime());
		interfaceStage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		texturesPixmap.dispose();
	}

	private void registerSharedInterfaceComponents() {
		this.sharedInterfaceComponents.add(titaniumBackground);
		this.sharedInterfaceComponents.add(uraniumBackground);
		this.sharedInterfaceComponents.add(petroleumBackground);
		this.sharedInterfaceComponents.add(goldBackground);
		this.sharedInterfaceComponents.add(hintsBackground);
		this.sharedInterfaceComponents.add(titaniumLabel);
		this.sharedInterfaceComponents.add(uraniumLabel);
		this.sharedInterfaceComponents.add(petroleumLabel);
		this.sharedInterfaceComponents.add(goldLabel);
		this.sharedInterfaceComponents.add(hintsLabel);
		this.sharedInterfaceComponents.add(companionsButton);
		this.sharedInterfaceComponents.add(settingsButton);
		this.sharedInterfaceComponents.add(settingsWindow);
		this.sharedInterfaceComponents.add(officersPanel);
	}

	public void initiateAnimationTasks() {
		sparklingTask = new Timer.Task() {
			@Override
			public void run() {
				animateBackground();
			}
		};
		interfaceOpeningTask = new Timer.Task() {
			@Override
			public void run() {
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(50 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
						showRegionButton.addAction(action1);
					}
				}, 0.1f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(50 * GameVariables.getScaleX(), 250 * GameVariables.getScaleY());
						saveButton.addAction(action1);
					}
				}, 0.3f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(50 * GameVariables.getScaleX(), 150 * GameVariables.getScaleY());
						loadButton.addAction(action1);
					}
				}, 0.2f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(50 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						MoveToAction action2 = new MoveToAction();
						action2.setDuration(0.2f);
						action2.setPosition(150 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						titaniumBackground.addAction(action1);
						titaniumLabel.addAction(action2);
					}
				}, 0.4f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(350 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						MoveToAction action2 = new MoveToAction();
						action2.setDuration(0.2f);
						action2.setPosition(450 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						petroleumBackground.addAction(action1);
						petroleumLabel.addAction(action2);
					}
				}, 0.5f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(650 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						MoveToAction action2 = new MoveToAction();
						action2.setDuration(0.2f);
						action2.setPosition(750 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						uraniumBackground.addAction(action1);
						uraniumLabel.addAction(action2);
					}
				}, 0.6f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(950 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						MoveToAction action2 = new MoveToAction();
						action2.setDuration(0.2f);
						action2.setPosition(1050 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						goldBackground.addAction(action1);
						goldLabel.addAction(action2);
					}
				}, 0.7f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(1700 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						MoveToAction action2 = new MoveToAction();
						action2.setDuration(0.2f);
						action2.setPosition(1800 * GameVariables.getScaleX(), 950 * GameVariables.getScaleY());
						citiesPanel.addAction(action1);
						citiesLabel.addAction(action2);
					}
				}, 0.8f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(1730 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
						companionsButton.addAction(action1);
					}
				}, 0.9f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(1200 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
						MoveToAction action2 = new MoveToAction();
						action2.setDuration(0.2f);
						action2.setPosition(1200 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
						hintsLabel.addAction(action1);
						hintsBackground.addAction(action2);
						this.cancel();
					}
				}, 1f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						MoveToAction action1 = new MoveToAction();
						action1.setDuration(0.2f);
						action1.setPosition(1130 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
						MoveToAction action2 = new MoveToAction();
						settingsButton.addAction(action1);
						this.cancel();
					}
				}, 1.1f, 1, 1);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						if (!firstMessagePopUp) {
							firstPopUp = new TutorialPopUp(LanguageMap.findString("firstTutorialMessage"), 1100, 450,
									350, 100, 100, 90);
							interfaceStage.addActor(firstPopUp);
							regionsStage.addListener(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									firstPopUp.remove();
									firstPopUp = null;
									regionsStage.removeListener(this);
								}
							});
							firstMessagePopUp = true;
						}
					}
				}, 3f);

				this.cancel();
			}
		};
		regionsFadeInTask = new Timer.Task() {
			@Override
			public void run() {
				showRegionIcons();
				this.cancel();
			}
		};
		regionsFadeIn = new Timer.Task() {
			@Override
			public void run() {
				if (GameVariables.getStageFadingAlpha() < 1) {
					GameVariables.setStageFadingAlpha(GameVariables.getStageFadingAlpha() + (1f / 30f));
				} else {
					GameVariables.setStageFadingAlpha(1);
					this.cancel();
				}
			}
		};
		regionsFadeOut = new Timer.Task() {
			@Override
			public void run() {
				if (GameVariables.getStageFadingAlpha() > 0) {
					GameVariables.setStageFadingAlpha(GameVariables.getStageFadingAlpha() - (1f / 30f));
				} else {
					GameVariables.setStageFadingAlpha(0);
					for (final RegionIcon icon : registeredRegionIcons) {
						icon.remove();
					}
					this.cancel();
				}
			}
		};
	}

	public void setUpListeners() {
		this.companionsButton.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText(LanguageMap.findString("hintsLabelOfficerWindowText"));
				event.stop();
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText("");
				event.stop();
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				officersPanel.setVisible(true);
				GameSession.notifyObservers();
				event.stop();
			}
		});
		this.petroleumBackground.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText(LanguageMap.findString("hintsLabelPetroleumText"));
				event.stop();
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText("");
				event.stop();
			}
		});
		this.titaniumBackground.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText(LanguageMap.findString("hintsLabelTitaniumText"));
				event.stop();
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText("");
				event.stop();
			}
		});
		this.uraniumBackground.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText(LanguageMap.findString("hintsLabelUraniumText"));
				event.stop();
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText("");
				event.stop();
			}
		});
		this.goldBackground.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText(LanguageMap.findString("hintsLabelGoldText"));
				event.stop();
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText("");
				event.stop();
			}
		});
		this.citiesPanel.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText(LanguageMap.findString("hintsLabelCitiesPanelText"));
				event.stop();
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hintsLabel.setText("");
				event.stop();
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (GameSession.getIsPlayerCyborg() != null) {
					GalaxyView.this.citiesSummaryWindow.setVisible(true);
					GalaxyView.this.citiesSummaryWindow.updateSummary();
				}
			}
		});
		this.showRegionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (regionsFadeIn.isScheduled() || registeredRegionIcons.get(0).hasParent()) {
					hideRegionIcons();
				} else {
					showRegionIcons();
				}
				event.stop();
			}
		});
		this.settingsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				settingsWindow.setVisible(true);
			}
		});
		this.saveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				saveGameWindow.setVisible(true);
				saveGameWindow.updateFilesPanel();
			}
		});
		this.loadButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				loadGameWindow.setVisible(true);
				loadGameWindow.updateFilesPanel();
			}
		});
	}

	private void animateBackground() {
		if (MathUtils.random(4) == 1 && spark0.getColor().a == 0) {
			spark0.fadeIn(1);
		} else if (MathUtils.random(2) == 1 && spark0.getColor().a == 1) {
			spark0.fadeOut(1);
		}
		if (MathUtils.random(4) == 1 && spark1.getColor().a == 0) {
			spark1.fadeIn(1);
		} else if (MathUtils.random(2) == 1 && spark1.getColor().a == 1) {
			spark1.fadeOut(1);
		}
		if (MathUtils.random(4) == 1 && spark2.getColor().a == 0) {
			spark2.fadeIn(1);
		} else if (MathUtils.random(2) == 1 && spark2.getColor().a == 1) {
			spark2.fadeOut(1);
		}
	}

	private void showRegionIcons() {
		regionsFadeOut.cancel();
		regionsFadeIn.cancel();
		GameVariables.setStageFadingAlpha(0);
		for (final RegionIcon icon : registeredRegionIcons) {
			regionsStage.addActor(icon);
		}
		Timer.schedule(regionsFadeIn, 0, (1f / 30f));
		officersPanel.getLightFade().remove();
		citiesSummaryWindow.getLightFade().remove();
		citiesSummaryWindow.getSiegeReportsWindow().getLightFade().remove();
		saveGameWindow.getLightFade().remove();
		loadGameWindow.getLightFade().remove();
		this.regionsStage.addActor(officersPanel.getLightFade());
		this.regionsStage.addActor(citiesSummaryWindow.getLightFade());
		this.regionsStage.addActor(citiesSummaryWindow.getSiegeReportsWindow().getLightFade());
		this.regionsStage.addActor(settingsWindow.getLightFade());
		this.regionsStage.addActor(saveGameWindow.getLightFade());
		this.regionsStage.addActor(loadGameWindow.getLightFade());
	}

	public void hideRegionIcons() {
		regionsFadeOut.cancel();
		regionsFadeIn.cancel();
		GameVariables.setStageFadingAlpha(1);
		Timer.schedule(regionsFadeOut, 0, (1f / 30f));
	}

	private void setUpRegionIcons() {
		RegionIcon reg0 = new RegionIcon("szakala", 1072, 65, 374, 343, GameSession.getGalaxyRegions().get(0));
		RegionIcon reg1 = new RegionIcon("jednorozca", 703, 75, 460, 320, GameSession.getGalaxyRegions().get(1));
		RegionIcon reg2 = new RegionIcon("nemesis", 586, 128, 341, 322, GameSession.getGalaxyRegions().get(2));
		RegionIcon reg3 = new RegionIcon("gawrona", 233, 153, 486, 328, GameSession.getGalaxyRegions().get(3));
		RegionIcon reg4 = new RegionIcon("lwa", 283, 355, 371, 318, GameSession.getGalaxyRegions().get(4));
		RegionIcon reg5 = new RegionIcon("ptasznika", 281, 459, 483, 367, GameSession.getGalaxyRegions().get(5));
		RegionIcon reg6 = new RegionIcon("padalca", 931, 644, 390, 287, GameSession.getGalaxyRegions().get(6));
		RegionIcon reg7 = new RegionIcon("wieloryba", 1150, 588, 425, 274, GameSession.getGalaxyRegions().get(7));
		RegionIcon reg8 = new RegionIcon("upiora", 1235, 455, 354, 202, GameSession.getGalaxyRegions().get(8));
		RegionIcon reg9 = new RegionIcon("pokutnika", 1242, 210, 350, 296, GameSession.getGalaxyRegions().get(9));
		RegionIcon reg10 = new RegionIcon("feniksa", 1058, 352, 225, 353, GameSession.getGalaxyRegions().get(10));
		RegionIcon reg11 = new RegionIcon("gonca", 909, 366, 182, 292, GameSession.getGalaxyRegions().get(11));
		RegionIcon reg12 = new RegionIcon("drapacza", 716, 404, 224, 314, GameSession.getGalaxyRegions().get(12));
		RegionIcon reg13 = new RegionIcon("borubara", 602, 641, 353, 289, GameSession.getGalaxyRegions().get(13));
		registeredRegionIcons.add(reg0);
		registeredRegionIcons.add(reg1);
		registeredRegionIcons.add(reg2);
		registeredRegionIcons.add(reg3);
		registeredRegionIcons.add(reg4);
		registeredRegionIcons.add(reg5);
		registeredRegionIcons.add(reg6);
		registeredRegionIcons.add(reg7);
		registeredRegionIcons.add(reg8);
		registeredRegionIcons.add(reg9);
		registeredRegionIcons.add(reg10);
		registeredRegionIcons.add(reg11);
		registeredRegionIcons.add(reg12);
		registeredRegionIcons.add(reg13);
		for (final RegionIcon icon : registeredRegionIcons) {
			icon.addListener(new ClickListener() {
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					String name = "Region " + icon.getGalaxyRegion().getName() + ", ";
					if (icon.getGalaxyRegion().getCaptureState() == RegionCaptureState.NOT_AVAILABLE) {
						name += LanguageMap.findString("hintsLabelRegionAccessDenied");
					}
					if (icon.getGalaxyRegion().getCaptureState() == RegionCaptureState.AVAILABLE) {
						name += LanguageMap.findString("hintsLabelRegionEnemyState");
					}
					if (icon.getGalaxyRegion().getCaptureState() == RegionCaptureState.CAPTURED) {
						name += LanguageMap.findString("hintsLabelRegionAllyState");
					}
					hintsLabel.setText(name);
					event.stop();
				}

				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					hintsLabel.setText("");
					event.stop();
				}

				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (icon.getGalaxyRegion().getCaptureState() != RegionCaptureState.NOT_AVAILABLE) {
						mainGame.goToGalaxyRegionView(icon);
					}
					event.stop();
				}
			});
		}
	}

	public void setTitaniumAmount(int amount) {
		this.titaniumLabel.setText(amount);
	}

	public void setPetroleumAmount(int amount) {
		this.petroleumLabel.setText(amount);
	}

	public void setUraniumAmount(int amount) {
		this.uraniumLabel.setText(amount);
	}

	public void setGoldAmount(int amount) {
		this.goldLabel.setText(amount);
	}

	public void setHintsText(String text) {
		this.hintsLabel.setText(text);
	}

	public ArrayList<Actor> getSharedInterfaceComponents() {
		return sharedInterfaceComponents;
	}

	public void startSparkling() {
		Timer.schedule(sparklingTask, 0, 0.5f);
	}

	public void stopSparkling() {
		sparklingTask.cancel();
	}

	public void animateInterfaceOpening() {
		Timer.schedule(interfaceOpeningTask, 0, 1);
	}

	public void animateInterfaceOpeningGW() {
		Timer.schedule(interfaceOpeningTask, 0, 1);
		Timer.schedule(regionsFadeInTask, 1, 1);
	}

	public void showSiegeInfo() {
		this.citiesSummaryWindow.getSiegeReportsWindow().setVisible(true);
	}

	public InputProcessor getInputMultiplexer() {
		return this.inpM;
	}

	public Stage getBackgroundStages() {
		return this.backgroundStages;
	}

	public Stage getInterfaceStage() {
		return this.interfaceStage;
	}
	public Stage getRegionsStage()
	{
		return this.regionsStage;
	}
	public MenuWindow getEndWindow()
	{
		return this.endWindow;
	}
	public LaserowaAnihilacjaNajdzdzcowzKosmosu getMainGame()
	{
		return this.mainGame;
	}

	@Override
	public void update() {
		//poprzednia zawartoœæ regionStateChanged
		boolean winFlag = true;
		for (RegionIcon icon : this.registeredRegionIcons) {
			for (GalaxyRegion region : GameSession.getGalaxyRegions()) {
				if (region.getCaptureState() != RegionCaptureState.CAPTURED) {
					winFlag = false;
				}
			}
			icon.changeColor();
		}
		if (winFlag == true) {
			MenuWindow winWindow = new MenuWindow(LanguageMap.findString("endMessage"), 350, 300, 1220, 480);
			this.interfaceStage.addActor(winWindow);
			this.regionsStage.addActor(winWindow.getLightFade());
			winWindow.addListenerToOkButton(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Gdx.app.exit();
					event.stop();
				}
			});
			this.citiesSummaryWindow.getSiegeReportsWindow().remove();
		}
		//poprzednia zawartoœæ updateSiegeInfo
		ArrayList<Siege>array = GameSession.getActiveSieges();
		ArrayList<Siege>array2 = GameSession.getEndedSieges();
		if (array.isEmpty() && array2.isEmpty()) {
			this.citiesSummaryWindow.getSiegeReportsWindow().setText(LanguageMap.findString("siegeReportNoSieges"));
		} else {
			this.citiesSummaryWindow.getSiegeReportsWindow().setText("");
			if (!array.isEmpty()) {
				for (Siege siege : array) {
					if (siege.alreadyStarted() && siege.getOfficer() == null) {
						this.citiesSummaryWindow.getSiegeReportsWindow()
								.addText(LanguageMap.findString("siegeReportInRegion")
										+ siege.getCity().getSystem().getRegionName()
										+ LanguageMap.findString("siegeReportStarSystem")
										+ siege.getCity().getSystem().getName()
										+ LanguageMap.findString("siegeReportSiegeNoDefender"));
					} else if (siege.alreadyStarted() && siege.getOfficer() != null) {
						this.citiesSummaryWindow.getSiegeReportsWindow()
								.addText(LanguageMap.findString("siegeReportInRegion")
										+ siege.getCity().getSystem().getRegionName()
										+ LanguageMap.findString("siegeReportStarSystem")
										+ siege.getCity().getSystem().getName()
										+ LanguageMap.findString("siegeReportSiegeDefender")
										+ siege.getOfficer().getName()
										+ LanguageMap.findString("siegeReportSiegeDefender1"));
					} else if (!siege.alreadyStarted() && siege.getOfficer() == null) {
						this.citiesSummaryWindow.getSiegeReportsWindow()
								.addText(LanguageMap.findString("siegeReportInRegion")
										+ siege.getCity().getSystem().getRegionName()
										+ LanguageMap.findString("siegeReportStarSystem1")
										+ siege.getCity().getSystem().getName()
										+ LanguageMap.findString("siegeReportEnemySpoted"));
					} else if (!siege.alreadyStarted() && siege.getOfficer() != null) {
						this.citiesSummaryWindow.getSiegeReportsWindow()
								.addText(LanguageMap.findString("siegeReportInRegion")
										+ siege.getCity().getSystem().getRegionName()
										+ LanguageMap.findString("siegeReportStarSystem1")
										+ siege.getCity().getSystem().getName() + "\n" + siege.getOfficer().getName()
										+ LanguageMap.findString("siegeReportDefenderPrepare"));
					}
				}
			}
			if (!array2.isEmpty()) {
				for (Siege siege : array2) {
					if (siege.isWon() == true) {
						this.citiesSummaryWindow.getSiegeReportsWindow()
								.addText(LanguageMap.findString("siegeReportInRegion")
										+ siege.getCity().getSystem().getRegionName()
										+ LanguageMap.findString("siegeReportDefenderSuccess")
										+ siege.getCity().getSystem().getName() + "!\n" + siege.getOfficer().getName()
										+ LanguageMap.findString("siegeReportDefenderSuccess1"));
					} else {
						if (siege.getOfficer() != null)
							this.citiesSummaryWindow.getSiegeReportsWindow()
									.addText(LanguageMap.findString("siegeReportInRegion")
											+ siege.getCity().getSystem().getRegionName()
											+ LanguageMap.findString("siegeReportCityLose")
											+ siege.getCity().getSystem().getName()
											+ LanguageMap.findString("siegeReportCityLose1")
											+ siege.getOfficer().getName()
											+ LanguageMap.findString("siegeReportCityLose2"));
						else
							this.citiesSummaryWindow.getSiegeReportsWindow()
									.addText(LanguageMap.findString("siegeReportInRegion")
											+ siege.getCity().getSystem().getRegionName()
											+ LanguageMap.findString("siegeReportCityLose")
											+ siege.getCity().getSystem().getName()
											+ LanguageMap.findString("siegeReportCityLose1"));
					}
				}
			}
		}
		//poprzednia zawartoœæ ecoChanged
		this.titaniumLabel.setText(GameSession.getCurrentTitanium());
		this.petroleumLabel.setText(GameSession.getCurrentPetroleum());
		this.uraniumLabel.setText(GameSession.getCurrentUranium());
		this.goldLabel.setText(GameSession.getCurrentGold());
		//poprzednia zawartoœæ updateCitiesCount
		this.citiesLabel.setText(GameSession.getRegisteredCities().size());
	}
}
