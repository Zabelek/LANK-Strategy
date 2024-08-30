package pl.zabel.lank.utilities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import pl.zabel.lank.GameVariables;

public class SoundEffectManager {
	
	public static ArrayList<Sound> registeredSoundEffects;
	//interface
	public static Sound clickSound, clickLocked, galaxyNavigation, smallClick, windowpopUp, buildingPlaced,
	nextTurn;
	//mapView
	public static Sound bigExplosionSound, heavyBotShootSound, botSelectSound, commandUnitSelectSound,
	commandUnitShootSound, heavyTankShootSound, lightTankShootSound, buildLaserSound, 
	mediumBotShootSound, lightBotShootSound, mediumTankShootSound, buildingSelectSound, 
	smallExplosion, tankSelectSound;
	
	public static void init()
	{
		registeredSoundEffects = new ArrayList<Sound>();
	}
	public static void playClickSound()
	{
		if(clickSound==null)
		{
			clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.ogg"));
			registeredSoundEffects.add(clickSound);
		}
		try {
		clickSound.play(GameVariables.getEffectsVolume());}	
		catch(NullPointerException e) {}
	}
	public static void playClickLockedSound()
	{
		if(clickLocked==null)
		{
			clickLocked = Gdx.audio.newSound(Gdx.files.internal("sounds/click-locked.ogg"));
			registeredSoundEffects.add(clickLocked);
		}
		try {
		clickLocked.play(GameVariables.getEffectsVolume());}
		catch(NullPointerException e) {}
	}
	public static void playGalaxyNavigationSound()
	{
		if(galaxyNavigation==null)
		{
			galaxyNavigation = Gdx.audio.newSound(Gdx.files.internal("sounds/galaxy-navigation-sound.ogg"));
			registeredSoundEffects.add(galaxyNavigation);
		}
		try {
		galaxyNavigation.play(GameVariables.getEffectsVolume());}
		catch(NullPointerException e) {}
	}
	public static void playSmallClickSound()
	{
		if(smallClick==null)
		{
			smallClick = Gdx.audio.newSound(Gdx.files.internal("sounds/small-click.ogg"));
			registeredSoundEffects.add(smallClick);
		}
		try {
		smallClick.play(GameVariables.getEffectsVolume());}
		catch(NullPointerException e) {}
	}
	public static void playWindowPopUpSound()
	{
		if(windowpopUp==null)
		{
			windowpopUp = Gdx.audio.newSound(Gdx.files.internal("sounds/window-popup-sound.ogg"));
			registeredSoundEffects.add(windowpopUp);
		}
		try {
		windowpopUp.play(GameVariables.getEffectsVolume());}
		catch(NullPointerException e) {}
	}
	public static void playBuildingPlacedSound()
	{
		if(buildingPlaced==null)
		{
			buildingPlaced = Gdx.audio.newSound(Gdx.files.internal("sounds/building-placed-sound.ogg"));
			registeredSoundEffects.add(buildingPlaced);
		}
		try {
		buildingPlaced.play(GameVariables.getEffectsVolume());}
		catch(NullPointerException e) {}
	}
	public static void playNextTurnSound()
	{
		if(nextTurn==null)
		{
			nextTurn = Gdx.audio.newSound(Gdx.files.internal("sounds/next-turn-sound.ogg"));
			registeredSoundEffects.add(nextTurn);
		}
		try {
		nextTurn.play(GameVariables.getEffectsVolume());}
		catch(NullPointerException e) {}
	}
	public static void disposeAllSounds()
	{
		for(int i = registeredSoundEffects.size(); i>0; i--)
		{
			registeredSoundEffects.get(i-1).dispose();
		}
		registeredSoundEffects.clear();
	}
	public static void stopAllSounds()
	{
		for(int i = registeredSoundEffects.size(); i>0; i--)
		{
			registeredSoundEffects.get(i-1).stop();
		}
		registeredSoundEffects.clear();
	}
	public static void playBigExplosionSound(float volume)
	{
		if(bigExplosionSound==null)
		{
			bigExplosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/big-explosion.ogg"));
			registeredSoundEffects.add(bigExplosionSound);
		}
		try {
		bigExplosionSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playHeavyBotShootSound(float volume)
	{
		if(heavyBotShootSound==null)
		{
			heavyBotShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/heavy-bot-shoot.ogg"));
			registeredSoundEffects.add(heavyBotShootSound);
		}
		try {
		heavyBotShootSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playBotSelectSound(float volume)
	{
		if(botSelectSound==null)
		{
			botSelectSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bot-select.ogg"));
			registeredSoundEffects.add(botSelectSound);
		}
		try {
		botSelectSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playCommandUnitSelectSound(float volume)
	{
		if(commandUnitSelectSound==null)
		{
			commandUnitSelectSound = Gdx.audio.newSound(Gdx.files.internal("sounds/command-unit-select.ogg"));
			registeredSoundEffects.add(commandUnitSelectSound);
		}
		try {
		commandUnitSelectSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playCommandUnitShootSound(float volume)
	{
		if(commandUnitShootSound==null)
		{
			commandUnitShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/command-unit-shoot.ogg"));
			registeredSoundEffects.add(commandUnitShootSound);
		}
		try {
		commandUnitShootSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playHeavyTankShootSound(float volume)
	{
		if(heavyTankShootSound==null)
		{
			heavyTankShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/heavy-tank-shoot.ogg"));
			registeredSoundEffects.add(heavyTankShootSound);
		}
		try {
		heavyTankShootSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playLightTankShootSound(float volume)
	{
		if(lightTankShootSound==null)
		{
			lightTankShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/light-tank-shoot.ogg"));
			registeredSoundEffects.add(lightTankShootSound);
		}
		try {
		lightTankShootSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playMediumBotShootSound(float volume)
	{
		if(mediumBotShootSound==null)
		{
			mediumBotShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/medium-bot-shoot.ogg"));
			registeredSoundEffects.add(mediumBotShootSound);
		}
		try {
		mediumBotShootSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playLightBotShootSound(float volume)
	{
		if(lightBotShootSound==null)
		{
			lightBotShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/light-bot-shoot.ogg"));
			registeredSoundEffects.add(lightBotShootSound);
		}
		try {
		lightBotShootSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playMediumTankShootSound(float volume)
	{
		if(mediumTankShootSound==null)
		{
			mediumTankShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/medium-tank-shoot.ogg"));
			registeredSoundEffects.add(mediumTankShootSound);
		}
		try {
		mediumTankShootSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playBuildingSelectSound(float volume)
	{
		if(buildingSelectSound==null)
		{
			buildingSelectSound = Gdx.audio.newSound(Gdx.files.internal("sounds/building-select.ogg"));
			registeredSoundEffects.add(buildingSelectSound);
		}
		try {
		buildingSelectSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playSmallExplosion(float volume)
	{
		if(smallExplosion==null)
		{
			smallExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/small-explosion.ogg"));
			registeredSoundEffects.add(smallExplosion);
		}
		try {
		smallExplosion.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playTankSelectSound(float volume)
	{
		if(tankSelectSound==null)
		{
			tankSelectSound = Gdx.audio.newSound(Gdx.files.internal("sounds/tank-select.ogg"));
			registeredSoundEffects.add(tankSelectSound);
		}
		try {
		tankSelectSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static void playBuildLaserSound(float volume)
	{
		if(buildLaserSound==null)
		{
			buildLaserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/build-laser.ogg"));
			registeredSoundEffects.add(buildLaserSound);
		}
		try {
		buildLaserSound.play(GameVariables.getEffectsVolume()*volume);}
		catch(NullPointerException e) {}
	}
	public static Long playSoundLoop(float volume, Sound givenSound, String name)
	{
		registeredSoundEffects.add(givenSound);
		return givenSound.loop(GameVariables.getEffectsVolume()*volume);
	}
	public static void stopSoundLoop(Sound sound, Long id)
	{
		if(sound!=null)
		{
			sound.stop(id);
			registeredSoundEffects.remove(sound);
			sound.dispose();
		}
	}
}
