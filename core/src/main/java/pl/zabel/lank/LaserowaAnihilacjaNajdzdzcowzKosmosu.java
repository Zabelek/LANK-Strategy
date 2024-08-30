package pl.zabel.lank;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.texturecontainers.CityTextureContainer;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.LabWojTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.userinterface.MenuWindow;
import pl.zabel.lank.userinterface.RegionIcon;
import pl.zabel.lank.utilities.FadeableActor;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.MusicManager;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.City.CityType;
import pl.zabel.lank.gameplayobjects.missionview.Mission;
import pl.zabel.lank.views.CityView;
import pl.zabel.lank.views.GalaxyRegionView;
import pl.zabel.lank.views.GalaxyView;
import pl.zabel.lank.views.IntroView;
import pl.zabel.lank.views.MapView;
import pl.zabel.lank.views.MissionView;

public class LaserowaAnihilacjaNajdzdzcowzKosmosu extends Game {
	
	
	private SpriteBatch batch;
	private FadeableActor black; //czarny screen do fadeOut ca³ej sceny. Przekazywaæ do podscen.
	
	ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	//intro related variables
	
	Label introLabel;
	FadeableActor title;
	
	private GalaxyView galaxyView;
	private GalaxyRegionView galaxyRegionView;
	private CityView cityView;
	private MissionView missionView;
	private MapView mapView;
	
