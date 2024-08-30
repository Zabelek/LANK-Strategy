package pl.zabel.lank;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.gameplayobjects.AlienEnemy;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.Siege;
import pl.zabel.lank.gameplayobjects.SupportOfficer;
import pl.zabel.lank.gameplayobjects.galaxyview.GalaxyRegion;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;
import pl.zabel.lank.gameplayobjects.galaxyview.GalaxyRegion.RegionCaptureState;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;
import pl.zabel.lank.utilities.GameplayDataReport;
import pl.zabel.lank.utilities.Observer;
import pl.zabel.lank.utilities.SoundEffectManager;

public class GameSession {
	
	private static ArrayList<GalaxyRegion> galaxyRegions;
	private static ArrayList<Observer> observers;
	private static ArrayList<Siege> activeSieges, endedSieges;
	private static ArrayList<City> registeredCities;
	private static SupportOfficer anna, kaite, zack, simon;
	private static Boolean isPlayerCyborg;
	private static AlienEnemy enemy;
	private static int currentTitanium, currentPetroleum, currentUranium, currentGold;
	private static int turnNumber;
	
	public static void init()
	{
		observers = new ArrayList<Observer>();
		galaxyRegions = new ArrayList<GalaxyRegion>();
		activeSieges = new ArrayList<Siege>();
		endedSieges = new ArrayList<Siege>();
		registeredCities = new ArrayList<City>();
		enemy = new AlienEnemy();
		enemy.setAgressionLevel(0);
		galaxyRegions.add(new GalaxyRegion("Szakala", RegionCaptureState.AVAILABLE, (byte)0));
		galaxyRegions.add(new GalaxyRegion("Jednorozca", RegionCaptureState.NOT_AVAILABLE, (byte)1));
		galaxyRegions.add(new GalaxyRegion("Nemesis", RegionCaptureState.NOT_AVAILABLE, (byte)2));
		galaxyRegions.add(new GalaxyRegion("Gawrona", RegionCaptureState.NOT_AVAILABLE, (byte)4));
		galaxyRegions.add(new GalaxyRegion("lwa", RegionCaptureState.NOT_AVAILABLE, (byte)5));
		galaxyRegions.add(new GalaxyRegion("ptasznika", RegionCaptureState.NOT_AVAILABLE, (byte)6));
		galaxyRegions.add(new GalaxyRegion("padalca", RegionCaptureState.NOT_AVAILABLE, (byte)10));
		galaxyRegions.add(new GalaxyRegion("wieloryba", RegionCaptureState.NOT_AVAILABLE, (byte)7));
		galaxyRegions.add(new GalaxyRegion("upiora", RegionCaptureState.NOT_AVAILABLE, (byte)5));
		galaxyRegions.add(new GalaxyRegion("pokutnika", RegionCaptureState.NOT_AVAILABLE, (byte)2));
		galaxyRegions.add(new GalaxyRegion("feniksa", RegionCaptureState.NOT_AVAILABLE, (byte)11));
		galaxyRegions.add(new GalaxyRegion("gonca", RegionCaptureState.NOT_AVAILABLE, (byte)11));
		galaxyRegions.add(new GalaxyRegion("drapacza", RegionCaptureState.NOT_AVAILABLE, (byte)13));
		galaxyRegions.add(new GalaxyRegion("borubara", RegionCaptureState.NOT_AVAILABLE, (byte)6));
		anna = new SupportOfficer("Anna");
		kaite = new SupportOfficer("Kaite");
		zack = new SupportOfficer("Zack");
		simon = new SupportOfficer("Simon");
		currentTitanium = 0;
		currentPetroleum = 0;
		currentUranium = 0;
		currentGold = 0;
		turnNumber=0;
		updateCaptureStates();
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
			}
		}, 10);
	}
	public static void attach(Observer observer){
		observers.add(observer);        
    }
	public static void notifyObservers(){
        for (Observer observer : observers) {
           observer.update();
        }
	}
	public static void addRegisteredCity(City city)
	{
		registeredCities.add(city);
		notifyObservers();
	}
	public static void removeRegisteredCity(City city)
	{
		registeredCities.remove(city);
		notifyObservers();
	}
	public static void clearRegosteredCities()
	{
		registeredCities.clear();
		notifyObservers();
	}
	public static ArrayList<City> getRegisteredCities()
	{
		return registeredCities;
	}
	public static GameplayDataReport generateGameplayDataReport()
	{
		return new GameplayDataReport(registeredCities);
	}
	public static boolean changeGalaxyEcoValue(String name, int amount)
	{
		if(name == "titanium")
		{
			if(currentTitanium+amount<0)
				return false;
			currentTitanium +=amount;
			notifyObservers();
			return true;
		}
		else if(name == "petroleum")
		{
			if(currentPetroleum+amount<0)
				return false;
			currentPetroleum +=amount;
			notifyObservers();
			return true;
		}
		else if(name == "uranium")
		{
			if(currentUranium+amount<0)
				return false;
			currentUranium +=amount;
			notifyObservers();
			return true;
		}
		else if(name == "gold")
		{
			if(currentGold+amount<0)
				return false;
			currentGold +=amount;
			notifyObservers();
			return true;
		}
		return false;
	}
	public static void resetEcoValues()
	{
		currentGold=0;
		currentTitanium = 0;
		currentPetroleum = 0;
		currentUranium=0;
		notifyObservers();
	}
	public static int getCurrentTitanium()
	{
		return currentTitanium;
	}
	public static int getCurrentPetroleum()
	{
		return currentPetroleum;
	}
	public static int getCurrentUranium()
	{
		return currentUranium;
	}
	public static int getCurrentGold()
	{
		return currentGold;
	}
	public static void nextTurn()
	{
		SoundEffectManager.playNextTurnSound();
		turnNumber = getTurnNumber() + 1;
		endedSieges.clear();
		for (City city : registeredCities)
		{
			city.resetBuildAndResearchCapability();		
		}
		GameplayDataReport report = generateGameplayDataReport();
		changeGalaxyEcoValue("titanium", report.titaniumGain);
		changeGalaxyEcoValue("petroleum", report.petroleumGain);
		changeGalaxyEcoValue("uranium", report.uraniumGain);
		changeGalaxyEcoValue("gold", report.goldGain);
		for(int i = activeSieges.size(); i>0; i--)
		{
			activeSieges.get(i-1).doSingleTick();
		}
		enemy.takeTurnAction();
		updateCaptureStates();
		notifyObservers();
		
	}
	public static void updateCaptureStates()
	{
		//unlocking stars to capture based on captured stars
		for (GalaxyRegion region : galaxyRegions)
		{
			if(region.getStarSystems()!=null)
			{
			int unlockedStarSystems=0;
			for(StarSystem system : region.getStarSystems())
			{
				if (system.getCaptureState()==CaptureState.CAPTURED || system.getCaptureState()==CaptureState.CITY)
				{
					unlockedStarSystems++;
				}
			}
			for(StarSystem system : region.getStarSystems())
			{
				if(unlockedStarSystems>= system.getStarsToUnlock() && system.getCaptureState()==CaptureState.NOT_AVAILABLE)
				{
					system.setCaptureState(CaptureState.AVAILABLE, null);
				}
			}
			if (region.getStarSystems().size()==unlockedStarSystems)
			{
				region.setCaptureState(RegionCaptureState.CAPTURED);
			}
		}
		}
		//unlock region based on captured regions
		int unlockedRegions = 0;
		for (GalaxyRegion region1 : galaxyRegions)
		{
			if(region1.getCaptureState()==RegionCaptureState.CAPTURED) {
				unlockedRegions++;
			}
		}
		for (GalaxyRegion region : galaxyRegions)
		{		
			if(region.getCapturedRegionsToUnlock()<=unlockedRegions && region.getCaptureState()==RegionCaptureState.NOT_AVAILABLE)
			{
				region.setCaptureState(RegionCaptureState.AVAILABLE);
			}
		}
		notifyObservers();
	}
	public static void addNewSiege(City city, int power) {
		activeSieges.add(new Siege(city, power));	
	}
	public static int getTurnNumber() {
		return turnNumber;
	}
	public static void setTurnNumber(Integer valueOf) {
		turnNumber =valueOf;
	}
	public static void resetGalaxyRegions()
	{
		for(GalaxyRegion region : galaxyRegions)
		{
			region.setCaptureState(RegionCaptureState.NOT_AVAILABLE);
		}
	}
	public static ArrayList<GalaxyRegion> getGalaxyRegions() {
		return galaxyRegions;
	}
	public static void setGalaxyRegions(ArrayList<GalaxyRegion> galaxyRegions) {
		GameSession.galaxyRegions = galaxyRegions;
	}
	public static ArrayList<Siege> getActiveSieges() {
		return activeSieges;
	}
	public static void setActiveSieges(ArrayList<Siege> activeSieges) {
		GameSession.activeSieges = activeSieges;
	}
	public static ArrayList<Siege> getEndedSieges() {
		return endedSieges;
	}
	public static void setEndedSieges(ArrayList<Siege> endedSieges) {
		GameSession.endedSieges = endedSieges;
	}
	public static SupportOfficer getAnna() {
		return anna;
	}
	public static void setAnna(SupportOfficer anna) {
		GameSession.anna = anna;
	}
	public static SupportOfficer getKaite() {
		return kaite;
	}
	public static void setKaite(SupportOfficer kaite) {
		GameSession.kaite = kaite;
	}
	public static SupportOfficer getZack() {
		return zack;
	}
	public static void setZack(SupportOfficer zack) {
		GameSession.zack = zack;
	}
	public static SupportOfficer getSimon() {
		return simon;
	}
	public static void setSimon(SupportOfficer simon) {
		GameSession.simon = simon;
	}
	public static Boolean getIsPlayerCyborg() {
		return isPlayerCyborg;
	}
	public static void setIsPlayerCyborg(Boolean isPlayerCyborg) {
		GameSession.isPlayerCyborg = isPlayerCyborg;
	}
	public static AlienEnemy getEnemy() {
		return enemy;
	}
	public static void setEnemy(AlienEnemy enemy) {
		GameSession.enemy = enemy;
	}
	public static void setRegisteredCities(ArrayList<City> registeredCities) {
		GameSession.registeredCities = registeredCities;
	}
	public static void setCurrentTitanium(int currentTitanium) {
		GameSession.currentTitanium = currentTitanium;
	}
	public static void setCurrentPetroleum(int currentPetroleum) {
		GameSession.currentPetroleum = currentPetroleum;
	}
	public static void setCurrentUranium(int currentUranium) {
		GameSession.currentUranium = currentUranium;
	}
	public static void setCurrentGold(int currentGold) {
		GameSession.currentGold = currentGold;
	}
	public static void setTurnNumber(int turnNumber) {
		GameSession.turnNumber = turnNumber;
	}
	
}
