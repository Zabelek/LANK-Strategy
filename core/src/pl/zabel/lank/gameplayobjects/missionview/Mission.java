package pl.zabel.lank.gameplayobjects.missionview;

import java.util.ArrayList;

import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;
import pl.zabel.lank.gameplayobjects.mapview.GameplayMap;
import pl.zabel.lank.utilities.JsonHandler;

public class Mission {
	
	private StarSystem starSystem;
	private GameplayMap gameplayMap;
	private String missionName;
	private ArrayList<DialogueSequence> dialogueSequences, winDialogueSequences, loseDialogueSequences;
	
	public Mission(StarSystem starSystem, String missionName)
	{
		this.starSystem = starSystem;
		this.missionName = missionName;
		this.gameplayMap = new GameplayMap(starSystem.getName());
		this.dialogueSequences = JsonHandler.loadDialogsForMission(this.missionName);
		this.winDialogueSequences = JsonHandler.loadWinDialogsForMission(this.missionName);
		this.loseDialogueSequences = JsonHandler.loadLoseDialogsForMission(this.missionName);
		this.gameplayMap.loadSpawnPoints(JsonHandler.loadSpawnPointsForMap(this.getMissionName()));
	}
	public Mission(StarSystem starSystem)
	{
		this.starSystem = starSystem;
		this.gameplayMap = new GameplayMap(starSystem.getName());
		this.missionName = "training";
		this.dialogueSequences = JsonHandler.loadDialogsForMission(this.missionName);
		this.winDialogueSequences = JsonHandler.loadWinDialogsForMission(this.missionName);
		this.loseDialogueSequences = JsonHandler.loadLoseDialogsForMission(this.missionName);
		this.gameplayMap.loadSpawnPoints(JsonHandler.loadSpawnPointsForMap(this.starSystem.getName()));
		
	}

	public StarSystem getStarSystem() {
		return starSystem;
	}

	public GameplayMap getGameplayMap() {
		return gameplayMap;
	}

	public String getMissionName() {
		return missionName;
	}

	public ArrayList<DialogueSequence> getDialogueSequences() {
		return dialogueSequences;
	}
	public ArrayList<DialogueSequence> getWinDialogueSequences() {
		return winDialogueSequences;
	}
	public ArrayList<DialogueSequence> getLoseDialogueSequences() {
		return loseDialogueSequences;
	}

}