	@Override
	public void create () {
		GameSession.init();
		MapSession.init();
		LanguageMap.init();
		InterfaceTextureContainer.load();
		TextStylesContainer.load();
		GameVariables.initializeGameVariables();
		setBlack(new FadeableActor(InterfaceTextureContainer.findRegion("blackFade"), 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
		IntroView introView = new IntroView(this);
		this.setScreen(introView);
		Gdx.input.setInputProcessor(introView);
		MusicManager.init();
		SoundEffectManager.init();
		Pixmap pm = new Pixmap(Gdx.files.internal("graphics/cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
	}

	@Override
	public void render () {

		super.render();
	}
	
	
	public GalaxyView getGalaxyView() {
		return galaxyView;
	}

	public void setViews(GalaxyView galaxyView) {
		this.galaxyView = galaxyView;
		galaxyRegionView = new GalaxyRegionView(this);
		cityView = new CityView(this);
		missionView = new MissionView(this);
		mapView = new MapView(this);
	}

	@Override
	public void dispose () {	
		
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public FadeableActor getBlack() {
		return black;
	}

	public void setBlack(FadeableActor black) {
		this.black = black;
	}
	public void goBackToGalaxyView() {
		MoveToAction move = new MoveToAction();
		move.setPosition(0, 0);
		move.setDuration(0.01f);
		ScaleToAction scale = new ScaleToAction();
		scale.setScale(1);
		scale.setDuration(0.01f);
		galaxyView.getBackgroundStages().addAction(new ParallelAction(move, scale));
		galaxyView.hideRegionIcons();
		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				setScreen(galaxyView);
				for (Actor comp : galaxyView.getSharedInterfaceComponents()) {
					comp.remove();
					galaxyView.getInterfaceStage().addActor(comp);
				}
				galaxyView.setInterfaceStartPosition();
				galaxyView.animateInterfaceOpeningGW();
				galaxyView.startSparkling();
				galaxyView.getInterfaceStage().addActor(getBlack());
				getBlack().fadeOut(1);
				galaxyRegionView.dispose();
				Gdx.input.setInputProcessor(galaxyView.getInputMultiplexer());
				this.cancel();
			}
		}, 1, 1, 1);
		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				getBlack().remove();
				this.cancel();
			}
		}, 2, 1, 1);

	}

	public void goToGalaxyRegionView(final RegionIcon galaxyRegion) {
		Gdx.input.setInputProcessor(galaxyView.getBackgroundStages());
		galaxyView.hideRegionIcons();
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				MoveByAction move = new MoveByAction();
				move.setAmount(-galaxyRegion.getX() * 2.5f, -galaxyRegion.getY() * 2.5f);
				move.setDuration(2);
				ScaleByAction scale = new ScaleByAction();
				scale.setAmount(2);
				scale.setDuration(2);
				galaxyView.getBackgroundStages().addAction(new ParallelAction(move, scale));
				galaxyView.getInterfaceStage().addActor(getBlack());
				getBlack().fadeIn(2);
				this.cancel();
			}
		}, 1, 100, 1);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				try {
					galaxyRegionView.assignRegion(galaxyRegion.getGalaxyRegion());
					setScreen(galaxyRegionView);
					for (Actor comp : galaxyView.getSharedInterfaceComponents()) {
						comp.remove();
						if (MenuWindow.class.isAssignableFrom(comp.getClass())) {
							((MenuWindow) comp).getLightFade().remove();
						}
					}
					galaxyRegionView.addSharedComponents();
					galaxyView.setInterfaceStartPosition();
					galaxyView.animateInterfaceOpening();
					galaxyView.stopSparkling();
					getBlack().remove();
				} catch (NullPointerException e) {
					galaxyView.getInterfaceStage().addActor(galaxyView.getEndWindow());
					galaxyView.getRegionsStage().addActor(galaxyView.getEndWindow().getLightFade());
					galaxyView.getEndWindow().setVisible(true);
					Gdx.input.setInputProcessor(galaxyView.getInterfaceStage());
				}
				this.cancel();
			}
		}, 3, 100, 1);
	}

	public void goToCityView(final City city) {
		Gdx.input.setInputProcessor(galaxyView.getBackgroundStages());
		galaxyRegionView.fadeOut(0.5f);
		CityTextureContainer.load();
		LabWojTextureContainer.load();
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				cityView.setCity(city);
				setScreen(cityView);
				for (Actor comp : galaxyView.getSharedInterfaceComponents()) {
					comp.remove();
					if (MenuWindow.class.isAssignableFrom(comp.getClass())) {
						((MenuWindow) comp).getLightFade().remove();
					}
				}
				cityView.addSharedComponents();
				galaxyView.setInterfaceStartPosition();
				galaxyView.animateInterfaceOpening();
				galaxyView.stopSparkling();
				getBlack().remove();
				Gdx.input.setInputProcessor(cityView.getInputMultiplexer());
				cityView.fadeIn(0.5f);
				if (city.getType() == CityType.MUTANT)
					MusicManager.playMutantCityMusic();
				else
					MusicManager.playCyborgCityMusic();
				this.cancel();
			}
		}, 0.5f, 100, 1);
	}

	public void goToMissionView(final Mission mission, byte sequence) {
		Gdx.input.setInputProcessor(galaxyView.getBackgroundStages());
		galaxyRegionView.fadeOut(0.5f);
		missionView.AssignMission(mission, sequence);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				MusicManager.playMissionBackground();
				setScreen(missionView);
				Gdx.input.setInputProcessor(missionView.getMainStage());
				missionView.checkIfLast();
				this.cancel();
			}
		}, 1f, 100, 1);
	}

	public void goToMapView(final Mission mission) {
		Gdx.input.setInputProcessor(galaxyView.getBackgroundStages());
		this.missionView.fadeOut(0.5f);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				MusicManager.playMapMusic();
				mapView.loadMap(mission);
				setScreen(mapView);
				this.cancel();
			}
		}, 0.5f, 100, 1);
	}

	public GalaxyRegionView getGalaxyRegionView() {
		return galaxyRegionView;
	}

	public void setGalaxyRegionView(GalaxyRegionView galaxyRegionView) {
		this.galaxyRegionView = galaxyRegionView;
	}

	public CityView getCityView() {
		return cityView;
	}

	public void setCityView(CityView cityView) {
		this.cityView = cityView;
	}

	public MissionView getMissionView() {
		return missionView;
	}

	public void setMissionView(MissionView missionView) {
		this.missionView = missionView;
	}

	public MapView getMapView() {
		return mapView;
	}

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}
	
}
