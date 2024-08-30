package pl.zabel.lank.utilities;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.views.MapView;

public class MusicManager {

	public static Music galaxyViewMusic, cyborgCityMusic, mutantCityMusic, missionBackground,
	mapFirstMusic, mapMusic1, mapMusic2, mapMusic3, battleMusic1, battleMusic2, battleMusic3, outroMusic;
	public static ArrayList<Music> registeredMusic;
	public static long galaxyViewMusicId, cyborgCityMusicId, mutantCityMusicId;
	private static Timer.Task changeMapMusicTask;
	private static boolean afterFirstMusic, currentlyPlayedBattleMusic;
	private static Random random;
	
	public static void init()
	{
		registeredMusic = new ArrayList<Music>();
		random = new Random();
		mapFirstMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/map-first-music.ogg"));
		changeMapMusicTask = new Timer.Task() {
			
			@Override
			public void run() {
				if(!mapFirstMusic.isPlaying() && !MapView.battleStarted)
				{
					if(afterFirstMusic==false)
					{
						mapFirstMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/map-first-music.ogg"));
						mapFirstMusic.setLooping(false);
						mapFirstMusic.setVolume(GameVariables.getMusicVolume());
						registeredMusic.add(mapFirstMusic);
						try {
						mapFirstMusic.play();
						}
						catch(GdxRuntimeException e)
						{
							
						}
						afterFirstMusic=true;
						currentlyPlayedBattleMusic=false;
					}
					else
					{
						registeredMusic.remove(mapFirstMusic);
						mapFirstMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/map-music-"+(Math.abs((random.nextInt()%3))+1)+".ogg"));
						mapFirstMusic.setLooping(false);
						mapFirstMusic.setVolume(GameVariables.getMusicVolume());
						registeredMusic.add(mapFirstMusic);
						try {
						mapFirstMusic.play();
						}
						catch(GdxRuntimeException e)
						{
							
						}
						afterFirstMusic=true;
						currentlyPlayedBattleMusic=false;
					
					}
				}
				else if(MapView.battleStarted && !mapFirstMusic.isPlaying())
				{
					registeredMusic.remove(mapFirstMusic);
					mapFirstMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/battle-music-"+(Math.abs((random.nextInt()%3))+1)+".ogg"));
					mapFirstMusic.setLooping(false);
					mapFirstMusic.setVolume(GameVariables.getMusicVolume());
					registeredMusic.add(mapFirstMusic);
					try
					{
					mapFirstMusic.play();
					}
					catch(GdxRuntimeException e)
					{
						
					}
					afterFirstMusic=true;
					MapView.battleStarted=false;
					currentlyPlayedBattleMusic=true;
				}
				else if(MapView.battleStarted && currentlyPlayedBattleMusic==false)
				{
					mapFirstMusic.stop();
				}
				else
				{
					MapView.battleStarted=false;
				}
			}
		};
		afterFirstMusic = false;
		currentlyPlayedBattleMusic = false;
	}
	public static void playGalaxyViewMusic()
	{
		muteAllMusic();
		galaxyViewMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/galaxy-view-music.ogg"));
		galaxyViewMusic.setLooping(true);
		galaxyViewMusic.setVolume(GameVariables.getMusicVolume());
		registeredMusic.add(galaxyViewMusic);
		Timer.schedule(new Timer.Task() {		
			@Override
			public void run() {
				try {
				galaxyViewMusic.play();
				}
				catch(GdxRuntimeException e)
				{
					
				}
				this.cancel();
			}
		}, 1, 1, 1);
	}
	public static void playCyborgCityMusic()
	{
		muteAllMusic();
		cyborgCityMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/cyborg-city-music.ogg"));
		cyborgCityMusic.setLooping(true);
		cyborgCityMusic.setVolume(GameVariables.getMusicVolume());
		registeredMusic.add(cyborgCityMusic);
		Timer.schedule(new Timer.Task() {		
			@Override
			public void run() {
				try {
				cyborgCityMusic.play();
				}
				catch(GdxRuntimeException e)
				{
					
				}
				this.cancel();
			}
		}, 1, 1, 1);	
	}
	public static void playMutantCityMusic()
	{
		muteAllMusic();
		mutantCityMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/mutant-city-music.ogg"));
		mutantCityMusic.setLooping(true);
		mutantCityMusic.setVolume(GameVariables.getMusicVolume());
		registeredMusic.add(mutantCityMusic);

		Timer.schedule(new Timer.Task() {		
			@Override
			public void run() {
				try {
				mutantCityMusic.play();
				}
				catch(GdxRuntimeException e)
				{
					
				}
				this.cancel();
			}
		}, 1, 1, 1);	
	}
	public static void muteAllMusic()
	{
		if(changeMapMusicTask.isScheduled())
		{
			changeMapMusicTask.cancel();
		}
		for(Music music : registeredMusic)
		{
			fadeOutMusic(music);
		}
		afterFirstMusic = false;
	}
	private static void fadeOutMusic(final Music music)
	{
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
					music.setVolume(Math.abs(music.getVolume()-0.1f*GameVariables.getMusicVolume()));
					if(music.getVolume()==0)
						this.cancel();
			}
		}, 0, 0.1f, 10);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
					music.stop();
					music.dispose();
					registeredMusic.remove(music);
					this.cancel();
			}
		}, 1f, 1, 1);
	}
	public static void playMissionBackground()
	{
		muteAllMusic();
		missionBackground = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/mission-background.ogg"));
		missionBackground.setLooping(true);
		missionBackground.setVolume(GameVariables.getMusicVolume());
		registeredMusic.add(missionBackground);

		Timer.schedule(new Timer.Task() {		
			@Override
			public void run() {
				try {
				missionBackground.play();
				}
				catch(GdxRuntimeException e)
				{
	
				}
				this.cancel();
			}
		}, 1, 1, 1);	
	}
	public static void playMapMusic()
	{
		muteAllMusic();
		Timer.schedule(changeMapMusicTask, 1, 0.5f);
	}
	public static void setMusicVolume(float volume)
	{
		for(Music music : registeredMusic)
		{
			music.setVolume(volume);
		}
	}
	public static void playOutroMusic()
	{
		muteAllMusic();
		outroMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/outro-music.ogg"));
		outroMusic.setLooping(true);
		outroMusic.setVolume(GameVariables.getMusicVolume());
		registeredMusic.add(outroMusic);

		Timer.schedule(new Timer.Task() {		
			@Override
			public void run() {
				try {
					outroMusic.play();
				}
				catch(GdxRuntimeException e)
				{
					
				}
				this.cancel();
			}
		}, 1, 1, 1);	
	}
}
