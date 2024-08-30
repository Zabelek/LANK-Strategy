package pl.zabel.lank.views;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.galaxyview.GalaxyRegion;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.userinterface.AnimatedStar;
import pl.zabel.lank.userinterface.CityBuildWindow;
import pl.zabel.lank.userinterface.MenuWindow;
import pl.zabel.lank.userinterface.OfficersPanel;
import pl.zabel.lank.userinterface.StarSystemView;
import pl.zabel.lank.userinterface.TranslatableMenuButton;
import pl.zabel.lank.userinterface.TutorialPopUp;
import pl.zabel.lank.utilities.FadeableActor;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.LaserowaAnihilacjaNajdzdzcowzKosmosu;

public class GalaxyRegionView implements Screen{
	
	private LaserowaAnihilacjaNajdzdzcowzKosmosu adapter;
	public FadeableActor black;
	private Stage interfaceStage, backgroundStage, starsStage;
	private GalaxyRegion currentRegion;
	private TextureAtlas internalBackgroundAtlas;
	private TranslatableMenuButton goBackButton;
	InputMultiplexer inpM;
	private StarSystemView starSystemView;
	private CityBuildWindow cityBuildWindow;
	
	private ArrayList<AnimatedStar> stars;
	Timer.Task animateStarsTask;
	
