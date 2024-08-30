package pl.zabel.lank.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;
import pl.zabel.lank.gameplayobjects.missionview.DialogueSequence;

public class JsonHandler {

	public static ArrayList<StarSystem> loadStarSystemsForRegion(String name) {
		try {
			ArrayList<StarSystem> output = new ArrayList<StarSystem>();
			BufferedReader reader = new BufferedReader(
					new FileReader(Gdx.files.internal("data/galaxyregions/" + name + ".json").file().getAbsolutePath()
							.replace("lwjgl3", "core/assets")));
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			JsonArray starSystems = (JsonArray) parser.get("starSystems");
			for (Object entry : starSystems) {
				JsonObject temp = (JsonObject) entry;
				String starName = (String) temp.get("name");
				BigDecimal a = (BigDecimal) temp.get("resourcePotential");
				int resourcePotential = a.intValue();
				a = (BigDecimal) temp.get("xPos");
				int xPos = a.intValue();
				a = (BigDecimal) temp.get("yPos");
				int yPos = a.intValue();
				a = (BigDecimal) temp.get("starsToUnlock");
				int starsToUnlock = a.intValue();
				output.add(new StarSystem(starName, resourcePotential, xPos, yPos, starsToUnlock, name));
			}
			reader.close();
			return output;
		}

		catch (IOException | JsonException ex) {
			// ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<DialogueSequence> loadDialogsForMission(String name) {
		try {
			ArrayList<DialogueSequence> output = new ArrayList<DialogueSequence>();
			BufferedReader reader = new BufferedReader(
					new FileReader(Gdx.files.internal("data/missions/" + name.toLowerCase() + ".json").file().getAbsolutePath()
							.replace("lwjgl3", "core/assets")));
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			JsonArray dialogueSequences;
			dialogueSequences = (JsonArray) parser.get("startDialogueSequences");
			if(dialogueSequences==null)
			{
				if(GameSession.getIsPlayerCyborg()==true)
				{
					dialogueSequences = (JsonArray) parser.get("startDialogueSequencesA");
				}
				else
				{
					dialogueSequences = (JsonArray) parser.get("startDialogueSequencesB");
				}
			}
			for (Object entry : dialogueSequences) {
				JsonObject temp = (JsonObject) entry;
				String character = (String) temp.get("character");
				String text = (String) temp.get("text");
				output.add(new DialogueSequence(character, text));
			}
			reader.close();
			return output;
		} catch (IOException | JsonException ex) {
			// ex.printStackTrace();
			return null;
		}
	}
	public static ArrayList<DialogueSequence> loadWinDialogsForMission(String name) {
		try { 
			ArrayList<DialogueSequence> output = new ArrayList<DialogueSequence>();
			BufferedReader reader = new BufferedReader(
					new FileReader(Gdx.files.internal("data/missions/" + name.toLowerCase() + ".json").file().getAbsolutePath()
							.replace("lwjgl3", "core/assets")));
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			JsonArray dialogueSequences;
			dialogueSequences = (JsonArray) parser.get("winDialogueSequences");
			if(dialogueSequences==null)
			{
				if(GameSession.getIsPlayerCyborg()==true)
				{
					dialogueSequences = (JsonArray) parser.get("winDialogueSequencesA");
				}
				else
				{
					dialogueSequences = (JsonArray) parser.get("winDialogueSequencesB");
				}
			}
			for (Object entry : dialogueSequences) {
				JsonObject temp = (JsonObject) entry;
				String character = (String) temp.get("character");
				String text = (String) temp.get("text");
				output.add(new DialogueSequence(character, text));
			}
			reader.close();
			return output;
		} catch (IOException | JsonException ex) {
			// ex.printStackTrace();
			return null;
		}
	}
	public static ArrayList<DialogueSequence> loadLoseDialogsForMission(String name) {
		try {
			ArrayList<DialogueSequence> output = new ArrayList<DialogueSequence>();
			BufferedReader reader = new BufferedReader(
					new FileReader(Gdx.files.internal("data/missions/" + name.toLowerCase() + ".json").file().getAbsolutePath()
							.replace("lwjgl3", "core/assets")));
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			JsonArray dialogueSequences = (JsonArray) parser.get("loseDialogueSequences");
			for (Object entry : dialogueSequences) {
				JsonObject temp = (JsonObject) entry;
				String character = (String) temp.get("character");
				String text = (String) temp.get("text");
				output.add(new DialogueSequence(character, text));
			}
			reader.close();
			return output;
		} catch (IOException | JsonException ex) {
			// ex.printStackTrace();
			return null;
		}
	}
	public static ArrayList<Integer> loadSpawnPointsForMap(String name) {
		try {
			ArrayList<Integer> output = new ArrayList<Integer>();
			BufferedReader reader = new BufferedReader(
					new FileReader(Gdx.files.internal("data/maps/" + name.toLowerCase() + ".json").file().getAbsolutePath()
							.replace("lwjgl3", "core/assets")));
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			int temp1 = ((BigDecimal) parser.get("playerStartX")).intValue();
			int temp2 = ((BigDecimal) parser.get("playerStartY")).intValue();
			int temp3 = ((BigDecimal) parser.get("enemyStartX")).intValue();
			int temp4 = ((BigDecimal) parser.get("enemyStartY")).intValue();
			output.add(temp1);
			output.add(temp2);
			output.add(temp3);
			output.add(temp4);
			reader.close();
			return output;
			
		} catch (IOException | JsonException ex) {
			// ex.printStackTrace();
			return null;
		}
	}
		public static HashMap<String, String> loadStrings() {
			try {
				HashMap<String, String> output = new HashMap<String, String>();
				BufferedReader reader = new BufferedReader(
						new FileReader(Gdx.files.internal("data/lang/pl.json").file().getAbsolutePath()
								.replace("lwjgl3", "core/assets")));
				JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
				JsonArray strings;
				strings = (JsonArray) parser.get("string");
				for (Object entry : strings) {
					JsonObject temp = (JsonObject) entry;
					String key = (String) temp.get("key");
					String value = (String) temp.get("value"); 
					output.put(key, value);
				}
				reader.close();
				return output;
				
			} catch (IOException | JsonException ex) {
				//ex.printStackTrace();
				return null;
			}
	}
}
