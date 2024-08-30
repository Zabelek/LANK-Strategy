package pl.zabel.lank.views;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import pl.zabel.lank.LaserowaAnihilacjaNajdzdzcowzKosmosu;
import pl.zabel.lank.texturecontainers.GalaxyTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.FadeableActor;
import pl.zabel.lank.utilities.MusicManager;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.utilities.PlayableVideo;

public class IntroView implements Screen, InputProcessor{
	
	private LaserowaAnihilacjaNajdzdzcowzKosmosu mainGame;
	private static Stage stage;
	private Timer.Task nextScene;
	private PlayableVideo intro;
	
	Label introLabel;
	FadeableActor title;
	public IntroView(LaserowaAnihilacjaNajdzdzcowzKosmosu mainGame)
	{
		this.mainGame = mainGame;
		stage = new Stage(new ScreenViewport());
		this.intro = new PlayableVideo("intro", 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(intro);
		setTasks();
	    GalaxyTextureContainer.load();
		Timer.schedule(nextScene,100, 1, 1);
		playIntro();
		
	}
	private void playIntro()
	{			
	}
	
	public void setTasks()
	{
		nextScene = new Timer.Task() {			
			@Override
			public void run() {
				stopIntro();
			}
		};
	}
	private void stopIntro()
	{
		mainGame.setViews(new GalaxyView(mainGame));
		mainGame.setScreen(mainGame.getGalaxyView());
		MusicManager.playGalaxyViewMusic();
		nextScene.cancel();
		if(intro!=null)
			intro.stop();
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(float delta) {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean keyDown(int keycode) {
		this.stopIntro();
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