	//tutorial flags
	private boolean firstMessagePopUp, secondMessagePopUp;
	private TutorialPopUp firstPopUp, secondPopUp;
	private int capturedFlag;
	
	
	public GalaxyRegionView(LaserowaAnihilacjaNajdzdzcowzKosmosu adapter) {	
		this.adapter = adapter;
		this.interfaceStage = new Stage(new ScreenViewport());
		this.backgroundStage = new Stage(new ScreenViewport());
		this.starsStage = new Stage(new ScreenViewport());		
		this.adapter.getGalaxyView().setInterfaceStartPosition();
		this.adapter.getGalaxyView().initiateAnimationTasks();
		this.goBackButton = new TranslatableMenuButton(LanguageMap.findString("backToGalaxyView"), 50*GameVariables.getScaleX(), 50*GameVariables.getScaleY());
		this.goBackButton.addListener(new ClickListener() 
		{
				@Override
				public void clicked (InputEvent event, float x, float y) {
				{
					startGoToGalaxyViewProcedure();
					event.stop();
				} 			
			}		
		});
		
		interfaceStage.addActor(goBackButton);
		interfaceStage.addActor(goBackButton.getContainer());
		
		this.inpM = new InputMultiplexer();
		this.inpM.addProcessor(starsStage);
		this.inpM.addProcessor(interfaceStage);
		black = new FadeableActor(InterfaceTextureContainer.findRegion("blackFade"), 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		starSystemView = new StarSystemView(adapter.getGalaxyView(), this);
		stars = new ArrayList<AnimatedStar>();
		interfaceStage.addActor(starSystemView);
		starSystemView.setVisible(false);
		cityBuildWindow = new CityBuildWindow();
		cityBuildWindow.setVisible(false);
		interfaceStage.addActor(cityBuildWindow);
		animateStarsTask = new Timer.Task() {
			
			@Override
			public void run() {
				for(AnimatedStar star: stars)
				{
					star.animate();
				}
			}
		};
		
		firstMessagePopUp = false;
		secondMessagePopUp = false;
		capturedFlag=0;
	}
	
	public void assignRegion(GalaxyRegion region)
	{
		this.currentRegion = region;
		internalBackgroundAtlas = new TextureAtlas(Gdx.files.internal("graphics/galaxyregions/"+currentRegion.getName()+".atlas"));
		Image galaxyRegionBackground = new Image(internalBackgroundAtlas.findRegion("background"));
		galaxyRegionBackground.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		backgroundStage.addActor(galaxyRegionBackground);
		Gdx.input.setInputProcessor(this.inpM);
		this.interfaceStage.addActor(black);
		black.fadeOut(1);
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				black.remove();
				this.cancel();
			}
		}, 1,1,1);

		for(final StarSystem system : this.currentRegion.getStarSystems())
		{
			AnimatedStar ans = new AnimatedStar(this, system);
			this.stars.add(ans);
			this.starsStage.addActor(ans);
			ans.addListener(new ClickListener() {
				@Override
				public void clicked (InputEvent event, float x, float y) {
					if (system.getCaptureState()!=CaptureState.NOT_AVAILABLE)
					{
						starSystemView.setStarSystem(system);
						starSystemView.setVisible(true);
					}
					event.stop();
				} 
			});
			if(system.getCaptureState()==CaptureState.CAPTURED)
			{
				capturedFlag = 1;
			}
		}
		this.starsStage.addActor(starSystemView.getLightFade());
		Timer.schedule(animateStarsTask, 0, 0.1f);
		starSystemView.setVisible(false);
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				if(!firstMessagePopUp)
				{
					firstPopUp = new TutorialPopUp(LanguageMap.findString("clickStarSystemIcon"), 1100, 480, 450, 100, 200, -90);
					interfaceStage.addActor(firstPopUp);
					starsStage.addListener(new ClickListener() {
						@Override
						public void clicked (InputEvent event, float x, float y) {
							firstPopUp.remove();
							firstPopUp = null;
							starsStage.removeListener(this);
						}
					});
					firstMessagePopUp=true;
				}
				else if(!secondMessagePopUp && capturedFlag==1)
				{
					secondPopUp = new TutorialPopUp(LanguageMap.findString("cityBuildingTutorialPopup"), 1100, 480, 500, 100, 200, -90);
					interfaceStage.addActor(secondPopUp);
					starsStage.addListener(new ClickListener() {
						@Override
						public void clicked (InputEvent event, float x, float y) {
							secondPopUp.remove();
							secondPopUp = null;
							starsStage.removeListener(this);
						}
					});
					secondMessagePopUp=true;
				}
			}
		}, 3);
	}
	public Stage getStarsStage()
	{
		return this.starsStage;
	}
	@Override
	public void show() {}
	
	public void addSharedComponents()
	{
		for(Actor actor : this.adapter.getGalaxyView().getSharedInterfaceComponents())
		{
			this.interfaceStage.addActor(actor);
			if(MenuWindow.class.isAssignableFrom(actor.getClass()))
			{
				this.starsStage.addActor(((MenuWindow)actor).getLightFade());
			}
		}
	}
	public void displayOnHintsLabel(String str)
	{
		adapter.getGalaxyView().setHintsText(str);
	}
	public void fadeOut(float time)
	{
		black.remove();
		interfaceStage.addActor(black);
		black.fadeIn(time);
	}
	public void fadeIn(float time)
	{
		interfaceStage.addActor(black);
		black.fadeOut(time);
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				black.remove();
				this.cancel();
				
			}
		}, time,1,1);	
	}

	@Override
	public void render(float delta) {
		backgroundStage.act(Gdx.graphics.getDeltaTime());
		backgroundStage.draw();
		starsStage.act(Gdx.graphics.getDeltaTime());
		starsStage.draw();
		interfaceStage.act(Gdx.graphics.getDeltaTime());
		interfaceStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		backgroundStage.clear();
		internalBackgroundAtlas.dispose();
		internalBackgroundAtlas = null;		
	}

	public void showBuildWindow(StarSystem starSystem) {
		cityBuildWindow.setStarSystem(starSystem);
		cityBuildWindow.setVisible(true);
	}
	public void startGoToGalaxyViewProcedure()
	{
		fadeOut(1);
		Gdx.input.setInputProcessor(this.backgroundStage);
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				starsStage.clear();
				stars.clear();
				animateStarsTask.cancel();
				this.cancel();
			}
		}, 1,1,1);
		this.adapter.goBackToGalaxyView();
	}
}
